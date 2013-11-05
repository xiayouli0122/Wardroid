package com.example.ward.media.image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Thumbnails;
import android.widget.ImageView;

public class AsyncImageLoader2 {
	private static final String TAG = AsyncImageLoader2.class.getName();
	// SoftReference鏄蒋寮曠敤锛屾槸涓轰簡鏇村ソ鐨勪负浜嗙郴缁熷洖鏀跺彉閲?
	private HashMap<Long, SoftReference<Bitmap>> audioCache;
	private HashMap<String, SoftReference<Drawable>> apkCache;
	private HashMap<String, SoftReference<Bitmap>> videoCache;
	public static  HashMap<String ,SoftReference<Bitmap>> bitmapCache;
	private Context context;
	private ExecutorService pool ; 
	
//	private FileInfoManager fileInfoManager;

	public AsyncImageLoader2(Context context) {
		this.context = context;
//		audioCache = new HashMap<Long, SoftReference<Bitmap>>();
//		apkCache = new HashMap<String, SoftReference<Drawable>>();
//		videoCache = new HashMap<String, SoftReference<Bitmap>>();
		bitmapCache = new HashMap<String, SoftReference<Bitmap>>();
		
//		fileInfoManager = new FileInfoManager(context);
//		pool = Executors.newFixedThreadPool(15);
	}
	
	public Bitmap loadImage(final String url, final ImageView imageView, final ILoadImageCallback callback){
		if (bitmapCache.containsKey(url)) {
			SoftReference<Bitmap> softReference = bitmapCache.get(url);
			Bitmap bitmap = softReference.get();
			if (null != bitmap) {
				return bitmap;
			}
		}
		
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				callback.onObtainBitmap((Bitmap)msg.obj, imageView);
			}
		};
		
		new Thread(){
			public void run() {
				Bitmap bitmap = null;
				bitmap = getBitmapFromUrl(url);
				bitmapCache.put(url, new SoftReference<Bitmap>(bitmap));
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);
			}
		}.start();
		
		return null;
	}
	
	private int width = 120;//每个Item的宽度,可以根据实际情况修改
	private int height = 150;//每个Item的高度,可以根据实际情况修改
	private Bitmap getBitmapFromUrl(String url){
		Bitmap bitmap = null;
		bitmap = ImageLoaderGridViewActivity.gridviewBitmapCaches.get(url);
		if(bitmap != null){
			System.out.println(url);
			return bitmap;
		}
//		url = url.substring(0, url.lastIndexOf("/"));//处理之前的路径区分，否则路径不对，获取不到图片
//		System.out.println("url:" + url);
		
		//bitmap = BitmapFactory.decodeFile(url);
		//这里我们不用BitmapFactory.decodeFile(url)这个方法
		//用decodeFileDescriptor()方法来生成bitmap会节省内存
		//查看源码对比一下我们会发现decodeFile()方法最终是以流的方式生成bitmap
		//而decodeFileDescriptor()方法是通过Native方法decodeFileDescriptor生成bitmap的
		
//		try {
//			FileInputStream is = new FileInputStream(url);
//			bitmap = BitmapFactory.decodeFileDescriptor(is.getFD());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize =4;
		Bitmap bitmap2 = BitmapFactory.decodeFile(url, options);
		
		bitmap = BitmapUtilities.getBitmapThumbnail(bitmap2,width,height);
		return bitmap;
	}
	
	/**
	 * get apk file icon
	 * @param apkPath
	 * @param imageView
	 * @param callback
	 * @return apk luancher icon
	 */
//	public Drawable loadApkDrawable(final String apkPath, final ImageView imageView, final ILoadImageCallback callback){
////		if (apkCache.containsKey(apkPath)) {
////			// 浠庣紦瀛樹腑鑾峰彇
////			SoftReference<Drawable> softReference = apkCache.get(apkPath);
////			Drawable drawable = softReference.get();
////			if (null != drawable) {
////				return drawable;
////			}
////		}
//		
////		final Handler handler = new Handler() {
////			public void handleMessage(Message message) {
////				callback.onObtainDrawable((Drawable)message.obj, imageView);
////			}
////		};
//		
////		new Thread() {
////			public void run() {
////				Drawable drawable = fileInfoManager.getApkIcon(apkPath);
////				apkCache.put(apkPath, new SoftReference<Drawable>(drawable));
////				Message msg = handler.obtainMessage(0, drawable);
////				handler.sendMessage(msg);
////			}
////		}.start();
////		return null;
//	}

	/**
	 * get audio album and show 
	 * @param songId  song id in db
	 * @param albumId  song album id in db
	 * @param imageView
	 * @param callback
	 * @return audio album
	 */
	public Bitmap loadAudioBitmap(final long songId, final long  albumId, final ImageView imageView, final ILoadImageCallback callback) {
		if (audioCache.containsKey(songId)) {
			// 浠庣紦瀛樹腑鑾峰彇
			SoftReference<Bitmap> softReference = audioCache.get(songId);
			Bitmap bitmap = softReference.get();
			if (null != bitmap) {
				return bitmap;
			}
		}
		
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				callback.onObtainBitmap((Bitmap) message.obj, imageView);
			}
		};
		
		// 寤虹珛涓?釜鑾峰彇涓撹緫鍥剧墖
		new Thread() {
			public void run() {
//				Bitmap bitmap = MediaInfoManager.getArtwork(context, songId, albumId, true, false);
//				audioCache.put(songId, new SoftReference<Bitmap>(bitmap));
//				Message msg = handler.obtainMessage(0, bitmap);
//				handler.sendMessage(msg);
			}
		}.start();
		return null;
	}
	
	/**
	 * get video thumbnail and show in imageview
	 * @param path video path
	 * @param imageView 
	 * @param callback 
	 * @return video thumbnail
	 */
	public Bitmap loadVideoBitmap(final String path, final ImageView imageView, final ILoadImageCallback callback) {
		if (videoCache.containsKey(path)) {
			// 浠庣紦瀛樹腑鑾峰彇
			SoftReference<Bitmap> softReference = videoCache.get(path);
			Bitmap bitmap = softReference.get();
			if (null != bitmap) {
				return bitmap;
			}
		}
		
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				callback.onObtainBitmap((Bitmap) message.obj, imageView);
			}
		};
		
		new Thread() {
			public void run() {
				Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);
				videoCache.put(path, new SoftReference<Bitmap>(bitmap));
				Message msg = handler.obtainMessage(0, bitmap);
				handler.sendMessage(msg);
			}
		}.start();
		return null;
	}

	/**
	 * 寮傛鍔犺浇鍥剧墖鐨勫洖璋冩帴鍙?
	 */
	public interface ILoadImageCallback {
		public void onObtainBitmap(Bitmap bitmap, ImageView imageView);
		public void onObtainDrawable(Drawable drawable, ImageView imageView);
	}
}
