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
package cn.xphsc.web.boot.sqlInjection.utils;

import cn.xphsc.web.utils.StringUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: sql注入工具
 * @since 1.0.0
 */
public class SqlInjectionUtils {

    private static Set<String> NOT_ALLOEWD_KEYWORDS = new HashSet<String>(23);
    private static String SQL_KEYWORD="select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute|from|version|user|database|extractvalue|concat|system_user|updatexml";
    private static String STRING_REGEX="\\s*( |\\t|\\r|\\n|\\()\\s*";
    static {
        String keyStr[] = SQL_KEYWORD.split("\\|");
        for (String str : keyStr) {
            NOT_ALLOEWD_KEYWORDS.add(str);
        }
    }
    public static boolean checkSqlKeyWords(String value) {
        String paramValue = value;
        if(StringUtils.isEmpty(paramValue)){
            return true;
        }
        String[] paramArr = paramValue.split(STRING_REGEX);
        for (String arr : paramArr) {
            if (NOT_ALLOEWD_KEYWORDS.contains(arr.toLowerCase())) {
                return  false;
            }
        }
        return  true;
    }

    public static String cleanSqlKeyWords(String value){
        String paramValue = value;
        String[] paramArr = paramValue.split(STRING_REGEX);
        for (String stringValue : paramArr) {
            if (NOT_ALLOEWD_KEYWORDS.contains(stringValue.toLowerCase())) {
                paramValue = StringUtils.replace(paramValue,stringValue,StringUtils.EMPTY);
            }
        }
        return paramValue;
    }


}
