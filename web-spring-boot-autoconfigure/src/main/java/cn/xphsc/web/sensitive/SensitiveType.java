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
package cn.xphsc.web.sensitive;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public enum SensitiveType {
	/**
	 * 中文名
	 */
	CHINESE_NAME,
	/**
	 * 身份证号
	 */
	ID_CARD,
	/**
	 * 座机号
	 */
	FIXED_PHONE,
	/**
	 * 手机号
	 */
	MOBILE_PHONE,
	/**
	 * 密码
	 */
	PASSWORD,
	/**
	 * 地址
	 */
	ADDRESS,
	/**
	 * 电子邮件
	 */
	EMAIL,
	/**
	 * 银行卡
	 */
	BANK_CARD,
	/**
	 * 公司开户银行联号
	 */
	SHOPS_CODE,
	/**
	 * 自定义正则过滤
	 */
	REGEXP,
	
	/**
	 * 空值
	 */
	NULL,
	/**
	 * 自定义处理
	 */
	CUSTOM,

  /**
   * 通用脱敏处理
  */
	AUTO;
}
