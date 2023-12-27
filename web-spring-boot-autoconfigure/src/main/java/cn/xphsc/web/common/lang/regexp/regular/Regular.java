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
package cn.xphsc.web.common.lang.regexp.regular;




/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 正则
 * @since 1.0.0
 */
public class Regular {

    /**
     * 数字偶然字母
     */
    public static final String INT_AND_STR = "^[A-Za-z0-9]+$";

    public static final String INT_AND_STR_AND = "^[0-9a-zA-Z_]{1,}$";

    /**
     * 中文
     */
    public static final String CN = "[\\u4e00-\\u9fa5]";

    /**
     * a-z、A-Z、_、0-9
     */
    public static final String WORD = "^/w+$";

    public static final String IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    public static final String URL = "^(https?|ftp|file|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";


    /**
     * 助记词正则匹配：首位与末位为字母，中间用“_”连接；eg:a_A_b
     */
    public static final String MARK_REGULAR = "^[a-zA-Z]+(\\_?[a-zA-Z])*$";


    /**
     * 特殊字符正则
     */
    public static final String SPECIAL = "[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";


    /**
     * ip的cidr正则
     */
    public static final String CIDR = "^(?:(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\/([1-9]|[1-2]\\d|3[0-2])$";


    /**
     * qq号正则，目前10位
     */
    public static final String QQ = "[1-9][0-9]{4,10}";

    /**
     * 匹配中文，英文字母和数字及_:
     */
    public static final String NAME = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$";

    public static final String SQL =  "('.+--)|(--)|(\\\\|)|(%7C)";

    public static final String JS =  "<script>";

    public static final int RANDOM_BASE = 10;

    public static final char[] STRS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final char[] INTS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    public static final int[] LI_SEC_POS_VALUE = {1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    public static final String[] LC_FIRST_LETTER = {"a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z"};

    /**
     * 省，直辖市代码表： { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
     * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
     * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
     * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
     * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
     * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
     */
    public static final String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" }, { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" }, { "22", "吉林" }, { "23", "黑龙江" },
            { "31", "上海" }, { "32", "江苏" }, { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" }, { "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
            { "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" }, { "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" }, { "61", "陕西" }, { "62", "甘肃" },
            { "63", "青海" }, { "64", "宁夏" }, { "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" }, { "91", "国外" } };

    public static final String CITY_CODE[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52",
            "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91" };

    // 每位加权因子
    public static final int POWER[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    public static final String DATE= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/]((((0?[13578])|(1[02]))[\\-\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/]((((0?[13578])|(1[02]))[\\-\\/]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/]((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    public static final String MONEY = "^\\d+(\\.\\d{1,2})?$";

    public static final  String NUMBER="^\\d+$";

    public static final String PHONE= "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    //台胞证
    public static final String TAIWANESE_CARD = "^\\d{8}$";//表示台胞证的正则表达式

    //回乡证
    public static final String RETURN_PERMIT_CARD = "^[H|M]{1}\\d{10}$";//表示回乡证的正则表达式

    //护照
    public static final String PASSPORT_CARD = "^(P\\d{7}|G\\d{8}|S\\d{7,8}|D\\d+|1[4,5]\\d{7})$";//表示护照的正则表达式


    //居住证
    public static final String RESIDENCE_PERMIT = "^[A-Z0-9]{1,}$";//表示居住证的正则表达式



    //港澳通行证

    public static final String HONE_KONG_PASS = "/^[HMhm]{1}([0-9]{10}|[0-9]{8})$/";//表示港澳通行证的正则表达式


}
