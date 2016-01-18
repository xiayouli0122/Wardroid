package com.example.ward.menu;

import com.example.ward.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author haozi
 * 
 */
public class MyMenu extends PopupWindow implements OnItemClickListener {

	private MyMenuAdapter myMenuAdapter; //
	private OnMenuItemClickListener mListener;
	private LinearLayout mLayout;
	private GridView gvTitle;  
	private Menu menu;

	public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
		mListener = listener;
	}
	
	/**
	 * @param context
	 * @param titleClick
	 * @param myMenuAdapter
	 * @param menu
	 * @param myMenuAnim
	 */
	public MyMenu(Context context, Menu menu,
			int myMenuAnim) {
		super(context);
		
		this.menu = menu;
		LayoutInflater inflater = LayoutInflater.from(context);
		mLayout = (LinearLayout) inflater.inflate(R.layout.custommenu, null);
		gvTitle = (GridView) mLayout.findViewById(R.id.gv_custom_menu);
		
		myMenuAdapter = new MyMenuAdapter(context, menu);
		gvTitle.setAdapter(myMenuAdapter);
		gvTitle.setOnItemClickListener(this);

		setContentView(this.mLayout);
		
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
//		setBackgroundDrawable(new ColorDrawable(Color.argb(255, 139, 106, 47)));
		setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		setAnimationStyle(myMenuAnim);
		setFocusable(true);

		mLayout.setFocusableInTouchMode(true);
		mLayout.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_MENU && isShowing()) {
					dismiss();
					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		mListener.onMenuItemClick(menu.getItem(position));
		dismiss();
	}

	/**
	 * @author haozi
	 * 
	 */
	class MyMenuAdapter extends BaseAdapter {

		private Context context;
		private Menu menu;
		private LayoutInflater mInflater = null;

		/**
		 * @param context
		 */
		public MyMenuAdapter(Context context, Menu menu) {
			this.context = context;
			this.menu = menu;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return menu.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return menu.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return menu.getItem(position).getItemId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = null;
			if (convertView == null) {
				v = mInflater.inflate(R.layout.custommenu_item, null);
			} else {
				v = convertView;
			}
			
			ImageView imageView = (ImageView) v.findViewById(R.id.iv_custommenu_icon);
			TextView textView = (TextView) v.findViewById(R.id.tv_custommenu_title);
			
			imageView.setImageDrawable(menu.getItem(position).getIcon());
			textView.setText(menu.getItem(position).getTitle());
			return v;
		}
	}
}
