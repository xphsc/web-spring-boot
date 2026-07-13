package cn.xphsc.web.boot.sensitive.resolver;

import cn.xphsc.web.sensitive.annotation.SensitiveRestore;
import cn.xphsc.web.sensitive.context.SensitiveRestoreContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SensitiveRestoreGet ArgumentResolver
 * @since 2.0.4
 */
public class SensitiveRestoreGetArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SensitiveRestore.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        SensitiveRestore restoreAnnotation = parameter.getParameterAnnotation(SensitiveRestore.class);
        if (restoreAnnotation == null) {
            return null;
        }

        String paramName = getParameterName(parameter);
        String paramValue = null;
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
            String requestParamName = requestParam.value().isEmpty() ?
                    (requestParam.name().isEmpty() ? paramName : requestParam.name()) : requestParam.value();
            paramValue = webRequest.getParameter(requestParamName);
        }
        else if (parameter.hasParameterAnnotation(PathVariable.class)) {
            PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
            String pathVariableName = pathVariable.value().isEmpty() ?
                    (pathVariable.name().isEmpty() ? paramName : pathVariable.name()) : pathVariable.value();
            paramValue = (String) webRequest.getAttribute(
                    "org.springframework.web.servlet.HandlerMapping.uriTemplateVariables",
                    NativeWebRequest.SCOPE_REQUEST);
            if (paramValue != null) {
            }
        }
        // 默认处理
        else {
            paramValue = webRequest.getParameter(paramName);
        }
        if (paramValue != null && !paramValue.isEmpty()) {
            String originalValue = SensitiveRestoreContext.getOriginalValue(paramName, paramValue);
            if (originalValue != null) {
                return convertParameterValue(originalValue, parameter.getParameterType());
            }
            return convertParameterValue(paramValue, parameter.getParameterType());
        }

        return convertParameterValue(paramValue, parameter.getParameterType());
    }

    private String getParameterName(MethodParameter parameter) {
        String paramName = parameter.getParameterName();
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
            if (!requestParam.value().isEmpty()) {
                return requestParam.value();
            }
            if (!requestParam.name().isEmpty()) {
                return requestParam.name();
            }
        }
        if (parameter.hasParameterAnnotation(PathVariable.class)) {
            PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
            if (!pathVariable.value().isEmpty()) {
                return pathVariable.value();
            }
            if (!pathVariable.name().isEmpty()) {
                return pathVariable.name();
            }
        }

        return paramName;
    }

    private Object convertParameterValue(String value, Class<?> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType == String.class) {
            return value;
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.valueOf(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.valueOf(value);
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.valueOf(value);
        }
        return value;
    }
}
