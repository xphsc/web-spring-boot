package cn.xphsc.web.common.exception;


/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  IO运行时异常
 * @since 1.1.6
 */
public class IORuntimeException extends RuntimeException {

    public IORuntimeException() {
    }

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IORuntimeException(Throwable cause) {
        super(cause);
    }

}
