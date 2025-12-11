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
package cn.xphsc.web.log.entity;

import java.util.Date;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class OperationLog {
    /**
     * 用户标识
     */
    private String user;
    /**
     * 操作人标识
     */
    private String  Operator;


    private String moduleName;
    /**
     * 名称
     */

    private String name;
    /**
     * 操作描述
     */

    private String description;
    /**
     * 业务标识
     */
    private String bizNo;
    /**
     * 扩展
     */
    private String  extra;
    /**
     * 请求(ip)
     */
    private String requestIp;
    /**
     *  操作类型 0 登录 1查询 2 新增 3 修改 4 删除
     */
    private String type;
    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 操作时间
     */
    private Date requestTime;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求方法名
     */
    private String methodName;
    /**
     * 请求bean名称
     */
    private String beanName;
    /**
     * 操作结果 成功是1，失败是0
     */
    private int responseResult;
    /**
     *  扩展字段
     */
    private String extendFields;


    /**
     * 终端信息
     */
    private String userAgent;
    /**
     * 内容
     */
    private String  content;

    /**
     * 失败消息
     */
    private String  failMessage;


    public OperationLog(){}

    public static Builder builder(){
        return  new Builder();
    }
    private OperationLog(Builder builder){
        this.Operator=builder.Operator;
        this.name=builder.name;
        this.user=builder.user;
        this.requestIp=builder.requestIp;
        this.type=builder.type;
        this.moduleName=builder.moduleName;
        this.description=builder.description;
        this.bizNo=builder.bizNo;
        this.extra=builder.extra;
        this.requestUrl=builder.requestUrl;
        this.requestTime=builder.requestTime;
        this.requestMethod=builder.requestMethod;
        this.methodName=builder.methodName;
        this.beanName=builder.beanName;
        this.responseResult=builder.responseResult;
        this.extendFields=builder.extendFields;
        this.userAgent=builder.userAgent;
        this.content=builder.content;
        this.failMessage=builder.failMessage;
    }
    public static class Builder {
        private String  Operator;
        private String name;

        private String user;
        private String requestIp;
        private String type;
        private String moduleName;
        private String description;
        private String bizNo;

        private String  extra;
        private String requestUrl;
        private Date requestTime;
        private String requestMethod;
        private String methodName;
        private String beanName;
        private int responseResult;
        private String extendFields;
        private String userAgent;
        private String content;
        private String  failMessage;
        public Builder(){
        }
        public Builder Operator(String Operator) {
            this.Operator=Operator;
            return this;
        }
        public Builder name(String name) {
            this.name=name;
            return this;
        }
        public Builder user(String user) {
            this.user=user;
            return this;
        }
        public Builder requestIp(String requestIp) {
            this.requestIp=requestIp;
            return this;
        }
        public Builder type(String type) {
            this.type=type;
            return this;
        }
        public Builder moduleName(String moduleName) {
            this.moduleName=moduleName;
            return this;
        }
        public Builder description(String description) {
            this.description=description;
            return this;
        }

        public Builder bizNo(String bizNo) {
            this.bizNo=bizNo;
            return this;
        }

        public Builder extra(String extra) {
            this.extra=extra;
            return this;
        }
        public Builder requestUrl(String requestUrl) {
            this.requestUrl=requestUrl;
            return this;
        }  public Builder requestTime(Date requestTime) {
            this.requestTime=requestTime;
            return this;
        }
        public Builder requestMethod(String requestMethod) {
            this.requestMethod=requestMethod;
            return this;
        }
        public Builder methodName(String methodName) {
            this.methodName=methodName;
            return this;
        }
        public Builder beanName(String beanName) {
            this.beanName=beanName;
            return this;
        }
        public Builder responseResult(int responseResult) {
            this.responseResult=responseResult;
            return this;
        }


        public Builder extendFields(String extendFields) {
            this.extendFields=extendFields;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent=userAgent;
            return this;
        }

        public Builder content(String content) {
            this.content=content;
            return this;
        }

        public Builder failMessage(String failMessage) {
            this.failMessage=failMessage;
            return this;
        }

        public OperationLog build() {
            return new OperationLog(this);
        }
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public int getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(int responseResult) {
        this.responseResult = responseResult;
    }

    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
}
