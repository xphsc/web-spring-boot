/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.logger.log4j;
import cn.xphsc.web.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Sensitive Pattern Builder
 * @since 2.0.1
 */
public class SensitivePatternBuilder {
    private final  static Pattern PATTENR = Pattern.compile("[0-9a-zA-Z]");
    protected final static Map<String,String> SENSITIVE_KEYWORD= new HashMap<>(10);

    public static String format(String keys,String msg) {
        String[] split = keys.split(",");
        for(String sensitiveKey:split){
            SENSITIVE_KEYWORD.put(sensitiveKey,sensitiveKey);
        }
        Set<String> keysArray = SENSITIVE_KEYWORD.keySet();
        for(String key: keysArray){
            int index = -1;
            int i = 0;
            do{
                index = msg.indexOf(key, index + 1);
                if(index != -1){
                    if(isWordChar(msg, key, index)){
                        continue;
                    }
                    int valueStart = getValueStartIndex(msg, index + key.length());

                    int valueEnd = getValueEndEIndex(msg, valueStart);
                    // 对获取的值进行脱敏
                    String value = msg.substring(valueStart, valueEnd);
                    String  middleValue=msg.substring(valueStart-1,valueStart);
                    String keyPrefix= StringUtils.removeEnd(msg,SENSITIVE_KEYWORD.get(key)+middleValue+value);
                    value=desensitization(value);
                    msg=desensitization(SENSITIVE_KEYWORD.get(key));
                    msg =keyPrefix+msg+middleValue+value;

                    i++;
                }
            }while(index != -1);
        }

        return msg;
    }
    private static boolean isWordChar(String msg, String key, int index){
        if(index != 0){
            // 判断key前面一个字符
            char preCh = msg.charAt(index-1);
            Matcher match = PATTENR.matcher(preCh + "");
            if(match.matches()){
                return true;
            }
        }
        // 判断key后面一个字符
        char nextCh = msg.charAt(index + key.length());
        Matcher match = PATTENR.matcher(nextCh + "");
        if(match.matches()){
            return true;
        }
        return false;
    }
    private static  int getValueStartIndex(String msg, int valueStart ){
        do{
            char ch = msg.charAt(valueStart);
            if(ch == ':' || ch == '='||ch == ' '||ch == '.'){
                valueStart ++;
                ch = msg.charAt(valueStart);
                if(ch == '"'){
                    valueStart ++;
                }
                break;
            }else{
                valueStart ++;
            }
        }while(true);

        return valueStart;
    }
    private static int getValueEndEIndex(String msg, int valueEnd){
        do{
            if(valueEnd == msg.length()){
                break;
            }
            char ch = msg.charAt(valueEnd);

            if(ch == '"'){
                if(valueEnd + 1 == msg.length()){
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if(nextCh == ';' || nextCh == ','|| nextCh == '}'){
                    while(valueEnd > 0 ){
                        char preCh = msg.charAt(valueEnd - 1);
                        if(preCh != '\\'){
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                }else{
                    valueEnd ++;
                }
            }else if (ch ==';' || ch == ',' || ch == '}'){
                break;
            }else{
                valueEnd ++;
            }
        }while(true);

        return valueEnd;
    }

    private static String desensitization(String value) {
        if (null == value || "".equals(value)) {
            return value;
        }
        int len = value.length();
        int pamaone = len / 2;
        int pamatwo = pamaone - 1;
        int pamathree = len % 2;
        StringBuilder stringBuilder = new StringBuilder();
        if (len <= 2) {
            if (pamathree == 1) {
                return SYMBOL;
            }


            if (len == 2){

                stringBuilder.append(value.charAt(len-1));
                stringBuilder.append(SYMBOL);
            }else{
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value.charAt(len-1));
            }

        } else {
            if (pamatwo <= 0) {
                stringBuilder.append(value.substring(0, 1));
                stringBuilder.append(SYMBOL);
                stringBuilder.append(value.substring(len - 1, len));

            } else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
                int pamafive = (len - SIZE) / 2;
                stringBuilder.append(value.substring(0, pamafive));
                for (int i = 0; i < SIZE; i++) {
                    stringBuilder.append(SYMBOL);
                }
                if ((pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0)) {
                    stringBuilder.append(value.substring(len - pamafive, len));
                } else {
                    stringBuilder.append(value.substring(len - (pamafive + 1), len));
                }
            } else {
                int pamafour = len - 2;
                stringBuilder.append(value.substring(0, 1));
                for (int i = 0; i < pamafour; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value.substring(len - 1, len));
            }
        }
        return stringBuilder.toString();
    }
    private static final int SIZE = 6;
    private static final String SYMBOL = "*";

}
