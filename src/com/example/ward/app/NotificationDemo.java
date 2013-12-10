package com.example.ward.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.ward.R;

public class NotificationDemo extends Activity implements OnClickListener {
	NotificationManager mNotificationManager ;
	private int ledOnMs;
	private int ledOffMs;
	
	private ImageView mImageView;
	private int currentColor;
	private static final int COLOR = 0xff00ffff;
	
	private int onMS;
	private int offMS;
	
	private static final String red_path= "/sys/class/leds/red/brightness";
	private static final String green_path= "/sys/class/leds/green/brightness";
	private static final String blue_path= "/sys/class/leds/blue/brightness";
	
	private RemoteViews mNotificationView;
	private Notification mNotification;
	private int progressNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_demo);
		
		Button button = (Button) findViewById(R.id.notification_demo);
		button.setOnClickListener(this);
		
		Button button2 = (Button) findViewById(R.id.notification_demo2);
		button2.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.color_pick);
		mImageView.setOnClickListener(this);
		
		Button stopButton = (Button) findViewById(R.id.stop);
		stopButton.setOnClickListener(this);
		
		Button edButton = (Button) findViewById(R.id.edit_button);
		edButton.setOnClickListener(this);
		//得到NotificationManager
		mNotificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
		
		ledOnMs = getResources().getInteger(R.integer.ledonms);
		ledOffMs = getResources().getInteger(R.integer.ledoffms);
		
		currentColor = COLOR;
		mImageView.setBackgroundColor(currentColor);
		
		Button downloadButton = (Button) findViewById(R.id.download_button);
		downloadButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.notification_demo:
			//创建Notification对象
			Notification notification =  new Notification();
			notification.icon = R.drawable.ic_launcher;
			notification.tickerText = "这是一个简单的NOtification";
			notification.when = System.currentTimeMillis() + 100;
			
			//填充属性
			Context context = getApplicationContext(); 
			CharSequence contentTitle = "My notification"; 
			CharSequence contentText = "Hello World!"; 
			Intent notificationIntent = new Intent(this, NotificationDemo.class); 
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0); 
			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			
			//设置led
			//使用默认的灯光
//			notification.defaults |= Notification.DEFAULT_LIGHTS;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
			
			//自定义灯光
//			notification.ledARGB = currentColor;
//			notification.ledOnMS = onMS;
//			notification.ledOffMS = offMS;
			notification.ledARGB = currentColor;
			notification.ledOnMS = 750;
			notification.ledOffMS = 2000;
			System.out.println("color:" + currentColor + "\n"
					+ "on:" + onMS + "\n"
					+ "off:" + offMS);
			mNotificationManager.notify(111, notification);
			break;
			
		case R.id.notification_demo2:
			Intent intent = new Intent();
			intent.setClass(NotificationDemo.this, NotificationService.class);
			intent.putExtra("Color", currentColor);
			startService(intent);
			break;
			
		case R.id.color_pick:
			ColorPickerDialog mColorDialog = new ColorPickerDialog(NotificationDemo.this, currentColor,
					getResources().getString(R.string.app_name), new com.example.ward.app.ColorPickerDialog.OnColorChangedListener() {
				@Override
				public void colorChanged(int color) {
					currentColor = color;
					mImageView.setBackgroundColor(currentColor);
				}
			});
			mColorDialog.show();
			break;
			
		case R.id.stop:
			mNotificationManager.cancel(111);
			Intent stopintent = new Intent();
			stopintent.setClass(NotificationDemo.this, NotificationService.class);
			stopService(stopintent);
			break;
			
		case R.id.edit_button:
			LayoutInflater inflater = LayoutInflater.from(NotificationDemo.this);
			View view  = inflater.inflate(R.layout.notification_demo_dialog, null);
			final EditText redEdit = (EditText) view.findViewById(R.id.red_edit);
			final EditText greenEdit = (EditText) view.findViewById(R.id.green_edit);
			final EditText blueEdit = (EditText) view.findViewById(R.id.blue_edit);
			new AlertDialog.Builder(NotificationDemo.this)
			.setTitle("set color")
			.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String color = redEdit.getText().toString().trim();
					String on = greenEdit.getText().toString().trim();
					String off = blueEdit.getText().toString().trim();
					if (color == null || color.equals("")) {
						//
					}else {
						currentColor =Integer.parseInt(color);
					}
					System.out.println("currentColro=" + currentColor);
					onMS = Integer.parseInt(on);
					offMS = Integer.parseInt(off);
//					fileWriteDemo(red_path, red);
//					fileWriteDemo(green_path, green);
//					fileWriteDemo(blue_path, blue);
				}
			})
			.setNegativeButton("Cacnel", null)
			.create().show();
			break;
		case R.id.download_button:
			//创建Notification对象
			mNotification = new Notification();
			mNotification.icon = R.drawable.ic_launcher;
			mNotification.tickerText = "这是一个简单的NOtification";
			mNotification.when = System.currentTimeMillis() + 100;
			//加载layout
			mNotificationView = new RemoteViews(getPackageName(), R.layout.notification_download);

			//new一个线程用于发送消息
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < 20; i++) {
						progressNumber = (i + 1) * 5;
						try {
							if (i < 19) {
								Message msg = mHandler.obtainMessage();
								msg.what = MSG_RUN;
								msg.sendToTarget();
								Thread.sleep(1000);
							}else {
								Message msg = mHandler.obtainMessage();
								msg.what = MSG_STOP;
								msg.sendToTarget();
								Thread.currentThread().interrupt();
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}).start();
			break;

		default:
			break;
		}
	}
	
	private static final int MSG_RUN = 0;
	private static final int MSG_STOP = 1;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_RUN:
				mNotificationView.setProgressBar(R.id.bar_progress, 100, progressNumber, false);
				mNotificationView.setTextViewText(R.id.tv_name, "第" + progressNumber + "个");
				mNotificationView.setTextViewText(R.id.tv_progress, "(" + progressNumber + "/" + "100)");
				
				mNotification.contentView = mNotificationView;
				mNotification.contentIntent = null;
				
				mNotificationManager.notify(111, mNotification);
				break;
			case MSG_STOP:
				mNotificationManager.cancel(111);
				break;

			default:
				break;
			}
		};
	};
	
	/**将备份的内容先写入文件*/
	public static void fileWriteDemo(String path, String content){
		System.out.println("filewritedemo   " + content);
		//default path
		File file = new File(path);
		if (! file.exists()) {
			Log.e("Yuri", "File is not exites");
		}
		
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}finally{
			fw = null;
			bw = null;
		}
		
		return;
	}
}
