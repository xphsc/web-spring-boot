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
package cn.xphsc.web.dicttraslate.handler;


import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Dictionary escape Handler
 * For reference, the example
 @Component
 public class TestDictHandler implements DictTransHandler {
 public Map<String, String> dictTransByName(String dictName) {}
 * @since 1.0.0
 */
public interface DictTransHandler {

    /**
     * Dictionary escape  method
     */
    Map<String, String> dictTransByName(String dictName);

}
