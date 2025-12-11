/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.log.builder;

import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.log.annotation.LogRecord;
import cn.xphsc.web.log.annotation.LogTransMapping;
import cn.xphsc.web.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  log  Record
 * @since 2.0.3
 */
public class LogRecordBuilder {

    private LogRecord logRecord;
    public  LogRecordBuilder(LogRecord logRecord){
        this.logRecord=logRecord;

    }

    public LogRecord getLogRecord() {
        return logRecord;
    }

    public String getOperator() {
        LogRecord sysOperationLog=getLogRecord();
        String operator=null;
        if(sysOperationLog!=null){
            operator=sysOperationLog.operator();
        }
        return operator;
    }
    public String getDescription() {
        LogRecord sysOperationLog=getLogRecord();
        String description=null;
        if(sysOperationLog!=null){
            description=sysOperationLog.description();
        }
        return description;
    }

    public String getName() {
        LogRecord sysOperationLog=getLogRecord();
        String name=null;
        if(sysOperationLog!=null){
            name=sysOperationLog.name();
        }

        return name;
    }
    public String getModuleName() {
        LogRecord sysOperationLog=getLogRecord();
        String moduleName=null;
        if(sysOperationLog!=null){
            moduleName=sysOperationLog.moduleName();
        }

        return moduleName;
    }

    public String getBizNo() {
        LogRecord sysOperationLog=getLogRecord();
        String businessId=null;
        if(sysOperationLog!=null){
            businessId=sysOperationLog.bizNo();
        }

        return businessId;
    }
    public String getExtra() {
        LogRecord sysOperationLog=getLogRecord();
        String extra=null;
        if(sysOperationLog!=null){
            extra=sysOperationLog.extra();
        }

        return extra;
    }

    public String getActionType() {
        LogRecord sysOperationLog=getLogRecord();
        String actionType=null;
        if(sysOperationLog!=null){
            if(sysOperationLog.actionType()!=null){
                actionType=sysOperationLog.actionType();
            }

        }
        return actionType;
    }

    public String getContent() {
        LogRecord sysOperationLog=getLogRecord();
        String content=null;
        if(sysOperationLog!=null){
            content=sysOperationLog.content();
        }
        return content;
    }
    protected Map logTransMappingAttribute(LogField logField){
        Map<String,String> logTransMappingAttribute=new HashMap();
        if(logField.transMapping()!=null&& StringUtils.isNotBlank(logField.description())){
            LogTransMapping[] logTransMappings= logField.transMapping();
            for(LogTransMapping logTransMapping:logTransMappings){
                if(StringUtils.isNotBlank(logTransMapping.key())&&StringUtils.isNotBlank(logTransMapping.value())){
                    logTransMappingAttribute.put(logField.description(),logTransMapping.value());
                }
            }
        }
        return logTransMappingAttribute;
    }

}
