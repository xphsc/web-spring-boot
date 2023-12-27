package com.xphsc.web.example.infrastructure;


import cn.xphsc.web.log.entity.OperationLog;
import cn.xphsc.web.log.event.OperationLogEvent;
import cn.xphsc.web.utils.JacksonUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * {@link ApplicationListener}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@Component
public class OperationLogEventListener implements ApplicationListener<OperationLogEvent> {
    @Override
    public void onApplicationEvent(OperationLogEvent event) {
        OperationLog eventType = event.getOperationLog();
        System.out.println("---event----"+ JacksonUtils.toJSONString(eventType));
    }


}
