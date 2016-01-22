package com.miaotech.health.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式校验工具类 用于用户输入的格式校验
 * 
 * @author Mr.liang
 *
 */
public class CheckApplication {

	// 邮箱验证格式
	public boolean isEmail(String email) {
		Pattern emailPattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

		Matcher matcher = emailPattern.matcher(email);
		if (matcher.find()) {
			return true;
		}
		return false;

	}

	// 手机号码验证格式
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher matcher = p.matcher(mobiles);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 用户名校验 只能是 3-10位的字母中午或数字下划线组成
	public boolean isName(String name) {
		//Pattern p = Pattern.compile("^[a-zA-z][a-zA-Z0-9_]{2,9}$");
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5a-zA-Z0-9]{3,10}$");
		Matcher matcher = p.matcher(name);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 密码校验 只能是 6-18位的字母或数字下划线组成
	public boolean isPassword(String password) {
		Pattern p = Pattern.compile("^\\w{6,18}$");
		Matcher matcher = p.matcher(password);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 车牌颜色校验 只能为汉字
	public boolean isPlate(String plate) {
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");
		Matcher matcher = p.matcher(plate);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 验证码只能为4位
	public boolean isCheck(String check) {
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher matcher = p.matcher(check);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	// 车牌只能为6位
	public boolean isPlant(String check) {
		Pattern p = Pattern.compile("^[A-Z]{1}[A-Z0-9]{5}$");
		Matcher matcher = p.matcher(check);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 身份证为15或者18位,15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
	public boolean isIdentificationNum(String idnums) {
//		Pattern p = Pattern.compile("^\\d{15}|\\d{18}$");
		Pattern p = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
		Matcher matcher = p.matcher(idnums);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 年龄为1-2位
	public boolean isAge(String age) {
		Pattern p = Pattern.compile("^\\d{0,2}$");
		Matcher matcher = p.matcher(age);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 日期 包括闰年的验证
	public boolean isTime(String time) {
		Pattern p = Pattern
				.compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$");
		Matcher matcher = p.matcher(time);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	//信联帐号格式
	public boolean isPayaccount(String payaccount){
		boolean isMail = isEmail(payaccount);
		boolean isPhoneNo = isMobileNO(payaccount);
		
		if(isMail||isPhoneNo){
			//当手机号和邮箱有一个通过时
			return true;
		}
		return false;
	}

}
