package com.example.ward.menu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.ward.R;

/**
 * @author Administrator
 * 
 */
public class MenuDemo extends Dialog implements OnItemClickListener {
	private Context context;

	private GridView menuGrid;

	private MenuItemDemo Menu_Item;

	// private Bitmap bmp_9path;
	//
	// private NinePatch np;

	public MenuDemo(Context context, String myMenuStr[], int myMenuBit[],
			MenuItemDemo Menu_Item) {
		super(context, R.style.dialog_fullscreen);
		setContentView(R.layout.mymenu);
		this.context = context;
		this.Menu_Item = Menu_Item;
		setProperty();
		// bmp_9path = Tool.CreatImage(context, R.drawable.tool_box_bkg_wood);
		// np = new NinePatch(bmp_9path, bmp_9path.getNinePatchChunk(), null);
		// np.getTransparentRegion(new Rect(250, 50, 250 + bmp_9path
		// .getWidth() * 2, 90 + bmp_9path.getHeight() * 2));
		menuGrid = (GridView) this.findViewById(R.id.GridView_toolbar);
		menuGrid.setAdapter(getMenuAdapter(myMenuStr, myMenuBit));
		menuGrid.setOnItemClickListener(this);

//		View view = findViewById(R.id.mainlayout);
//		view.getBackground().setAlpha(0);
//		view.setBackgroundResource(R.drawable.tool_box_bkg_wood);

		this.setCanceledOnTouchOutside(true);

	}


	private void setProperty() {
		// // TODO Auto-generated method stub
		// window = getWindow();
		// WindowManager.LayoutParams wl = window.getAttributes();
		// // wl.alpha=0.6f;
		// wl.screenBrightness = 1;//
		// wl.gravity = Gravity.CENTER_VERTICAL;
		// wl.setTitle("");
		// window.setAttributes(wl);

		Window w = getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.alpha = 1.0f;//

		// lp.dimAmount = 0.0f;//
		// lp.y = 100; //
		// lp.gravity = Gravity.CENTER_VERTICAL;
		w.setAttributes(lp);

	}

	/**
	 * Adapter
	 * 
	 * @param menuNameArray
	 * @param imageResourceArray
	 * @return SimpleAdapter
	 */
	public SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Menu_Item.ItemClickListener(position);
		this.dismiss();
	}

}
