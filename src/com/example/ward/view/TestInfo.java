package com.example.ward.view;

public class TestInfo {
	private static boolean mIsChecked = false;
	
	public static boolean isChecked(){
		return mIsChecked;
	}
	
	public static void setChecked(boolean state){
		mIsChecked = state;
	}
}
