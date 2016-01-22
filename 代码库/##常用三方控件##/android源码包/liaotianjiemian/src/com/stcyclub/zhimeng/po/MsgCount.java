package com.stcyclub.zhimeng.po;

public class MsgCount {
	String groupId;
	String uId;
	String count;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "MsgCount [groupId=" + groupId + ", uId=" + uId + ", count="
				+ count + "]";
	}
	public MsgCount(String groupId, String uId, String count) {
		super();
		this.groupId = groupId;
		this.uId = uId;
		this.count = count;
	}
	public MsgCount() {
	}
}
