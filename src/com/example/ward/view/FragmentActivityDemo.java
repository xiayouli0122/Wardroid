package com.example.ward.view;

import com.example.ward.R;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class FragmentActivityDemo extends FragmentActivity {
	private LinearLayout container = null;
	private Button button1,button2,button3,button4;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activitygroup);
		
container = (LinearLayout) findViewById(R.id.container1);
		
		button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				container.removeAllViews();
				getSupportFragmentManager().beginTransaction().add(R.id.container1, new CountingFragment()).commit();
			}
		});
		button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				container.removeAllViews();
				getSupportFragmentManager().beginTransaction().add(R.id.container1, new CursorFragment()).commit();
			}
		});
		button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				container.removeAllViews();
				getSupportFragmentManager().beginTransaction().add(R.id.container1, new FragmentPagerSupport.ArrayListFragment()).commit();
			}
		});
		button4 = (Button)findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		container.removeAllViews();
		getSupportFragmentManager().beginTransaction().add(R.id.container1, new CountingFragment()).commit();
	}
}
