package cn.xphsc.web.common.exception;


/**
 * {@link RuntimeException}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  时间转化运行时异常
 * @since 1.1.6
 */
public class ParseDateException extends ParseRuntimeException {

    public ParseDateException() {
    }

    public ParseDateException(int errorOffset) {
        super(errorOffset);
    }

    public ParseDateException(String message) {
        super(message);
    }

    public ParseDateException(String message, int errorOffset) {
        super(message, errorOffset);
    }

}
