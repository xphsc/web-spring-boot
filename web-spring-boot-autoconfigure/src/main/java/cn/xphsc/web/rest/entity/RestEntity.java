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



import org.springframework.http.HttpEntity;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class RestEntity {
    /**
     * 请求URL
     */
    private  String url;
    /**
     *  请求参数
     */
    private HttpEntity<?> requestEntity;
    /**
     * responseType 返回对象类型
     */
    private Class<?> responseType ;
    /**
     * URL中的变量，按顺序依次对应 变量类型为map or object数组
     */
    private Object uriVariables;


    public static RestEntity.Builder builder(){
        return  new RestEntity.Builder();
    }
    private RestEntity(RestEntity.Builder builder){
        this.url=builder.url;
        this.requestEntity=builder.requestEntity;
        this.responseType=builder.responseType;
        this.uriVariables=builder.uriVariables;
    }
    public static class Builder {
        private  String url;
        private HttpEntity<?> requestEntity;
        private Class<?> responseType;
        private Object uriVariables;
        public Builder(){
        }

        public RestEntity.Builder url(String url) {
            this.url=url;
            return this;
        }
        public RestEntity.Builder requestEntity(HttpEntity<?> requestEntity) {
            this.requestEntity=requestEntity;
            return this;
        }
        public RestEntity.Builder responseType(Class<?> responseType) {
            this.responseType=responseType;
            return this;
        }
        public RestEntity.Builder uriVariables(Object uriVariables) {
            this.uriVariables=uriVariables;
            return this;
        }

        public RestEntity build() {
            return new RestEntity(this);
        }
    }

    public String url() {
        return url;
    }

    public Class<?> responseType() {
        return responseType;
    }

    public Object uriVariables() {
        return uriVariables;
    }

    public HttpEntity<?> requestEntity() {
        return requestEntity;
    }

}
