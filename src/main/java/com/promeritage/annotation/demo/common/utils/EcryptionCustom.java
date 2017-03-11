package com.promeritage.annotation.demo.common.utils;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class EcryptionCustom {
	private static final int RADIX = 16;
	private static final String SEED = "092819801109230114";

	/**
	 * 加密(自訂格式)
	 * 
	 * @param text
	 * @return
	 */
	public static final String encrypt(String text) throws Exception {
		if (StringUtils.isBlank(text)) {
			return "";
		}

		BigInteger bi_text = new BigInteger(text.getBytes());
		BigInteger bi_seed = new BigInteger(SEED);
		BigInteger bi_xor = bi_seed.xor(bi_text);

		return bi_xor.toString(RADIX);
	}

	/**
	 * 解密(自訂格式)
	 * 
	 * @param encryptedText
	 * @return
	 */
	public static final String decrypt(String encryptedText) throws Exception {
		if (StringUtils.isBlank(encryptedText)) {
			return "";
		}

		BigInteger bi_seed = new BigInteger(SEED);
		BigInteger bi_xor = new BigInteger(encryptedText, RADIX);
		BigInteger bi_text = bi_xor.xor(bi_seed);

		return new String(bi_text.toByteArray());
	}

	public static void main(String args[]) throws Exception {
		String text = "{\"osType\":\"ios\"}";
		System.out.println(encrypt(text));
	}
}
