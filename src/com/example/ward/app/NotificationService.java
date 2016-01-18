package com.example.ward.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import com.example.ward.R;

public class NotificationService extends Service{
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("onStartCommand");
		int color = intent.getIntExtra("Color", Color.RED);
		Notification.Builder builder = new Notification.Builder(getApplicationContext());
//		builder.set
		builder.setLights(Color.RED, 750, 750);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("这是一个简单的NOtification");
		builder.setWhen(System.currentTimeMillis() + 100);
		final NotificationManager mNotificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
		//创建Notification对象
//		final Notification notification =  new Notification();
		final Notification notification = builder.getNotification();
//		Notification.Builder builder = new Notification.Builder(NotificationService.this);
//		builder.
//		notification.icon = R.drawable.ic_launcher;
//		notification.tickerText = "这是一个简单的NOtification";
//		notification.when = System.currentTimeMillis() + 100;
//		
//		//填充属性
//		Context context = getApplicationContext(); 
//		CharSequence contentTitle = "My notification"; 
//		CharSequence contentText = "Hello World!"; 
//		Intent notificationIntent = new Intent(this, NotificationDemo.class); 
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0); 
//		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//		
//		//设置led
//		//使用默认的灯光
////		notification.defaults |= Notification.DEFAULT_LIGHTS;
//		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
////		
////		//自定义灯光
//		notification.ledARGB = color;
//		notification.ledOnMS = 1;
//		notification.ledOffMS = 0;
//		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("mNotificationManager.notify(1111, notification)");
				mNotificationManager.notify(1111, notification);
			}
		}, 3000);
		return super.onStartCommand(intent, flags, startId);
		
	}

}
