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
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;



public class SwitchAnimationActivity extends Activity{

    private ImageView mImageView;
    private Animation mAnimation;

    private static int[] IMAGES = {
      R.drawable.picture10, R.drawable.picture11, R.drawable.picture12  
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation);
        setTitle(SwitchAnimationActivity.class.getSimpleName());
        
        
        mImageView = (ImageView) findViewById(R.id.imageview);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
    }
    
    public void doStart(View view) {
       new Thread(new MyThread()).start();
    }

    public void doEnd(View view) {
        mCancel = true;
    }
    
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            int pos = msg.arg1;
            mImageView.setImageResource(IMAGES[pos]);
            mImageView.startAnimation(mAnimation);
        };
    };
    
    private boolean mCancel = false;
    public class MyThread implements Runnable{
        @Override
        public void run() {
            int index = 0;
            while (!mCancel) {
                try {
                    Thread.sleep(3000);

                    if(index >= IMAGES.length - 1){
                        index = 0;
                    }
                    
                    Message message = new Message();
                    message.arg1 = index;
                    message.what = 1;
                    mHandler.sendMessage(message);
                    
                    index ++;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
    
}
