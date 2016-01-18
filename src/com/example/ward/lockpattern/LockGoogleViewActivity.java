package com.example.ward.lockpattern;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.ward.R;
import com.example.ward.lockpattern.LockPatternView.Cell;
import com.example.ward.lockpattern.LockPatternView.DisplayMode;
import com.example.ward.lockpattern.LockPatternView.OnPatternListener;

public class LockGoogleViewActivity extends Activity implements OnClickListener{
	private LockPatternUtils lockPatternUtils;
	private LockPatternView lockPatternView;
	
	private Button btn_set_pwd;
	private Button btn_reset_pwd;
	private Button btn_check_pwd;
	
	private boolean opFlag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lock_googleview_main);
		
		lockPatternView = (LockPatternView)findViewById(R.id.lpv_lock);
		
		btn_reset_pwd = (Button) findViewById(R.id.btn_reset_pwd);
		btn_set_pwd = (Button) findViewById(R.id.btn_set_pwd);
		btn_check_pwd = (Button) findViewById(R.id.btn_check_pwd);
		btn_reset_pwd.setOnClickListener(this);
		btn_set_pwd.setOnClickListener(this);
		btn_check_pwd.setOnClickListener(this);
		
		lockPatternUtils = new LockPatternUtils(this);
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			
			@Override
			public void onPatternStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPatternDetected(List<Cell> pattern) {
				// TODO Auto-generated method stub
				if (opFlag) {
					int result = lockPatternUtils.checkPattern(pattern);
					if (result != 1) {
						if (result == 0) {
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
//							Toast.makeText(LockGoogleViewActivity.this, "�������", Toast.LENGTH_LONG).show();
						} else {
							lockPatternView.clearPattern();
//							Toast.makeText(LockGoogleViewActivity.this, "����������", Toast.LENGTH_LONG).show();
						}
					} else {
//						Toast.makeText(LockGoogleViewActivity.this, "������ȷ", Toast.LENGTH_LONG).show();
					}
				} else {
					lockPatternUtils.saveLockPattern(pattern);
//					Toast.makeText(LockGoogleViewActivity.this, "�����Ѿ�����", Toast.LENGTH_LONG).show();
					lockPatternView.clearPattern();
				}
			}
			
			@Override
			public void onPatternCleared() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn_reset_pwd) {
			lockPatternView.clearPattern();
			lockPatternUtils.clearLock();
		} else if (v == btn_check_pwd) {
			opFlag = true;
		} else {
			opFlag = false;
		}
	}
}
