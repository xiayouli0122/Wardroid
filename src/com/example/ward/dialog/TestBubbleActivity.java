package com.example.ward.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.example.ward.R;


public class TestBubbleActivity extends Activity {

	private static int SCREEN_WIDTH = 0 ;
	private static int SCREEN_HEIGHT = 0;
	
    private View bubbleView = null;
    private Dialog bubbleAlert = null;
    
	private TextView tvKnow = null;
	private TextView tvBubContent = null;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_main);
        
        getDimension();
        
        bubbleView = getLayoutInflater().inflate(R.layout.bubble_dialog, null);
		tvKnow = (TextView)bubbleView.findViewById(R.id.bubble_btn);
		tvKnow.setText(Html.fromHtml("<u>"+"测试专用"+"</u>"));
		tvBubContent = (TextView)bubbleView.findViewById(R.id.bubble_text);
		tvBubContent.setText("这是�?���?.");
		
		tvKnow.setOnClickListener(new View.OnClickListener(){
//			@Override
			public void onClick(View v) {
				bubbleAlert.cancel();
			}
			
		});

		int tmpWidth = SCREEN_WIDTH/5*3;
		int tmpHeight =SCREEN_HEIGHT/8;
		System.out.println("tmpWidth=****=" + tmpWidth);
		System.out.println("tmpHeight=++++=" + tmpHeight);
		
		tvKnow.setMinWidth(tmpWidth);
		tvBubContent.setMaxWidth(tmpWidth/2);

		//创建Dialog并设定风�?		bubbleAlert = new Dialog(this,R.style.bubble_dialog);
        Window win = bubbleAlert.getWindow();
		LayoutParams params = win.getAttributes();
		params.x = -(SCREEN_WIDTH/30);//设定X坐标
		params.y = -tmpHeight;//设定Y坐标
		params.width = tmpWidth/2;
		
		win.setAttributes(params);

		bubbleAlert.setCancelable(false);
		bubbleAlert.setContentView(bubbleView);
		bubbleAlert.show();
    }
    
	/**
	 * 获取屏幕分辨率
	 */
	private void getDimension(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
	}
    
}