package com.example.ward.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ward.R;

public class ProgressBar1 extends Activity {

	private boolean mIsDestroy = false;
	private boolean mIsPause = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pb1_main);

		final ProgressBarWithText progressBar = (ProgressBarWithText) findViewById(R.id.pbtext);
		progressBar.setMax(100);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						int n = 0;
						while (!mIsDestroy && n <= 100) {
							// if pause
							if (!mIsPause) {
								n = n + 5;
								progressBar.setProgress(n);
								progressBar.setText(n + "/" + "100");
								SystemClock.sleep(500);
							}
						}
					}
				}).start();
			}
		});

		final Button pauseButton = (Button) findViewById(R.id.button1);
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIsPause) {
					mIsPause = false;
					pauseButton.setText("Pause");
				} else {
					mIsPause = true;
					pauseButton.setText("Resume");
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mIsDestroy = true;
	}
}
