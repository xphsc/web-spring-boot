package cn.xphsc.web.log.builder;

import cn.xphsc.web.log.annotation.LogField;
import cn.xphsc.web.log.annotation.LogTransMapping;
import cn.xphsc.web.log.annotation.SysOperationLog;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link SysOperationLog}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SysOperationLogBuilder {

    private Method method;
    public  SysOperationLogBuilder(Method method){
        this.method=method;

    }

    public SysOperationLog getSysOperationLog() {
        return  AnnotationUtils.getAnnotation(method,SysOperationLog.class);
    }

    public String getDescription() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String description=null;
        if(sysOperationLog!=null){
            description=sysOperationLog.description();
        }
        return description;
    }


    public String getModuleName() {
        SysOperationLog sysOperationLog=getSysOperationLog();
        String moduleName=null;
        if(sysOperationLog!=null){
            moduleName=sysOperationLog.name();
        }

        return moduleName;
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
