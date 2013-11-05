package com.example.ward.lockpattern;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ward.R;

public class NinePointLineView extends View {
	private static final String TAG = "NinepointLineView";
	Paint linePaint = new Paint();

	Paint whiteLinePaint = new Paint();

	Paint textPaint = new Paint();

	// 由于两个图片都是正方形，�?��获取�?��长度就行�?	
	Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);
	int defaultBitmapRadius = defaultBitmap.getWidth() / 2;

	// 初始化被选中图片的直径�?半径
	Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_lock_area);
	int selectedBitmapDiameter = selectedBitmap.getWidth();
	int selectedBitmapRadius = selectedBitmapDiameter / 2;

	// 定义�?个点的数�?	
	PointInfo[] points = new PointInfo[9];

	// 相应ACTION_DOWN的那个点
	PointInfo startPoint = null;

	// 屏幕的宽�?	
	int width, height;

	// 当ACTION_MOVE时获取的X，Y坐标
	int moveX, moveY;

	// 是否发生ACTION_UP
	boolean isUp = false;

	// �?��生成的用户锁序列
	StringBuffer lockString = new StringBuffer();
	
	////////
	private Context mContext;

	public NinePointLineView(Context context) {
		super(context);
		mContext = context;
		this.setBackgroundColor(Color.WHITE);
		initPaint();
	}

	public NinePointLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(Color.WHITE);
		initPaint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.i(TAG, "onMeasure");
		// 初始化屏幕大�?		
		width = getWidth();
		height = getHeight();
		if (width != 0 && height != 0) {
			initPoints(points);
		}
		Log.i(TAG, "width、height = " + width + "、" + height);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.i(TAG, "onLayout");
		super.onLayout(changed, left, top, right, bottom);
	}

	private int startX = 0, startY = 0;

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawText("用户的滑动顺序：" + lockString, 0, 40, textPaint);

		if (moveX != 0 && moveY != 0 && startX != 0 && startY != 0) {
			// 绘制当前活动的线�?			drawLine(canvas, startX, startY, moveX, moveY);
		}

		drawNinePoint(canvas);

		super.onDraw(canvas);
	}

	// 记住，这个DOWN和MOVE、UP是成对的，如果没从UP释放，就不会再获得DOWN�?	// 而获得DOWN时，�?��要确认消费该事件，否则MOVE和UP不会被这个View的onTouchEvent接收
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean flag = true;

		if (isUp) {// 如果已滑完，重置每个点的属�?和lockString
			finishDraw();

			// 当UP后，要返回false，把事件释放给系统，否则无法获得Down事件
			flag = false;

		} else {// 没滑完，则继续绘�?			handlingEvent(event);

			// 这里要返回true，代表该View消�?此事件，否则不会收到MOVE和UP事件
			flag = true;

		}
		return flag;
	}

	private void handlingEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			moveX = (int) event.getX();
			moveY = (int) event.getY();
			Log.i(TAG, "onMove:" + moveX + "," + moveY);
			for (PointInfo temp : points) {
				if (temp.isInMyPlace(moveX, moveY) && temp.isNotSelected()) {
					temp.setSelected(true);
					startX = temp.getCenterX();
					startY = temp.getCenterY();
					int len = lockString.length();
					if (len != 0) {
						int preId = lockString.charAt(len - 1) - 48;
						points[preId].setNextId(temp.getId());
					}
					lockString.append(temp.getId());
					break;
				}
			}

			invalidate(0, height - width, width, height);
			break;

		case MotionEvent.ACTION_DOWN:
			int downX = (int) event.getX();
			int downY = (int) event.getY();
			lockString.delete(0, lockString.length());/////
			Log.i(TAG, "onDown:" + downX + "," + downY);
			for (PointInfo temp : points) {
				if (temp.isInMyPlace(downX, downY)) {
					temp.setSelected(true);
					startPoint = temp;
					startX = temp.getCenterX();
					startY = temp.getCenterY();
					lockString.append(temp.getId());
					break;
				}
			}
			invalidate(0, height - width, width, height);
			break;

		case MotionEvent.ACTION_UP:
			Log.i(TAG, "onUp");
			startX = startY = moveX = moveY = 0;
			isUp = true;
			
			SystemClock.sleep(500);
			
			finishDraw();
			invalidate();
			break;
		default:
			Log.i(TAG, "收到其他事件！！");
			break;
		}
	}

	private void finishDraw() {
		for (PointInfo temp : points) {
			temp.setSelected(false);
			temp.setNextId(temp.getId());
		}
//		lockString.delete(0, lockString.length());
		isUp = false;
		invalidate();
		
		startOtherActivity(lockString.toString());
	}
	
	///////
	/**
	 * 根据绘画结果判断是否启动下一个Activity
	 * @param str
	 */
	private void startOtherActivity(String str){
//		if (str.equals("03645")) {
//			mContext.startActivity(new Intent(mContext, OtherActivity.class));
//		}else {
//			Toast.makeText(mContext, "Draw Error", Toast.LENGTH_SHORT).show();
//		}
	}

	private void initPoints(PointInfo[] points) {

		int len = points.length;

		int seletedSpacing = (width - selectedBitmapDiameter * 3) / 4;

		// 被�?择时显示图片的左上角坐标
		int seletedX = seletedSpacing;
		int seletedY = height - width + seletedSpacing;

		// 没被选时图片的左上角坐标
		int defaultX = seletedX + selectedBitmapRadius - defaultBitmapRadius;
		int defaultY = seletedY + selectedBitmapRadius - defaultBitmapRadius;

		// 绘制好每个点
		for (int i = 0; i < len; i++) {
			if (i == 3 || i == 6) {
				seletedX = seletedSpacing;
				seletedY += selectedBitmapDiameter + seletedSpacing;

				defaultX = seletedX + selectedBitmapRadius - defaultBitmapRadius;
				defaultY += selectedBitmapDiameter + seletedSpacing;

			}
			points[i] = new PointInfo(i, defaultX, defaultY, seletedX, seletedY);

			seletedX += selectedBitmapDiameter + seletedSpacing;
			defaultX += selectedBitmapDiameter + seletedSpacing;

		}
	}

	private void initPaint() {
		initLinePaint(linePaint);
		initTextPaint(textPaint);
		initWhiteLinePaint(whiteLinePaint);
	}

	/**
	 * 初始化文本画�?	 * @param paint
	 */
	private void initTextPaint(Paint paint) {
		textPaint.setTextSize(30);
		textPaint.setAntiAlias(true);
		textPaint.setTypeface(Typeface.MONOSPACE);
	}

	/**
	 * 初始化黑线画�?	 * @param paint
	 */
	private void initLinePaint(Paint paint) {
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(defaultBitmap.getWidth() / 2);
		paint.setAntiAlias(true);
//		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeCap(Cap.BUTT);
	}

	/**
	 * 初始化白线画�?	 * 
	 * @param paint
	 */
	private void initWhiteLinePaint(Paint paint) {
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(defaultBitmap.getWidth() / 2 - 5);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);

	}

	/**
	 * 绘制已完成的部分
	 * 
	 * @param canvas
	 */
	private void drawNinePoint(Canvas canvas) {

		if (startPoint != null) {
			drawEachLine(canvas, startPoint);
		}

		// 绘制每个点的图片
		for (PointInfo pointInfo : points) {
			if (pointInfo.isSelected()) {// 绘制大圈
				canvas.drawBitmap(selectedBitmap, pointInfo.getSeletedX(), pointInfo.getSeletedY(), null);
			}
			// 绘制�?			canvas.drawBitmap(defaultBitmap, pointInfo.getDefaultX(), pointInfo.getDefaultY(), null);
		}

	}

	/**
	 * 递归绘制每两个点之间的线�?	 * @param canvas
	 * @param point
	 */
	private void drawEachLine(Canvas canvas, PointInfo point) {
		if (point.hasNextId()) {
			int n = point.getNextId();
			drawLine(canvas, point.getCenterX(), point.getCenterY(), points[n].getCenterX(), points[n].getCenterY());
			// 递归
			drawEachLine(canvas, points[n]);
		}
	}

	/**
	 * 先绘制黑线，再在上面绘制白线，达到黑边白线的效果
	 * @param canvas
	 * @param startX
	 * @param startY
	 * @param stopX
	 * @param stopY
	 */
	private void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
		canvas.drawLine(startX, startY, stopX, stopY, linePaint);
		canvas.drawLine(startX, startY, stopX, stopY, whiteLinePaint);
	}

	/**
	 * 用来表示�?���?	 * 
	 * @author zkwlx
	 */
	private class PointInfo {

		// �?��点的ID
		private int id;

		// 当前点所指向的下�?��点的ID，当没有时为自己ID
		private int nextId;

		// 是否被�?�?		
		private boolean selected;

		// 默认时图片的左上角X坐标
		private int defaultX;

		// 默认时图片的左上角Y坐标
		private int defaultY;

		// 被�?中时图片的左上角X坐标
		private int seletedX;

		// 被�?中时图片的左上角Y坐标
		private int seletedY;

		public PointInfo(int id, int defaultX, int defaultY, int seletedX, int seletedY) {
			this.id = id;
			this.nextId = id;
			this.defaultX = defaultX;
			this.defaultY = defaultY;
			this.seletedX = seletedX;
			this.seletedY = seletedY;
		}

		public boolean isSelected() {
			return selected;
		}

		public boolean isNotSelected() {
			return !isSelected();
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public int getId() {
			return id;
		}

		public int getDefaultX() {
			return defaultX;
		}

		public int getDefaultY() {
			return defaultY;
		}

		public int getSeletedX() {
			return seletedX;
		}

		public int getSeletedY() {
			return seletedY;
		}

		public int getCenterX() {
			return seletedX + selectedBitmapRadius;
		}

		public int getCenterY() {
			return seletedY + selectedBitmapRadius;
		}

		public boolean hasNextId() {
			return nextId != id;
		}

		public int getNextId() {
			return nextId;
		}

		public void setNextId(int nextId) {
			this.nextId = nextId;
		}

		/**
		 * 坐标(x,y)是否在当前点的范围内
		 * 
		 * @param x
		 * @param y
		 * @return
		 */
		public boolean isInMyPlace(int x, int y) {
			boolean inX = x > seletedX && x < (seletedX + selectedBitmapDiameter);
			boolean inY = y > seletedY && y < (seletedY + selectedBitmapDiameter);

			return (inX && inY);
		}

	}

}
