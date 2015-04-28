package com.example.ward.media.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ward.R;

public class RoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round);

		//Bitmap bmBitmap = toRoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fans1));
		//Bitmap bmBitmap = getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.playbill));
		final ImageView ivImageView = (ImageView) findViewById(R.id.imageView1);

		//ivImageView.setImageBitmap(bmBitmap);
		
		Button bt_one = (Button) findViewById(R.id.bt_one);
		Button bt_two = (Button) findViewById(R.id.bt_two);
		Button bt_three = (Button) findViewById(R.id.bt_three);
		
		bt_one.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bitmap bmBitmap = BitmapUtilities.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img));
				ivImageView.setImageBitmap(bmBitmap);
			}
		});
		
		bt_two.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bitmap bmBitmap = BitmapUtilities.toRoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img));
				ivImageView.setImageBitmap(bmBitmap);
			}
		});
		
		bt_three.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap bmBitmap = BitmapUtilities.createReflectedImage(BitmapFactory.decodeResource(getResources(), R.drawable.img));
				ivImageView.setImageBitmap(bmBitmap);
			}
		});
	}
	
}
