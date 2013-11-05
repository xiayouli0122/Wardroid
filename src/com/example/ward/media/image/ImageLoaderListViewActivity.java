package com.example.ward.media.image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ward.R;

public class ImageLoaderListViewActivity extends Activity implements OnScrollListener, OnItemClickListener{
	private static final String TAG = "MainActivity";
	
	private ListView listView;
	
	private ArrayList<String> mList = null;
	//用来保存GridView中每个Item的图片，以便释放
	public static Map<String,Bitmap> gridviewBitmapCaches = new HashMap<String,Bitmap>();

	private MyListViewAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageloader_listview_main);
        findViews();
        initData();
        setAdapter();
    }
  
    private void findViews(){
    	listView = (ListView) findViewById(R.id.image_listview);
    	listView.setEmptyView(findViewById(R.id.image_empty));
    	listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, 1, 0, "Info");
			}
		});
    	listView.setOnItemClickListener(this);
    }
    
    private void initData(){
    	mList = new ArrayList<String>();
    	mList = getData();
//    	String url = "/mnt/sdcard/testGridView/jay.png";//为sd卡下面创建testGridView文件夹，将图片放入其中
//    	//为了方便测试，我们这里只存入一张图片，将其路径后面添加数字进行区分，到后面要获取图片时候再处理该路径。
//    	for(int i=0;i<1000;i++){
//    		mList.add(url+"/"+i);//区分路径
//    	}
    }
    
    private void setAdapter(){
    	mAdapter = new MyListViewAdapter(this, mList);
    	listView.setAdapter(mAdapter);
    	listView.setOnScrollListener(this);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	AdapterView.AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
    	int position = menuInfo.position;
    	Toast.makeText(ImageLoaderListViewActivity.this, mList.get(position), Toast.LENGTH_SHORT).show();
    	return super.onContextItemSelected(item);
    }
    
    //卸载图片的函数
  	private void recycleBitmapCaches(int fromPosition,int toPosition){		
  		Bitmap delBitmap = null;
  		for(int del=fromPosition;del<toPosition;del++){
  			delBitmap = gridviewBitmapCaches.get(mList.get(del));	
  			if(delBitmap != null){	
  				//如果非空则表示有缓存的bitmap，需要清理	
  				Log.d(TAG, "release position:"+ del);		
  				//从缓存中移除该del->bitmap的映射		
  				gridviewBitmapCaches.remove(mList.get(del));		
  				delBitmap.recycle();	
  				delBitmap = null;
  			}
  		}		
  	}
  	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		//注释：firstVisibleItem为第一个可见的Item的position，从0开始，随着拖动会改变
		//visibleItemCount为当前页面总共可见的Item的项数
		//totalItemCount为当前总共已经出现的Item的项数
		recycleBitmapCaches(0,firstVisibleItem);
		recycleBitmapCaches(firstVisibleItem+visibleItemCount, totalItemCount);
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_FLING:
			mAdapter.setFlagBusy(false);
			break;
		case OnScrollListener.SCROLL_STATE_IDLE:
			mAdapter.setFlagBusy(true);
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			mAdapter.setFlagBusy(false);
			break;

		default:
			break;
		}
		mAdapter.notifyDataSetChanged();
	}
	
	private ArrayList<String> getData(){

		Log.d(TAG, "getImageInfo.start:" + System.currentTimeMillis());
		ArrayList<String> imageInfos = new ArrayList<String>();
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.MediaColumns.DATE_MODIFIED);

		if (cursor.moveToFirst()) {
			do {
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
				long size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
				if (size != 0) {
					if (new File(path).exists()) {
						imageInfos.add(path);
					}else {
						Log.e("Yuri", path + " is not exist");
					}
					
					Log.d("Yuri", "path:" + path + "\n" + "size=" + size);
				}else {
					System.out.println(path);
				}
//				System.out.println("path:" + path);
//				imageInfos.add(path);
			} while (cursor.moveToNext());
		}
		cursor.close();
		Log.d(TAG, "getImageInfo.end:" + System.currentTimeMillis());
		return imageInfos;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(ImageLoaderListViewActivity.this, ImagePagerActivity.class);
		intent.putStringArrayListExtra("image", mList);
		intent.putExtra("position", position);
		startActivity(intent);
		
	}
	
		
}
