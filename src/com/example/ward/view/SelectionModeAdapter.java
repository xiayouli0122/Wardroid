package com.example.ward.view;

import com.example.ward.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectionModeAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mDatas = null;
	private final LayoutInflater mInflater;

	public static final int MODE_EDIT = 1;
	public static final int MODE_NORMAL = 2;

	//填充数据的总数
	private int mSize = -1;

	private int mMode = MODE_NORMAL;

	//定义一个数组，保存每一个item是否被选中
	private boolean mCheckedArray[] = null;

	/**
	 * 被选中的数量
	 */
	private int mCheckedCount = 0;

	public SelectionModeAdapter(Context context, String[] datas){
		this.mContext = context;
		this.mDatas = datas;

		mSize = datas.length;

		mInflater = LayoutInflater.from(mContext);

		mCheckedArray = new boolean[datas.length];
		//初始化，都不被选中
		for (int i = 0; i < datas.length; i++) {
			mCheckedArray[i] = false;
		}
	}

	//点一下，选中，点两下取消选中
	public void setChecked(int position){
		mCheckedArray[position] = !mCheckedArray[position];
		if (mCheckedArray[position]) {
			mCheckedCount ++ ;
		}else {
			mCheckedCount -- ;
		}
		notifyDataSetChanged();
	}

	/**
	 * 全选
	 */
	public void selectAll(){
		for (int i = 0; i < mSize; i++) {
			mCheckedArray[i] = true;
		}
		notifyDataSetChanged();
	}

	/**
	 * 取消全选
	 */
	public void unSelectAll(){
		for (int i = 0; i < mSize; i++) {
			mCheckedArray[i] = false;
		}
		notifyDataSetChanged();
	}

	/**
	 * 判断是否被选中
	 * @param position
	 * @return
	 */
	public boolean isChecked(int position){
		return mCheckedArray[position];
	}

	public void changeMode(int mode){
		switch (mode) {
			case MODE_NORMAL:
				unSelectAll();
				break;

			default:
				break;
		}
		mMode = mode;
		notifyDataSetChanged();
	}

	public int getMode(){
		return mMode;
	}

	public boolean isMode(int mode){
		return mMode == mode;
	}

	/**
	 * @return
	 */
	public int getCheckedCount(){
		return mCheckedCount;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSize;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder{
		TextView mTextView;
	}

	private static final int THEME_COLOR_DEFAULT = 0x7F33b5e5;
	private static final int COLOR_ALPHA = 0x7FFFFFFF;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		View view = convertView;

		if (view == null) {
			view = mInflater.inflate(R.layout.listview_selection_mode_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) view.findViewById(R.id.item_text);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.mTextView.setText(mDatas[position]);

		switch (mMode) {
			case MODE_EDIT:
				if (mCheckedArray[position]) {
					//被选中，就更改背景颜色
					view.setBackgroundColor(THEME_COLOR_DEFAULT);
				}else {
					view.setBackgroundColor(Color.TRANSPARENT);
				}
				break;
			case MODE_NORMAL:
				//do nothing
				break;

			default:
				break;
		}
		return view;
	}

}