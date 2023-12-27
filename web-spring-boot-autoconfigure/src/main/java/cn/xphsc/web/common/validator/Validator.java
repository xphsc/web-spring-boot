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
package cn.xphsc.web.common.validator;

import cn.xphsc.web.common.lang.regexp.Matches;
import cn.xphsc.web.common.lang.regexp.regular.Regular;
import cn.xphsc.web.utils.Collects;
import cn.xphsc.web.utils.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.xphsc.web.utils.StringUtils.isBlank;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class Validator {



    /**
     * 判定输入的是否是汉字
     */
    public static boolean chinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串中是否包含中文
     */
    public static boolean containChinese(String str) {
        Pattern p = Pattern.compile(Regular.CN);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 验证是否为数字字母
     */
    public static boolean numberOrString(String String) {
        if (isBlank(String)) {
            return false;
        }
        Pattern p = Pattern.compile(Regular.INT_AND_STR);
        Matcher m = p.matcher(String);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 校验一个字符是否是汉字
     */
    public static boolean chineseChar(char c) {
        try {
            return String.valueOf(c).getBytes("UTF-8").length > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 校验某个字符是否是a-z、A-Z、_、0-9
     */
    public static boolean isWord(char c) {
        Pattern p = Pattern.compile(Regular.WORD);
        Matcher m = p.matcher("" + c);
        return m.matches();
    }


    /**
     * 验证字符串是否符合正则表达式
     */
    public static boolean isRegular(String str, String regular) {
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 过滤掉中文
     */
    public static String filterChinese(String str) {
        String result = str;
        boolean Chinese = containChinese(str);
        if (Chinese) {
            StringBuffer sb = new StringBuffer();
            boolean Chineses = false;

            char chinese = 0;
            char[] charArray = str.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                Chineses = chinese(chinese);
                if (!Chineses) {
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }


    /**
     * 是否url
     */
    public static boolean isUrl(String url) {
        if (isBlank(url)) {
            return false;
        }
        return isRegular(url, Regular.URL);
    }


    /**
     * 取得给定汉字串的首字母串,即声母串
     */
    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + getFirstLetter(str.substring(i, i + 1));
        }
        return _str;
    }

    /**
     * 取得给定汉字的首字母,即声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionString(chinese, "GB2312", "ISO8859-1");
        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= Regular.LI_SEC_POS_VALUE[i]
                            && li_SecPosCode < Regular.LI_SEC_POS_VALUE[i + 1]) {
                        chinese = Regular.LC_FIRST_LETTER[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionString(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese;
    }

    /**
     * 字符串编码转换
     */
    private static String conversionString(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (Exception ex) {
        }
        return str;
    }


    public static boolean phone(String str){
        Pattern pattern = Pattern.compile(Regular.PHONE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean number(String numberstr)
    {
        Pattern pattern = Pattern.compile(Regular.NUMBER);
        Matcher matcher = pattern.matcher(numberstr);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean money(String Money)
    {
        Pattern pattern = Pattern.compile(Regular.MONEY);
        Matcher matcher = pattern.matcher(Money);
        if(matcher.matches()){
            return true;
        }
        return false;
    }


    public static boolean date(String date)
    {
        Pattern pattern = Pattern.compile(Regular.DATE);
        Matcher matcher = pattern.matcher(date);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }


    public static boolean taiwaneseCard(String taiwaneseCard)
    {
         Pattern pattern = Pattern.compile(Regular.TAIWANESE_CARD);
        Matcher matcher = pattern.matcher(taiwaneseCard);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }
    public static boolean returnPermitCard(String returnPermitCard)
    {
        Pattern pattern = Pattern.compile(Regular.RETURN_PERMIT_CARD);
        Matcher matcher = pattern.matcher(returnPermitCard);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }
    public static boolean passportCard(String passportCard)
    {
        Pattern pattern = Pattern.compile(Regular.PASSPORT_CARD);
        Matcher matcher = pattern.matcher(passportCard);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }
    public static boolean residencePermit(String residencePermit)
    {
        Pattern pattern = Pattern.compile(Regular.RESIDENCE_PERMIT);
        Matcher matcher = pattern.matcher(residencePermit);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }
    public static boolean honeKongPass(String honeKongPass)
    {
        Pattern pattern = Pattern.compile(Regular.HONE_KONG_PASS);
        Matcher matcher = pattern.matcher(honeKongPass);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }



    public static boolean idCard(String idCard) {
        if (idCard.length() == 15) {
            idCard = Validator.convertIdcarByFiteenbit(idCard);
        }
        return Validator.validateEighteenIdcard(idCard);
    }

    /**
     * <p>
     * 判断18位身份证的合法性
     * </p>
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * </p>
     * <p>
     * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
     * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
     * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     * </p>
     * <p>
     * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
     * 2 1 6 3 7 9 10 5 8 4 2
     * </p>
     * <p>
     * 2.将这17位数字和系数相乘的结果相加。
     * </p>
     * <p>
     * 3.用加出来和除以11，看余数是多少？
     * </p>
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     * <p>
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     * </p>
     */
    public static boolean validateEighteenIdcard(String idcard) {
        if (idcard.length() != 18) {
            return false;
        }
        String idcard17 = idcard.substring(0, 17);
        String idcard18Code = idcard.substring(17, 18);
        char c[] = null;
        String checkCode = "";
        if (isDigital(idcard17)) {
            c = idcard17.toCharArray();
        } else {
            return false;
        }

        if (null != c) {
            int bit[] = new int[idcard17.length()];
            bit = converCharToInt(c);
            int sum17 = 0;
            sum17 = getPowerSum(bit);

            checkCode = getCheckCodeBySum(sum17);
            if (null == checkCode) {
                return false;
            }
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
     */
    protected boolean validateFiteenIdcard(String idcard) {
        if (idcard.length() != 15) {
            return false;
        }
        if (isDigital(idcard)) {
            String provinceid = idcard.substring(0, 2);
            String birthday = idcard.substring(6, 12);
            int year = Integer.parseInt(idcard.substring(6, 8));
            int month = Integer.parseInt(idcard.substring(8, 10));
            int day = Integer.parseInt(idcard.substring(10, 12));
            boolean flag = false;
            for (String id : Regular.CITY_CODE) {
                if (id.equals(provinceid)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (birthdate == null || new Date().before(birthdate)) {
                return false;
            }

            GregorianCalendar curDay = new GregorianCalendar();
            int curYear = curDay.get(Calendar.YEAR);
            int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
            if ((year < 50 && year > year2bit)) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }

            boolean mflag = false;
            curDay.setTime(birthdate);
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    mflag = (day >= 1 && day <= 31);
                    break;
                case 2: // 公历的2月非闰年有28天,闰年的2月是29天。
                    if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
                        mflag = (day >= 1 && day <= 29);
                    } else {
                        mflag = (day >= 1 && day <= 28);
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    mflag = (day >= 1 && day <= 30);
                    break;
            }
            if (!mflag) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 将15位的身份证转成18位身份证
     */
    public static String convertIdcarByFiteenbit(String idcard) {
        String idcard18 = null;
        if (idcard.length() != 15) {
            return null;
        }
        if (isDigital(idcard)) {
            String birthday = idcard.substring(6, 12);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cday = Calendar.getInstance();
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            idcard18 = idcard.substring(0, 6) + year + idcard.substring(8);

            char c[] = idcard18.toCharArray();
            String checkCode = "";

            if (null != c) {
                int bit[] = new int[idcard18.length()];
                bit = converCharToInt(c);
                int sum17 = 0;
                sum17 = getPowerSum(bit);
                checkCode = getCheckCodeBySum(sum17);
                if (null == checkCode) {
                    return null;
                }

                idcard18 += checkCode;
            }
        } else { // 身份证包含数字
            return null;
        }
        return idcard18;
    }

    /**
     * 15位和18位身份证号码的基本数字和位数验校
     */
    protected boolean fiteencard(String idcard) {
        return idcard == null || "".equals(idcard) ? false : Pattern.matches("(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard);
    }

    /**
     * 15位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    protected boolean fiteenIdcard(String idcard) {
        return idcard == null || "".equals(idcard) ? false : Pattern.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", idcard);
    }

    /**
     * 18位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    public boolean is18Idcard(String idcard) {
        return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", idcard);
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    protected static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit
     * @return
     */
    protected static int getPowerSum(int[] bit) {
        int sum = 0;
        if (Regular.POWER.length != bit.length) {
            return sum;
        }
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < Regular.POWER.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * Regular.POWER[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     */
    protected static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }
        return checkCode;
    }

    /**
     * 是否为整数(正则)
     * @param str 待验证的字符串
     * @return true整数
     */
    public static boolean isInteger(String str) {
        if (isBlank(str)) {
            return false;
        }
        return Matches.isInteger(str);
    }

    /**
     * 是否不为整数
     * @param str 待验证的字符串
     * @return true不为整数
     */
    public static boolean isNotInteger(String str) {
        return !isInteger(str);
    }

    /**
     * 是否为浮点数数(正则)
     * @param str 待验证的字符串
     * @return true浮点数
     */
    public static boolean isDouble(String str) {
        if (isBlank(str)) {
            return false;
        }
        return Matches.isDouble(str);
    }

    /**
     * 是否不为浮点数数(正则)
     * @param str 待验证的字符串
     * @return true不为浮点数
     */
    public static boolean isNotDouble(String str) {
        return !isDouble(str);
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c
     * @return
     * @throws NumberFormatException
     */
    protected static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    public static boolean containString(Object object){
        boolean string = false;
        if (Optional.ofNullable(object).isPresent()) {
            if (object instanceof String) {
                string=true;
            }
        }
        return string;
    }

    public static <T> T notNull(T object) {
        return notNull(object, "The validated object is null", new Object[0]);
    }

    public static <T> T notNull(T object, String message, Object... values) {
        if(object == null) {
            throw new NullPointerException(String.format(message, values));
        } else {
            return object;
        }
    }
    public static boolean isNullOrEmpty(Object value) {
        return null == value?true:(value instanceof CharSequence? isBlank((CharSequence) value):(isCollectionsSupportType(value)? Collects.sizeIsEmpty(value):false));
    }
    public static void isTrue(boolean expression, String message, Object... values) {
        if(!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isTrue(boolean expression) {
        if(!expression) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }
    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }
    private static boolean isCollectionsSupportType(Object value) {
        return value instanceof Collection || value instanceof Map || value instanceof Enumeration || value instanceof Iterator || value.getClass().isArray();
    }

}
