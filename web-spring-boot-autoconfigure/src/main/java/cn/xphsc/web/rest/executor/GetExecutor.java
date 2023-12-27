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

import cn.xphsc.web.rest.entity.GetEntity;
import cn.xphsc.web.rest.http.SimpleHttpRestBuilderConvert;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class GetExecutor<T> extends AbstractExecutor<T>{
    private GetEntity entity;
    private Class<?> clazzResponseType;
    private  Map<String, ?> uriVariables;
    private  Object[]  arrayUriVariables={};
    private  Map<String, String> headers;
    private HttpHeaders httpHeaders;
    private  Map<String, ?> parameters;
    private boolean  uriVariable;
    public GetExecutor(RestTemplate restTemplate, GetEntity entity) {
        super(restTemplate);
        this.entity = entity;
        if (StringUtils.isNotBlank(entity.url()) && entity.responseType() != null) {
                clazzResponseType = (Class<?>) entity.responseType();
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
        }
        this.parameters=entity.parameters();
    }
    @Override
    protected T doExecute() {
            if(clazzResponseType!=null){
                  if(uriVariable) {
                      if (uriVariables != null && arrayUriVariables == null) {
                              if (headers != null) {
                                  if (parameters != null) {
                                      HttpEntity<?> requestEntity = new HttpEntity<>(headers);
                                      return (T) exchange(SimpleHttpRestBuilderConvert.urlSplice(entity.url(), parameters), HttpMethod.GET, requestEntity, clazzResponseType, uriVariables);
                                  }else {
                                      HttpHeaders httpHeaders = new HttpHeaders();
                                      httpHeaders.setAll(headers);
                                      HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
                                      return (T) exchange(entity.url(), HttpMethod.GET, requestEntity, clazzResponseType, uriVariables);
                                  }
                              } else {
                                   if(httpHeaders!=null){
                                       HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
                                       return (T) exchange(entity.url(), HttpMethod.GET, requestEntity, clazzResponseType, uriVariables);
                               }else{
                                       return (T) this.getRestTemplate().getForEntity(entity.url(), clazzResponseType, uriVariables).getBody();
                                   }
                          }

                      } else {
                          if(headers==null&&httpHeaders!=null){
                              HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
                              return (T) exchange(entity.url(), HttpMethod.GET, requestEntity, clazzResponseType, arrayUriVariables);
                          }else {
                              if(headers!=null&&httpHeaders==null){
                                  if (parameters != null) {
                                      HttpEntity<?> requestEntity = new HttpEntity<>(headers);
                                      return (T) exchange(SimpleHttpRestBuilderConvert.urlSplice(entity.url(), parameters), HttpMethod.GET, requestEntity, clazzResponseType, uriVariables);
                                  }else{
                                  HttpHeaders httpHeaders = new HttpHeaders();
                                  httpHeaders.setAll(headers);
                                  HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);
                                  return (T) exchange(entity.url(), HttpMethod.GET, requestEntity, clazzResponseType, arrayUriVariables);}
                              }else{
                              return (T) this.getRestTemplate().getForEntity(entity.url(), clazzResponseType, arrayUriVariables).getBody();}
                          }
                      }
                  } else{
                        return (T) this.getRestTemplate().getForEntity(entity.url(), clazzResponseType).getBody();
                    }
                }
            return null;

    }


}
