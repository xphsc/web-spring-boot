### 简介* 日志脱敏是常见的安全需求  内置了常见的脱敏方式 支持 logback  等常见的日志脱敏插件。 正则匹配，。
## 




## logback.xml使用详情
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
	<conversionRule conversionWord="msg" converterClass="cn.xphsc.web.logger.logback.sensitive.SensitiveMessageConverter"/>
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
## log4j使用详情
~~~
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，你会看到log4j2内部各种详细输出-->
<!--monitorInterval：Log4j能够自动检测修改配置 文件和重新配置本身，设置间隔秒数-->
<Configuration status="INFO" monitorInterval="240">
    <Properties>
        <Property name="filePath">./logs</Property>
        <Property name="commonPattern">%highlight{【%X{reqId}】【%X{remoteAddr}】[%d{yyyy.MM.dd HH:mm:ss,sss z}] [%p] [%t]
            [%l] :%m%n}
        </Property>
    </Properties>
    <!--先定义所有的输出源-->
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <!--&lt;!&ndash;控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）&ndash;&gt;-->
            <ThresholdFilter level="INFO"
                             onMatch="ACCEPT"
                             onMismatch="DENY"/>
            <log4jSensitivePatternLayout pattern="${commonPattern}" header="${header}">
                <log4jSensitivePatternReplaces>
                    <replace/>
                    <keys>idcard,certificateNo</keys>
                </log4jSensitivePatternReplaces>
            </log4jSensitivePatternLayout>
        </Console>

        <!-- 这个会打印出所有的info及以下级别的信息，每次时间超过1天、大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="INFOLOG"
                     fileName="${filePath}/info.log"
                     filePattern="${filePath}/archive/%d{yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
                <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
            </Filters>
            <log4jSensitivePatternLayout pattern="${commonPattern}" header="${header}">
                <log4jSensitivePatternReplaces>
                    <replace/>
                    <keys>certificateNo,certificate,idcard</keys>
                </log4jSensitivePatternReplaces>
            </log4jSensitivePatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="99999"></DefaultRolloverStrategy>
        </RollingFile>


        <RollingFile name="ERRORLOG"
                     fileName="${filePath}/error.log"
                     filePattern="${filePath}/archive/%d{yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log.gz">
            <Filters>
                <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
                <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>

            <log4jSensitivePatternLayout pattern="${commonPattern}" header="${header}">
                <log4jSensitivePatternReplaces>
                    <replace/>
                    <keys>certificateNo,certificate,cardtelno,idcard</keys>
                </log4jSensitivePatternReplaces>
            </log4jSensitivePatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="99999"></DefaultRolloverStrategy>
        </RollingFile>

    </Appenders>
    <Loggers>
        <Root level="INFO">
            <appender-ref ref="Console"/>
            <appender-ref ref="INFOLOG"/>
            <appender-ref ref="ERRORLOG"/>
            <appender-ref ref="ALLLOG"/>
        </Root>
    </Loggers>
</Configuration>

~~~