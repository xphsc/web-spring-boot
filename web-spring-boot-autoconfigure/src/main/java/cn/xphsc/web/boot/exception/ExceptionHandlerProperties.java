package cn.xphsc.web.boot.exception;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;

import static cn.xphsc.web.common.WebBeanTemplate.CESTOMEXCRPTION_PREFIX;
import static cn.xphsc.web.common.WebBeanTemplate.CESTOMEXCRPTION_PREFIX_ORDER;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = CESTOMEXCRPTION_PREFIX)
public class ExceptionHandlerProperties {
   private int order=CESTOMEXCRPTION_PREFIX_ORDER;
   private boolean  outputErrorLog=true;
    /**
     * 通用Exception
     */
    private String  message=HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    /**
     * 自定义异常类
     */
    private String[] exceptionClassName;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isOutputErrorLog() {
        return outputErrorLog;
    }

    public void setOutputErrorLog(boolean outputErrorLog) {
        this.outputErrorLog = outputErrorLog;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String[] exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }
}
