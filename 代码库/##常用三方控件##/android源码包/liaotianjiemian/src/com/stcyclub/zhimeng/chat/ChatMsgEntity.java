
package com.stcyclub.zhimeng.chat;

public class ChatMsgEntity {
	private String db_id;
    private String name;
    private String date;
    private String text;
    private String status;//·¢ËÍ×´Ì¬
    
    private boolean isComMeg = true;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }

    public ChatMsgEntity() {
    }

	
	public String getDb_id() {
		return db_id;
	}

	public void setDb_id(String db_id) {
		this.db_id = db_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ChatMsgEntity [db_id=" + db_id + ", name=" + name + ", date="
				+ date + ", text=" + text + ", status=" + status
				+ ", isComMeg=" + isComMeg + "]";
	}

	public ChatMsgEntity(String db_id, String name, String date, String text,
			String status, boolean isComMeg) {
		super();
		this.db_id = db_id;
		this.name = name;
		this.date = date;
		this.text = text;
		this.status = status;
		this.isComMeg = isComMeg;
	}

 

}
