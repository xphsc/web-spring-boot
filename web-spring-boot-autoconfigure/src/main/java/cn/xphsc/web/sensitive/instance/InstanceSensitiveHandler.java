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
package cn.xphsc.web.sensitive.instance;


import cn.xphsc.web.sensitive.handler.AbstractSensitiveHandler;
import cn.xphsc.web.sensitive.handler.SensitiveHandler;


/**
 * 脱敏服务工具类
 */
public class InstanceSensitiveHandler {

    /**
     * 脱敏工具
     */
    private static SensitiveHandler sensitiveHandler;

    public void InstanceSensitiveHandler(SensitiveHandler sensitiveHandler) {
        InstanceSensitiveHandler.sensitiveHandler = sensitiveHandler;
    }

    /**
     * 获取脱敏工具类
     */
    public static SensitiveHandler getSensitiveHandler() {
        if(InstanceSensitiveHandler.sensitiveHandler == null){
            InstanceSensitiveHandler.sensitiveHandler = new AbstractSensitiveHandler() {};
        }
        return InstanceSensitiveHandler.sensitiveHandler;
    }
}
