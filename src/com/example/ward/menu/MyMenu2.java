package com.example.ward.menu;

import com.yuri.utilslib.BitmapUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author haozi
 *
 */
public class MyMenu2 extends PopupWindow {

	private Context context;
	private GridView gvTitle;
	private LinearLayout mLayout;
	private MyMenuAdapter myMenuAdapter;
	
	/**
	 * MyMenu
	 * @param context
	 * @param titleClick
	 * @param myMenuAdapter
	 * @param myMenuBackgroundColor
	 * @param myMenuAnim
	 */
	public MyMenu2(Context context, OnItemClickListener titleClick, 
			int myMenuBackgroundColor, int myMenuAnim){
		super(context);
		
		this.context = context;
		myMenuAdapter = (MyMenuAdapter) createMenuAdapter();
		
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		gvTitle = new GridView(context);
		gvTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		gvTitle.setNumColumns(myMenuAdapter.getCount());
		gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(Gravity.CENTER);
		gvTitle.setOnItemClickListener(titleClick);
		gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gvTitle.setAdapter(myMenuAdapter);
		this.mLayout.addView(gvTitle);
		setContentView(this.mLayout);
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(myMenuBackgroundColor));
		setAnimationStyle(myMenuAnim);
		setFocusable(true);
        mLayout.setFocusableInTouchMode(true);
        mLayout.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if(keyCode == KeyEvent.KEYCODE_MENU && isShowing()){
					dismiss();
					return true;
				}
				return false;
			}
		});
	}
	
	/**
	 * �˵�ѡ��ļ���
	 *
	 * @param index
	 * 
	 * @project:baby-bus for xxx 
	 *
	 * @author haozi on 2012-3-5
	 */
    public void setTitleSelect(int index)  
    {  
        gvTitle.setSelection(index);  
        this.myMenuAdapter.setFocus(index);  
        switch (index) {
		case 0:
			Toast.makeText(context, "111", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(context, "222", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(context, "333", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(context, "444", Toast.LENGTH_SHORT).show();
			break;
		}
    }
    
    /**
     *
     * @return
     * 
     * @project:baby-bus for xxx 
     *
     * @author haozi on 2012-3-5
     */
    public BaseAdapter createMenuAdapter(){
    	
    	return new MyMenuAdapter(context, 
				new String[]{"AAA", "BBB", "CCC", "DDD"},
				16, 
				Color.argb(255, 139, 106, 47),
				Color.argb(255, 247, 246, 234),
				Color.argb(255, 247, 246, 234),
				Color.argb(255, 139, 106, 47));
    }
	
	/**
	 * @author haozi
	 *
	 */
	class MyMenuAdapter extends BaseAdapter{

		private Context context;
		private TextView[] tvTitles;
		private int fontUnSelColor;
		private int fontSelColor;
		private int bgUnSelColor;
		private int bgSelColor;
		
		public MyMenuAdapter(Context context, String[] titles,
				int fontSize, int fontUnSelColor, int fontSelColor, int bgUnSelColor, int bgSelColor){
			this.context = context;
			this.fontUnSelColor = fontUnSelColor;
			this.fontSelColor = fontSelColor;
			this.bgUnSelColor = bgUnSelColor;
			this.bgSelColor = bgSelColor;
			tvTitles = new TextView[titles.length];
			for(int i=0; i<titles.length; i++){
				tvTitles[i] = new TextView(context);
				tvTitles[i].setText(titles[i]);
				tvTitles[i].setTextSize(fontSize);
				tvTitles[i].setTextColor(fontUnSelColor);
				tvTitles[i].setGravity(Gravity.CENTER);
				tvTitles[i].setPadding(10, 30, 10, 30);
			}
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tvTitles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tvTitles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return tvTitles[position].getId();
		}
		
		/**
		 *
		 * @param index
		 * 
		 * @project:
		 *
		 * @author haozi on 2012-3-5
		 */
		public void setFocus(int index){
			
			for(int i=0; i<tvTitles.length; i++){
				
				if(i != index){
					this.tvTitles[i].setBackgroundColor(this.bgUnSelColor);
					this.tvTitles[i].setTextColor(this.fontUnSelColor);
				}else{
					this.tvTitles[i].setBackgroundColor(this.bgSelColor);
					this.tvTitles[i].setTextColor(this.fontSelColor);					
				}
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = null;
			if(convertView == null){
				v = tvTitles[position];
			}else{
				v = convertView;
			}
			return v;
		}
	}
}
