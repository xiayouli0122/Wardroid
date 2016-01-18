package com.example.ward.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lianxi
 */
public class ParcelableDemo implements Parcelable {
	
	private int id;
	private String name;
	
	public ParcelableDemo(int id, String name){
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
		dest.writeInt(id);
		dest.writeString(name);
	}

}
