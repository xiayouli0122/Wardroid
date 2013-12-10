package com.example.ward.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;


public class WardUtils {

	/**
	 * byte convert
	 * @param size like 3232332
	 * @return like 3.23M
	 */
	public static String getFormatSize(long size){
		if (size >= 1024 * 1024 * 1024){
			Double dsize = (double) (size / (1024 * 1024 * 1024));
			return new DecimalFormat("#.00").format(dsize) + "G";
		}else if (size >= 1024 * 1024) {
			Double dsize = (double) (size / (1024 * 1024));
			return new DecimalFormat("#.00").format(dsize) + "M";
		}else if (size >= 1024) {
			Double dsize = (double) (size / 1024);
			return new DecimalFormat("#.00").format(dsize) + "K";
		}else {
			return String.valueOf((int)size) + "B";
		}
	}
	
	public static String getFormatSize(double size){
		if (size >= 1024 * 1024 * 1024){
			Double dsize = size / (1024 * 1024 * 1024);
			return new DecimalFormat("#.00").format(dsize) + "G";
		}else if (size >= 1024 * 1024) {
			Double dsize = size / (1024 * 1024);
			return new DecimalFormat("#.00").format(dsize) + "M";
		}else if (size >= 1024) {
			Double dsize = size / 1024;
			return new DecimalFormat("#.00").format(dsize) + "K";
		}else {
			return String.valueOf((int)size) + "B";
		}
	}
	
	/**get app install date*/
	public static String getFormatDate(long date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(new Date(date));
		return dateString;
	}
	
	/** 
     * ��ʽ��ʱ�䣬������ת��Ϊ��:���ʽ 
     * @param time audio/video time like 12323312
     * @return the format time string like 00:12:23
     */  
	public static String mediaTimeFormat(long duration) {
		long hour = duration / (60 * 60 * 1000);
		String min = duration % (60 * 60 * 1000) / (60 * 1000) + "";
		String sec = duration % (60 * 60 * 1000) % (60 * 1000) + "";

		if (min.length() < 2) {
			min = "0" + duration / (1000 * 60) + "";
		}

		if (sec.length() == 4) {
			sec = "0" + sec;
		} else if (sec.length() == 3) {
			sec = "00" + sec;
		} else if (sec.length() == 2) {
			sec = "000" + sec;
		} else if (sec.length() == 1) {
			sec = "0000" + sec;
		}

		if (hour == 0) {
			return min + ":" + sec.trim().substring(0, 2);
		} else {
			String hours = "";
			if (hour < 10) {
				hours = "0" + hour;
			} else {
				hours = hours + "";
			}
			return hours + ":" + min + ":" + sec.trim().substring(0, 2);
		}
	}
	
	public static void showInfoDialog(Context context, String title, String info){
		new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(info)
		.setPositiveButton(android.R.string.ok, null)
		.create().show();
	}
	
	/**set dialog dismiss or not*/
	public static void setDialogDismiss(DialogInterface dialog, boolean dismiss){
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, dismiss);
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Drawable ת�����ֽ�*/
	public static synchronized byte[] drawableToByte(Drawable drawable) {
		if (drawable != null) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
					drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			int size = bitmap.getWidth() * bitmap.getHeight() * 4;
			// ����һ���ֽ����������,���Ĵ�СΪsize
			ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
			// ����λͼ��ѹ����ʽ������Ϊ100%���������ֽ������������
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// ���ֽ����������ת��Ϊ�ֽ�����byte[]
			byte[] imagedata = baos.toByteArray();
			return imagedata;
		}
		return null;
	}
	
	/**
	 * get parent path
	 * @param path current file path
	 * @return parent path
	 */
	public static String getParentPath(String path){
		File file = new File(path);
		return file.getParent();
	}
	
	/**
	 * io copy
	 * 
	 * @param srcPath
	 *           src file path
	 * @param desPath
	 *           des file path
	 * @return
	 * @throws Exception
	 */
	public static void fileStreamCopy(String srcPath, String desPath) throws IOException{
		File files = new File(desPath);// �����ļ�
		FileOutputStream fos = new FileOutputStream(files);
		byte buf[] = new byte[128];
		InputStream fis = new BufferedInputStream(new FileInputStream(srcPath),
				8192 * 4);
		do {
			int read = fis.read(buf);
			if (read <= 0) {
				break;
			}
			fos.write(buf, 0, read);
		} while (true);
		fis.close();
		fos.close();
	}
}
