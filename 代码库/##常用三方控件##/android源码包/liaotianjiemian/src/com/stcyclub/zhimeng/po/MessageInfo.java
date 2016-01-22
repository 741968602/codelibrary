package com.stcyclub.zhimeng.po;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "MessageInfo")
public class MessageInfo implements Serializable {
	@Id(column = "_id")
	private int _id;
	private String msg_from;
	private String msg_to;
	private String msg_content;
	private String msg_acceptTime;
	private String msg_readStatus;

	public MessageInfo(int _id, String msg_from, String msg_to,
			String msg_content, String msg_acceptTime, String msg_readStatus) {
		super();
		this._id = _id;
		this.msg_from = msg_from;
		this.msg_to = msg_to;
		this.msg_content = msg_content;
		this.msg_acceptTime = msg_acceptTime;
		this.msg_readStatus = msg_readStatus;
	}

	@Override
	public String toString() {
		return "MessageInfo [_id=" + _id + ", msg_from=" + msg_from
				+ ", msg_to=" + msg_to + ", msg_content=" + msg_content
				+ ", msg_acceptTime=" + msg_acceptTime + ", msg_readStatus="
				+ msg_readStatus + "]";
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getMsg_from() {
		return msg_from;
	}

	public void setMsg_from(String msg_from) {
		this.msg_from = msg_from;
	}

	public String getMsg_to() {
		return msg_to;
	}

	public void setMsg_to(String msg_to) {
		this.msg_to = msg_to;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getMsg_acceptTime() {
		return msg_acceptTime;
	}

	public void setMsg_acceptTime(String msg_acceptTime) {
		this.msg_acceptTime = msg_acceptTime;
	}

	public String getMsg_readStatus() {
		return msg_readStatus;
	}

	public void setMsg_readStatus(String msg_readStatus) {
		this.msg_readStatus = msg_readStatus;
	}

	public MessageInfo() {

	}
}
