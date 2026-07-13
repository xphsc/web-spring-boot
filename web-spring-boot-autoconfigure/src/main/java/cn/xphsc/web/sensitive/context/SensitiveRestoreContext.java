/*
 * Copyright (c) 2025 huipei.x
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
package cn.xphsc.web.sensitive.context;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 敏感数据恢复上下文类
 * 该类用于管理敏感数据恢复过程中的上下文信息
 * 包含恢复操作所需的各种配置参数和状态信息
 * @since 2.0.4
 */

public class SensitiveRestoreContext {


        private static final Map<String, Map<String, String>> GLOBAL_CONTEXT =
                new ConcurrentHashMap<>();

        public static void storeMapping(String fieldKey, String desensitized, String original) {
            if (fieldKey != null && desensitized != null && original != null) {
                GLOBAL_CONTEXT.computeIfAbsent(fieldKey, k -> new ConcurrentHashMap<>())
                        .put(desensitized, original);
            }
        }

        public static String getOriginalValue(String fieldKey, String desensitized) {
            Map<String, String> fieldMappings = GLOBAL_CONTEXT.get(fieldKey);
            if (fieldMappings != null) {
                return fieldMappings.get(desensitized);
            }
            return null;
        }

        public static void clearExpiredMappings() {

            GLOBAL_CONTEXT.clear();
        }
    }
