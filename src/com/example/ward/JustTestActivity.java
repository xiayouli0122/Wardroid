package com.example.ward;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.example.ward.util.WardUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JustTestActivity extends Activity {
	
	BroadcastReceiver myReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			System.out.println("onReceive:" + intent.getAction());
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.justtest_main);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(myReceiver, filter);
		
		Button mButton = (Button)findViewById(R.id.button);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//				startActivity(intent);
//				Intent intent = new Intent();
//				intent.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
//				startActivity(intent);
				int a = 15/0;
				
				boolean isEnable = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
				Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnable ? 0 :1);
				Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
				intent.putExtra("state", !isEnable);
				sendBroadcast(intent);
			}
		});
		
		EditText editText = (EditText)findViewById(R.id.content_edit);
		editText.addTextChangedListener(mTextWatcher);
		
		System.out.println("1:" + Environment.getDataDirectory().getPath());
		System.out.println("2:" + Environment.getExternalStorageDirectory().getAbsolutePath());
		System.out.println("3:" + Environment.getRootDirectory().getAbsolutePath());
		
		String mnt = "/mnt";
		String[] files = new File(mnt).list();
		for (int i = 0; i < files.length; i++) {
			System.out.println("files[" + i + "]:" + files[i]);
		}
		
		File dataFile = Environment.getDataDirectory();
		StatFs stat = new StatFs(dataFile.getPath());
		long blocksize = stat.getBlockSize();
		long availableblocks = stat.getAvailableBlocks();
		System.out.println("data.avaible=" + WardUtils.getFormatSize(availableblocks * blocksize));
		long totalBlocks = stat.getBlockCount();
		System.out.println("data.total=" + WardUtils.getFormatSize(totalBlocks * blocksize));
		
		File sdcardFile = null;
		if (externalMemoryAvaivble()) {
			sdcardFile = Environment.getExternalStorageDirectory();
			stat = new StatFs(sdcardFile.getPath());
			blocksize = stat.getBlockSize();
			
			availableblocks = stat.getAvailableBlocks();
			totalBlocks = stat.getBlockCount();
			
			System.out.println("sdcard.avaible=" + WardUtils.getFormatSize(availableblocks * blocksize));
			System.out.println("sdcard.total=" + WardUtils.getFormatSize(totalBlocks * blocksize));
		}
		
	}
	
	public boolean externalMemoryAvaivble(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		return super.onCreateOptionsMenu(menu);
	}
	
	private TextWatcher mTextWatcher = new TextWatcher() {
		private int mStart;
		private int mEnd;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			System.out.println("count=" + count);
			System.out.println("s:" + s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			System.out.println("s.len=" + s.toString().length());
		}
	};
}
