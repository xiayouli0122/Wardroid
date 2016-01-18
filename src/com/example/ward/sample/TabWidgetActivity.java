package com.example.ward.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.example.ward.R;

public class TabWidgetActivity extends TabActivity {

	private TabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_widget);
		
		mTabHost=getTabHost();
		
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB1",getResources().getDrawable(R.drawable.img1)).setContent(R.id.textview1));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("TAB2",getResources().getDrawable(R.drawable.img2)).setContent(R.id.textview2));
		mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("TAB3",getResources().getDrawable(R.drawable.img3)).setContent(R.id.textview3));
		
	    mTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
	    
	    mTabHost.setCurrentTab(0);
	    
	    mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				Dialog dialog=new AlertDialog.Builder(TabWidgetActivity.this)
				.setTitle("TabHost")
				.setMessage(tabId+"****")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(TabWidgetActivity.this, "OK", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(TabWidgetActivity.this, "cancel", Toast.LENGTH_LONG).show();
						dialog.cancel();
					}
				})
				.create();
				dialog.show();
			}
		});
	}

}
