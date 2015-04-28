package com.example.ward.util;

import android.content.Context;
import android.util.Log;

public class LogUtil {
	
	public static void v(Context context, String msg){
		String tag = context.getClass().getName();
		Log.v(tag, msg);
	}
	
	public static void i(Context context, String msg){
		String tag = context.getClass().getName();
		Log.i(tag, msg);
	}
	
	public static void d(Context context, String msg){
		String tag = context.getClass().getName();
		Log.d(tag, msg);
	}
	
	public static void w(Context context, String msg){
		String tag = context.getClass().getName();
		Log.w(tag, msg);
	}
	
	public static void e(Context context, String msg){
		String tag = context.getClass().getName();
		Log.e(tag, msg);
	}
}
