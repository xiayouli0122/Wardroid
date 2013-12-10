package com.example.ward.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;
import com.example.ward.app.AsyncAppIconLoader.ILoadImageCallback;
import com.example.ward.util.WardUtils;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstallAppAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private boolean mIdleFlag = true;
	private List<File> mList = new ArrayList<File>();
	private AsyncAppIconLoader iconLoader;
	private SparseBooleanArray mSelectedArray;
	
	public static final int MODE_NORMAL = 0;
	public static final int MODE_EDIT = 1;
	private int mMode = MODE_NORMAL;
	
	public InstallAppAdapter(Context context, List<File> list){
		mInflater = LayoutInflater.from(context);
		mList = list;
		iconLoader = new AsyncAppIconLoader(context);
		mSelectedArray = new SparseBooleanArray(list.size());
		initArray();
	}

	public void initArray(){
		for (int i = 0; i < mSelectedArray.size(); i++) {
			mSelectedArray.put(i, false);
		}
	}
	
	public void changeMode(int mode){
		this.mMode = mode;
	}
	
	public boolean isMode(int mode){
		return mMode == mode;
	}
	
	public void setSelected(int position){
		setSelected(position, !isSelected(position));
		notifyDataSetChanged();
	}
	
	public void setSelected(int position, boolean value){
		mSelectedArray.put(position, value);
	}
	
	public boolean isSelected(int position){
		return mSelectedArray.get(position);
	}
	
	public void setFlag(boolean idle){
		this.mIdleFlag = idle;
	}
	
	public void setList(List<File> list){
		mList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class ViewHolder{
		ImageView imageView;
		TextView nameView;
		TextView sizeView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;
		if (null == convertView || null == convertView.getTag()) {
			view = mInflater.inflate(R.layout.app_install_item, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) view.findViewById(R.id.icon);
			holder.nameView = (TextView) view.findViewById(R.id.text);
			holder.sizeView = (TextView) view.findViewById(R.id.text_size);
		}else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		File file = mList.get(position);
		String name = file.getName();
		String size = WardUtils.getFormatSize(file.length());
		holder.nameView.setText(name);
		holder.sizeView.setText(size);
		if (!mIdleFlag) {
			if (AsyncAppIconLoader.bitmapCache.size() > 0
					&& AsyncAppIconLoader.bitmapCache.get(file.getAbsolutePath()) != null) {
				holder.imageView.setImageBitmap(AsyncAppIconLoader.bitmapCache
						.get(file.getAbsolutePath()).get());
			} else {
				holder.imageView.setImageResource(R.drawable.ic_launcher);
			}
		}else {
			Bitmap bitmap = iconLoader.loadImage(file.getAbsolutePath(), holder.imageView, new ILoadImageCallback() {
				@Override
				public void onObtainBitmap(Bitmap bitmap, ImageView imageView) {
					imageView.setImageBitmap(bitmap);
				}
			});
			
			if (bitmap != null) {
				holder.imageView.setImageBitmap(bitmap);
			}else {
				holder.imageView.setImageResource(R.drawable.ic_launcher);
			}
		}
		
		if (isSelected(position)) {
			view.setBackgroundResource(android.R.color.holo_blue_dark);
		}else {
			view.setBackgroundResource(android.R.color.transparent);
		}
		return view;
	}

}
