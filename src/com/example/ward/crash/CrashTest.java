package com.example.ward.crash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ward.R;


public class CrashTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.crashtest_layout);
		
		Button button = (Button) findViewById(R.id.crash_btn);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int i = 11 / 0;
			}
		});
	}
}
