package com.example.ward.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 通过writeToParcel将你的对象映射成Parcel对象，再通过createFromParcel将Parcel对象映射成你的对象。也可以将Parcel看成是一个流，
 * 通过writeToParcel把对象写到流里面，在通过createFromParcel从流里读取对象，只不过这个过程需要你来实现，因此写的顺序和读的顺序必须一致。
 * @author lianxi
 */
public class ParcelableDemo implements Parcelable {
	
	//这里定义了两个变量，读和写的顺序要要保持一致
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
		//把数据写到Parcel中，按顺序先写id,再写name
		dest.writeInt(id);
		dest.writeString(name);
	}

}
