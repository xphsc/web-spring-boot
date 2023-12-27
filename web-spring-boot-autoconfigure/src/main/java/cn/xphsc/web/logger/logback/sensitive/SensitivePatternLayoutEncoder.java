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
package cn.xphsc.web.logger.logback.sensitive;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

import java.text.MessageFormat;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SensitivePatternLayoutEncoder extends PatternLayoutEncoder {

    protected static final String PATTERN_D1 = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread]";
    protected static final String PATTERN_D2 = " [{0}:%X'{'{1}'}']";
    protected static final String PATTERN_D3_S1 = " [%level] %logger{10}-%L ";
    //0:日志脱敏开关,1:查找深度(超过深度后停止正则匹配),2:日志最大长度,3:待脱敏关键字
    protected static final String PATTERN_D3_S2 = "%msg'{'{0},{1},{2},{3}'}'%n";
    //日志脱敏开关
    protected String sensitiveEnable = "false";
    //待脱敏关键字
    protected String sensitiveData;
    //自定义MDC的key,多个key用逗号分隔。
    protected String mdcKeys;
    //匹配深度(若深度值过大影响性能)
    protected int depth = 12;
    //单条消息的最大长度
    protected int maxLength = 2048;


    @Override
    public void start() {
        if (getPattern() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(PATTERN_D1);

            //拼接MDC参数
            if (mdcKeys != null) {
                String[] keys = mdcKeys.split(",");
                for (String key : keys) {
                    sb.append(MessageFormat.format(PATTERN_D2, key, key));
                }
            }
            sb.append(PATTERN_D3_S1);

            if (maxLength < 0 || maxLength > 10240) {
                maxLength = 2048;
            }
            sb.append(MessageFormat.format(PATTERN_D3_S2, sensitiveEnable, String.valueOf(depth), String.valueOf(maxLength), sensitiveData));
            setPattern(sb.toString());
        }
        super.start();
    }

    public static String getPatternD1() {
        return PATTERN_D1;
    }

    public static String getPatternD2() {
        return PATTERN_D2;
    }

    public static String getPatternD3S1() {
        return PATTERN_D3_S1;
    }

    public static String getPatternD3S2() {
        return PATTERN_D3_S2;
    }

    public String getSensitiveEnable() {
        return sensitiveEnable;
    }

    public void setSensitiveEnable(String sensitiveEnable) {
        this.sensitiveEnable = sensitiveEnable;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    public String getMdcKeys() {
        return mdcKeys;
    }

    public void setMdcKeys(String mdcKeys) {
        this.mdcKeys = mdcKeys;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
