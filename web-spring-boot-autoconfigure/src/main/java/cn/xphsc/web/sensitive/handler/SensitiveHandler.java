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

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public interface SensitiveHandler {
    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    String chineseName(final String fullName);



    /**
     * 【身份证号】前三位 和后三位
     */
    String idCardNumber(String idCardNum, int front, int end);

    /**
     * 【固定电话 前四位，后两位
     */
    String fixedPhone(String num);

    /**
     * 【手机号码】前三位，后两位，其他隐藏，比如135******10
     */
    String mobilePhone(String num);

    /**
     * [地址] 只显示到地区，不显示详细地址>
     * @param sensitiveSize 敏感信息长度
     */
    String address(final String address, final int sensitiveSize);

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替
     */
    String email(final String email);

    /**
     * [银行卡号] 前六位，后四位，
     */
    String bankCardNo(final String bankCardNo);

    /**
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，
     */


    String bankJointNum(final String bankJointNum);

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     */
    String password(String password);

    /**
     * 自定义处理方法-jackson
     */
    String customJacksonHandler(String input, Sensitive param);

    /**
     *通用处理
     */
    String currency(String currency);

}
