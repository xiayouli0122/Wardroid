package com.example.ward.app;

import com.example.ward.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ColorPickerDemo extends Activity implements OnClickListener {
	private static final String TAG = "ColorPickerDemo";
	private TextView mColorPickerView;
	private TextView mColorCodeEdit;
	private TextView mColorShowView;
	private TextView mCurrentColor;
	private Button	mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_color_picker);
		int initColor = getResources().getColor(android.R.color.holo_blue_light);
		mColorCodeEdit = (TextView) findViewById(R.id.et_color_code);
		mColorPickerView = (TextView) findViewById(R.id.tv_app_color_picker);
		mColorPickerView.setOnClickListener(this);
		mColorShowView = (TextView) findViewById(R.id.tv_color_show);
		mCurrentColor = (TextView) findViewById(R.id.tv_current_color);
		mCurrentColor.setText("?????????" + Integer.toHexString(initColor));
		mButton = (Button) findViewById(R.id.btn_ok);
		mButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_app_color_picker:
			ColorPickerDialog mColorDialog = new ColorPickerDialog(ColorPickerDemo.this, 0 ,
					getResources().getString(R.string.app_name), new com.example.ward.app.ColorPickerDialog.OnColorChangedListener() {
				@Override
				public void colorChanged(int color) {
					System.out.println("color:" + color);
					System.out.println("16color:" + Integer.toHexString(color));
					mColorPickerView.setBackgroundColor(color);
					mCurrentColor.setText("?????????" + Integer.toHexString(color));
				}
			});
			mColorDialog.show();
			break;
		case R.id.btn_ok:
			int ir = 0, ig = 0, ib = 0, ia = 0;//red,green,blue,alpha
			String sr = null,sg = null, sb = null, sa = null;
			String code = mColorCodeEdit.getText().toString();
			int len = code.length();
			if (3 == len) {
				sa = "ff";
				sr = code.substring(0, 1);
				sr.concat(sr);
				sg = code.substring(1, 2);
				sg.concat(sg);
				sb = code.substring(2, 3);
				sb.concat(sb);
			}else if (4 == len) {
				sa = code.substring(0, 1);
				sa.concat(sa);
				sr = code.substring(1, 2);
				sr.concat(sr);
				sg = code.substring(2, 3);
				sg.concat(sg);
				sb = code.substring(3, 4);
				sb.concat(sb);
			}else if (6 == len) {
				sa = "ff";
				sr = code.substring(0, 2);
				sg = code.substring(2, 4);
				sb = code.substring(4, 6);
			}else if (8 == len) {
				sa = code.substring(0, 2);
				sr = code.substring(2, 4);
				sg = code.substring(4, 6);
				sb = code.substring(6, 8);
			}else {
				Toast.makeText(this, "??????????????", Toast.LENGTH_SHORT).show();
				break;
			}
			
			try {
				//conver hex to dec
				ia = Integer.parseInt(sa, 16);
				ir = Integer.parseInt(sr, 16);
				ig = Integer.parseInt(sg, 16);
				ib = Integer.parseInt(sb, 16);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "??????????????", Toast.LENGTH_SHORT).show();
				break;
			}
			
			int color = Color.argb(ia, ir, ig, ib);
			System.out.println("color:" + color);
			mColorShowView.setBackgroundColor(color);
			break;

		default:
			break;
		}
	}
}
