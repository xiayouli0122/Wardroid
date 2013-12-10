package com.example.ward.animation;

import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityAnimationDemo  extends Activity{
	private Button openButton;
	private Spinner mSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_main);
		
		setTitle("Activity动画切换效果测试");
		
		openButton = (Button)findViewById(R.id.openButton);
		mSpinner = (Spinner)findViewById(R.id.animation_sp);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityAnimationDemo.this, ActivityAnimationOther2.class);
				startActivity(intent);
			}
		});
		
		//通过资源文件获取填充内容
		String[] array = getResources().getStringArray(R.array.animation_type);
		
		List<String> list = new ArrayList<String>();
		//把数组内容填充到集合中
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		
		ArrayAdapter<String> animType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		animType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(animType);
		mSpinner.setSelection(0);
		
		openButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ActivityAnimationDemo.this, ActivityAnimationOther.class);
				startActivity(intent);
				
				switch (mSpinner.getSelectedItemPosition()) {
				case 0:
					overridePendingTransition(R.anim.fade, R.anim.hold);
					break;
				case 1:
					overridePendingTransition(R.anim.my_scale_action,
							R.anim.my_alpha_action);
					break;
				case 2:
					overridePendingTransition(R.anim.scale_rotate,
							R.anim.my_alpha_action);
					break;
				case 3:
					overridePendingTransition(R.anim.scale_translate_rotate,
							R.anim.my_alpha_action);
					break;
				case 4:
					overridePendingTransition(R.anim.scale_translate,
							R.anim.my_alpha_action);
					break;
				case 5:
					overridePendingTransition(R.anim.hyperspace_in,
							R.anim.hyperspace_out);
					break;
				case 6:
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					break;
				case 7:
					overridePendingTransition(R.anim.push_up_in,
							R.anim.push_up_out);
					break;
				case 8:
					overridePendingTransition(R.anim.slide_left,
							R.anim.slide_right);
					break;
				case 9:
					overridePendingTransition(R.anim.wave_scale,
							R.anim.my_alpha_action);
					break;
				case 10:
					overridePendingTransition(R.anim.zoom_enter,
							R.anim.zoom_exit);
					break;
				case 11:
					overridePendingTransition(R.anim.slide_up_in,
							R.anim.slide_down_out);
					break;
				case 12:
					overridePendingTransition(R.anim.push_right_in,
							R.anim.push_right_out);
					break;
				default:
					break;
				}
			}
		});
	}
	
}
