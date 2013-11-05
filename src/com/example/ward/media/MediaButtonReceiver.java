package com.example.ward.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class MediaButtonReceiver extends BroadcastReceiver {
	private static final String TAG = "MediaButtonReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//���actiuon
		String action = intent.getAction();
		//���keyevent����
		KeyEvent keyEvent = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
		Log.e(TAG,  "Action ---->" + action + "  KeyEvent----->"+ keyEvent.toString());
		if (Intent.ACTION_MEDIA_BUTTON.equals(action)) {
			
			//���keycode
			int keyCode = keyEvent.getKeyCode();
			System.out.println("keyCode=" + keyCode);
		}
	}

}
