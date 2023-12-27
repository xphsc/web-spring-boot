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
package cn.xphsc.web.rest;



import cn.xphsc.web.rest.entity.GetEntity;
import cn.xphsc.web.rest.entity.PostEntity;
import cn.xphsc.web.rest.entity.RestEntity;
import cn.xphsc.web.rest.executor.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.*;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class RestBuilderTemplate {

    private final RestTemplate restTemplate;
    public RestBuilderTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取RestTemplate实例对象，可自由调用其方法
     */
    public RestTemplate restTemplate() {
        return restTemplate;
    }

    /**
     * GET请求调用方式
     * @param url  请求URL
     * @param responseType 返回对象类型
     * @return T 响应对象
     */
    public <T> T get(String url, Class<T> responseType) {
        GetEntity entity=GetEntity.builder().url(url).responseType(responseType).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * GET请求调用方式
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T get(String url, Class<T> responseType, Object... uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * GET请求调用方式
     * @param url 请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * 带请求头的GET请求调用方式
     * @param url    请求URL
     * @param headers 请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * 带请求头的GET请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T get(String url, HttpHeaders headers, Class<T> responseType, Object... uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * 带请求头的GET请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * 带请求头的GET请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T get(String url, HttpHeaders headers, Class<T> responseType, Map<String, ?> uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * 带请求头的GET请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @param parameters  URL 中的变量
     */
    public <T> T get(String url, Map<String, ?> parameters, Map<String, String> headers, Class<T> responseType, Object... uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).parameters(parameters).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * 带请求头的GET请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @param parameters  URL 中的变量
     */
    public <T> T get(String url, Map<String, ?> parameters, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables) {
        GetEntity entity=GetEntity.builder().url(url).headers(headers).parameters(parameters).responseType(responseType).uriVariables(uriVariables).build();
        GetExecutor executor=new GetExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    public <T> T get(GetEntity getEntity) {
        GetExecutor executor=new GetExecutor(restTemplate,getEntity);
        return (T) executor.execute();
    }


    // ----------------------------------POST-------------------------------------------------------


    /**
     * POST请求调用方式
     * @param url  请求URL
     * @param responseType 返回对象类型
     */
    public <T> T post(String url, Class<T> responseType) {
        PostEntity entity= PostEntity.builder().url(url).responseType(responseType).build();
        PostExecutor executor=new PostExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    public <T> T post(PostEntity postEntity) {
        PostExecutor executor=new PostExecutor(restTemplate,postEntity);
        return (T) executor.execute();
    }

    /**
     * POST请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     */
    public <T> T post(String url, Object requestBody, Class<T> responseType) {
        PostEntity entity= PostEntity.builder().url(url).responseType(responseType).requestBody(requestBody).build();
        PostExecutor executor=new PostExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * POST请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T post(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        PostEntity entity= PostEntity.builder().url(url).responseType(responseType).requestBody(requestBody).build();
        PostExecutor executor=new PostExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * POST请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T post(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        PostEntity entity= PostEntity.builder().url(url).responseType(responseType).requestBody(requestBody).build();
        PostExecutor executor=new PostExecutor(restTemplate,entity);
        return (T) executor.execute();
    }



    /**
     * 带请求头的POST请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }



    /**
     * 带请求头的POST请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }



    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T post(String url, Map<String, String> headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }



    /**
     * 带请求头的POST请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }





    /**
     * 自定义请求头和请求体的POST请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T post(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }


    /**
     * 带文件 POST请求调用方式
     * @param url          请求URL
     * @param parameters       请求Body 参数
     * @param responseType 返回对象类型
     * @param fileFields   文件字段集合
     * @param uriVariables URL中的变量
     */
    public <T> T postFile(String url, Object parameters, Class<T> responseType, List<String> fileFields, Object... uriVariables) {
        PostEntity entity= PostEntity.builder().build();
        PostExecutor executor=new PostExecutor(restTemplate,entity);
        return (T) executor.postFile(url,parameters,responseType,fileFields,uriVariables);
    }


    // ----------------------------------PUT-------------------------------------------------------


    public <T> T put(RestEntity restEntity) {
        PutExecutor executor=new PutExecutor(restTemplate,restEntity);
        return (T) executor.execute();
    }

    /**
     * PUT请求调用方式
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T put(String url, Class<T> responseType, Object... uriVariables) {
        RestEntity entity=RestEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * PUT请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T put(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * PUT请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T put(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }





    /**
     * 带请求头的PUT请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }




    /**
     * 带请求头的PUT请求调用方式
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T put(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * 自定义请求头PUT请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean put(String url, HttpEntity<?> requestEntity, Map<String, ?> uriVariables) {
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (Boolean) executor.execute();
    }

    /**
     * 自定义请求头PUT请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean put(String url, HttpEntity<?> requestEntity, Object... uriVariables) {
        RestEntity entity=RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        PutExecutor executor=new PutExecutor(restTemplate,entity);
        return (Boolean) executor.execute();
    }

    // ----------------------------------DELETE-------------------------------------------------------

    public <T> T delete(RestEntity restEntity) {
        DeleteExecutor executor=new DeleteExecutor(restTemplate,restEntity);
        return (T) executor.execute();
    }
    /**
     * DELETE请求调用方式
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T delete(String url, Class<T> responseType, Object... uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * DELETE请求调用方式
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T delete(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * DELETE请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T delete(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * DELETE请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T delete(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }


    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T delete(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (T) executor.execute();
    }

    /**
     * 自定义请求头DELETE请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean delete(String url, HttpEntity<?> requestEntity, Map<String, ?> uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (Boolean) executor.execute();
    }

    /**
     * 自定义请求头DELETE请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean delete(String url, HttpEntity<?> requestEntity, Object... uriVariables) {
        RestEntity entity= RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        DeleteExecutor executor=new DeleteExecutor(restTemplate,entity);
        return (Boolean) executor.execute();
    }

    // ----------------------------------PATCH-------------------------------------------------------

    public <T> T patch(RestEntity restEntity) {
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,restEntity);
        return (T) parchExecutor.execute();
    }

    /**
     * PATCH请求调用方式
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     */
    public <T> T patch(String url, Class<T> responseType, Object... uriVariables) {
        RestEntity patchEntity=RestEntity.builder().url(url).responseType(responseType).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (T) parchExecutor.execute();
    }


    /**
     * PATCH请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return T 响应对象
     */
    public <T> T patch(String url, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (T) parchExecutor.execute();
    }



    /**
     * PATCH 请求调用方式
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     */
    public <T> T patch(String url, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (T) parchExecutor.execute();
    }




    /**
     * 自定义请求头和请求体的PATCH请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T patch(String url, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (T) parchExecutor.execute();
    }


    /**
     * 自定义请求头和请求体的PATCH请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T patch(String url, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).responseType(responseType).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (T) parchExecutor.execute();
    }


    /**
     * 自定义请求头PATCH请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean patch(String url, HttpEntity<?> requestEntity, Map<String, ?> uriVariables) {
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (Boolean) parchExecutor.execute();
    }

    /**
     * 自定义请求头PATCH请求调用方式
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param uriVariables  URL中的变量
     */
    public Boolean patch(String url, HttpEntity<?> requestEntity, Object... uriVariables) {
        RestEntity patchEntity=RestEntity.builder().url(url).requestEntity(requestEntity).uriVariables(uriVariables).build();
        ParchExecutor parchExecutor=new ParchExecutor(restTemplate,patchEntity);
        return (Boolean) parchExecutor.execute();
    }

    // ----------------------------------通用方法-------------------------------------------------------


    /**
     * 通用调用方式
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T)exchangeExecutor.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T) exchangeExecutor.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T)exchangeExecutor.exchange(url, method, requestEntity, responseType);
    }

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T)exchangeExecutor.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T)exchangeExecutor.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     */
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        ExchangeExecutor exchangeExecutor=new ExchangeExecutor(restTemplate);
        return (T)exchangeExecutor.exchange(url, method, requestEntity, responseType);
    }




}
