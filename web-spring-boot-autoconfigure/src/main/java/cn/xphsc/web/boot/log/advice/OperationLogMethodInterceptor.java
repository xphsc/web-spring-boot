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
package cn.xphsc.web.boot.log.advice;




import cn.xphsc.web.boot.log.autoconfigure.OperationLogProperties;
import cn.xphsc.web.common.bean.BeanFor;
import cn.xphsc.web.common.collect.Lists;
import cn.xphsc.web.common.exception.ApiException;
import cn.xphsc.web.common.exception.BusinessException;
import cn.xphsc.web.common.exception.CustomException;
import cn.xphsc.web.common.exception.ServiceException;
import cn.xphsc.web.common.response.Response;
import cn.xphsc.web.log.annotation.LogIgnore;
import cn.xphsc.web.log.annotation.LogRecord;
import cn.xphsc.web.log.annotation.SysOperationLog;
import cn.xphsc.web.log.builder.OperationLogBuilder;
import cn.xphsc.web.log.context.OperationLogContext;
import cn.xphsc.web.log.event.OperationLogEvent;
import cn.xphsc.web.log.handler.UserHandler;
import cn.xphsc.web.utils.CollectionUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * {@link MethodInterceptor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Core interceptor of operation log
 * @since 1.0.0
 */
public class OperationLogMethodInterceptor implements MethodInterceptor{

    private final static Logger logger = LoggerFactory.getLogger(OperationLogMethodInterceptor.class);

    private  ApplicationContext applicationContext;
    private UserHandler userHandler;
    private  OperationLogProperties operationLogProperties;
    private Class<? extends Annotation> annotationClass;
    public OperationLogMethodInterceptor(ApplicationContext applicationContext, OperationLogProperties operationLogProperties,Class<? extends Annotation> annotationClass){
       this.applicationContext=applicationContext;
       this.operationLogProperties=operationLogProperties;
       this.annotationClass=annotationClass;
       if(containsBean(UserHandler.class)){
           this.userHandler=applicationContext.getBean(UserHandler.class);
       }
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation){
        Object result=null;
        String message=null;
            try {
                result=methodInvocation.proceed();
            }catch (Throwable throwable){
                try {
                    message=throwable.getLocalizedMessage();
                    publishOperationLog(methodInvocation,result,message);
                    return exceptionResult(throwable);
                }catch (CustomException e){
                    e.printStackTrace();
                }
            }
            if(logger.isDebugEnabled()){
                if (logIgnoreAnnotation(methodInvocation.getMethod(),methodInvocation.getThis())) {
                    return result;
                }
            }
        publishOperationLog(methodInvocation,result,message);
        return result;
    }

    private static boolean logIgnoreAnnotation(Method method,Object target) {
        LogIgnore logIgnore = method.getDeclaredAnnotation(LogIgnore.class);
        LogIgnore ignoreClass =target.getClass().getAnnotation(LogIgnore.class);
        if (ignoreClass != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("类：" + target.getClass().getName() + ":标记为忽略,不记录日志");
            }
            return true;
        } else if (logIgnore != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("方法名" + method.getName() + ":标记为忽略,不记录日志");
            }
            return true;
        }
        return false;
    }


    private  boolean containsBean(Class<?> beanType) {
        try {
            applicationContext.getBean(beanType);
            return true;
        } catch (NoUniqueBeanDefinitionException e) {
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }
      private Response exceptionResult(Throwable throwable){
          if(throwable instanceof BusinessException){
              BusinessException businessException= (BusinessException) throwable;
              return Response.fail(businessException.getCode(),businessException.getMessage());
          }
          if(throwable instanceof CustomException){
              CustomException customException= (CustomException) throwable;
              return Response.fail(customException.getCode(),customException.getMessage());
          }
          if(throwable instanceof ServiceException){
              ServiceException serviceException= (ServiceException) throwable;
              return Response.fail(serviceException.getCode(),serviceException.getMessage());
          }
          if(throwable instanceof ApiException){
              ApiException apiException= (ApiException) throwable;
              return Response.fail(apiException.getCode(),apiException.getMessage());
          }
          if(CollectionUtils.isNotEmpty(exceptionClass())){
              for(Class<? extends Exception>  cl:exceptionClass()){
                  if(throwable.getClass().equals(cl)){
                      Map map= BeanFor.beanOf(throwable);
                      if(map.get("code")!=null){
                          return Response.fail((Integer) map.get("code"),throwable.getMessage());
                      }else{
                          return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),throwable.getMessage());
                      }
                  }
              }
          }
           return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),throwable.getMessage());
      }

       private List<Class<? extends Exception>> exceptionClass() {
        List<Class<? extends Exception>> list= Lists.newArrayList(2);
        try {
            for(String exceptionClassName: operationLogProperties.getExceptionClassName()){
                list.add((Class<? extends Exception>) Class.forName(exceptionClassName));
            }
            return list;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void publishOperationLog(MethodInvocation methodInvocation,Object result, String message){
        Annotation annotation= AnnotationUtils.getAnnotation(methodInvocation.getMethod(),annotationClass);
        if(annotation instanceof LogRecord.LogRecords){
            LogRecord.LogRecords loggerRecordannotations= (LogRecord.LogRecords) annotation;
            LogRecord[]  logRecords = loggerRecordannotations.value();
         for(LogRecord logRecord:logRecords){
             publishEvent(logRecord,methodInvocation, result,  message);
        }
        }else{
            publishEvent(annotation,methodInvocation, result,  message);
        }
        OperationLogContext.clean();
      }

      private void publishEvent(Annotation annotation,MethodInvocation methodInvocation,Object result, String message){
          OperationLogBuilder operationLogBuilder = new OperationLogBuilder(annotation,methodInvocation.getMethod(),methodInvocation.getThis(),result,methodInvocation.getArguments(), operationLogProperties,applicationContext, userHandler,message);
          OperationLogEvent event = new OperationLogEvent("object",operationLogBuilder.getOperationLog());
          if(this.operationLogProperties.isAsync()){
              TaskExecutor operationLogExecutor = new SimpleAsyncTaskExecutor("operationLogExecutor-");
              operationLogExecutor.execute(() -> {
                  applicationContext.publishEvent(event);
              });
          }else{
              applicationContext.publishEvent(event);
          }
      }
}
