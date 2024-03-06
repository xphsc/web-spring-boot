### 简介* 日志脱敏是常见的安全需求  内置了常见的脱敏方式 支持 logback  等常见的日志脱敏插件。 正则匹配，。
## 




## 使用详情
指定 logback.xml 配置
~~~
<encoder class=cn.xphsc.web.logger.logback.sensitive.SensitivePatternLayoutEncoder>
在 配置需要脱敏的Key idcard,idNo,phone 等 <sensitiveData>
<sensitiveData> 
~~~


例子
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>
    <springProperty scope="context" name="springAppName" source="spring.application.name" />
    <conversionRule conversionWord="msg" converterClass="com.vrv.vap.log.sensitive.SensitiveMessageConverter"/>
    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="cn.xphsc.web.logger.logback.sensitive.SensitivePatternLayoutEncoder">
         <!--   <pattern>%d{ISO8601}  %p %t %c{0}.%M - %msg%n%xException</pattern>-->
            <sensitiveEnable>true</sensitiveEnable>
            <sensitiveData>idcard,idNo,phone,email,idNumber,password,key,KEY,pwd</sensitiveData>
            <mdcKeys>sessionId</mdcKeys>
            <depth>128</depth>
            <maxLength>2048</maxLength>
            <charset>UTF-8</charset>
        </encoder>
       <!-- <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%d{ISO8601}  %p %t %c{0}.%M - %msg%n%xException</pattern>
        </layout>-->
    </appender>
    <appender name="RollingFile"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOGS}/${springAppName}.log</file>
        <!--<encoder
                class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{ISO8601}  %p %t %c{0}.%M - %msg%n%xException</pattern>
        </encoder>-->
        <encoder class="cn.xphsc.web.logger.logback.sensitive.SensitivePatternLayoutEncoder">
            <sensitiveEnable>true</sensitiveEnable>
            <sensitiveData>idcard,idNo,phone,,email,idNumber,password,key,KEY,pwd,sessionId</sensitiveData>
            <mdcKeys>sessionId</mdcKeys>
            <depth>128</depth>
            <maxLength>2048</maxLength>
            <charset>UTF-8</charset>
        </encoder>
       <!-- <sensitiveData>idcard,idNo,idNumber</sensitiveData>-->
        <rollingPolicy
                class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- rollover daily and when the file reaches 10 MegaBytes -->
            <fileNamePattern>${LOGS}/${springAppName}-%d{yyyy-MM-dd}.%i.log
            </fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy
                    class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
    </appender>

    <logger name="com">
        <level value="INFO" />
    </logger>

    <logger name="org">
        <level value="INFO" />
    </logger>

    <root level="INFO">

        <appender-ref ref="stdout" />
       <!-- <appender-ref ref="SensitiveAppender"/>-->
        <appender-ref ref="RollingFile"/>
    </root>
</configuration>
```