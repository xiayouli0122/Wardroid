package com.example.ward.animation;

import com.example.ward.R;
import com.example.ward.animation.SwitchAnimationActivity.MyThread;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ViewFlipper;

public class ViewFlipperDemo extends Activity{
    
    private ViewFlipper mViewFlipper;
    
    private int[] IMAGE_IDS = {
            R.drawable.picture10, R.drawable.picture11, R.drawable.picture12
    };
    
    private Drawable[] mDrawables;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        
        mDrawables = new Drawable[IMAGE_IDS.length];
        
        for (int i = 0; i < IMAGE_IDS.length; i++) {
            
        }
        
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        
        ImageView imageView = null;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < IMAGE_IDS.length; i++) {
            imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ScaleType.MATRIX);
            imageView.setImageResource(IMAGE_IDS[i]);
            mViewFlipper.addView(imageView);
        }
        
        mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_left_in);
        mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_left_out);
        mViewFlipper.startFlipping();
    }
    
    public void doStart(View view) {
        
     }

     public void doEnd(View view) {
         mViewFlipper.stopFlipping();
     }
}
