
package cn.xphsc.web.boot.sensitive.advice;

import cn.xphsc.web.sensitive.annotation.SensitiveRestore;
import cn.xphsc.web.sensitive.context.SensitiveRestoreContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SensitiveRestoreRequestBody Advice
 * @since 2.0.4
 */
@ControllerAdvice
public class SensitiveRestoreRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(SensitiveRestore.class) ||
                (methodParameter.getParameterType().isAnnotationPresent(SensitiveRestore.class));
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
            throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        try {
            if (body != null) {
                // 获取方法参数上的注解
                SensitiveRestore restoreAnnotation = parameter.getParameterAnnotation(SensitiveRestore.class);
                Set<String> specifiedFields = new HashSet<>();
                if (restoreAnnotation != null) {
                    specifiedFields.addAll(Arrays.asList(restoreAnnotation.fields()));
                }
                processDtoFields(body, specifiedFields);
            }
            return body;
        } finally {
            SensitiveRestoreContext.clearExpiredMappings();
        }
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /** 递归处理 DTO 字段回填原始值 */
    private void processDtoFields(Object dto, Set<String> specifiedFields) {
        if (dto == null) return;

        Class<?> clazz = dto.getClass();

        ReflectionUtils.doWithFields(clazz, field -> {
            field.setAccessible(true);
            boolean fieldSpecified = !specifiedFields.isEmpty() && specifiedFields.contains(field.getName());
            SensitiveRestore fieldRestore = field.getAnnotation(SensitiveRestore.class);
            // 处理条件：要么指定了字段列表且当前字段在其中，要么字段上有 @SensitiveRestore 注解
            if ((!specifiedFields.isEmpty() && fieldSpecified) || fieldRestore != null) {
                try {
                    Object fieldValue = field.get(dto);

                    if (fieldValue instanceof String || fieldValue == null) {
                        String desensitizedValue = (String) fieldValue;
                        if (desensitizedValue != null && !desensitizedValue.isEmpty()) {
                            // 根据字段名查找原始值
                            String original = SensitiveRestoreContext.getOriginalValue(field.getName(), desensitizedValue);
                            if (original != null) {
                                field.set(dto, original);
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }

            Object value = field.get(dto);
            if (value != null && !isBaseType(field.getType())) {
                processDtoFields(value, specifiedFields);
            }
        });
    }
    /** 判断基本类型 */
    private boolean isBaseType(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == String.class
                || Number.class.isAssignableFrom(clazz)
                || clazz == Boolean.class
                || clazz.isEnum();
    }
}