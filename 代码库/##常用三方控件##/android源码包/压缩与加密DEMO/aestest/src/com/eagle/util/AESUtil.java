package com.eagle.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 数据的加密与解密算法
 * 
 */
public class AESUtil {

	public static String passowordString = "nishibendan00000";// key

	public static void main(String[] args) throws Exception// TODO　测试
	{

		String contentString = "1234abcd汉字def+-*/";
		String decontentString = "559kwix0RJWHU34CJvHSqRyABDNSZqW2kgFh2QhG2NI=";
		String aString = getAesBase(contentString);
		String bString = deAesBase(decontentString);
		System.out.println(aString);
		System.out.println(bString);
		// System.err.println(contentString.getBytes().length);
		// System.out.println("加密测试"+getBase64AESCode(encodeContent(contentString),passowordString));
		// System.out.println("解密测试"+decodeResult(decode("559kwix0RJWHU34CJvHSqRyABDNSZqW2kgFh2QhG2NI=",passowordString)));
	}

	/**
	 * AES加密且转base64加补位的最终方法
	 * 
	 * @param contentString
	 * @param passowordString
	 * @return
	 */
	public static String getAesBase(String contentString) {
		String pass = passowordString;
		String aString = "加密测试" + getBase64AESCode(encodeContent(contentString), pass);
		return aString;
	}

	/**
	 * AES解密且转base64加补位的最终方法
	 * 
	 * @param contentString
	 * @param passowordString
	 * @return
	 */
	public static String deAesBase(String decontentString) {
		String pass = passowordString;
		String bString = "解密测试" + decodeResult(decode(decontentString, pass));
		return bString;
	}

	/**
	 * 加密内容不足16倍数位，自动补位
	 * 
	 * @param content
	 * @return
	 */
	public static String encodeContent(String content) {
		StringBuilder sbBuilder = new StringBuilder(content);
		int count;
		count = content.getBytes().length;
		int recount = (count / 16 + 1) * 16;
		for (int i = 0; i < recount - count; i++) {
			sbBuilder.append("0");
		}
		return sbBuilder.toString();
	}

	/**
	 * 解密内容不足16倍数位，自动补位
	 * 
	 * @param result
	 * @return
	 */
	public static String decodeResult(String result) {
		return result.replaceAll("0+$", "");
	}

	/**
	 * 返回MD5码
	 * 
	 * @param parStr
	 *            要编码的字符串
	 * @return 返回值
	 */
	public static String getMD5Code(String parStr) {
		return DigestUtils.md5Hex(parStr);
	}

	public static String getBase64AESCode(String content, String password) {
		return getBase64Code(getAESCode(content, password));
	}

	/**
	 * 对content进行AES加密，AES算法的模式及补码方式为ECB/PKCS5Padding，加密内容采用UTF-8编码
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] getAESCode(String content, String password) {
		try {
			String ivString = "1234567812345678";
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			byte[] byteContent = content.getBytes("utf-8");
			IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
			// ECB
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param base64
	 * @param password
	 * @return
	 */
	private static String decode(String base64, String password) {
		try {
			return new String(decodeAESCode(Base64.decode(base64), password), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param code
	 * @param password
	 * @return
	 */
	private static byte[] decodeAESCode(byte[] code, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec("1234567812345678".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key, iv);// 初始化
			byte[] result = cipher.doFinal(code);
			return result; // 加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DECRYPT_MODE解密 进行base64加密
	 * 
	 * @param aesCode
	 * @return
	 */
	public static String getBase64Code(byte[] aesCode) {
		return Base64.encode(aesCode);
	}

}
