package com.example.ward.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ProgressBarWithText extends ProgressBar {

	private String text;
	private Paint mPaint;
	private static final int TEXT_SIZE = 25;

	public ProgressBarWithText(Context context) {
		super(context);
		initText();
	}

	public ProgressBarWithText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initText();
	}

	public ProgressBarWithText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initText();
	}

	@Override
	public synchronized void setProgress(int progress) {
		setText(progress);
		super.setProgress(progress);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.text, x, y, this.mPaint);
	}

	//init paint
	private void initText() {
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setColor(Color.WHITE);
		this.mPaint.setTextSize(TEXT_SIZE);
	}

	@SuppressWarnings("unused")
	private void setText() {
		setText(this.getProgress());
	}

	/**
	 * set progress text
	 * @param progress the progress of progressbar
	 */
	private void setText(int progress) {
		int i = (progress * 100) / this.getMax();
		this.text = String.valueOf(i) + "%";
	}
	
	/**
	 * set preogress text
	 * @param text
	 */
	public void setText(String text){
		this.text = text;
	}

}
