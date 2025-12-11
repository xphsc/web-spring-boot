package cn.xphsc.web.common.lang.trace;
import cn.xphsc.web.common.lang.id.UUID;
import org.slf4j.MDC;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  trace  id tools
 * @since 2.0.4
 */
public class Traces {
    public static String HTTP_HEADER_TRACE_ID = "X-Request-Id";

    public static String LOG_TRACE_ID = "x-trace-id";


    public static String computeIfAbsent() {
        String traceId = getTraceId();
        return isNotBlank(traceId) ? traceId : uuid();
    }


    public static void setTraceIdIfAbsent() {
        if (isBlank(getTraceId())) {
            setTraceId();
        }
    }

    public static void setTraceId() {
        setTraceId(uuid());
    }

    public static String getTraceId() {
        return MDC.get(LOG_TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        MDC.put(LOG_TRACE_ID, traceId);
    }

    public static void removeTraceId() {
        MDC.remove(LOG_TRACE_ID);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static void setLogTraceKey(String traceKey) {
        LOG_TRACE_ID = traceKey;
    }

    public static void setHeadTraceKey(String traceKey) {
        HTTP_HEADER_TRACE_ID = traceKey;
    }
}
