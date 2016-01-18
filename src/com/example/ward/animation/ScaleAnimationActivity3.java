package com.example.ward.animation;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ward.R;
import com.zhaoyan.common.utils.Log;



/**
 * 使用ObjectAnimator对图片中心点，进行放大和缩小
 * @author Yuri
 *  ImageView  要使用fitXY
 *
 */
public class ScaleAnimationActivity3 extends Activity implements AnimatorListener{

    private ImageView mImageView;
    
    //缩放动画
    private AnimatorSet mAnimatorSet;
    //判断缩放图的状态
    private boolean mPreViewStatus=true;
    //每一次放大和缩小的持续时间
    private static final int ANIMATION_TIME = 5000;
    //缩放比例
    private static final float ANIMATION_SCALE=1.5f;
    
    private List<Animator> mFangdaAnimators = new ArrayList<Animator>();
    private List<Animator> mSuoxiaoAnimators = new ArrayList<Animator>();

    private float mScreenWidth;

    private float mScreenHeight;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation3);
        setTitle(ScaleAnimationActivity3.class.getSimpleName());
        mImageView = (ImageView) findViewById(R.id.imageview);
        
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        
        View view = findViewById(R.id.layout_frame);
        resetViewSize(view);
//        resetViewSize(mImageView);
        
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.addListener(this);
        
//        //X,Y方向的放大动画，同步进行
//        mFangdaAnimators.add(ObjectAnimator.ofFloat(mImageView, "scaleX", 1f, ANIMATION_SCALE));
//        mFangdaAnimators.add(ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, ANIMATION_SCALE));
//        
//        //X,Y方向的缩小动画，同步进行
//        mSuoxiaoAnimators.add(ObjectAnimator.ofFloat(mImageView, "scaleX", ANIMATION_SCALE, 1f));
//        mSuoxiaoAnimators.add(ObjectAnimator.ofFloat(mImageView, "scaleY", ANIMATION_SCALE, 1f));
        ObjectAnimator xBigAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", 1f, ANIMATION_SCALE);
        ObjectAnimator yBigAnimator = ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, ANIMATION_SCALE);
        
        ObjectAnimator xSmalAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", ANIMATION_SCALE, 1f);
        ObjectAnimator ySmalAnimator = ObjectAnimator.ofFloat(mImageView, "scaleY", ANIMATION_SCALE, 1f);
        
        mAnimatorSet.play(xBigAnimator).with(yBigAnimator);
        mAnimatorSet.play(xSmalAnimator).with(ySmalAnimator).after(xBigAnimator);
        mAnimatorSet.setDuration(ANIMATION_TIME * 2);
        
    }
    
    private float width = 720.0f;
    private float height = 404.0f;
    // 动态配置View的大小
    private void resetViewSize(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = (int) mScreenWidth;
        lp.height = (int)(mScreenWidth * height / width);
        Log.d("Yuri", "height:" + lp.height);
        view.setLayoutParams(lp);
        Bitmap bitmap;
    }
    
    public void doStart(View view){
//        startPreViewAnim();
        mAnimatorSet.start();
    }
    
    public void doEnd(View view){
        mAnimatorSet.end();
    }
    

    /**
     * 播放预览动画
     */
    private void startPreViewAnim(){
        mPreViewStatus=!mPreViewStatus;//切换放大或缩小状态
        if(mAnimatorSet!=null){
            if(mPreViewStatus){
                mAnimatorSet.playTogether(
                        ObjectAnimator.ofFloat(mImageView, "scaleX", ANIMATION_SCALE, 1f),
                        ObjectAnimator.ofFloat(mImageView, "scaleY", ANIMATION_SCALE, 1f));
            }
            else{
                mAnimatorSet.playTogether(
                        ObjectAnimator.ofFloat(mImageView, "scaleX", 1f, ANIMATION_SCALE),
                        ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, ANIMATION_SCALE));
            }
            mAnimatorSet.setDuration(ANIMATION_TIME);
            mAnimatorSet.start();
        }
    }
    
    
    
    
    
    
    @Override
    public void onAnimationStart(Animator animation) {
        // TODO Auto-generated method stub
        Log.d("Yuri", "onAnimationStart");
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // TODO Auto-generated method stub
        Log.d("Yuri", "onAnimationEnd");
        mAnimatorSet.start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // TODO Auto-generated method stub
    }
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
    }
}
