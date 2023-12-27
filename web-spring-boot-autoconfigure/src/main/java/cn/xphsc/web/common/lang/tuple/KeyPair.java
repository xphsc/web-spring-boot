/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.common.lang.tuple;


import cn.xphsc.web.utils.RsaUtils;
import java.security.PrivateKey;
import java.security.PublicKey;


 /**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: sa 的 key pair 封装
 * @since 1.0.0
 */
public class KeyPair {
	private final java.security.KeyPair keyPair;

	public KeyPair(java.security.KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public PublicKey getPublic() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivate() {
		return keyPair.getPrivate();
	}

	public byte[] getPublicBytes() {
		return this.getPublic().getEncoded();
	}

	public byte[] getPrivateBytes() {
		return this.getPrivate().getEncoded();
	}

	public String getPublicBase64() {
		return RsaUtils.getKeyString(this.getPublic());
	}

	public String getPrivateBase64() {
		return RsaUtils.getKeyString(this.getPrivate());
	}

	@Override
	public String toString() {
		return "PublicKey=" + this.getPublicBase64() + '\n' + "PrivateKey=" + this.getPrivateBase64();
	}
}
