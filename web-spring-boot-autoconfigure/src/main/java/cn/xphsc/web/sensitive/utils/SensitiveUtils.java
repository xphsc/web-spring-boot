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
package cn.xphsc.web.sensitive.utils;

import cn.xphsc.web.utils.StringUtils;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SensitiveUtils {

	private static final String SYMBOL = "*";
	private static final String SIX_SYMBOL = "******";

	/**
	 * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
	 */
	public static String chineseName(final String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return StringUtils.EMPTY;
		}
		final String name = StringUtils.left(fullName, 1);
		return StringUtils.rightPad(name, StringUtils.length(fullName), SYMBOL);
	}



	/**
	 * 【身份证号】前三位 和后三位
	 *
	 * @param front
	 * @param end
	 */
	public static String idCardNum(String idCardNum, int front, int end) {
		if (StringUtils.isEmpty(idCardNum)) {
			return StringUtils.EMPTY;
		}
		if ((front + end) > idCardNum.length()) {
			return StringUtils.EMPTY;
		}
		//需要截取的不能小于0
		if (front < 0 || end < 0) {
			return StringUtils.EMPTY;
		}
		//计算*的数量
		int asteriskCount = idCardNum.length() - (front + end);
		StringBuffer asteriskStr = new StringBuffer();
		for (int i = 0; i < asteriskCount; i++) {
			asteriskStr.append(SYMBOL);
		}
		String regex = "(\\w{" + front + "})(\\w+)(\\w{" + end + "})";
		return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
	}

	/**
	 * 【固定电话 前四位，后两位
	 *
	 * @param num
	 * @return
	 */
	public static String fixedPhone(String num) {
		if (StringUtils.isBlank(num)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.left(num, 4).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 2), StringUtils.length(num), SYMBOL), "****"));
	}



	/**
	 * 【手机号码】前三位，后两位，其他隐藏，比如138******90
	 *
	 * @param num
	 * @return
	 */
	public static String mobilePhone(String num) {
		if (StringUtils.isBlank(num)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 2), StringUtils.length(num), SYMBOL), "***"));
	}

	/**
	 * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
	 * @param sensitiveSize 敏感信息长度
	 */
	public static String address(final String address, final int sensitiveSize) {
		if (StringUtils.isBlank(address)) {
			return StringUtils.EMPTY;
		}
		if(address.length() <= sensitiveSize){
			return address;
		}
		final int length = StringUtils.length(address);
		return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
	}

	/**
	 * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
	 */
	public static String email(final String email) {
		if (StringUtils.isBlank(email)) {
			return StringUtils.EMPTY;
		}
		final int index = StringUtils.indexOf(email, "@");
		if (index <= 1) {
			return email;
		} else {
			return StringUtils.rightPad(StringUtils.left(email, 1), index, SYMBOL)
				.concat(StringUtils.mid(email, index, StringUtils.length(email)));
		}
	}

	/**
	 * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6202600**********1234>
	 */
	public static String bankCardNo(final String bankCardNo) {
		if (StringUtils.isBlank(bankCardNo)) {
			return StringUtils.EMPTY;
		}
		if(bankCardNo.length() < 11){
			return idCardNum(bankCardNo, 3, 2);
		}
		return StringUtils.left(bankCardNo, 6).concat(StringUtils.removeStart(
			StringUtils.leftPad(StringUtils.right(bankCardNo, 4), StringUtils.length(bankCardNo), SYMBOL),
				SIX_SYMBOL));
	}

	/**
	 * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********>
	 */
	public static String bankJointNum(final String bankJointNum) {
		if (StringUtils.isBlank(bankJointNum)) {
			return StringUtils.EMPTY;
		}
		return StringUtils.rightPad(StringUtils.left(bankJointNum, 2), StringUtils.length(bankJointNum), SYMBOL);
	}

	/**
	 * 【密码】密码的全部字符都用*代替，比如：******
	 *
	 * @param password
	 * @return
	 */
	public static String password(String password) {
		if (StringUtils.isBlank(password)) {
			return StringUtils.EMPTY;
		}
		String pwd = StringUtils.left(password, 0);
		return StringUtils.rightPad(pwd, StringUtils.length(password), SYMBOL);
	}

	/**
	 * 通用脱敏处理
	 * @param value
	 * @return
	 */
	public static String desensitization(String value) {
		if (null == value || "".equals(value)) {
			return value;
		}
		int len = value.length();
		int pamaone = len / 2;
		int pamatwo = pamaone - 1;
		int pamathree = len % 2;
		StringBuilder stringBuilder = new StringBuilder();
		if (len <= 2) {
			if (pamathree == 1) {
				return SYMBOL;
			}
			stringBuilder.append(SYMBOL);
			stringBuilder.append(value.charAt(len - 1));
		} else {
			if (pamatwo <= 0) {
				stringBuilder.append(value.substring(0, 1));
				stringBuilder.append(SYMBOL);
				stringBuilder.append(value.substring(len - 1, len));

			} else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
				int pamafive = (len - SIZE) / 2;
				stringBuilder.append(value.substring(0, pamafive));
				for (int i = 0; i < SIZE; i++) {
					stringBuilder.append(SYMBOL);
				}
				if ((pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0)) {
					stringBuilder.append(value.substring(len - pamafive, len));
				} else {
					stringBuilder.append(value.substring(len - (pamafive + 1), len));
				}
			} else {
				int pamafour = len - 2;
				stringBuilder.append(value.substring(0, 1));
				for (int i = 0; i < pamafour; i++) {
					stringBuilder.append(SYMBOL);
				}
				stringBuilder.append(value.substring(len - 1, len));
			}
		}
		return stringBuilder.toString();
	}
	private static final int SIZE = 6;
}
