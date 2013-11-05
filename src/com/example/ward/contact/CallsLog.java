package com.example.ward.contact;

import java.util.Date;

public class CallsLog {
	//���ݿⱣ���ID
	private int id;
	
	//���磬δ�ӣ��ѽӱ�ʾ��ͼ��ID�����һ��ͨ����״̬��
	private int iconID;
	//ͬһ�������ܵ�ͨ����¼���������ѽӣ�δ�ӣ�
	private int callNos;
	
	//����  
	private String name;
	//����
	private String phoneNumber;
	
	//ͨ��״̬���ѽӣ�δ�ӣ�����
	private int callType;
	
	//sim card ��������Ϣ
	private String simCardInfo;
	
	//ͨ�������ڣ�ʱ��,�Ժ�����
//	private Date dataTime;
	private long dateTime;
	
	//һ�����˶�����
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
