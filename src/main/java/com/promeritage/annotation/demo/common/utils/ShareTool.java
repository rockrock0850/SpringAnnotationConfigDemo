package com.promeritage.annotation.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.validation.FieldError;

import com.promeritage.annotation.demo.module.vo.SimpleFieldVO;

import net.sf.json.JSONObject;

public class ShareTool {
	private static Logger log = Logger.getLogger(ShareTool.class);

	/**
	 * 物件轉字串(包括List)
	 * 
	 * @param object
	 * @return
	 */
	public static String toStringBuilder(Object object) {
		try {
			if (object instanceof String || object instanceof Map || object instanceof Integer || object instanceof Double
					|| object instanceof Long) {
				return object.toString();
			}

			if (object instanceof List) {
				List<?> list = (List<?>) object;

				if (list == null || list.isEmpty()) {
					return "";
				}

				/*
				 * List 最多10筆
				 */
				int size = list.size() > 10 ? 10 : list.size();
				list = list.subList(0, size);

				List<String> dataList = new ArrayList<>();
				dataList.add("size[" + String.valueOf(list.size()) + "]");
				for (Object o : list) {
					dataList.add(toStringBuilder(o));
				}

				return StringUtils.join(dataList, ", ");
			}
		} catch (Exception e) {
			log.error(e, e);
		}

		return ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 物件轉Json字串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonStringBuilder(Object object) {
		JSONObject json = new JSONObject();
		try {
			json = JSONObject.fromObject(object);
		} catch (Exception e) {
			log.error(e, e);
		}
		return json.toString();
	}

	/**
	 * 取得系統屬性
	 * 
	 * @param prop
	 * @return
	 */
	public static String getProperty(String prop) {
		return ResourceBundle.getBundle("system").getString(prop);
	}

	/**
	 * 取得特定的系統屬性
	 * 
	 * @param file
	 * @param prop
	 * @return
	 */
	public static String getProperty(String file, String prop) {
		return ResourceBundle.getBundle(file).getString(prop);
	}

	/**
	 * Base64 decode->AES decode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String iv, String key, String data) throws Exception {
		try {
			if (StringUtils.isBlank(iv) || StringUtils.isBlank(key) || StringUtils.isBlank(data)) {
				return data;
			}

			byte[] decode = Base64.getDecoder().decode(data);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] doFinal = cipher.doFinal(decode);

			data = new String(doFinal, "utf-8");
		} catch (Exception e) {
			log.error(e, e);
		}
		return data;
	}

	/**
	 * AES encode->Base64 encode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String iv, String key, String data) {
		try {
			if (StringUtils.isBlank(iv) || StringUtils.isBlank(key) || StringUtils.isBlank(data)) {
				return data;
			}

			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ciper.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] doFinal = ciper.doFinal(data.getBytes());// data.toString()

			data = Base64.getEncoder().encodeToString(doFinal);
		} catch (Exception e) {
			log.error(e, e);
		}
		return data;
	}

	/**
	 * 簡化FieldError類別的欄位訊息
	 * 
	 * @param list
	 * @return
	 */
	public static List<SimpleFieldVO> simpleFieldError(List<FieldError> list) {
		List<SimpleFieldVO> fieldList = new ArrayList<SimpleFieldVO>();
		try {
			for (FieldError fieldError : list) {
				SimpleFieldVO field = new SimpleFieldVO();
				field.setField(fieldError.getField());
				field.setMessage(fieldError.getDefaultMessage());
				fieldList.add(field);
			}
		} catch (Exception e) {
			log.error(e, e);
		}

		return fieldList;
	}

	/**
	 * 
	 * @param baseUri
	 * @param img
	 * @return
	 */
	public static String getImgSrc(String baseUri, String img) {
		String src = "";
		try {
			if (StringUtils.isNotBlank(img)) {
				src = baseUri + img.substring(img.indexOf("\\resources"));
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return src;
	}

	/**
	 * 通過隨機數乘法運算間接實現補位
	 * 
	 * @return
	 */
	public static int getNextIntT() {
		int n = 0;
		while (n < 1000) {
			n = (int) (Math.random() * 10000);
		}
		return n;
	}

	/**
	 * 取給定位數隨機數(限制2~8位)
	 * 
	 * @param radix
	 *            幾位數的亂數
	 * @return rtnValue
	 */
	public static int getRandomValue(int radix) {
		try {
			radix--;

			return ((int) ((Math.random() + 1) * (radix < 2 ? 100 : radix > 9 ? 100 : Math.pow(10D, radix))));
		} catch (Exception e) {
			log.error(e, e);
		}

		return radix;
	}

	public static Date toDate(String str, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = sdf.parse(str);

			return date;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}

	public static String toStirng(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String str = sdf.format(date);

			return str;
		} catch (Exception e) {
			log.error(e, e);
		}

		return null;
	}
}
