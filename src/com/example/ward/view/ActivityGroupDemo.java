package com.example.ward.view;

import com.example.ward.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityGroupDemo extends ActivityGroup {
	private LinearLayout container = null;
	
	private Button button1,button2,button3,button4;
	private FragmentManager fm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activitygroup);
		
		fm =getFragmentManager();
		container = (LinearLayout) findViewById(R.id.container1);
		
		button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		button4 = (Button)findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		launchActivity("View1", CountingFragment.class);
	}
	
	 private void launchActivity(String id, Class<?> activityClass) {
	        container.removeAllViews();
	        
	        Intent intent =  new Intent(ActivityGroupDemo.this, activityClass);
	        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        
	        Window window = getLocalActivityManager().startActivity(id, intent);
	        View view = window.getDecorView();
	        container.addView(view);
	    }
}
