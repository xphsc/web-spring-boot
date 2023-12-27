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
package cn.xphsc.web.rest.executor;

import cn.xphsc.web.rest.entity.PostEntity;
import cn.xphsc.web.rest.http.SimpleHttpRestBuilderConvert;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class PostExecutor<T> extends AbstractExecutor<T>{
    private PostEntity entity;
    private Class<?> clazzResponseType;
    private  Map<String, ?> uriVariables;
    private  Object[]  arrayUriVariables={};
    private  Map<String, String> headers;
    private HttpHeaders httpHeaders;
    private Object requestBody;
    private boolean responseTypes;
    private boolean  uriVariable;
    private boolean header;
    public PostExecutor(RestTemplate restTemplate, PostEntity entity) {
        super(restTemplate);
        this.entity = entity;
        if (StringUtils.isNotBlank(entity.url()) && entity.responseType() != null) {
                clazzResponseType = (Class<?>) entity.responseType();
                 responseTypes=true;
            }

       if(entity.uriVariables()!=null) {
           if(entity.uriVariables() instanceof Map){
              uriVariables= (Map<String, ?>) entity.uriVariables();
           }else{
               arrayUriVariables= (Object[]) entity.uriVariables();
           }

           uriVariable=true;
        }
        if(entity.headers()!=null){
            if(entity.headers() instanceof Map){
               headers= (Map<String, String>) entity.headers();
            }
            if(entity.headers() instanceof HttpHeaders){
                httpHeaders= (HttpHeaders) entity.headers();
            }
            header=true;
        }
        this.requestBody=entity.requestBody();

    }
    @Override
    protected T doExecute() {
        if(responseTypes){
            if(clazzResponseType!=null){
                if(requestBody!=null){
                  if(uriVariable){
                      if(uriVariables!=null&&arrayUriVariables==null){
                          if(header){
                              if(headers!=null){
                                  HttpHeaders httpHeaders = new HttpHeaders();
                                  httpHeaders.setAll(headers);
                                  HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, httpHeaders);
                                  return (T) exchange(entity.url(), HttpMethod.POST, requestEntity, clazzResponseType, uriVariables);
                              }else{
                                  HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, httpHeaders);
                                  return (T) exchange(entity.url(), HttpMethod.POST, requestEntity, clazzResponseType, uriVariables);

                              }
                          }else{
                              return (T) this.getRestTemplate().postForEntity(entity.url(),requestBody, clazzResponseType, uriVariables).getBody();
                          }

                      }else {
                          if(header){
                              if(headers!=null){
                                  HttpHeaders httpHeaders = new HttpHeaders();
                                  httpHeaders.setAll(headers);
                                  HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, httpHeaders);
                                  return (T) exchange(entity.url(), HttpMethod.POST, requestEntity, clazzResponseType, arrayUriVariables);
                              }else{
                                  HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, httpHeaders);
                                  return (T) exchange(entity.url(), HttpMethod.POST, requestEntity, clazzResponseType, arrayUriVariables);
                              }
                          }else{
                          return (T) this.getRestTemplate().postForEntity(entity.url(), requestBody, clazzResponseType, arrayUriVariables).getBody();}
                      }
                  }else{
                      return (T) this.getRestTemplate().postForEntity(entity.url(), entity.requestBody(), clazzResponseType).getBody();
                  }
                }else{
                        return (T) this.getRestTemplate().postForEntity(entity.url(), HttpEntity.EMPTY, clazzResponseType).getBody();
                }
            }
        }
        return null;
    }



    public <T> T post(String url, HttpHeaders headers, Object requestBody, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }

    public <T> T post(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    public <T> T post(String url, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        return exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }
    public <T> T postFile(String url, Object params, Class<T> responseType, List<String> fileFields, Object... uriVariables) {
        MultiValueMap<String, Object> param = SimpleHttpRestBuilderConvert.objectToMultiMap(params, fileFields);
        List<File> files = null;
        if (!Objects.isNull(params)) {
            try {
                files = new ArrayList<File>();
                for (String name : fileFields) {
                    Field field = params.getClass().getDeclaredField(name);
                    field.setAccessible(true);
                    //因上层代码已转为io.File,因此此处无须再转
                    File file = (File) field.get(params);
                    files.add(file);
                    param.add(name, new FileSystemResource(file));
                }
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        }
        ResponseEntity<T> responseEntity = this.getRestTemplate().postForEntity(url, param, responseType, uriVariables);
        if (!files.isEmpty()) {
            files.forEach(f -> {
                if (f.exists()) {
                    f.delete();
                }
            });
        }
        return responseEntity.getBody();
    }
}
