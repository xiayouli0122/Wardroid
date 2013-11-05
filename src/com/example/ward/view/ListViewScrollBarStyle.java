package com.example.ward.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ward.R;

public class ListViewScrollBarStyle extends Activity {
	private ListView mListView;
	private SimpleAdapter mAdapter;
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_scroll_style);
		
		mListView = (ListView) findViewById(R.id.listview);
		
		HashMap<String, String>	map ;
		for (int i = 0; i < 500; i++) {
			map = new HashMap<String, String>();
			map.put("name", i+ "");
			mData.add(map);
		}
		
		mAdapter = new SimpleAdapter(this, mData, android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1});
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "");
		return true;
	}
	
	public void setStyle(Drawable icon) {
		try {
			Field f = AbsListView.class.getDeclaredField("mFastScroller");
			f.setAccessible(true);
			Object o = f.get(mListView);
			f = f.getType().getDeclaredField("mThumbDrawable");
			f.setAccessible(true);
			Drawable drawable = (Drawable) f.get(o);
			drawable = icon;
			f.set(o, drawable);
			Toast.makeText(this, f.getType().getName(), 1000).show();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
