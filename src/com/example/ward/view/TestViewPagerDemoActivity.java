package com.example.ward.view;

import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;

import android.R.integer;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Tab页面手势滑动切换以及动画效果
 * 
 * @author yuri
 */
public class TestViewPagerDemoActivity extends Activity implements OnClickListener {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	private ViewPager viewPager;
	private List<View> listViews;// Tab页面列表
	private ImageView cursor1,cursor2,cursor3;// 动画图片

	private TextView tap1, tap2, tap3,tap4,tap5;// 页卡内容

	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW; // 动画图片宽度

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		
		setTitle("ViewPager测试");

		initImageView();
		initTextView();
		initViewPager();

	}

	public void initTextView() {
		tap1 = (TextView) findViewById(R.id.page01);
		tap2 = (TextView) findViewById(R.id.page02);
		tap3 = (TextView) findViewById(R.id.page03);
//		tap4 = (TextView) findViewById(R.id.page04);
//		tap5 = (TextView) findViewById(R.id.page05);

		tap1.setOnClickListener(this);
		tap2.setOnClickListener(this);
		tap3.setOnClickListener(this);
//		tap4.setOnClickListener(this);
//		tap5.setOnClickListener(this);
	}

	public void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.viewpager_lay1, null));
		listViews.add(mInflater.inflate(R.layout.viewpager_lay2, null));
		listViews.add(mInflater.inflate(R.layout.viewpager_lay3, null));
//		listViews.add(mInflater.inflate(R.layout.viewpager_lay4, null));
//		listViews.add(mInflater.inflate(R.layout.viewpager_lay5, null));
		
		viewPager.setAdapter(new MyViewPagerAdapter(listViews));
		//设置默认页面
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyPageListen());
	}

	// 初始化动画
	public void initImageView() {
		cursor1 = (ImageView) findViewById(R.id.cursor1);
		cursor2 = (ImageView) findViewById(R.id.cursor2);
		cursor3 = (ImageView) findViewById(R.id.cursor3);
		// 获取图片宽度
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager).getWidth();
		// 获取屏幕分辨率
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		// 获取分辨率宽度
		int screenW = dMetrics.widthPixels;
		// 计算偏移量
		offset = (screenW / 3 - bmpW) / 2;

		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// 设置动画初始位置
//		cursor.setImageMatrix(matrix);
		cursor1.setVisibility(View.VISIBLE);
		cursor2.setVisibility(View.GONE);
		cursor3.setVisibility(View.GONE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.page01:
			viewPager.setCurrentItem(0);
			break;
		case R.id.page02:
			viewPager.setCurrentItem(1);
			break;
		case R.id.page03:
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}

	class MyPageListen implements OnPageChangeListener{

		// 页卡切换监听
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				setImageView(View.VISIBLE, View.GONE, View.GONE);
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				setImageView(View.GONE, View.VISIBLE, View.GONE);
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				setImageView(View.GONE, View.GONE, View.VISIBLE);
				break;
			case 3:
//				if (currIndex == 0) {
//					animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta)
//				}
			default:
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停留在动画结束位置
			animation.setDuration(300);
//			cursor.startAnimation(animation);
		}
	}
	
	public void setImageView(int visiible1,int visiblele2,int visible3){
		cursor1.setVisibility(visiible1);
		cursor2.setVisibility(visiblele2);
		cursor3.setVisibility(visible3);
	}
}
