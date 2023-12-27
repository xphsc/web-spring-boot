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
import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.common.exception.ApiException;
import cn.xphsc.web.common.exception.BusinessException;
import cn.xphsc.web.common.exception.CustomException;
import cn.xphsc.web.common.exception.ServiceException;
import cn.xphsc.web.common.response.ResultMapper;
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

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        result=ResultMapper.builder().mapping("code",HttpStatus.NOT_FOUND.value())
                .mapping("message",HttpStatus.NOT_FOUND.getReasonPhrase()).build();
        outPutErrorException(ex);
        return result;
    }

    /**
     * HttpServerErrorException.
     * http状态码为503
     * @param ex HttpServerErrorException
     * @return ModelAndView
     */
    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object handleHttpServerException(HttpServerErrorException ex) {
        result=ResultMapper.builder().mapping("code", HttpStatus.SERVICE_UNAVAILABLE.value())
                .mapping("message", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()).build();
        outPutErrorException(ex);
        return result;
    }



    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex) {
        if(ex.getMessage()!=null){
            if(ex.getMessage().contains(String.valueOf(ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode()))){
                result=  ResultMapper.builder().mapping("code",ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode())
                        .mapping("message", ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getName()).build();
            } else if(ex.getMessage().contains(String.valueOf(ExceptionEnum.XSS_EXCEPTION.getCode()))){
                result=  ResultMapper.builder().mapping("code",ExceptionEnum.XSS_EXCEPTION.getCode())
                        .mapping("message", ExceptionEnum.XSS_EXCEPTION.getName()).build();
            }
            else {
                result=ResultMapper.builder().mapping("code", HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .mapping("message", exceptionHandlerProperties.getMessage()).build();

            }


        }
        outPutErrorException(ex);
        return result;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        result=ResultMapper.builder().mapping("code",HttpStatus.BAD_REQUEST.value())
                .mapping("message", HttpStatus.BAD_REQUEST.getReasonPhrase()).build();
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleTypeMismatchException(
            TypeMismatchException ex) {
        result=ResultMapper.builder().mapping("code", HttpStatus.BAD_REQUEST.value())
                .mapping("message", HttpStatus.BAD_REQUEST.getReasonPhrase()).build();
        outPutErrorException(ex);
        return result;

    }

    @ExceptionHandler(ApiException.class)
    public Object apiAxception(ApiException ex){
        result=ResultMapper.builder().mapping("code",ex.getCode())
                .mapping("message",ex.getMessage()).build();
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    public Object serviceException(ServiceException ex){
        result=ResultMapper.builder().mapping("code",ex.getCode())
                .mapping("message",ex.getMessage()).build();
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    public Object businessException(BusinessException ex){
        result=ResultMapper.builder().mapping("code",ex.getCode())
                .mapping("message",ex.getMessage()).build();
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(CustomException.class)
    public Object customException(CustomException ex){
        result=ResultMapper.builder().mapping("code",ex.getCode())
                .mapping("message",ex.getMessage()).build();
        outPutErrorException(ex);
        return result;
    }

    @ExceptionHandler(SQLException.class)
    public Object sqlException(SQLException ex){
        result=ResultMapper.builder().mapping("code",ExceptionEnum.SQL_EXCEPTION.getCode())
                .mapping("message",ExceptionEnum.SQL_EXCEPTION.getCode()).build();
        outPutErrorException(ex);
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

    public Map getResult() {
        return result;
    }
}
