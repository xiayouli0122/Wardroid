package com.example.ward.view;

//import com.andreabaccega.widget.FormEditText;
import com.example.ward.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EdittextValidDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittext_valid);
		
//		final FormEditText feEditText = (FormEditText) findViewById(R.id.et);
//		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (feEditText.testValidity()) {
//					Toast.makeText(EdittextValidDemo.this, "Nice", Toast.LENGTH_SHORT).show();
//				}
			}
		});
	}
	
}
