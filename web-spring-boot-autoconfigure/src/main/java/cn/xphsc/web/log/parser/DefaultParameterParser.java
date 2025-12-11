package cn.xphsc.web.log.parser;

import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.log.annotation.LogTransMapping;
import cn.xphsc.web.log.entity.ExtendFiledEntity;
import cn.xphsc.web.sensitive.utils.SensitiveUtils;
import cn.xphsc.web.utils.ArrayUtils;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.ObjectUtils;
import cn.xphsc.web.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Parameter 解析器
 * @since 1.0.0
 */
public class DefaultParameterParser implements ParameterParser {

    private final static Logger logger = LoggerFactory.getLogger(DefaultParameterParser.class);

    @Override
    public List<ExtendFiledEntity> parameterFiled(ParameterNameDiscoverer parameterNameDiscoverer, Method method,Object[] args) {
        ParameterNameDiscoverer pnd=new DefaultParameterNameDiscoverer();
        String[] parameterNames = pnd.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        List<ExtendFiledEntity> extendFiledList = new ArrayList(parameters.length);
        ExtendFiledEntity extendFiledEntity = null;
        if(ArrayUtils.isNotEmpty(parameters)) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> paramClazz = parameter.getType();
                Object arg = args[i];
                String parameterName = parameterNames[i];
                LogField logField = null;
                if (isPrimite(parameter.getType())) {
                    logField = parameter.getAnnotation(LogField.class);
                    ;

                    List<Map> mapList = getParamkeyValue(parameterName, arg);
                    if (!mapList.isEmpty()) {
                        for (Map<String, Object> map : mapList) {
                            for (Map.Entry<String, Object> entity : map.entrySet()) {
                                if (logField != null) {
                                    extendFiledEntity = new ExtendFiledEntity();
                                    if (StringUtils.isNotBlank(logField.name())) {
                                        extendFiledEntity.setFieldName(logField.name());
                                    } else {
                                        extendFiledEntity.setFieldName(entity.getKey());
                                    }
                                    if (logField.description() != null) {
                                        extendFiledEntity.setFieldDescription(logField.description());

                                        if (logField.desensitization()) {
                                            if (entity.getValue() != null) {
                                                extendFiledEntity.setDesensitizationValue(SensitiveUtils.desensitization(String.valueOf(entity.getValue())));
                                            }
                                        } else {
                                            if (ObjectUtils.isNotEmpty(logTransMappingAttribute(logField, entity.getValue()))) {
                                                extendFiledEntity.setFieldValue(logTransMappingAttribute(logField, entity.getValue()));
                                            } else {
                                                extendFiledEntity.setFieldValue(entity.getValue());
                                            }

                                        }

                                    }
                                    extendFiledList.add(extendFiledEntity);

                                }


                            }
                        }
                    }
                    continue;
                }
                if (parameter.getType().isAssignableFrom(HttpServletRequest.class) || parameter.getType().isAssignableFrom(HttpSession.class) ||
                        parameter.getType().isAssignableFrom(HttpServletResponse.class) /*|| parameter.getAnnotation(RequestBody.class) == null*/) {
                    continue;
                }


                Field[] declaredFields = paramClazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    LogField logFieldObject = field.getAnnotation(LogField.class);
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(arg);
                        if (fieldValue != null) {
                            if (logFieldObject != null) {
                                extendFiledEntity = new ExtendFiledEntity();
                                if (StringUtils.isNotBlank(logFieldObject.name())) {
                                    extendFiledEntity.setFieldName(logFieldObject.name());
                                } else {
                                    extendFiledEntity.setFieldName(field.getName());
                                }
                                if (StringUtils.isNotBlank(logFieldObject.description())) {
                                    extendFiledEntity.setFieldDescription(logFieldObject.description());
                                    if (logFieldObject.desensitization()) {
                                        if (fieldValue != null) {
                                            extendFiledEntity.setDesensitizationValue(SensitiveUtils.desensitization(String.valueOf(fieldValue)));
                                        }
                                    } else {
                                        if (ObjectUtils.isNotEmpty(logTransMappingAttribute(logFieldObject, fieldValue))) {
                                            extendFiledEntity.setFieldValue(logTransMappingAttribute(logFieldObject, fieldValue));
                                        } else {
                                            extendFiledEntity.setFieldValue(fieldValue);
                                        }
                                    }
                                }
                                extendFiledList.add(extendFiledEntity);
                            }

                        }

                    } catch (IllegalAccessException e) {
                        logger.error("sylog异常处理!!!", e);
                    }
                }
            }
        }
        return extendFiledList;

    }
    private boolean isPrimite(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

    private Object logTransMappingAttribute(LogField logField,Object fieldValue){
        Object logTransMappingAttribute=null;
       if(ArrayUtils.isNotEmpty(logField.transMapping())){
           LogTransMapping[] logTransMappings= logField.transMapping();
         for(LogTransMapping logTransMapping:logTransMappings){
             if(StringUtils.isNotBlank(logTransMapping.key())&&StringUtils.isNotBlank(logTransMapping.value())){
                 if(fieldValue!=null){
                     if(fieldValue.toString().equals(logTransMapping.key())){
                         logTransMappingAttribute=logTransMapping.value();
                 }
                 }
             }
         }
       }
       return logTransMappingAttribute;
    }
    private List<Map> getParamkeyValue(String paramNames, Object arg) {
        Map<String, Object> map = null;
        List list = new ArrayList(10);
        if (arg != null) {
            if (arg instanceof Double
                    || arg instanceof Float
                    || arg instanceof Long
                    || arg instanceof Short
                    || arg instanceof Byte
                    || arg instanceof Boolean
                    || arg instanceof String
                    || arg instanceof Integer
                    ) {
                map = new HashMap<>();
                map.put(paramNames, arg);
                list.add(map);
            } else if (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)) {
                String json = "";
                try {
                    json = JacksonUtils.toJSONString(arg);
                } catch (Exception e) {
                    logger.error("json转化异常", e);
                }
                map = JacksonUtils.toJsonObject(json, Map.class);
                if (map != null && !map.isEmpty()) {
                    map.put(paramNames, JacksonUtils.toJSONString(map));
                    list.add(map);
                }
            } else {
                map = new HashMap<>();
                map.put(paramNames, arg);
            }
        }
        return list;
    }



}
