package cn.xphsc.web.common.exception;


/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  解析异常
 * @since 1.1.6
 */
public class ParseRuntimeException extends RuntimeException {

    private int errorCode;

    public ParseRuntimeException() {
    }

    public ParseRuntimeException(int errorCode) {
        this.errorCode = errorCode;
    }

    public ParseRuntimeException(String message) {
        super(message);
    }

    public ParseRuntimeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ParseRuntimeException(Throwable t) {
        super(t);
    }

    public ParseRuntimeException(int errorOffset, Throwable t) {
        super(t);
        this.errorCode = errorCode;
    }

    public ParseRuntimeException(String message, Throwable t) {
        super(message, t);
    }

    public ParseRuntimeException(String message, int errorOffset, Throwable t) {
        super(message, t);
        this.errorCode = errorOffset;
    }

    public int getErrorOffset() {
        return errorCode;
    }

}
