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
package cn.xphsc.web.logger.logback.sensitive;


import cn.xphsc.web.logger.logback.BaseMessageConverter;
import cn.xphsc.web.sensitive.utils.SensitiveUtils;
import cn.xphsc.web.utils.StringUtils;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SensitiveMessageConverter extends BaseMessageConverter {

    private final  static Pattern PATTENR = Pattern.compile("[0-9a-zA-Z]");
    @Override
    public String invokeMsg(String oriMsg) {
        String tempMsg = oriMsg;
        try {
            if("true".equals(sensitiveEnable)){
                if(!SENSITIVE_KEYWORD.isEmpty()){
                    Set<String> keysArray = SENSITIVE_KEYWORD.keySet();
                    for(String key: keysArray){
                        int index = -1;
                        int i = 0;
                        do{
                            index = tempMsg.indexOf(key, index + 1);
                            if(index != -1){
                                if(isWordChar(tempMsg, key, index)){
                                    continue;
                                }
                                int valueStart = getValueStartIndex(tempMsg, index + key.length());
                                int valueEnd = getValueEndEIndex(tempMsg, valueStart);
                                // 对获取的值进行脱敏
                                String value = tempMsg.substring(valueStart, valueEnd);
                                String  middleValue=tempMsg.substring(valueStart-1,valueStart);
                                String keyPrefix= StringUtils.removeEnd(tempMsg,SENSITIVE_KEYWORD.get(key)+middleValue+value);
                                value=SensitiveUtils.desensitization(value);
                                tempMsg= SensitiveUtils.desensitization(SENSITIVE_KEYWORD.get(key));
                                tempMsg =keyPrefix+tempMsg+middleValue+value;
                                i++;
                            }
                        }while(index != -1 && i < depth);
                    }
                }
            }
        } catch (Exception e) {
            return tempMsg;
        }
        return tempMsg;
    }


    private boolean isWordChar(String msg, String key, int index){
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
    private int getValueStartIndex(String msg, int valueStart ){
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
    private int getValueEndEIndex(String msg, int valueEnd){
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


}
