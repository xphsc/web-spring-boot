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
package cn.xphsc.web.crypto.annotation;



import cn.xphsc.web.crypto.encrypt.CryptoType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 解密
 * @since 1.0.0
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {


    /**
     * 如果选择的 RSA 加/解密算法，那么 key 为必填项
     * @return CryptoType
     */
    CryptoType type() default CryptoType.AES;

    /**
     * 可选，如果未配置则采用全局的key
     * @return String
     */
    String key() default "";

    /**
     * 描述信息
     * @return String
     */
    String description() default "";

}
