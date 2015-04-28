package com.example.ward.sample;

import java.util.regex.Pattern;

import com.example.ward.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VibratorDemo extends Activity {
	Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vibrator_main);
		
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		final long[] pattern1 = getLongIntArray(getResources(), R.array.vibrator_pattern1);
		final long[] pattern2 = getLongIntArray(getResources(), R.array.vibrator_pattern2);
		final long[] pattern3 = getLongIntArray(getResources(), R.array.vibrator_pattern3);
		final long[] pattern4 = getLongIntArray(getResources(), R.array.vibrator_pattern4);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibrator(pattern1);
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibrator(pattern2);
			}
		});
		
		
		Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibrator(pattern3);
			}
		});
		
		
		Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vibrator(pattern4);
			}
		});
	}
	
	void vibrator(long[] pattern){
		if (pattern.length == 1) {
			vibrator.vibrate(pattern[0]);
		} else {
			vibrator.vibrate(pattern, -1);
		}
	}
	
	static long[] getLongIntArray(Resources r, int resId){
		int[] ar = r.getIntArray(resId);
		if (ar == null) {
			return null;
		}
		
		long[] out = new long[ar.length];
		for (int i = 0; i < ar.length; i++) {
			out[i] = ar[i];
		}
		
		return out;
	}
}	
