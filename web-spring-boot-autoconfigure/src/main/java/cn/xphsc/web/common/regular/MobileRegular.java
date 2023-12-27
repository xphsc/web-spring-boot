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
package cn.xphsc.web.common.regular;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public enum MobileRegular {

    CN("(\\+?0?86\\-?)","^1[345789]\\d{9}$"),
    TW("(\\+?886\\-?|0)","^9\\d{8}$"),
    HK("(\\+?852\\-?)","^[569]\\d{3}\\-?\\d{4}$"),
    MS("(\\+?6?01){1}","^(([145]{1}(\\-|\\s)\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$"),
    PH("(\\+?0?63\\-?)","^\\d{10}$"),
    TH("(\\+?0?66\\-?)","^\\d{10}$"),
    SG("(\\+?0?65\\-?)","^\\d{10}$"),
    /** 一下是其他国家的手机号校验正则，*/
    DZ("(\\+?213|0)", "^(5|6|7)\\d{8}$"),
    BL("(\\+?880)","^\\d{5,15}$"),
    SY("(!?(\\+?963)|0)","^9\\d{8}$"),
    SA("(!?(\\+?966)|0)","^5\\d{8}$"),
    US("(\\+?1)","^[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
    CZ("(\\+?420)?","^ ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$"),
    DE("(\\+?49[ \\.\\-])","^([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$"),
    DK("(\\+?45)","^(\\d{8})$"),
    GR("(\\+?30)","^(69\\d{8})$"),
    AU("(\\+?61|0)","^4\\d{8}$"),
    GB("(\\+?44|0)","^7\\d{9}$"),
    CA("(\\+?1)","^[2-9]\\d{2}[2-9](?!11)\\d{6}$"),
    IN("(\\+?91|0)","^[789]\\d{9}$"),
    NZ("(\\+?64|0)","^2\\d{7,9}$"),
    ID("(\\+?62|0)","^(\\d{7,15})$"),
    ZA("(\\+?27|0)","^\\d{9}$"),
    ZM("(\\+?26)","^09[567]\\d{7}$"),
    ES("(\\+?34)","^(6\\d{1}|7[1234])\\d{7}$"),
    FI("(\\+?358|0)\\s","^(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$"),
    FR("(\\+?33|0)","^[67]\\d{8}$"),
    IL("(\\+972|0)","^([23489]|5[0248]|77)[1-9]\\d{6}"),
    HU("(\\+?36)","^(20|30|70)\\d{7}$"),
    IT("(\\+?39)","^?\\s?3\\d{2} ?\\d{6,7}$"),
    JP("(\\+?81|0)","^\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$"),
    NO("(\\+?47)","^[49]\\d{7}$"),
    BE("(\\+?32|0)4","^\\d{8}$"),
    PL("(\\+?48)","^ ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$"),
    BR("(\\+?55|0)","^[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$"),
    PT("(\\+?351)","^9[1236]\\d{7}$"),
    RU("(\\+?7|8)","^9\\d{9}$"),
    RS("(\\+3816|06)","^[\\d]{5,9}$"),
    R("(\\+?90|0)","^5\\d{9}$"),
    VN("(\\+?84|0)","^((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$");


    /**
     * 国际名称
     */
    private String national;

    /**
     * 正则表达式
     */
    private String regularExp;

    public String getNational() {
        return national;
    }



    public String getRegularExp() {
        return regularExp;
    }


    MobileRegular(String national, String regularExp) {
        this.national = national;
        this.regularExp = regularExp;
    }



}
