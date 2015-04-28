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
 * 我的菜单
 * @author haozi
 *
 */
public class MyMenu2 extends PopupWindow {

	private Context context;
	private GridView gvTitle;                 // 标题
	private LinearLayout mLayout;             // PopupWindow的布局
	private MyMenuAdapter myMenuAdapter;      // 自定义菜单的适配器
	
	/**
	 * MyMenu适配器
	 * @param context 调用方的上下文
	 * @param titleClick 菜单点击事件
	 * @param myMenuAdapter 菜单适配器
	 * @param myMenuBackgroundColor 菜单背景颜色
	 * @param myMenuAnim 菜单需要的动画效果
	 */
	public MyMenu2(Context context, OnItemClickListener titleClick, 
			int myMenuBackgroundColor, int myMenuAnim){
		super(context);
		
		this.context = context;
		// 创建适配器
		myMenuAdapter = (MyMenuAdapter) createMenuAdapter();
		
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		// 菜单选项栏
		gvTitle = new GridView(context);
		gvTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		// 设置列数
		gvTitle.setNumColumns(myMenuAdapter.getCount());
		// 设置宽度自适应
		gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(Gravity.CENTER);
		gvTitle.setOnItemClickListener(titleClick);
		// 选中的时候为透明色
		gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gvTitle.setAdapter(myMenuAdapter);
		// 把gvTitle放在layout上
		this.mLayout.addView(gvTitle);
		// 设置菜单的特征
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
	 * 菜单选项的监听
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
			Toast.makeText(context, "目录", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(context, "书签", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(context, "摘要", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(context, "设置", Toast.LENGTH_SHORT).show();
			break;
		}
    }
    
    /**
     * 创建菜单的Adapter
     *
     * @return
     * 
     * @project:baby-bus for xxx 
     *
     * @author haozi on 2012-3-5
     */
    public BaseAdapter createMenuAdapter(){
    	
    	return new MyMenuAdapter(context, 
				new String[]{"目录", "书签", "摘要", "设置"}, 
				16, 
				Color.argb(255, 139, 106, 47),  // 未选中字体颜色
				Color.argb(255, 247, 246, 234), // 选中字体颜色
				Color.argb(255, 247, 246, 234), // 未选中背景颜色
				Color.argb(255, 139, 106, 47)); // 选中背景颜色
    }
	
	/**
	 * 适配器
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
		
		/**
		 * 设置 title
		 * @param context 调用方的上下文
		 * @param titles 数据
		 * @param fontSize 字体大小
		 * @param fontUnSelColor 未选中字体颜色
		 * @param fontSelColor 选中字体颜色
		 * @param bgUnSelColor 未选中背景颜色
		 * @param bgSelColor 选中背景颜色
		 */
		public MyMenuAdapter(Context context, String[] titles,
				int fontSize, int fontUnSelColor, int fontSelColor, int bgUnSelColor, int bgSelColor){
			this.context = context;
			this.fontUnSelColor = fontUnSelColor;
			this.fontSelColor = fontSelColor;
			this.bgUnSelColor = bgUnSelColor;
			this.bgSelColor = bgSelColor;
			// 根据传递进来的titles创建menu上的textView。
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
		 * 设置选中效果
		 *
		 * @param index
		 * 
		 * @project:
		 *
		 * @author haozi on 2012-3-5
		 */
		public void setFocus(int index){
			
			for(int i=0; i<tvTitles.length; i++){
				
				if(i != index){// 如果未选中
					this.tvTitles[i].setBackgroundColor(this.bgUnSelColor);
					this.tvTitles[i].setTextColor(this.fontUnSelColor);
				}else{// 如果选中
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
