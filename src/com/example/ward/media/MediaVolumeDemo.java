package com.example.ward.media;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ward.R;

public class MediaVolumeDemo extends Activity implements OnClickListener {
	private Button mPlay,mStop,mVolumeUp,mVolumeDown;
	
	private MediaVolumeService mVolumeService;
	private AudioManager mAudioManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.media_volume);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		mPlay = (Button)findViewById(R.id.play);
		mStop = (Button)findViewById(R.id.stop);
		mVolumeUp = (Button)findViewById(R.id.volume_up);
		mVolumeDown = (Button)findViewById(R.id.volume_down);
		
		mPlay.setOnClickListener(this);
		mStop.setOnClickListener(this);
		mVolumeUp.setOnClickListener(this);
		mVolumeDown.setOnClickListener(this);
		
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.example.ward.MUSIC");
		switch (v.getId()) {
		case R.id.play:
			mAudioManager.requestAudioFocus(new OnAudioFocusChangeListener() {
				@Override
				public void onAudioFocusChange(int focusChange) {
					// TODO Auto-generated method stub
				}
			}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
			startService(intent);
			break;
		case R.id.stop:
			stopService(intent);
			break;
		case R.id.volume_up:
			//第一个参数：音频流；第二个参数：音量上升；第三个参数：显示UI
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			break;
		case R.id.volume_down:
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			break;
		default:
			break;
		}
	}
}
