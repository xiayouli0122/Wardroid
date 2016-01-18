package com.example.ward.animation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ward.R;

public class BitmapAnimation extends Activity {

    private ImageView mImageView;
    private Bitmap mOriginBitmap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_animation);

        mImageView = (ImageView) findViewById(R.id.imageview);
        mOriginBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture12);
    }

    public void doStart(View view) {
        mOriginBitmap = big(mOriginBitmap);
        mImageView.setImageBitmap(mOriginBitmap);
    }

    public void doEnd(View view) {
        mOriginBitmap = small(mOriginBitmap);
        mImageView.setImageBitmap(mOriginBitmap);
    }

    private float bigXScale = 1.05f;
    private float bigYScale = 1.05f;
    
    private float smallXScale = 0.95f;
    private float smalllYScale = 0.95f;
    
    private float mWidth = 720.0f;
    private float mHeight = 404.0f;
    private Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(bigXScale, bigYScale, bitmap.getWidth()/2, bitmap.getHeight()/2); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(smallXScale, smalllYScale, mWidth/2, mHeight/2); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}
