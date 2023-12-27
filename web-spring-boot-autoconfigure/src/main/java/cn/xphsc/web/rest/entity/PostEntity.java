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



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class PostEntity {

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
    private Class<?> responseType ;
    /**
     * URL中的变量，按顺序依次对应 变量类型为map or object数组
     */
    private Object uriVariables;
    /**
     * 请求参数体
     */
    private Object requestBody;

    public static PostEntity.Builder builder(){
        return  new PostEntity.Builder();
    }
    private PostEntity(PostEntity.Builder builder){
        this.url=builder.url;
        this.headers=builder.headers;
        this.responseType=builder.responseType;
        this.uriVariables=builder.uriVariables;
        this.requestBody=builder.requestBody;
    }
    public static class Builder {
        private  String url;
        private Object headers;
        private Class<?> responseType;
        private Object uriVariables;
        private Object requestBody;
        public Builder(){
        }

        public PostEntity.Builder url(String url) {
            this.url=url;
            return this;
        }
        public PostEntity.Builder headers(Object headers) {
            this.headers=headers;
            return this;
        }
        public PostEntity.Builder responseType(Class<?> responseType) {
            this.responseType=responseType;
            return this;
        }
        public PostEntity.Builder uriVariables(Object uriVariables) {
            this.uriVariables=uriVariables;
            return this;
        }
        public PostEntity.Builder requestBody(Object requestBody) {
            this.requestBody=requestBody;
            return this;
        }

        public PostEntity build() {
            return new PostEntity(this);
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

    public Object requestBody() {
        return requestBody;
    }


}
