/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.boot.envencrypt.processor;



import cn.xphsc.web.boot.envencrypt.EncryptType;
import cn.xphsc.web.boot.envencrypt.EncryptorUtils;
import cn.xphsc.web.utils.Collects;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: configuration environment encryption Processor
 * @since 1.1.0
 */
public class EnvEncryptEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final String key = environment.getProperty("web.envencrypt.key");
         String enabled = environment.getProperty("web.envencrypt.enabled");
        final String encryptType = environment.getProperty("web.envencrypt.encryptType");
        if (Objects.nonNull(key)&&Boolean.valueOf(enabled)) {
            MutablePropertySources propertySources = environment.getPropertySources();

            for (PropertySource<?> propertySource : propertySources) {
                if (propertySource.getSource() instanceof Map<?, ?>) {
                    Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
                    Map<String, String> decEnvPropertySource = processEnv(environment, key, encryptType, source);
                    if (Objects.nonNull(decEnvPropertySource)) {
                        MapPropertySource mapPropertySource = new MapPropertySource(String.format("%s-ENC", propertySource.getName()),
                                Collections.unmodifiableMap(decEnvPropertySource));
                        propertySources.addFirst(mapPropertySource);
                    }
                }
            }
        }
    }

    private Map<String, String> processEnv(ConfigurableEnvironment environment,String envKey,String encryptType, Map<?, ?> source) {
        final String prefix = "ENC(";
        Map<String, String> map = new HashMap<>();
        Map<String, Object>  sysEnvMap=environment.getSystemEnvironment();
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            String newValue  =StringUtils.substringAfter(value,":");
            newValue  =StringUtils.substringBefore(newValue,")");
            String  envVariable  =StringUtils.substringBefore(value,":"+prefix);
            envVariable  =StringUtils.substringAfter(envVariable,"${");
            if(value.startsWith("${")){
                if (sysEnvMap.get(envVariable)!=null) {
                    value  =StringUtils.substringBefore(sysEnvMap.get(envVariable).toString(),")");
                    value=StringUtils.substringAfter(value,prefix);
                 if(StringUtils.isNotBlank(value)){
                        map.put(key, EncryptorUtils.decrypt(EncryptType.valueOf(encryptType.toUpperCase()),envKey,value));
                   }
                }else if (newValue.startsWith(prefix)) {
                    newValue  =StringUtils.substringAfter(newValue,prefix);

                    if(StringUtils.isNotBlank(value)){
                        map.put(key, EncryptorUtils.decrypt(EncryptType.valueOf(encryptType.toUpperCase()),envKey,newValue));

                    }
                }
                else{
                    String  envVar  =StringUtils.substringBefore(value,":");
                    envVar  =StringUtils.substringAfter(envVar,"${");
                    if(sysEnvMap.get(envVar)!=null){
                        value  =StringUtils.substringBefore(sysEnvMap.get(envVar).toString(),")");
                    }
                    value=StringUtils.substringAfter(value,prefix);
                    if(StringUtils.isNotBlank(value)){
                        map.put(key, EncryptorUtils.decrypt(EncryptType.valueOf(encryptType.toUpperCase()),envKey,value));
                    }
                }
            }else{
                if (value.startsWith(prefix)&&StringUtils.isBlank(envVariable)&&!sysEnvMap.keySet().contains(key)){
                    String   val  =StringUtils.substringBefore(value,")");
                    val  =StringUtils.substringAfter(val,prefix);
                    if(StringUtils.isNotBlank(val)){
                        map.put(key, EncryptorUtils.decrypt(EncryptType.valueOf(encryptType.toUpperCase()),envKey,val));
                    }
                }
            }

        }
        return Collects.isNotEmpty(map)?map:null;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE-14;
    }
}