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


import cn.xphsc.web.rest.entity.RestEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DeleteExecutor<T> extends AbstractExecutor<T>{
    private RestEntity entity;
    private Class<?> clazzResponseType;
    private  Map<String, ?> uriVariables;
    private  Object[]  arrayUriVariables={};
    private HttpEntity requestEntity;
    private boolean  uriVariable;
    public DeleteExecutor(RestTemplate restTemplate, RestEntity entity) {
        super(restTemplate);
        this.entity = entity;
        this.clazzResponseType = (Class<?>) entity.responseType();
        this.requestEntity=entity.requestEntity();
       if(this.entity.uriVariables()!=null) {
           if(entity.uriVariables() instanceof Map){
              uriVariables= (Map<String, ?>)entity.uriVariables();
           }else{
               arrayUriVariables= (Object[]) entity.uriVariables();
           }
           uriVariable=true;
        }
    }
    @Override
    protected T doExecute() {
        if (clazzResponseType != null) {
            if (uriVariable) {
                if (uriVariables != null && arrayUriVariables == null) {
                    if (requestEntity != null) {
                        return (T)exchange(entity.url(), HttpMethod.DELETE, requestEntity, clazzResponseType, uriVariables);
                    }else{
                        return (T)exchange(entity.url(), HttpMethod.DELETE, HttpEntity.EMPTY, clazzResponseType, uriVariables);
                    }
                } else {
                    if (requestEntity!= null) {
                        return (T)exchange(entity.url(), HttpMethod.DELETE, requestEntity, clazzResponseType, arrayUriVariables);
                    } else {
                        return (T)exchange(entity.url(), HttpMethod.DELETE, HttpEntity.EMPTY, clazzResponseType, arrayUriVariables);
                    }
                }
            }
        }else{
            if (uriVariable&& requestEntity != null) {
                if (uriVariables != null && arrayUriVariables == null) {
                    this.booleanMethod(entity.url(), requestEntity, HttpMethod.DELETE, uriVariables);
                }else{
                    this.booleanMethod(entity.url(), requestEntity, HttpMethod.DELETE, arrayUriVariables);
                }
            }

        }
        return null;
    }
}
