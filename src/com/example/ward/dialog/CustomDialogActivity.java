package com.example.ward.dialog;

import com.example.ward.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CustomDialogActivity extends Activity implements OnClickListener{
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ui_custom_dialog);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		
		//Activity dialog
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			CustomDialog1 dialog1 = new CustomDialog1(this, 15);
			dialog1.show();
			break;
		case R.id.button2:
			Intent intent = new Intent();
			intent.setClass(this, CustomDialog2.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	};
}
