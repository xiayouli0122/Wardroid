package com.example.ward.lockpattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

//�Զ���view��ʵ������google�ľŹ������
public class LockCustomViewActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		View view = new NinePointLineView(LockCustomViewActivity.this);
		setContentView(view);
	}
}
