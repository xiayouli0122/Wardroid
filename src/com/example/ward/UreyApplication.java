package com.example.ward;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

@ReportsCrashes(formKey="dGVacG0ydVHnaNHjRjVTUTEtb3FPWGc6MQ",
mode = ReportingInteractionMode.DIALOG,
resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
resDialogText = R.string.crash_dialog_text,
resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
//resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
resDialogOkToast = R.string.crash_dialog_ok_toast) // optional. displays a Toast message when the user accepts to send a report.

//send report by email
//@ReportsCrashes(formKey = "", // will not be used
//mailTo = "reports@yourdomain.com",
//mode = ReportingInteractionMode.TOAST,
//resToastText = R.string.crash_toast_text)
public class UreyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
//		ACRA.init(this);
		initImageLoader(getApplicationContext());
	}
	
	public static void initImageLoader(Context context){
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
			.threadPriority(Thread.NORM_PRIORITY -2)
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.writeDebugLogs()
			.build();
		
		ImageLoader.getInstance().init(configuration);
	}
}
