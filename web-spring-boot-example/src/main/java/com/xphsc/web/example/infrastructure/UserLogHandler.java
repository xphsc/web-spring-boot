package com.xphsc.web.example.infrastructure;

import cn.xphsc.web.log.handler.UserHandler;
import org.springframework.stereotype.Component;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Component
public class UserLogHandler implements UserHandler {
    @Override
    public String user() {
        return "123098";
    }
}
