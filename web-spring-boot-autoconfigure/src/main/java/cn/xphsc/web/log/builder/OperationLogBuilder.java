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
package cn.xphsc.web.log.builder;

import cn.xphsc.web.boot.log.autoconfigure.OperationLogProperties;
import cn.xphsc.web.common.response.ResultMapper;
import cn.xphsc.web.common.useragent.UserAgent;
import cn.xphsc.web.common.useragent.UserAgentUtil;
import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.log.annotation.LogRecord;
import cn.xphsc.web.log.annotation.SysOperationLog;
import cn.xphsc.web.log.context.OperationLogContext;
import cn.xphsc.web.log.context.OperationLogExpressionEvaluator;
import cn.xphsc.web.log.entity.ExtendFiledEntity;
import cn.xphsc.web.log.entity.OperationLog;
import cn.xphsc.web.log.handler.UserHandler;
import cn.xphsc.web.log.parser.DefaultParameterParser;
import cn.xphsc.web.log.parser.ParameterParser;
import cn.xphsc.web.utils.Collects;
import cn.xphsc.web.utils.IpUtils;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * {@link OperationLog}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class OperationLogBuilder {
    private OperationLogProperties operationLogProperties;
    private ApplicationContext applicationContext;
    private Method method;
    private UserHandler userHandler;
    private Object resultTarget;
    private Object[] args;
    private  Object target;
    private  boolean contentNotNull;
    private  boolean businessIdNotNull;

    private  boolean descriptionNotNull;
    private  boolean extraNotNull;
    private  boolean operatortNull;
    private  String message;
    private SysOperationLog sysOperationLog;
    private LogRecord logRecord;
    public OperationLogBuilder(Annotation annotation,Method method, Object target, Object resultTarget, Object[] args, OperationLogProperties operationLogProperties, ApplicationContext applicationContext, UserHandler userHandler, String message) {
        this.applicationContext = applicationContext;
        this.method = method;
        this.resultTarget = resultTarget;
        this.target=target;
        this.args = args;
        this.operationLogProperties=operationLogProperties;
        this.userHandler = userHandler;
        this.message = message;
        if(annotation instanceof SysOperationLog){
            this.sysOperationLog=(SysOperationLog)annotation;
        }
        if(annotation instanceof LogRecord){
            this.logRecord=(LogRecord)annotation;
        }
    }

    public OperationLog getOperationLog() {
        SysOperationLogBuilder sysOperationLogBuilder = new SysOperationLogBuilder(sysOperationLog);
        LogRecordBuilder logBuilder = new LogRecordBuilder(logRecord);
        Map<String, Object> map= OperationLogContext.getCopyOfContextMap();
        if(Collects.isNotEmpty(map)){
            if(map.get("failVariableValue")!=null){
                this.message= String.valueOf(map.get("failVariableValue"));;
            }
        }
        ArrayList<String> templates = new ArrayList<>();
        if (StringUtils.isNotBlank(sysOperationLogBuilder.getContent())) {
            templates.add(sysOperationLogBuilder.getContent());
            contentNotNull=true;
        }
        Map<String, Object> process=null;
        if (StringUtils.isNotBlank(logBuilder.getContent())) {
            templates.add(logBuilder.getContent());
            if(logBuilder.getContent().contains("#")) {
                process = process(templates, target.getClass(), method, args, resultTarget, null, map);
                contentNotNull = true;
            }
        }


        ArrayList<String> businessIdTemplates = new ArrayList<>();
        if (StringUtils.isNotBlank(sysOperationLogBuilder.getBusinessId())) {
            businessIdTemplates.add(sysOperationLogBuilder.getBusinessId());
            businessIdNotNull=true;
        }
        if (StringUtils.isNotBlank(logBuilder.getBizNo())) {
            businessIdTemplates.add(logBuilder.getBizNo());
            businessIdNotNull=true;
        }
        Map<String, Object> descriptionProcess=null;
        ArrayList<String> descriptionTemplates = new ArrayList<>();
        if (StringUtils.isNotBlank(logBuilder.getDescription())) {
            descriptionTemplates.add(logBuilder.getDescription());
            if(logBuilder.getDescription().contains("#")){
                descriptionNotNull=true;
              descriptionProcess = process(descriptionTemplates, target.getClass(), method, args, resultTarget, null, map);
            }
        }
        if (StringUtils.isNotBlank(sysOperationLogBuilder.getDescription())) {
            descriptionTemplates.add(sysOperationLogBuilder.getDescription());
            if(sysOperationLogBuilder.getDescription().contains("#")){
                descriptionNotNull=true;
                descriptionProcess = process(descriptionTemplates, target.getClass(), method, args, resultTarget, null, map);
            }
        }
        ArrayList<String> extraTemplates = new ArrayList<>();
        if (StringUtils.isNotBlank(sysOperationLogBuilder.getExtra())) {
            extraTemplates.add(sysOperationLogBuilder.getExtra());
            extraNotNull=true;
        }
        if (StringUtils.isNotBlank(logBuilder.getExtra())) {
            extraTemplates.add(logBuilder.getExtra());
            extraNotNull=true;
        }
        ArrayList<String> operatorTemplates = new ArrayList<>();
        if (StringUtils.isNotBlank(sysOperationLogBuilder.getOperator())) {
            operatorTemplates.add(sysOperationLogBuilder.getOperator());
            operatortNull=true;
        }
        if (StringUtils.isNotBlank(logBuilder.getOperator())) {
            operatorTemplates.add(logBuilder.getOperator());
            operatortNull=true;
        }
        Map<String, Object>  extraProcess = process(extraTemplates, target.getClass(), method, args, resultTarget, null, map);
        Map<String, Object>  operatorProcess = process(operatorTemplates, target.getClass(), method, args, resultTarget, null, map);
        Map<String, Object> businessIdProcess = process(businessIdTemplates, target.getClass(), method, args, resultTarget, null, map);
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String methodName = method.getName();
        String beanName = method.getDeclaringClass().getTypeName();
        String description = sysOperationLogBuilder.getDescription()!=null?sysOperationLogBuilder.getDescription():logBuilder.getDescription();
        if (descriptionNotNull) {
            if (Collects.isNotEmpty(descriptionProcess)) {
                for(Map.Entry<String,Object> entry:descriptionProcess.entrySet()){
                    Object value=entry.getValue();
                    description=value.toString();
                }
            }
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        OperationLog operationLog = new OperationLog();
        operationLog.setBeanName(beanName);
        operationLog.setName(sysOperationLogBuilder.getName()!=null?sysOperationLogBuilder.getName():logBuilder.getName());
        operationLog.setModuleName(sysOperationLogBuilder.getModuleName()!=null?sysOperationLogBuilder.getModuleName():logBuilder.getModuleName());
        operationLog.setType(StringUtils.isNotBlank(sysOperationLogBuilder.getActionType())?sysOperationLogBuilder.getActionType():StringUtils.isNotBlank(sysOperationLogBuilder.getType())?sysOperationLogBuilder.getType():logBuilder.getActionType());
        operationLog.setMethodName(methodName);
        operationLog.setRequestUrl(uri);
        UserAgent userAgen=UserAgentUtil.parse(userAgent);
        Map userAg=ResultMapper.builder().mapping("browserName",userAgen.getBrowser().getName())
                .mapping("version",userAgen.getVersion())
                .mapping("platformName",userAgen.getPlatform().getName())
                .mapping("osName", userAgen.getOs().getName())
                .mapping("osVersion",userAgen.getOsVersion())
                .mapping("engineName",userAgen.getEngine().getName())
                .mapping("engineVersion",userAgen.getEngineVersion()).build();
        operationLog.setUserAgent(JacksonUtils.toJSONString(userAg));
        if (this.userHandler != null) {
            operationLog.setUser(this.userHandler.user());
        }
            operationLog.setRequestIp(IpUtils.getIpAddress(request));
            ParameterParser parameterParser = new DefaultParameterParser();
           List<ExtendFiledEntity> list=parameterParser.parameterFiled(parameterNameDiscoverer, method,args);
            operationLog.setExtendFields(JacksonUtils.toJSONString(list));
        int i = 1;
        StringBuilder content=new StringBuilder(list.size());
          String   businessId=null;
        if(Collects.isNotEmpty(list)){
            for(ExtendFiledEntity extendFiledEntity:list){
                Object  value= null;
                if(extendFiledEntity.getFieldDescription()!=null){
                    if(Collects.isNotEmpty(map)){
                        Map<String, Object> ma;
                        if(map.get("originObject")!=null||map.get("putVariable")!=null){
                            if(map.get("putVariable")!=null){
                               ma= JacksonUtils.toJsonObject(JacksonUtils.toJSONString(map.get("putVariable")),Map.class);
                            }else{
                               ma= JacksonUtils.toJsonObject(JacksonUtils.toJSONString(map.get("originObject")),Map.class);
                            }
                            value= ma.get(extendFiledEntity.getFieldName());

                            if(map.get("putVariable")!=null){
                                Class object=  map.get("putVariable").getClass();
                                Field[] declaredFields = object.getDeclaredFields();
                                for (Field field : declaredFields) {
                                    field.setAccessible(true);
                                    LogField logFieldObject= field.getAnnotation(LogField.class);
                                    if(logFieldObject!=null){
                                      Map mapField=  sysOperationLogBuilder.logTransMappingAttribute(logFieldObject);
                                        if(Collects.isNotEmpty(mapField)){
                                            if(mapField.get(extendFiledEntity.getFieldDescription())!=null){
                                                value= mapField.get(extendFiledEntity.getFieldDescription());
                                            }
                                        }
                                    }
                                }
                            }
                            if(value!=null){
                                if(!value.toString().equals(extendFiledEntity.getFieldValue())){
                                    if (i != 1) {
                                        content.append(",");
                                    }
                                    content.append(extendFiledEntity.getFieldDescription() + ":" + value + this.operationLogProperties.getContrastSeparator() +  extendFiledEntity.getFieldValue());
                                    i++;
                                }
                            }
                        }else if(contentNotNull) {
                            if(process.get("#"+extendFiledEntity.getFieldName())!=null){
                                value=process.get("#"+extendFiledEntity.getFieldName());

                                if(value!=null){
                                    if(!value.toString().equals(extendFiledEntity.getFieldValue())){
                                        if (i != 1) {
                                            content.append(",");
                                        }
                                        content.append(extendFiledEntity.getFieldDescription() + ":" +value+this.operationLogProperties.getContrastSeparator()+ extendFiledEntity.getFieldValue());
                                        i++;
                                    }
                                }
                            }
                    } } else{
                          if (i != 1) {
                              content.append(",");
                          }
                          content.append(extendFiledEntity.getFieldDescription() + ":" );
                          content.append(extendFiledEntity.getDesensitizationValue()!=null?extendFiledEntity.getDesensitizationValue(): extendFiledEntity.getFieldValue());
                          i++;
                    }
                }
            }

        }else {
            if (contentNotNull) {
                if (Collects.isNotEmpty(process)) {
                    for(Map.Entry<String,Object> entry:process.entrySet()){
                        Object value=entry.getValue();
                        if(value!=null){
                            if (i != 1) {
                                content.append(",");
                            }
                            content.append(entry.getValue());
                            i++;
                        }
                    }
                }
            }
        }

        if (businessIdNotNull) {
            if (Collects.isNotEmpty(businessIdProcess)) {
                for(Map.Entry<String,Object> entry:businessIdProcess.entrySet()){
                    Object value=entry.getValue();
                    businessId=value.toString();
                }
            }
        }
        operationLog.setBizNo(businessId);
        String extra=null;
        if (extraNotNull) {
            if (Collects.isNotEmpty(extraProcess)) {
                for(Map.Entry<String,Object> entry:extraProcess.entrySet()){
                    Object value=entry.getValue();
                    extra=value.toString();
                }
            }
        }
        String operator=null;
        if (operatortNull) {
            if (Collects.isNotEmpty(operatorProcess)) {
                for(Map.Entry<String,Object> entry:operatorProcess.entrySet()){
                    Object value=entry.getValue();
                    operator=value.toString();
                }
            }
        }
            operationLog.setOperator(operator);
            operationLog.setExtra(extra);
            operationLog.setContent(operationLogProperties.isFillFailContent()?StringUtils.isNotBlank(this.message)?this.message:content.toString():content.toString());operationLog.setFailMessage(this.message);
            operationLog.setRequestTime(new Date(System.currentTimeMillis()));
            operationLog.setRequestMethod(request.getMethod());
            operationLog.setDescription(description);
            Integer responseResult = Objects.isNull(resultTarget) ? 0 : StringUtils.isNotBlank(this.message)?0:1;
            operationLog.setResponseResult(responseResult);
            return operationLog;
        }
    private OperationLogExpressionEvaluator expressionEvaluator = new OperationLogExpressionEvaluator();

    private Map<String, Object> process(Collection<String> templates, Class<?> targetClass, Method method, Object[] args, Object retObj, String errorMsg, Map<String,Object> optContext) {
        Map<String, Object> expressionValues = new HashMap<>(16);
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(targetClass, method, args, retObj, errorMsg, optContext);
        for (String tpl : templates) {
            String keyStr[] = tpl.split("\\,");
            for (String str : keyStr) {
                AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
                String value = expressionEvaluator.parseExpression(evaluationContext, annotatedElementKey, str);
              if (tpl == null || tpl.isEmpty()) {
                  expressionValues.put(str, value);
                  continue;
              }
                try {
                    expressionValues.put(str, value);
                } catch (Exception e) {
                    expressionValues .put(str, str);
                }
            }
        }
        return expressionValues;
    }



}