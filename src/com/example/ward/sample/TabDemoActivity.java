package com.example.ward.sample;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import com.example.ward.R;

//��ǩ�ؼ�Tab
public class TabDemoActivity extends TabActivity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("��ǩ�ؼ�Tab");
		TabHost tabHost=getTabHost();
		LayoutInflater.from(this).inflate(R.layout.tab, tabHost.getTabContentView(),true);
		tabHost.addTab(tabHost.newTabSpec("biaoqian1").setIndicator("��ǩtab1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("biaoqian2").setIndicator("��ǩtab2").setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("biaoqian3").setIndicator("��ǩtab3").setContent(R.id.tab3));
	}	
}
