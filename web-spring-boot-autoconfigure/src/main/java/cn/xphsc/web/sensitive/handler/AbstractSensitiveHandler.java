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
package cn.xphsc.web.sensitive.handler;

import cn.xphsc.web.sensitive.annotation.Sensitive;
import cn.xphsc.web.sensitive.utils.SensitiveUtils;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class AbstractSensitiveHandler implements SensitiveHandler {

    @Override
    public String chineseName(String fullName) {
        return SensitiveUtils.chineseName(fullName);
    }



    @Override
    public String idCardNumber(String idCardNum, int front, int end) {
        return SensitiveUtils.idCardNum(idCardNum, front, end);
    }

    @Override
    public String fixedPhone(String num) {
        return SensitiveUtils.fixedPhone(num);
    }



    @Override
    public String mobilePhone(String num) {
        return SensitiveUtils.mobilePhone(num);
    }

    @Override
    public String address(String address, int sensitiveSize) {
        return SensitiveUtils.address(address, sensitiveSize);
    }

    @Override
    public String email(String email) {
        return SensitiveUtils.email(email);
    }

    @Override
    public String bankCardNo(String cardNum) {
        return SensitiveUtils.bankCardNo(cardNum);
    }

    @Override
    public String bankJointNum(String code) {
        return SensitiveUtils.bankJointNum(code);
    }

    @Override
    public String password(String password) {
        return SensitiveUtils.password(password);
    }


    @Override
    public String customJacksonHandler(String input, Sensitive param) {
        return input;
    }

    @Override
    public String currency(String currency) {
        return SensitiveUtils.desensitization(currency);
    }
}
