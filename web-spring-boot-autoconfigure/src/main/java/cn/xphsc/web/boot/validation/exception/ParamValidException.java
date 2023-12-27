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
package cn.xphsc.web.boot.validation.exception;


import cn.xphsc.web.boot.validation.entity.FieldError;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ParamValidException extends Exception {
    Logger log = LoggerFactory.getLogger(getClass());

    private List<FieldError> fieldErrors = new ArrayList<FieldError>();

    /**
     *
     */
    private static final long serialVersionUID = -716441870779206738L;

    @Override
    public String getMessage() {
        return fieldErrors.toString();
    }

    public ParamValidException(ConstraintViolationException violationException, MethodParameter[] methodParameters) {
        List<FieldError> errors = new ArrayList<>();
        for(ConstraintViolation<?> constraintViolation:violationException.getConstraintViolations()){
            PathImpl pathImpl = (PathImpl)constraintViolation.getPropertyPath();
            int paramIndex = pathImpl.getLeafNode().getParameterIndex();
              String paramName = methodParameters[paramIndex].getParameterName();
                FieldError error = new FieldError();
                error.setName(paramName);
                error.setMessage(constraintViolation.getMessage());
                error.setValue(constraintViolation.getInvalidValue());
                errors.add(error);

       }
       this.fieldErrors = errors;

    }
    public ParamValidException(MethodArgumentNotValidException m) {
        List<FieldError> errors =new ArrayList();
        for(org.springframework.validation.FieldError fieldError:m.getBindingResult().getFieldErrors()){
            FieldError error = new FieldError();
                error.setObjectName(fieldError.getObjectName());
                error.setName(fieldError.getField());
                error.setMessage(fieldError.getDefaultMessage());
                error.setValue(fieldError.getRejectedValue());
                errors.add(error);
            }

        this.fieldErrors = errors;

    }


    public ParamValidException(List<FieldError> errors) {
        this.fieldErrors = errors;
    }

    public ParamValidException(BindException ex) {
        this.fieldErrors = bindExceptionToFieldError(ex);
    }

    private List<FieldError> bindExceptionToFieldError(BindException ex) {
        List<FieldError> FieldErrorlist= new ArrayList();
        List<org.springframework.validation.FieldError> flist=ex.getFieldErrors();
        for(org.springframework.validation.FieldError f:flist){
            FieldError error = new FieldError();
            error.setObjectName(f.getObjectName());
            error.setName(f.getField());
            error.setMessage(f.getDefaultMessage());
            error.setValue(f.getRejectedValue());
            FieldErrorlist.add(error);
        }
     return FieldErrorlist;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
