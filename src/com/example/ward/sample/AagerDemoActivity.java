package com.example.ward.sample;

import java.util.ArrayList;
import java.util.List;

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

import com.example.ward.R;
import com.example.ward.view.MyViewPagerAdapter;

/**
 * Tabҳ�����ƻ����л��Լ�����Ч��
 * 
 * @author yuri
 */
public class AagerDemoActivity extends Activity implements OnClickListener, OnPageChangeListener {
	// ViewPager��google SDk���Դ���һ�����Ӱ���һ���࣬��������ʵ����Ļ����л���
	// android-support-v4.jar
	private ViewPager viewPager;
	private List<View> listViews;// Tabҳ���б�
	private ImageView cursor;// ����ͼƬ

	private TextView tap1, tap2, tap3;// ҳ������

	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW; // ����ͼƬ���

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("________+_+_+_+_+_=");
		setContentView(R.layout.viewpager_layout);

		initImageView();
		initTextView();
		initViewPager();

	}

	public void initTextView() {
		tap1 = (TextView) findViewById(R.id.page01);
		tap2 = (TextView) findViewById(R.id.page02);
		tap3 = (TextView) findViewById(R.id.page03);

		tap1.setOnClickListener(this);
		tap2.setOnClickListener(this);
		tap3.setOnClickListener(this);
	}

	public void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.viewpager_lay1, null));
		listViews.add(mInflater.inflate(R.layout.viewpager_lay2, null));
		listViews.add(mInflater.inflate(R.layout.viewpager_lay3, null));

		viewPager.setAdapter(new MyViewPagerAdapter(listViews));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(this);
	}

	// ��ʼ������
	public void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor1);
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
		cursor.setImageMatrix(matrix);

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

	// ҳ���л�����
	int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
	int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		Animation animation = null;
		switch (arg0) {
		case 0:
			if (currIndex == 1) {
				animation = new TranslateAnimation(one, 0, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, 0, 0, 0);
			}
			break;
		case 1:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, one, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, one, 0, 0);
			}
			break;
		case 2:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, two, 0, 0);
			} else if (currIndex == 1) {
				animation = new TranslateAnimation(one, two, 0, 0);
			}
		default:
			break;
		}
		currIndex = arg0;
		animation.setFillAfter(true);// True:ͼƬͣ���ڶ�������λ��
		animation.setDuration(300);
		cursor.startAnimation(animation);
	}
}
