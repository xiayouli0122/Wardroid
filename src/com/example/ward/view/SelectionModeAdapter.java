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
	
	//������ݵ�����
	private int mSize = -1; 
	
	private int mMode = MODE_NORMAL;
	
	//����һ�����飬����ÿһ��item�Ƿ�ѡ��
	private boolean mCheckedArray[] = null;
	
	/**
	 * ��ѡ�е�����
	 */
	private int mCheckedCount = 0;
	
	public SelectionModeAdapter(Context context, String[] datas){
		this.mContext = context;
		this.mDatas = datas;
		
		mSize = datas.length;
		
		mInflater = LayoutInflater.from(mContext);
		
		mCheckedArray = new boolean[datas.length];
		//��ʼ����������ѡ��
		for (int i = 0; i < datas.length; i++) {
			mCheckedArray[i] = false;
		}
	}
	
	//��һ�£�ѡ�У�������ȡ��ѡ��
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
	 * ȫѡ
	 */
	public void selectAll(){
		for (int i = 0; i < mSize; i++) {
			mCheckedArray[i] = true;
		}
		notifyDataSetChanged();
	}
	
	/**
	 * ȡ��ȫѡ
	 */
	public void unSelectAll(){
		for (int i = 0; i < mSize; i++) {
			mCheckedArray[i] = false;
		}
		notifyDataSetChanged();
	}

	/**
	 * �ж��Ƿ�ѡ��
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
				//��ѡ�У��͸��ı�����ɫ
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
