/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.logger.log4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.status.StatusLogger;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Custom plugin (implementing log desensitization based on keyword matching)
 * @since 2.0.1
 */
@Plugin(name = "log4jSensitivePatternlaces", category = "Core", printObject = true)
public class Log4jSensitivePatternReplaces {
    private static final Logger LOGGER = StatusLogger.getLogger();

    // replace标签，复用log4j已有plugin， replaces 下可以0，1，多个replace
    private final RegexReplacement[] replaces;

    private String keys;
    private Log4jSensitivePatternReplaces(RegexReplacement[] replaces, final String keys) {
        this.keys=keys;
        this.replaces = replaces;
    }

    /**
     * 格式化输出日志信息， 此方法会执行关键字匹配与替换
     */
    public String format(String msg) {
        String message =SensitivePatternBuilder.format(this.keys,msg);
        return message;
    }

    /**
     * 实现pluginFactory， 用于生成pugin
     */
    @PluginFactory
    public static Log4jSensitivePatternReplaces createRegexReplacement(
            @PluginElement("replace") final RegexReplacement[] replaces, @PluginAttribute("keys") final String keys) {
        if (replaces == null) {
            LOGGER.warn("have the replace , but no replace is set");
            return null;
        }
        if (keys == null) {
            LOGGER.error("The json keys  is required");
            return null;
        }
        return new Log4jSensitivePatternReplaces(replaces,keys);
    }

}
