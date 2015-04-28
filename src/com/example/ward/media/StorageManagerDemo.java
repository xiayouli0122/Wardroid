package com.example.ward.media;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class StorageManagerDemo extends Activity {

	private StorageManager mStorageManager;
	private Method mMethodGetPaths;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mStorageManager = (StorageManager) getSystemService(Activity.STORAGE_SERVICE);
		try {
			mMethodGetPaths = mStorageManager.getClass().getMethod(
					"getVolumePaths");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		String paths[] = getVolumePaths();
		
		TextView textView = new TextView(getApplicationContext());
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(params);
		
		String internalPath = Environment.getExternalStorageDirectory().toString();
		
		String text = "";
		for (int i = 0; i < paths.length; i++) {
			text += paths[i] + "\n";
		}
		textView.setText(text);
		
		setContentView(textView);
	}

	public String[] getVolumePaths() {
		String[] paths = null;
		try {
			paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
			for (int i = 0; i < paths.length; i++) {
				System.out.println("paths[" + i + "]:" + paths[i]);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paths;
	}
}
