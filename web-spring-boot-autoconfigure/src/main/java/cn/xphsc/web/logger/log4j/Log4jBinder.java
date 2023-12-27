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
package cn.xphsc.web.logger.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import java.util.Properties;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Log4jBinder {
    private Properties properties = new Properties();


    public static final String DEFAULT_PATTERN = "[%p] %d{yyyy-MM-dd HH\\:mm\\:ss SSS} [%t] | %C.%M(%L) : %m %n";
    public static final String DEFAULT_DATEPATTERN = "'.'yyyy-MM-dd";

    public static final String DEFAULT_CONSOLE_APPENDER_NAME = "console";
    public static final String DEFAULT_STDOUT_APPENDER_NAME = "stdout";
    public static final String DEFAULT_ERROR_APPENDER_NAME = "error";

    /** common */
    public static final String ROOT_CATEGORY = "log4j.rootCategory";
    public static final String ROOT_LOGGER = "log4j.rootLogger";
    public static final String APPENDER = "log4j.appender.%s";
    public static final String LOGGER = "log4j.logger.%s";

    /** layout */
    public static final String LAYOUT = "log4j.appender.%s.layout";
    public static final String LAYOUT_CONVERSIONPATTERN = "log4j.appender.%s.layout.ConversionPattern";

    /** file log */
    public static final String FILE = "log4j.appender.%s.File";
    public static final String APPEND = "log4j.appender.%s.Append";
    public static final String THRESHOLD = "log4j.appender.%s.Threshold";
    public static final String DATEPATTERN = "log4j.appender.%s.DatePattern";

    /** Appender */
    public static final String CONSOLE_APPENDER = "org.apache.log4j.ConsoleAppender";
    public static final String DAILY_ROLLING_FILE_APPENDER = "org.apache.log4j.DailyRollingFileAppender";
    public static final String FILE_APPENDER = "org.apache.log4j.FileAppender";
    public static final String ROLLING_FILE_APPENDER = "org.apache.log4j.RollingFileAppender";
    public static final String WRITER_APPENDER = "org.apache.log4j.WriterAppender";

    /** Layout */
    public static final String PATTERN_LAYOUT = "org.apache.log4j.PatternLayout";
    public static final String HTML_LAYOUT = "org.apache.log4j.HTMLLayout";
    public static final String SIMPLE_LAYOUT = "org.apache.log4j.SimpleLayout";
    public static final String TTCC_LAYOUT = "org.apache.log4j.TTCCLayout";



    public static Log4jBinder create() {
        return new Log4jBinder();
    }

    public void bind() {
        PropertyConfigurator.configure(properties);
    }

    public Log4jBinder setRootCategory(Level level, String... appenders) {
        StringBuilder value = new StringBuilder(level.toString());
        for (String appender : appenders) {
            value.append(",").append(appender);
        }

        properties.setProperty(ROOT_CATEGORY, value.toString());
        return this;
    }

    public Log4jBinder setRootLogger(Level level, String... appenders) {
        StringBuilder value = new StringBuilder(level.toString());
        for (String appender : appenders) {
            value.append(",").append(appender);
        }

        properties.setProperty(ROOT_LOGGER, value.toString());
        return this;
    }

    public Log4jBinder setLogger(Level level, String logger, String... appenders) {
        StringBuilder value = new StringBuilder(level.toString());
        for (String appender : appenders) {
            value.append(",").append(appender);
        }

        properties.setProperty(String.format(LOGGER, logger), value.toString());
        return this;
    }

    public Log4jBinder setDailyRollingFileAppender(String appender) {
        properties.setProperty(String.format(APPENDER, appender), DAILY_ROLLING_FILE_APPENDER);
        return this;
    }

    public Log4jBinder setFileAppender(String appender) {
        properties.setProperty(String.format(APPENDER, appender), FILE_APPENDER);
        return this;
    }

    public Log4jBinder setRollingFileAppender(String appender) {
        properties.setProperty(String.format(APPENDER, appender), ROLLING_FILE_APPENDER);
        return this;
    }

    public Log4jBinder setConsoleAppender(String appender) {
        properties.setProperty(String.format(APPENDER, appender), CONSOLE_APPENDER);
        return this;
    }

    public Log4jBinder setWriterAppender(String appender) {
        properties.setProperty(String.format(APPENDER, appender), WRITER_APPENDER);
        return this;
    }

    public Log4jBinder setAppender(String appender, String appenderClass) {
        properties.setProperty(String.format(APPENDER, appender), appenderClass);
        return this;
    }

    public Log4jBinder setAppender(String appender, Class appenderClass) {
        properties.setProperty(String.format(APPENDER, appender), appenderClass.getName());
        return this;
    }

    public Log4jBinder setPatternLayout(String appender) {
        properties.setProperty(String.format(LAYOUT, appender), PATTERN_LAYOUT);
        return this;
    }

    public Log4jBinder setHtmlLayout(String appender) {
        properties.setProperty(String.format(LAYOUT, appender), HTML_LAYOUT);
        return this;
    }

    public Log4jBinder setSimpleLayout(String appender) {
        properties.setProperty(String.format(LAYOUT, appender), SIMPLE_LAYOUT);
        return this;
    }

    public Log4jBinder setTTCCLayout(String appender) {
        properties.setProperty(String.format(LAYOUT, appender), TTCC_LAYOUT);
        return this;
    }

    public Log4jBinder setPatternLayout(String appender, String layoutClass) {
        properties.setProperty(String.format(LAYOUT, appender), layoutClass);
        return this;
    }

    public Log4jBinder setPatternLayout(String appender, Class layoutClass) {
        properties.setProperty(String.format(LAYOUT, appender), layoutClass.getName());
        return this;
    }

    public Log4jBinder setConversionPattern(String appender) {
        properties.setProperty(String.format(LAYOUT_CONVERSIONPATTERN, appender), DEFAULT_PATTERN);
        return this;
    }

    public Log4jBinder setConversionPattern(String appender, String pattern) {
        properties.setProperty(String.format(LAYOUT_CONVERSIONPATTERN, appender), pattern);
        return this;
    }

    public Log4jBinder setFile(String appender, String path) {
        properties.setProperty(String.format(FILE, appender), path);
        return this;
    }

    public Log4jBinder setDatePattern(String appender) {
        properties.setProperty(String.format(DATEPATTERN, appender), DEFAULT_DATEPATTERN);
        return this;
    }

    public Log4jBinder setDatePattern(String appender, String pattern) {
        properties.setProperty(String.format(DATEPATTERN, appender), pattern);
        return this;
    }

    public Log4jBinder setThreshold(String appender, String level) {
        properties.setProperty(String.format(THRESHOLD, appender), level);
        return this;
    }

    public Log4jBinder setThreshold(String appender, Level level) {
        properties.setProperty(String.format(THRESHOLD, appender), level.toString());
        return this;
    }

    public Log4jBinder setAppend(String appender, String value) {
        properties.setProperty(String.format(APPEND, appender), Boolean.valueOf(value).toString());
        return this;
    }

    public Log4jBinder setAppend(String appender, Boolean value) {
        properties.setProperty(String.format(APPEND, appender), value.toString());
        return this;
    }

    public Log4jBinder addConsoleAppender() {
        return addConsoleAppender(DEFAULT_CONSOLE_APPENDER_NAME);
    }

    public Log4jBinder addConsoleAppender(String appender) {
        return setConsoleAppender(appender)
                .setPatternLayout(appender)
                .setConversionPattern(appender);
    }

    public Log4jBinder addStdoutAppender(String path) {
        return addStdoutAppender(DEFAULT_STDOUT_APPENDER_NAME, path);
    }

    public Log4jBinder addStdoutAppender(String appender, String path) {
        return setDailyRollingFileAppender(appender)
                .setFile(appender, path)
                .setDatePattern(appender)
                .setThreshold(appender, Level.INFO)
                .setAppend(appender, true)
                .setPatternLayout(appender)
                .setConversionPattern(appender);
    }

    public Log4jBinder addErrorAppender(String path) {
        return addStdoutAppender(DEFAULT_ERROR_APPENDER_NAME, path);
    }

    public Log4jBinder addErrorAppender(String appender, String path) {
        return setDailyRollingFileAppender(appender)
                .setFile(appender, path)
                .setDatePattern(appender)
                .setThreshold(appender, Level.ERROR)
                .setAppend(appender, true)
                .setPatternLayout(appender)
                .setConversionPattern(appender);
    }
}
