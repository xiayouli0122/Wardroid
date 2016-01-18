package com.example.ward.animation;

import com.example.ward.R;
import com.zhaoyan.common.utils.Log;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;


/**
 * ValueAnimator
 * 使用值动画，在图片的中心点进行放大和缩小
 * ImageView  要使用martix
 * @author Yuri
 *
 */
public class ScaleAnimationActivity2 extends Activity implements AnimatorListener{

    private ImageView mImageView;

    private MyScaleAnimatorListener myScaleAnimatorListener;
    
    private ValueAnimator mValueAnimator;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation);
        setTitle(ScaleAnimationActivity2.class.getSimpleName());
        mImageView = (ImageView) findViewById(R.id.imageview);
        myScaleAnimatorListener = new MyScaleAnimatorListener(mImageView.getImageMatrix());
        
        setScaleAnimation();
    }
    
    public void doStart(View view) {
        start();
    }

    public void doEnd(View view) {
        if (mValueAnimator != null) {
            Log.d("Yuri", "doEnd");
            mValueAnimator.cancel();
        }
    }
    
    private int duration = 20 * 1000;
    public void setScaleAnimation(){
        mValueAnimator = ValueAnimator.ofFloat(1.0f, 1.8f, 1.0f);
        mValueAnimator.addUpdateListener(myScaleAnimatorListener);
        mValueAnimator.setDuration(duration);
//        mBigValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.setStartDelay(0);
        mValueAnimator.setRepeatCount(Animation.INFINITE);
        mValueAnimator.addListener(this);
    }
    
    private void start(){
        mValueAnimator.start();
    }
    
    
    private class MyScaleAnimatorListener implements AnimatorUpdateListener {
        private float mWidth = 720.0f;
        private float mHeight = 404.0f;

        private Matrix mPrimaryMatrix;
        


        public MyScaleAnimatorListener ( Matrix matrix ) {
            mPrimaryMatrix = matrix;
        }


        @Override
        public void onAnimationUpdate ( ValueAnimator animation ) {
            float scale = ( Float ) animation.getAnimatedValue ();
            Log.d("Yuri", "onAnimationUpdate.scale:" + scale);
            Matrix matrix = new Matrix ( mPrimaryMatrix );
            matrix.postScale(scale,scale, mWidth / 2, mHeight / 2 );
            mImageView.setImageMatrix ( matrix );
        }
    }
    
    
    @Override
    public void onAnimationStart(Animator animation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // TODO Auto-generated method stub
    }
}
