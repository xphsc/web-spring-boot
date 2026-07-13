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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 脱敏转换器类
 * @since 1.0.0
 */
public class SensitiveMessageConverter extends BaseMessageConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final  static Pattern PATTENR = Pattern.compile("[0-9a-zA-Z]");
    @Override
    public String invokeMsg(String oriMsg) {
        String tempMsg = oriMsg;
        try {
            if ("true".equals(sensitiveEnable)) {
                // 首先尝试按照原有逻辑处理
                tempMsg = processSimpleKeyValue(tempMsg);
                // 然后尝试解析为 JSON 并处理
                if (!SENSITIVE_KEYWORD.isEmpty()) {
                    tempMsg = processJsonMessage(tempMsg);
                }
            }
        } catch (Exception e) {
            // 出现异常时返回原始消息或已处理的部分结果
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
    private int getValueStartIndex(String msg, int valueStart) {
        while (valueStart < msg.length()) {
            char ch = msg.charAt(valueStart);
            if (ch != ':' && ch != '=' && ch != ' ' && ch != '.') {
                break;
            }
            valueStart++;
        }
        while (valueStart < msg.length() && msg.charAt(valueStart) == ' ') {
            valueStart++;
        }
        return valueStart;
    }


    private int getValueEndEIndex(String msg, int valueEnd) {
        do {
            if (valueEnd >= msg.length()) {
                break;
            }
            char ch = msg.charAt(valueEnd);

            if (ch == '"') {
                if (valueEnd + 1 >= msg.length()) {
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if (nextCh == ';' || nextCh == ',' || nextCh == '}' || nextCh == ' ') {
                    // 处理转义字符
                    while (valueEnd > 0) {
                        char preCh = msg.charAt(valueEnd - 1);
                        if (preCh != '\\') {
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                } else {
                    valueEnd++;
                }
            } else if (ch == ';' || ch == ',' || ch == '}') {
                break;
            } else {
                valueEnd++;
            }
        } while (true);
        return valueEnd;
    }
    private String processSimpleKeyValue(String tempMsg) {
        if (!SENSITIVE_KEYWORD.isEmpty()) {
            Set<String> keysArray = SENSITIVE_KEYWORD.keySet();
            for (String key : keysArray) {
                int index = -1;
                int i = 0;
                do {
                    index = tempMsg.indexOf(key, index + 1);
                    if (index != -1) {
                        if (isWordChar(tempMsg, key, index)) {
                            continue;
                        }

                        int valueStart = getValueStartIndex(tempMsg, index + key.length());
                        int valueEnd = getValueEndEIndex(tempMsg, valueStart);

                        // 严格边界检查
                        if (valueStart >= tempMsg.length() ||
                                valueEnd > tempMsg.length() ||
                                valueStart >= valueEnd) {
                            break;
                        }

                        try {
                            // 获取值并脱敏
                            String originalValue = tempMsg.substring(valueStart, valueEnd);
                            String desensitizedValue = SensitiveUtils.desensitization(originalValue);

                            // 键脱敏处理
                            String displayKey = key;
                            if (SENSITIVE_KEYWORD.containsKey(key)) {
                                displayKey = SensitiveUtils.desensitization(key);
                            }

                            // 安全字符串替换
                            tempMsg = tempMsg.substring(0, index) +
                                    displayKey +
                                    tempMsg.substring(index + key.length(), valueStart) +
                                    desensitizedValue +
                                    tempMsg.substring(valueEnd);

                        } catch (Exception e) {
                            break;
                        }

                        i++;
                    }
                } while (index != -1 && i < depth);
            }
        }
        return tempMsg;
    }
    private String processJsonMessage(String msg) {
        try {
            // 尝试解析为 JSON 对象
            JsonNode jsonNode = objectMapper.readTree(msg);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                processJsonObject(objectNode);
                return objectMapper.writeValueAsString(objectNode);
            } else if (jsonNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) jsonNode;
                processJsonArray(arrayNode);
                return objectMapper.writeValueAsString(arrayNode);
            }
        } catch (Exception e) {
            // 如果不是有效的 JSON，返回原消息
            return msg;
        }
        return msg;
    }

    private void processJsonObject(ObjectNode objectNode) {
        Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode fieldValue = field.getValue();

            // 检查字段名是否在敏感词列表中
            if (SENSITIVE_KEYWORD.containsKey(fieldName)) {
                if (fieldValue.isTextual()) {
                    // 对文本值进行脱敏
                    String desensitizedValue = SensitiveUtils.desensitization(fieldValue.asText());
                    objectNode.put(fieldName, desensitizedValue);
                } else if (fieldValue.isObject()) {
                    // 递归处理嵌套对象
                    processJsonObject((ObjectNode) fieldValue);
                } else if (fieldValue.isArray()) {
                    // 处理数组
                    processJsonArray((ArrayNode) fieldValue);
                }
            } else if (fieldValue.isObject()) {
                // 即使字段名不在敏感词列表中，也要递归处理嵌套对象
                processJsonObject((ObjectNode) fieldValue);
            } else if (fieldValue.isArray()) {
                processJsonArray((ArrayNode) fieldValue);
            }
        }
    }

    private void processJsonArray(ArrayNode arrayNode) {
        for (int i = 0; i < arrayNode.size(); i++) {
            JsonNode element = arrayNode.get(i);
            if (element.isObject()) {
                processJsonObject((ObjectNode) element);
            } else if (element.isArray()) {
                processJsonArray((ArrayNode) element);
            }
        }
    }
}
