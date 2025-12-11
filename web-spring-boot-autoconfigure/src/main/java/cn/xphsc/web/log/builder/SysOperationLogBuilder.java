package cn.xphsc.web.log.builder;

import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.log.annotation.LogTransMapping;
import cn.xphsc.web.log.annotation.SysOperationLog;
import cn.xphsc.web.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link SysOperationLog}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SysOperationLogBuilder {

    private SysOperationLog sysOperationLog;
    public  SysOperationLogBuilder(SysOperationLog sysOperationLog){
        this.sysOperationLog=sysOperationLog;

    }

    public SysOperationLog getSysOperationLog() {
        return  sysOperationLog;
    }
    public String getOperator() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String operator=null;
        if(sysOperationLog!=null){
            operator=sysOperationLog.operator();
        }
        return operator;
    }
    public String getDescription() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String description=null;
        if(sysOperationLog!=null){
            description=sysOperationLog.description();
        }
        return description;
    }

    public String getName() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String name=null;
        if(sysOperationLog!=null){
            name=sysOperationLog.name();
        }

        return name;
    }
    public String getModuleName() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String moduleName=null;
        if(sysOperationLog!=null){
            moduleName=sysOperationLog.moduleName();
        }

        return moduleName;
    }
    public String getExtra() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String extra=null;
        if(sysOperationLog!=null){
            extra=sysOperationLog.extra();
        }

        return extra;
    }
    public String getBusinessId() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String businessId=null;
        if(sysOperationLog!=null){
            businessId=sysOperationLog.bizNo();
        }

        return businessId;
    }
    public String getType() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String type=null;
        if(sysOperationLog!=null){
            type=sysOperationLog.type();
        }

        return type;
    }
    public String getActionType() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String actionType=null;
        if(sysOperationLog!=null){
            if(sysOperationLog.actionType()!=null){
                actionType=sysOperationLog.actionType().getName();
            }

        }
        return actionType;
    }

    public String getContent() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String content=null;
        if(sysOperationLog!=null){
            content=sysOperationLog.content();
        }
        return content;
    }
    protected Map  logTransMappingAttribute(LogField logField){
    Map<String,String> logTransMappingAttribute=new HashMap();
        if(logField.transMapping()!=null&&StringUtils.isNotBlank(logField.description())){
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
