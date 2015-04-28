package com.example.ward.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.ward.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BitmapRegionDecodeDemo extends Activity {
	private static final String TAG = "yuri";
	/**
	 * 图片显示的宽度，这里为屏幕的宽度
	 */
	int width;
	int heightFollwidth;
	int imgW, imgH;

	int dealImgW1, dealImgH1;
	int dealImgW2, dealImgH2;
	int dealImgW3, dealImgH3;
	int dealImgW4, dealImgH4;

	Bitmap bitmap, dealBitmap1, dealBitmap2, dealBitmap3, dealBitmap4;
	Rect rect1, rect2, rect3, rect4;
	BitmapRegionDecoder bitmapRegionDecoder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels; // 屏幕宽度（像素）
		System.out.println("屏幕width: " + width);

		setContentView(R.layout.bitmap_brd_main);

		ImageView imageView1 = (ImageView) findViewById(R.id.bitmap_iv_1);
		ImageView imageView2 = (ImageView) findViewById(R.id.bitmap_iv_2);
		ImageView imageView3 = (ImageView) findViewById(R.id.bitmap_iv_3);
		ImageView imageView4 = (ImageView) findViewById(R.id.bitmap_iv_4);
		ImageView imageView5 = (ImageView) findViewById(R.id.bitmap_iv_5);

		Log.d("yuri", "1");
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.bmp_test1);
		Log.d("yuri", "2"); // 1.4s

//		imageView1.setImageBitmap(bitmap);

		imgW = bitmap.getWidth(); // 这个值是dp(=920),它大于图片实际像素宽px(=460)
		imgH = bitmap.getHeight();
		System.out.println("原始长图w: " + imgW + "\n原始长图h: " + imgH);

		int n = 4;
		rect1 = new Rect(0, 0, imgW, imgH / 3);
		rect2 = new Rect(0, imgH / 3, imgW, 2 * (imgH / 3));
		rect3 = new Rect(0, 2 * (imgH / 3), imgW, 3 * (imgH / 3));
//		rect4 = new Rect(0, 3 * (imgH / 4), imgW, 4* (imgH / 4));

		try {
			Log.d(TAG, "3"); // 0.004s
			
			InputStream isBm = getResources().openRawResource(R.drawable.bmp_test1);
			bitmapRegionDecoder = BitmapRegionDecoder.newInstance(
					isBm, false);
			Log.d(TAG, "4"); // 1s
			
			dealBitmap1 = bitmapRegionDecoder.decodeRegion(rect1, null);
			dealBitmap2 = bitmapRegionDecoder.decodeRegion(rect2, null);
			dealBitmap3 = bitmapRegionDecoder.decodeRegion(rect3, null);
//			dealBitmap4 = bitmapRegionDecoder.decodeRegion(rect4, null);
			Log.d(TAG, "5"); // 1.1s
			if (dealBitmap1 != null && dealBitmap2 != null
					&& dealBitmap3 != null) {
				Log.d(TAG, "6"); // 0.012s
				dealImgW1 = dealBitmap1.getWidth();
				dealImgH1 = dealBitmap1.getHeight();
				
				dealImgW2 = dealBitmap2.getWidth();
				dealImgH2 = dealBitmap2.getHeight();
				
				dealImgW3 = dealBitmap3.getWidth();
				dealImgH3 = dealBitmap3.getHeight();
				
//				dealImgW4 = dealBitmap4.getWidth();
//				dealImgH4 = dealBitmap4.getHeight();
				
				heightFollwidth = width * dealImgH1 / dealImgW1;
				imageView2.setLayoutParams(new LinearLayout.LayoutParams(width,
						heightFollwidth));
				imageView3.setLayoutParams(new LinearLayout.LayoutParams(width,
						heightFollwidth));
				imageView4.setLayoutParams(new LinearLayout.LayoutParams(width,
						heightFollwidth));
//				imageView5.setLayoutParams(new LinearLayout.LayoutParams(width,
//						heightFollwidth));
				imageView2.setImageBitmap(dealBitmap1);
				imageView3.setImageBitmap(dealBitmap2);
				imageView4.setImageBitmap(dealBitmap3);
//				imageView5.setImageBitmap(dealBitmap4);
				Log.d(TAG, "7"); // 0.000s
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
