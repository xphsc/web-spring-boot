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
package cn.xphsc.web.boot.sqlInjection;


import cn.xphsc.web.boot.sqlInjection.utils.SqlInjectionUtils;
import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.boot.sqlInjection.exception.SqlInjectionException;
import cn.xphsc.web.common.servlet.HttpServletRequestWrapperBuilder;
import cn.xphsc.web.common.servlet.MyServletInputStream;
import cn.xphsc.web.common.validator.Validator;
import cn.xphsc.web.utils.IoUtils;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.ObjectUtils;
import cn.xphsc.web.utils.StringUtils;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

import static cn.xphsc.web.common.lang.constant.Constants.LEFT_BRACKET;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SqlInjectionRequestWrapper extends HttpServletRequestWrapperBuilder {

    private boolean parameterEabled;
    public SqlInjectionRequestWrapper(HttpServletRequest request,boolean parameterEabled) {
        super(request);
        this.parameterEabled= parameterEabled;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String json= IoUtils.toString(this.getStreamBody());
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }
        if (json.startsWith(LEFT_BRACKET)) {
            ByteArrayInputStream bis = new ByteArrayInputStream(this.getStreamBody());
            return new MyServletInputStream(bis);
        }
            Map<String,Object> map =jsonStringToMap(json);
            filterOfvalue(map);
            ByteArrayInputStream bis = new ByteArrayInputStream(JacksonUtils.toJSONString(map).getBytes(this.getCharacterEncoding()));
            return new MyServletInputStream(bis);
    }

    @Override
    public Object getAttribute(String name) {
        Object attribute= attributeOf(name);
        if(attribute instanceof Map){
            Map<String,Object> map= (Map<String, Object>) attributeOf(name);
            attribute=filterOfvalue(map);
        }
        return attribute;
    }



    private  void checkFilterSqlException(String value){
       if(!SqlInjectionUtils.checkSqlKeyWords(value)){
           throw new SqlInjectionException(ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode(),ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getName());
       }
   }


private Map<String,Object> filterOfvalue(Map<String,Object> map) {
        for (Map.Entry<String, Object> entity : map.entrySet()) {
            if (parameterEabled) {
                if(Validator.containString(entity.getValue())){
                        map.put(entity.getKey(), SqlInjectionUtils.cleanSqlKeyWords(String.valueOf(entity.getValue())));
                    }
            } else {
                if(Validator.containString(entity.getValue())){
                checkFilterSqlException(ObjectUtils.isNotEmpty(entity.getValue())?String.valueOf(entity.getValue()):null);
                }
            }

        }
    return map;
}

}
