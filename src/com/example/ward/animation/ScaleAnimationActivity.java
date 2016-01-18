package com.example.ward.animation;

import com.example.ward.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class ScaleAnimationActivity extends Activity{

    private ImageView mImageView;
    private ScaleAnimation mScaleAnimation;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation);
        
        setTitle(ScaleAnimationActivity.class.getSimpleName());
        
        mImageView = (ImageView) findViewById(R.id.imageview);
        
        /** 设置缩放动画 */ 
        //float fromX 动画起始时 X坐标上的伸缩尺寸 ,通常设置为0.0f时，即大小从0开始
        //设置为1.0f时，即从原大小开始放大
        //float toX 动画结束时 X坐标上的伸缩尺寸   
        //想要实现放大和缩小的动画，即在fromX和toX以及fromY和toY上改一下即可
        //float fromY 动画起始时Y坐标上的伸缩尺寸 
        //float toY 动画结束时Y坐标上的伸缩尺寸  同上
        //int pivotXType X轴的伸缩模式 可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT
        //float pivotXValue X坐标的伸缩值；伸缩值
        //int pivotYType Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
        //float pivotYValue Y坐标的伸缩值
        mScaleAnimation =new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f, 
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); 
        mScaleAnimation.setDuration(5000);//设置动画持续时间 
        /** 常用方法 */ 
        mScaleAnimation.setRepeatCount(Animation.INFINITE);//设置重复次数 
        //animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态 
        //animation.setStartOffset(long startOffset);//执行前的等待时间 
        mImageView.setAnimation(mScaleAnimation);
    }
    
    public void doStart(View view) {
        
        mScaleAnimation.startNow();
    }

    public void doEnd(View view) {
    }
}
