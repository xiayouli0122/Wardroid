package com.example.ward.lockpattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

//自定义view，实现类似google的九宫格解锁
public class LockCustomViewActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		View view = new NinePointLineView(LockCustomViewActivity.this);
		setContentView(view);
	}
}
