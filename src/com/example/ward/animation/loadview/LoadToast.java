package com.example.ward.animation.loadview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by Wannes2 on 23/04/2015.
 */
public class LoadToast {

    private String mText = "";
    private LoadToastView mView;
    private ViewGroup mParentView;
    private int mTranslationY = 0;
    private boolean mShowCalled = false;
    private boolean mInflated = false;

    public LoadToast(Context context){
        mView = new LoadToastView(context);
        mParentView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        mParentView.addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mView.setAlpha(0);
        mParentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.setTranslationX((mParentView.getWidth() - mView.getWidth()) / 2);
                mView.setTranslationY(-mView.getHeight() + mTranslationY);
                mInflated = true;
                if(mShowCalled) show();
            }
        },1);

        mParentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                checkZPosition();
            }
        });
    }

    public LoadToast setTranslationY(int pixels){
        mTranslationY = pixels;
        return this;
    }

    public LoadToast setText(String message){
        mText = message;
        mView.setText(mText);
        return this;
    }

    public LoadToast setTextColor(int color){
        mView.setTextColor(color);
        return this;
    }

    public LoadToast setTextTypeface(Typeface font){
        mView.setTextTypeface(font);
        return this;
    }

    public LoadToast setBackgroundColor(int color){
        mView.setBackgroundColor(color);
        return this;
    }

    public LoadToast setProgressColor(int color){
        mView.setProgressColor(color);
        return this;
    }

    public LoadToast show(){
        if(!mInflated){
            mShowCalled = true;
            return this;
        }
        mView.show();
        mView.setAlpha(0);
        mView.setTranslationY(-mView.getHeight() + mTranslationY);
        sliedDown();
        return this;
    }

    public void success(){
        mView.success();
        slideUp();
    }

    public void error(){
        mView.error();
        slideUp();
    }

    private void checkZPosition(){
        int pos = mParentView.indexOfChild(mView);
        int count = mParentView.getChildCount();
        if(pos != count-1){
            mParentView.removeView(mView);
            mParentView.requestLayout();
            mParentView.addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void slideUp(){
        ViewPropertyAnimator animator = mView.animate();
        animator.setStartDelay(1000);
        animator.alpha(0f);
        animator.translationY(-mView.getHeight() + mTranslationY);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }
    
    private void sliedDown(){
        ViewPropertyAnimator animator = mView.animate();
        animator.alpha(1f);
        animator.translationY(25 + mTranslationY);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.setStartDelay(0);
        animator.start();
    }
}
