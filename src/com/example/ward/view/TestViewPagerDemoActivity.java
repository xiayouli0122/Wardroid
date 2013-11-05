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
 * Tabҳ�����ƻ����л��Լ�����Ч��
 * 
 * @author yuri
 */
public class TestViewPagerDemoActivity extends Activity implements OnClickListener {
	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	private ViewPager viewPager;
	private List<View> listViews;// Tabҳ���б�
	private ImageView cursor1,cursor2,cursor3;// ����ͼƬ

	private TextView tap1, tap2, tap3,tap4,tap5;// ҳ������

	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW; // ����ͼƬ���

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		
		setTitle("ViewPager����");

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
		//����Ĭ��ҳ��
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyPageListen());
	}

	// ��ʼ������
	public void initImageView() {
		cursor1 = (ImageView) findViewById(R.id.cursor1);
		cursor2 = (ImageView) findViewById(R.id.cursor2);
		cursor3 = (ImageView) findViewById(R.id.cursor3);
		// ��ȡͼƬ���
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager).getWidth();
		// ��ȡ��Ļ�ֱ���
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		// ��ȡ�ֱ��ʿ��
		int screenW = dMetrics.widthPixels;
		// ����ƫ����
		offset = (screenW / 3 - bmpW) / 2;

		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// ���ö�����ʼλ��
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

		// ҳ���л�����
		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
		
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
			animation.setFillAfter(true);// True:ͼƬͣ���ڶ�������λ��
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
