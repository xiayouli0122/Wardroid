package com.example.ward.media.image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import com.example.ward.R;
import com.example.ward.media.image.AsyncImageLoader2.ILoadImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter{

	private Context mContext = null;
	private LayoutInflater mLayoutInflater = null;
	private List<String> mList = null;
	
	private AsyncImageLoader2 asyncImageLoader;

	private int width = 120;
	private int height = 150;

	
	public static class MyGridViewHolder{
		public ImageView imageview_thumbnail;
	}
	
	public MyGridViewAdapter(Context context,List<String> list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mList = list;
		mLayoutInflater = LayoutInflater.from(context);
		asyncImageLoader = new AsyncImageLoader2(context);
	}

	private boolean busyFlag = true;
	public void setFlagBusy(boolean flag){
		this.busyFlag = flag;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyGridViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.imageloader_gridview_item, null);
			viewHolder.imageview_thumbnail = (ImageView)convertView.findViewById(R.id.imageview_thumbnail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (MyGridViewHolder)convertView.getTag();
		}
		
		String url = mList.get(position);
//		if (cancelPotentialLoad(url, viewHolder.imageview_thumbnail)) {
//	         AsyncLoadImageTask task = new AsyncLoadImageTask(viewHolder.imageview_thumbnail);
//	         LoadedDrawable loadedDrawable = new LoadedDrawable(task);
//	         viewHolder.imageview_thumbnail.setImageDrawable(loadedDrawable);
//	         task.execute(position);
//	     }		 
		
		/////////////////////
		if (!busyFlag) {
			if (AsyncImageLoader2.bitmapCache.size() > 0 &&
					AsyncImageLoader2.bitmapCache.get(url) != null) {
				System.out.println("busy,but i have cache:" + url);
				viewHolder.imageview_thumbnail.setImageBitmap(AsyncImageLoader2.bitmapCache.get(url).get());
			}else {
				System.out.println("busy,no cache:" + url);
				viewHolder.imageview_thumbnail.setImageResource(R.drawable.imageloader_default_icon);
			}
		}else {
			Bitmap cacheBitmap = asyncImageLoader.loadImage(url, viewHolder.imageview_thumbnail, new ILoadImageCallback() {
				
				@Override
				public void onObtainDrawable(Drawable drawable, ImageView imageView) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onObtainBitmap(Bitmap bitmap, ImageView imageView) {
					// TODO Auto-generated method stub
					imageView.setImageBitmap(bitmap);
				}
			});
			
			if (null != cacheBitmap) {
				System.out.println("not busy, not null:" + url);
				viewHolder.imageview_thumbnail.setImageBitmap(cacheBitmap);
			}else {
				System.out.println("not busy, null:" + url);
//				viewHolder.imageview_thumbnail.setImageResource(R.drawable.imageloader_default_icon);
				viewHolder.imageview_thumbnail.setImageDrawable(mContext.getResources().getDrawable(R.drawable.imageloader_default_icon));
			}
		}
		
		////////////////////////
		return convertView;
	}
	
	
	
	private Bitmap getBitmapFromUrl(String url){
		Bitmap bitmap = null;
		bitmap = ImageLoaderGridViewActivity.gridviewBitmapCaches.get(url);
		if(bitmap != null){
			System.out.println(url);
			return bitmap;
		}
//		url = url.substring(0, url.lastIndexOf("/"));
//		System.out.println("url:" + url);
		
		bitmap = BitmapFactory.decodeFile(url);

//		try {
//			FileInputStream is = new FileInputStream(url);
//			bitmap = BitmapFactory.decodeFileDescriptor(is.getFD());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		bitmap = BitmapUtilities.getBitmapThumbnail(bitmap,width,height);
		return bitmap;
	}

	private class AsyncLoadImageTask extends AsyncTask<Integer, Void, Bitmap>{
		private String url = null;
		private final WeakReference<ImageView> imageViewReference;
		
		public AsyncLoadImageTask(ImageView imageview) {
			super();
			// TODO Auto-generated constructor stub
			imageViewReference = new WeakReference<ImageView>(imageview);
		}

		@Override
		protected Bitmap doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Bitmap bitmap = null;
			this.url = mList.get(params[0]);			
			bitmap = getBitmapFromUrl(url);
			ImageLoaderGridViewActivity.gridviewBitmapCaches.put(mList.get(params[0]), bitmap);			
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap resultBitmap) {
			// TODO Auto-generated method stub
			if(isCancelled()){
				resultBitmap = null;
			}
			if(imageViewReference != null){
				ImageView imageview = imageViewReference.get();
				AsyncLoadImageTask loadImageTask = getAsyncLoadImageTask(imageview);
			    // Change bitmap only if this process is still associated with it
			    if (this == loadImageTask) {
			    	imageview.setImageBitmap(resultBitmap);
			    	imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			    }
			}
			super.onPostExecute(resultBitmap);
		}							
	}
	
	
	private boolean cancelPotentialLoad(String url,ImageView imageview){
		AsyncLoadImageTask loadImageTask = getAsyncLoadImageTask(imageview);

	    if (loadImageTask != null) {
	        String bitmapUrl = loadImageTask.url;
	        if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
	        	loadImageTask.cancel(true);	        	
	        } else {
	            return false;
	        }
	    }
	    return true;

	}
	
	private AsyncLoadImageTask getAsyncLoadImageTask(ImageView imageview){
		if (imageview != null) {
	        Drawable drawable = imageview.getDrawable();
	        if (drawable instanceof LoadedDrawable) {
	        	LoadedDrawable loadedDrawable = (LoadedDrawable)drawable;
	            return loadedDrawable.getLoadImageTask();
	        }
	    }
	    return null;
	}

	public static class LoadedDrawable extends ColorDrawable{
		private final WeakReference<AsyncLoadImageTask> loadImageTaskReference;

	    public LoadedDrawable(AsyncLoadImageTask loadImageTask) {
	        super(Color.TRANSPARENT);
	        loadImageTaskReference =
	            new WeakReference<AsyncLoadImageTask>(loadImageTask);
	    }

	    public AsyncLoadImageTask getLoadImageTask() {
	        return loadImageTaskReference.get();
	    }

	}
}
