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
package cn.xphsc.web.crypto.encrypt.factory;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public interface CryptoFactory {


    /**
     * 加密
     * @param key  密钥
     * @param content 需要加密的内容
     */
    byte[] encrypt(String key, byte[] content) throws RuntimeException;


    String encrypt(String key, String content) throws RuntimeException;

    /**
     * 解密
     * @param key     密钥
     * @param content 需要解密的内容
     * @return 解密结果
     * @throws RuntimeException RuntimeException
     */
    byte[] decrypt(String key, byte[] content) throws RuntimeException;

    String decrypt(String key, String content) throws RuntimeException;
}
