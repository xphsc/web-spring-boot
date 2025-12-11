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

import cn.xphsc.web.rest.http.SimpleHttpRestBuilderConvert;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public  abstract class AbstractExecutor <T> implements Executor<T> {
    private final RestTemplate restTemplate;

    public AbstractExecutor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public T execute() {
        return doExecute();
    }
    protected abstract T doExecute() ;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType).getBody();
    }

    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }
    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }

    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType).getBody();
    }

    public <T> T exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
    }


    public Boolean booleanMethod(String url, HttpEntity<?> requestEntity, HttpMethod method, Map<String, ?> uriVariables) {
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, method, requestEntity, JSONObject.class,
                uriVariables);
        return SimpleHttpRestBuilderConvert.getSUCCESS().equals(responseEntity.getStatusCodeValue());
    }

    public Boolean booleanMethod(String url, HttpEntity<?> requestEntity, HttpMethod method, Object... uriVariables) {
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, method, requestEntity, JSONObject.class,
                uriVariables);
        return SimpleHttpRestBuilderConvert.getSUCCESS().equals(responseEntity.getStatusCodeValue());
    }
}
