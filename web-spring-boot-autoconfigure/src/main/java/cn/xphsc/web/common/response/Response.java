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
package cn.xphsc.web.common.response;



import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.PropertiesUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Response<T> implements Serializable {

    /**
     * 响应数据
     */
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private T data;
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应描述信息
     */
    private String message;

    public Response(){}

    private Response(T data, Integer code, String message) {
        super();
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> ok() {
        return new Response(null, ResponseCode.SUCCESS.code(),ResponseCode.SUCCESS.message());
    }

    public static <T> Response<T> ok(T data) {
        return new Response(data, ResponseCode.SUCCESS.code(),ResponseCode.SUCCESS.message());
    }

    public static <T> Response<T> ok(T data,Integer code, String message) {
        return new Response(data,code,message);
    }

    public static Response<Void> fail(Integer code, String message) {
        return fail(null, code, message);
    }


    public static <T> Response<T> fail(T data,Integer code, String message) {
        return new Response(data, code, message);
    }

    public static Response<Void> fail(){
       return  fail(ResponseCode.ERROR.code(), ResponseCode.ERROR.message());
    }

    public static <T> Response<T> ok(List<T> data, long total) {
        Response response=new Response();
        response.setCode(ResponseCode.SUCCESS.code());
        response.setMessage(ResponseCode.SUCCESS.message());
        Map<String, Object> addProperties = new HashMap(2);
        addProperties.put("list",data);
        addProperties.put("total",total);
        response= (Response) PropertiesUtils.getTarget(response, addProperties);
        return response;

    }

    public static <T> Response<T> ok(List<T> data, long total, int pageNum,int pageSize) {
        Response response=new Response();
        response.setCode(ResponseCode.SUCCESS.code());
        response.setMessage(ResponseCode.SUCCESS.message());
        Map<String, Object> addProperties = new HashMap(2);
        addProperties.put("list",data);
        addProperties.put("page",ResultMapper.builder().mapping("pageNum",pageNum).mapping("pageSize",pageSize).mapping("total",total).build());
        response= (Response) PropertiesUtils.getTarget(response, addProperties);
        return response;

    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JacksonUtils.toJSONString(this);
    }
}