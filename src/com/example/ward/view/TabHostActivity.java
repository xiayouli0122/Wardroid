package com.example.ward.view;

import com.example.ward.R;

import android.app.TabActivity;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity{
	private TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_main);
		init();
	}
	
	private void init(){
		tabHost = getTabHost();
		//页面1
		TabSpec spec1 = tabHost.newTabSpec("1");	
		spec1.setIndicator("1", getResources().getDrawable(R.drawable.alert_dialog_icon));
		Intent intent1 = new Intent(this,Tab1Activity.class);
		spec1.setContent(intent1);
		
		//页面2
		TabSpec spec2 = tabHost.newTabSpec("2");	
		spec2.setIndicator("2", getResources().getDrawable(R.drawable.alert_dialog_icon));
		Intent intent2 = new Intent(this,Tab2Activity.class);
		spec2.setContent(intent2);
		
		//页面2
		TabSpec spec3 = tabHost.newTabSpec("3");	
		spec3.setIndicator("3", getResources().getDrawable(R.drawable.alert_dialog_icon));
		Intent intent3 = new Intent(this,Tab3Activity.class);
		spec3.setContent(intent3);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	private GestureDetector detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// TODO Auto-generated method stub
			if ((e2.getRawX() - e1.getRawX()) > 80) {
//				showNext();
				showPre();
				return true;
			}
			
			if ((e1.getRawX() - e2.getRawX()) > 80) {
//				showPre();
				showNext();
				return true;
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	});
	
	/**
	 * 当前页面索引
	 */
	int i = 0;
	
	/**
	 * 显示下一个页面
	 */
	protected void showNext() {
		// 三元表达式控制3个页面的循环.
		tabHost.setCurrentTab(i = i == 2 ? i = 0 : ++i);
		Log.i("kennet", i + "");

	}

	/**
	 * 显示前一个页面
	 */
	protected void showPre() {
		// 三元表达式控制3个页面的循环.
		tabHost.setCurrentTab(i = i == 0 ? i = 2 : --i);
	}
}
