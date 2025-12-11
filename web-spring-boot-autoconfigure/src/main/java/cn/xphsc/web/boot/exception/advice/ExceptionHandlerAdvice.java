/*
 * Copyright (c) 2021 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.boot.exception.advice;

import cn.xphsc.web.boot.exception.ExceptionHandlerProperties;
import cn.xphsc.web.common.bean.BeanFor;
import cn.xphsc.web.common.collect.Lists;
import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.common.exception.ApiException;
import cn.xphsc.web.common.exception.BusinessException;
import cn.xphsc.web.common.exception.CustomException;
import cn.xphsc.web.common.exception.ServiceException;
import cn.xphsc.web.common.response.ResultMapper;
import cn.xphsc.web.utils.CollectionUtils;
import cn.xphsc.web.utils.ContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 公共异常通知
 * @since 1.0.0
 */
@RestControllerAdvice
@EnableConfigurationProperties({ExceptionHandlerProperties.class})
public class ExceptionHandlerAdvice implements Ordered  {

    private ExceptionHandlerProperties exceptionHandlerProperties;

    public ExceptionHandlerAdvice(ExceptionHandlerProperties exceptionHandlerProperties){
        this.exceptionHandlerProperties=exceptionHandlerProperties;
    }


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public int getOrder() {
        return exceptionHandlerProperties.getOrder();
    }
     private  Map result = null;
    public List<Class<? extends Exception>> exceptionClass() {
        List<Class<? extends Exception>> list= Lists.newArrayList(2);
        try {
            for(String exceptionClassName: exceptionHandlerProperties.getExceptionClassName()){
                list.add((Class<? extends Exception>) Class.forName(exceptionClassName));
            }
          return list;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        handleCommonException(ex, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
        return result;
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object handleHttpServerException(HttpServerErrorException ex) {
        handleCommonException(ex, HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex) {
        if (ex.getMessage() != null) {
            if (ex.getMessage().contains(String.valueOf(ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode()))) {
                handleCommonException(ex, ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode(), ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getName());
            } else if (ex.getMessage().contains(String.valueOf(ExceptionEnum.XSS_EXCEPTION.getCode()))) {
                handleCommonException(ex, ExceptionEnum.XSS_EXCEPTION.getCode(), ExceptionEnum.XSS_EXCEPTION.getName());
            } else {
                handleExceptionBasedOnClass(ex);
            }
        }
        return result;
    }

    private void handleExceptionBasedOnClass(Exception ex) {
        if (CollectionUtils.isNotEmpty(exceptionClass())) {
            for (Class<? extends Exception> cl : exceptionClass()) {
                if (ex.getClass().equals(cl)) {
                    Map<String, Object> map = BeanFor.beanOf(ex);
                    result = map.containsKey("code")
                            ? ResultMapper.builder().mapping("code", map.get("code"))
                            .mapping("message", ex.getMessage())
                            .mapping("timestamp", System.currentTimeMillis())
                            .build()
                            : ResultMapper.builder().mapping("code", HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .mapping("message", ex.getMessage())
                            .mapping("timestamp", System.currentTimeMillis()).build();
                }
            }
        } else {
            result = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionHandlerProperties.getMessage());
        }
        outPutErrorException(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        handleCommonException(ex, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return result;
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleTypeMismatchException(TypeMismatchException ex) {
        handleCommonException(ex, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return result;
    }

    @ExceptionHandler(ApiException.class)
    public Object apiException(ApiException ex) {
        result = buildErrorResponse(ex.getCode(), ex.getMessage());
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    public Object serviceException(ServiceException ex) {
        result = buildErrorResponse(ex.getCode(), ex.getMessage());
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    public Object businessException(BusinessException ex) {
        result = buildErrorResponse(ex.getCode(), ex.getMessage());
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(CustomException.class)
    public Object customException(CustomException ex) {
        result = buildErrorResponse(ex.getCode(), ex.getMessage());
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(SQLException.class)
    public Object sqlException(SQLException ex) {
        handleCommonException(ex, ExceptionEnum.SQL_EXCEPTION.getCode(), ExceptionEnum.SQL_EXCEPTION.getName());
        return result;
    }
    private void outPutErrorException(Exception ex){
        if(exceptionHandlerProperties.isOutputErrorLog()){
            String message = String.format(" url [%s] 出现异常，异常摘要：%s",
                    ContextHolderUtil.getRequest().getRequestURI(),
                    ex.getMessage());
            logger.error(message,ex);
        }
}
    // 处理通用异常逻辑
    private void handleCommonException(Exception ex, int code, String message) {
        result = buildErrorResponse(code, message);
        outPutErrorException(ex);
    }
    // 通用构建错误响应的方法
    private Map<String, Object> buildErrorResponse(int code, String message) {
        return ResultMapper.builder()
                .mapping("code", code)
                .mapping("message", message)
                .mapping("timestamp", System.currentTimeMillis())
                .build();
    }
    public Map getResult() {
        return result;
    }
}
