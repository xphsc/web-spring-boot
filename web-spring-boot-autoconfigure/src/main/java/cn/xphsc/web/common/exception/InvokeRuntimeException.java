package cn.xphsc.web.common.exception;


/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  反射调用异常
 * @since 1.1.7
 */
public class InvokeRuntimeException extends RuntimeException {

    public InvokeRuntimeException() {
    }

    public InvokeRuntimeException(String message) {
        super(message);
    }

    public InvokeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeRuntimeException(Throwable cause) {
        super(cause);
    }

}
