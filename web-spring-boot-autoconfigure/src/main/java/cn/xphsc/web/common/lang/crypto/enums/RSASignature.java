/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.web.common.lang.crypto.enums;


import cn.xphsc.web.common.exception.Exceptions;
import cn.xphsc.web.utils.StringUtils;

import java.security.Signature;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: RSA 签名
 * @since 2.0.2
 */
public enum RSASignature {

    /**
     * NONE
     */
    NONE("NONEwithRSA"),

    /**
     * MD5
     */
    MD5("MD5withRSA"),

    /**
     * SHA1
     */
    SHA1("SHA1WithRSA"),

    /**
     * SHA224
     */
    SHA224("SHA224WithRSA"),

    /**
     * SHA256
     */
    SHA256("SHA256WithRSA"),

    /**
     * SHA384
     */
    SHA384("SHA384WithRSA"),

    /**
     * SHA512
     */
    SHA512("SHA512WithRSA");

    private final String model;

    RSASignature(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public Signature getSignature() {
        try {
            return Signature.getInstance(model);
        } catch (Exception e) {
            throw Exceptions.runtime(e);
        }
    }

    public static Signature getSignature(String model) {
        if (StringUtils.isBlank(model)) {
            return null;
        }
        RSASignature[] values = values();
        for (RSASignature value : values) {
            if (value.getModel().equalsIgnoreCase(model.trim())) {
                return value.getSignature();
            }
        }
        return null;
    }

}
