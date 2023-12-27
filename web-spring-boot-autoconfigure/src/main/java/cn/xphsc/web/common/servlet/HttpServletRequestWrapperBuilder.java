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
package cn.xphsc.web.common.servlet;

import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class HttpServletRequestWrapperBuilder extends HttpServletRequestWrapper {

    private  byte[] streamBody;
    public HttpServletRequestWrapperBuilder(HttpServletRequest request) {
        super(request);
           this.streamBody = HttpServletStreamBuilder.streamBody(request);
    }



    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public Object attributeOf(String name){
        if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
            Map <String, Object> uriTemplateVars = (Map <String, Object>) super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (uriTemplateVars.isEmpty()) {
                return uriTemplateVars;
            }
            Map filterValueMap = new LinkedHashMap(uriTemplateVars.size());
            for (Map.Entry <String, Object> entity : uriTemplateVars.entrySet()) {
                if (entity.getValue()!=null) {
                    if (entity.getValue() instanceof String) {
                        String value=entity.getValue().toString();
                        filterValueMap.put(entity.getKey(), value);
                    }else{
                        filterValueMap.put(entity.getKey(), entity.getValue());
                    }
                }
            }

            return filterValueMap;

        }else {
            return super.getAttribute(name);
        }

    }


    public Map<String, Object> jsonStringToMap(String  jsonString) {
        Map<String, Object> map=new HashMap<>(jsonString.length());
        map= JacksonUtils.toJsonObject(jsonString,HashMap.class);
            Set<String> set = map.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (map!=null && map.keySet().size()>0) {
                    if (StringUtils.isNotBlank(key)) {
                        Object value = null;
                        if (map.get(key) != null) {
                            if (map.get(key) instanceof String) {
                                value=map.get(key).toString();
                            } else {
                                value = map.get(key);
                            }
                            map.put(key, value);
                        }
                    }
                }
            }


        return map;
    }




    public byte[] getStreamBody() {
        return streamBody;
    }


    public void setStreamBody(byte[] streamBody) {
        this.streamBody = streamBody;
    }
}
