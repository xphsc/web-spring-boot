/*
 * Copyright (c) 2021 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.logger.logback;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.xphsc.web.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public abstract class BaseMessageConverter extends MessageConverter {
    // 日志脱敏开关
    protected String sensitiveEnable = "false";
    // 匹配深度
    protected  Integer depth = 12;
    //单条消息的最大长度，主要是message
    protected  Integer maxLength = 2048;
    // 日志脱敏关键字
    protected final static Map<String,String> SENSITIVE_KEYWORD= new HashMap<>(10);

    @Override
    public void start() {
        List<String> options = getOptionList();
        if (options != null && options.size() >= 3) {
            sensitiveEnable = String.valueOf(options.get(0));
            depth = Integer.valueOf(options.get(1));
            maxLength = Integer.valueOf(options.get(2));
            if (options.size() > 3){
                for (int i = 3;i < options.size();i++) {
                    String sensitiveData = String.valueOf(options.get(i));
                    if (!StringUtils.isEmpty(sensitiveData)) {
                        String[] sensitiveArray = sensitiveData.split(",");
                        if (sensitiveArray.length ==1) {
                            String keywordType = sensitiveArray[0];
                            SENSITIVE_KEYWORD.put(keywordType,keywordType);
                                }
                            }
                        }
                    }
        }
        super.start();
    }

    @Override
    public String convert(ILoggingEvent event) {
        String oriLogMsg = event.getFormattedMessage();
        if (oriLogMsg == null || oriLogMsg.isEmpty()) {
            return oriLogMsg;
        }
        //如果超长截取
        if (oriLogMsg.length() > maxLength) {
            oriLogMsg = oriLogMsg.substring(0, maxLength) + "<<<";//后面增加三个终止符
        }
        return invokeMsg(oriLogMsg);
    }
    public abstract String invokeMsg(final String oriMsg);

}
