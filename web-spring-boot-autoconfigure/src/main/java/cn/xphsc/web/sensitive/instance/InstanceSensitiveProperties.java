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


import cn.xphsc.web.boot.sensitive.SensitiveProperties;
import org.springframework.context.ApplicationContext;

public class InstanceSensitiveProperties {

    private static SensitiveProperties INSTANCE;
    public static SensitiveProperties getInstance(){
        if(INSTANCE == null) {
            throw new IllegalArgumentException("SensitiveProperties is Undefined");
        }
        return INSTANCE;
    }

    public static SensitiveProperties getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(SensitiveProperties INSTANCE) {
        InstanceSensitiveProperties.INSTANCE = INSTANCE;
    }
    public void setSensitiveProperties(SensitiveProperties sensitiveProperties) {
        INSTANCE = sensitiveProperties;
    }


}
