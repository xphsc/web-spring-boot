package cn.xphsc.web.common.lang.crypto.enums;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  DigestAlgorithm
 * @since 2.0.4
 */
public enum DigestAlgorithm  {

	/** 国密SM3摘要算法 */
	SM3("SM3"),

	MD5("MD5"),
	MD2("MD2"),

	SHA1("SHA-1"),
	SHA256("SHA-256"),
	SHA384("SHA-384"),
	SHA512("SHA-512"),
	SHA512_224("SHA-512/224"),
	SHA512_256("SHA-512/256"),
	SHA3_224("SHA3-224"),
	SHA3_256("SHA3-256"),
	SHA3_384("SHA3-384"),
	SHA3_512("SHA3-512"),


	;

	private final String code;

	DigestAlgorithm(String code) {
		this.code = code;
	}

	public String code() {
		return this.code;
	}
}
