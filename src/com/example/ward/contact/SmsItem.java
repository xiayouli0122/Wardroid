package com.example.ward.contact;

public class SmsItem {
	String address;  //收件人
    String person;  //
    String date;  //收发件日期
    String protocol;  
    String read;  //1：已读，0：未读
    String status;  
    String type;  //1：收件箱 2：发件箱
    String reply_path_present;  
    String body;  //短信内容
    String locked;  //
    String error_code;  
    String seen;//已读，未读
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReply_path_present() {
		return reply_path_present;
	}
	public void setReply_path_present(String reply_path_present) {
		this.reply_path_present = reply_path_present;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getSeen() {
		return seen;
	}
	public void setSeen(String seen) {
		this.seen = seen;
	}  
    
    
}
