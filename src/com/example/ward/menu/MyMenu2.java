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
 * �ҵĲ˵�
 * @author haozi
 *
 */
public class MyMenu2 extends PopupWindow {

	private Context context;
	private GridView gvTitle;                 // ����
	private LinearLayout mLayout;             // PopupWindow�Ĳ���
	private MyMenuAdapter myMenuAdapter;      // �Զ���˵���������
	
	/**
	 * MyMenu������
	 * @param context ���÷���������
	 * @param titleClick �˵�����¼�
	 * @param myMenuAdapter �˵�������
	 * @param myMenuBackgroundColor �˵�������ɫ
	 * @param myMenuAnim �˵���Ҫ�Ķ���Ч��
	 */
	public MyMenu2(Context context, OnItemClickListener titleClick, 
			int myMenuBackgroundColor, int myMenuAnim){
		super(context);
		
		this.context = context;
		// ����������
		myMenuAdapter = (MyMenuAdapter) createMenuAdapter();
		
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		// �˵�ѡ����
		gvTitle = new GridView(context);
		gvTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		// ��������
		gvTitle.setNumColumns(myMenuAdapter.getCount());
		// ���ÿ������Ӧ
		gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(Gravity.CENTER);
		gvTitle.setOnItemClickListener(titleClick);
		// ѡ�е�ʱ��Ϊ͸��ɫ
		gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gvTitle.setAdapter(myMenuAdapter);
		// ��gvTitle����layout��
		this.mLayout.addView(gvTitle);
		// ���ò˵�������
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
			Toast.makeText(context, "Ŀ¼", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(context, "��ǩ", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(context, "ժҪ", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(context, "����", Toast.LENGTH_SHORT).show();
			break;
		}
    }
    
    /**
     * �����˵���Adapter
     *
     * @return
     * 
     * @project:baby-bus for xxx 
     *
     * @author haozi on 2012-3-5
     */
    public BaseAdapter createMenuAdapter(){
    	
    	return new MyMenuAdapter(context, 
				new String[]{"Ŀ¼", "��ǩ", "ժҪ", "����"}, 
				16, 
				Color.argb(255, 139, 106, 47),  // δѡ��������ɫ
				Color.argb(255, 247, 246, 234), // ѡ��������ɫ
				Color.argb(255, 247, 246, 234), // δѡ�б�����ɫ
				Color.argb(255, 139, 106, 47)); // ѡ�б�����ɫ
    }
	
	/**
	 * ������
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
		 * ���� title
		 * @param context ���÷���������
		 * @param titles ����
		 * @param fontSize �����С
		 * @param fontUnSelColor δѡ��������ɫ
		 * @param fontSelColor ѡ��������ɫ
		 * @param bgUnSelColor δѡ�б�����ɫ
		 * @param bgSelColor ѡ�б�����ɫ
		 */
		public MyMenuAdapter(Context context, String[] titles,
				int fontSize, int fontUnSelColor, int fontSelColor, int bgUnSelColor, int bgSelColor){
			this.context = context;
			this.fontUnSelColor = fontUnSelColor;
			this.fontSelColor = fontSelColor;
			this.bgUnSelColor = bgUnSelColor;
			this.bgSelColor = bgSelColor;
			// ���ݴ��ݽ�����titles����menu�ϵ�textView��
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
		 * ����ѡ��Ч��
		 *
		 * @param index
		 * 
		 * @project:
		 *
		 * @author haozi on 2012-3-5
		 */
		public void setFocus(int index){
			
			for(int i=0; i<tvTitles.length; i++){
				
				if(i != index){// ���δѡ��
					this.tvTitles[i].setBackgroundColor(this.bgUnSelColor);
					this.tvTitles[i].setTextColor(this.fontUnSelColor);
				}else{// ���ѡ��
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
