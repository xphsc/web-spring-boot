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
package cn.xphsc.web.boot.validation.advice;


import cn.xphsc.web.boot.validation.ValidationProperties;
import cn.xphsc.web.boot.validation.exception.ParamValidException;
import cn.xphsc.web.common.response.ResultMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import javax.validation.ConstraintViolationException;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@RestControllerAdvice
@EnableConfigurationProperties({ValidationProperties.class})
public class ValidationControllerAdvice implements Ordered {
    private  ValidationProperties  validationProperties;
    public ValidationControllerAdvice(ValidationProperties validationProperties){
      this.validationProperties=validationProperties;
    }
    @Override
    public int getOrder() {
        return validationProperties.getOrder();
    }

    @ExceptionHandler(ParamValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object paramValidExceptionHandler(ParamValidException ex) {
        return ResultMapper.builder().mapping("status", validationProperties.getStatus())
                .mapping("data", ex.getFieldErrors())
                .mapping("code", validationProperties.getCode())
                .mapping("message", validationProperties.getMessage()).build();
    }

    @ExceptionHandler(BindException.class)
    public Object bindExceptionHandler(BindException ex) {
        return paramValidExceptionHandler(new ParamValidException(ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolationExceptionHandler(ConstraintViolationException ex, HandlerMethod handlerMethod) {
        return paramValidExceptionHandler(new ParamValidException(ex, handlerMethod.getMethodParameters()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object constraintViolationExceptionHandler(MethodArgumentNotValidException ex) {
        return paramValidExceptionHandler(new ParamValidException(ex));
    }



}