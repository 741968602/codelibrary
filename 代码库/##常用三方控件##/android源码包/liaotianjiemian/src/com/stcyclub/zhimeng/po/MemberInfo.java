package com.stcyclub.zhimeng.po;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
@Table(name="MemberInfo")
public class MemberInfo implements Serializable{
	@Id(column="uid")
	private String uid;
	private String group_id;//分组的id
	private String nickname;//昵称
	private String sex;//性别
	private String thumb;//图像
	private String xpoint;//地理位置经度
	private String ypoint;//地理位置纬度
	private String computer_line;//电脑在线0.1
	private String mobile_line;//手机在线0.1
	private String mobilePhone;//父节点phone
	
	public MemberInfo() {
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getXpoint() {
		return xpoint;
	}

	public void setXpoint(String xpoint) {
		this.xpoint = xpoint;
	}

	public String getYpoint() {
		return ypoint;
	}

	public void setYpoint(String ypoint) {
		this.ypoint = ypoint;
	}

	public String getComputer_line() {
		return computer_line;
	}

	public void setComputer_line(String computer_line) {
		this.computer_line = computer_line;
	}

	public String getMobile_line() {
		return mobile_line;
	}

	public void setMobile_line(String mobile_line) {
		this.mobile_line = mobile_line;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computer_line == null) ? 0 : computer_line.hashCode());
		result = prime * result
				+ ((group_id == null) ? 0 : group_id.hashCode());
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result
				+ ((mobile_line == null) ? 0 : mobile_line.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((thumb == null) ? 0 : thumb.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((xpoint == null) ? 0 : xpoint.hashCode());
		result = prime * result + ((ypoint == null) ? 0 : ypoint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberInfo other = (MemberInfo) obj;
		if (computer_line == null) {
			if (other.computer_line != null)
				return false;
		} else if (!computer_line.equals(other.computer_line))
			return false;
		if (group_id == null) {
			if (other.group_id != null)
				return false;
		} else if (!group_id.equals(other.group_id))
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (mobile_line == null) {
			if (other.mobile_line != null)
				return false;
		} else if (!mobile_line.equals(other.mobile_line))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (thumb == null) {
			if (other.thumb != null)
				return false;
		} else if (!thumb.equals(other.thumb))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (xpoint == null) {
			if (other.xpoint != null)
				return false;
		} else if (!xpoint.equals(other.xpoint))
			return false;
		if (ypoint == null) {
			if (other.ypoint != null)
				return false;
		} else if (!ypoint.equals(other.ypoint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberInfo [uid=" + uid + ", group_id=" + group_id
				+ ", nickname=" + nickname + ", sex=" + sex + ", thumb="
				+ thumb + ", xpoint=" + xpoint + ", ypoint=" + ypoint
				+ ", computer_line=" + computer_line + ", mobile_line="
				+ mobile_line + ", mobilePhone=" + mobilePhone + "]";
	}
	
	
	
	
}
