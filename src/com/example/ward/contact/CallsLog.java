package com.example.ward.contact;

import java.util.Date;

public class CallsLog {
	//数据库保存的ID
	private int id;
	
	//来电，未接，已接表示的图标ID（最后一次通话的状态）
	private int iconID;
	//同一个号码总的通话记录数，包括已接，未接，
	private int callNos;
	
	//姓名  
	private String name;
	//号码
	private String phoneNumber;
	
	//通话状态：已接，未接，拨出
	private int callType;
	
	//sim card 归属地信息
	private String simCardInfo;
	
	//通话的日期，时间,以毫秒算
//	private Date dataTime;
	private long dateTime;
	
	//一共打了多少秒
	private long duration;
	public CallsLog(int id) {
		super();
		this.id = id;
	}
	public CallsLog(int id, int iconID, int callNos, String name, String phoneNumber, int callType, String simCardInfo, long dateTime,
			long duration) {
		super();
		this.id = id;
		this.iconID = iconID;
		this.callNos = callNos;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.callType = callType;
		this.simCardInfo = simCardInfo;
		this.dateTime = dateTime;
		this.duration = duration;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIconID() {
		return iconID;
	}
	public void setIconID(int iconID) {
		this.iconID = iconID;
	}
	public int getCallNos() {
		return callNos;
	}
	public void setCallNos(int callNos) {
		this.callNos = callNos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getCallType() {
		return callType;
	}
	public void setCallType(int callType) {
		this.callType = callType;
	}
	public String getSimCardInfo() {
		return simCardInfo;
	}
	public void setSimCardInfo(String simCardInfo) {
		this.simCardInfo = simCardInfo;
	}
	public long getDateTime() {
		return dateTime;
	}
	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	
	
	
}
