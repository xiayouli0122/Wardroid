package com.example.ward.animation;

import com.example.ward.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DrawableAnimation extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawable_animation_main);
		
		ImageView imageView = (ImageView) findViewById(R.id.iv_da_loading);
		
		final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
		
		findViewById(R.id.startButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                animationDrawable.start();
            }
        });
		
		findViewById(R.id.endButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                animationDrawable.stop();
            }
        });
	}
}
