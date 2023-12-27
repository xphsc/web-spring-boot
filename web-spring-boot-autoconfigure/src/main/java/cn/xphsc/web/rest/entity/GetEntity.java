/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.rest.entity;



import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class GetEntity {

    /**
     * 请求URL
     */
    private  String url;
    /**
     *  请求头参数 参数类型为map or HttpHeaders
     */
    private Object headers;
    /**
     * responseType 返回对象类型
     */
    private Class<?> responseType;
    /**
     * URL中的变量，按顺序依次对应 变量类型为map or object数组
     */
    private Object uriVariables;
    /**
     * 请求参数
     */
    private  Map<String, ?> parameters;

    public static GetEntity.Builder builder(){
        return  new GetEntity.Builder();
    }
    private GetEntity(GetEntity.Builder builder){
        this.url=builder.url;
        this.headers=builder.headers;
        this.responseType=builder.responseType;
        this.uriVariables=builder.uriVariables;
        this.parameters=builder.parameters;
    }
    public static class Builder {
        private  String url;
        private Object headers;
        private Class<?> responseType;
        private Object uriVariables;
        private  Map<String, ?> parameters;
        public Builder(){
        }

        public GetEntity.Builder url(String url) {
            this.url=url;
            return this;
        }
        public GetEntity.Builder headers(Object headers) {
            this.headers=headers;
            return this;
        }
        public GetEntity.Builder responseType(Class<?> responseType) {
            this.responseType=responseType;
            return this;
        }
        public GetEntity.Builder uriVariables(Object uriVariables) {
            this.uriVariables=uriVariables;
            return this;
        }
        public GetEntity.Builder parameters( Map<String, ?> parameters) {
            this.parameters=parameters;
            return this;
        }
        public GetEntity build() {
            return new GetEntity(this);
        }
    }

    public String url() {
        return url;
    }

    public Object headers() {
        return headers;
    }

    public Object responseType() {
        return responseType;
    }

    public Object uriVariables() {
        return uriVariables;
    }


    public Map<String, ?> parameters() {
        return parameters;
    }
}
