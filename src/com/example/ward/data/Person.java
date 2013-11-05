package com.example.ward.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ͨ��writeToParcel����Ķ���ӳ���Parcel������ͨ��createFromParcel��Parcel����ӳ�����Ķ���Ҳ���Խ�Parcel������һ������
 * ͨ��writeToParcel�Ѷ���д�������棬��ͨ��createFromParcel�������ȡ����ֻ�������������Ҫ����ʵ�֣����д��˳��Ͷ���˳�����һ�¡�
 * @author lianxi
 */
public class Person implements Parcelable {
	
	//���ﶨ������������������д��˳��ҪҪ����һ��
	private int id;
	private String name;
	
	public Person(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		//������д��Parcel�У���˳����дid,��дname
		dest.writeInt(id);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			//��Parecel�ж�ȡ���ݣ�����Person����
			return new Person(source.readInt(), source.readString());
		}

		@Override
		public Person[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Person[size];
		}
	};

}
