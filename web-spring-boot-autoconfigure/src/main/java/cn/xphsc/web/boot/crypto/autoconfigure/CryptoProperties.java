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
package cn.xphsc.web.boot.crypto.autoconfigure;


import cn.xphsc.web.crypto.encrypt.CryptoType;
import cn.xphsc.web.crypto.encrypt.EncodingType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import static cn.xphsc.web.common.WebBeanTemplate.CRYPRO_PREFIX;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@ConfigurationProperties(CRYPRO_PREFIX)
public class CryptoProperties {

    private boolean enabled;
    private Encrypt encrypt;
    private Decrypt decrypt;
    private String charset = "UTF-8";
    private EncodingType encodingType=EncodingType.BASE64;

    public static class Encrypt {
        private String key;
        private CryptoType type;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public CryptoType getType() {
            return type;
        }

        public void setType(CryptoType type) {
            this.type = type;
        }
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Encrypt getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    public Decrypt getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Decrypt decrypt) {
        this.decrypt = decrypt;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public EncodingType getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(EncodingType encodingType) {
        this.encodingType = encodingType;
    }

    public static class Decrypt {

        private String key;
        private CryptoType type;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public CryptoType getType() {
            return type;
        }

        public void setType(CryptoType type) {
            this.type = type;
        }
    }


}
