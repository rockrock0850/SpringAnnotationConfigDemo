package com.promeritage.annotation.demo.common.utils;

public class Constant {

	/*
	 * 系統屬性
	 */

	public final static String IV = ShareTool.getProperty("aes.iv");
	public final static String KEY = ShareTool.getProperty("aes.key");

	/*
	 * Jsp path
	 */

	public final static String SYSTEM_INDEX = "demo/index";
	public final static String SYSTEM_LOGIN = "demo/login";

	/*
	 * Date Pattern
	 */
	public final static String DATE_PATTERN1 = "yyyy/MM/dd";

	/*
	 * 系統狀態
	 */

	public enum Status {
		SUCCESS("200", "Success"), UNKNOWN_ERROR("999", "UnKnown Error");

		private String code;
		private String message;

		private Status(String message) {
			this.message = message;
		}

		private Status(String code, String message) {
			this.code = code;
			this.message = message;
		}

		/*
		 * setter getter
		 */

		public String getMessage() {
			return message;
		}

		public String getCode() {
			return code;
		}
	}
}
