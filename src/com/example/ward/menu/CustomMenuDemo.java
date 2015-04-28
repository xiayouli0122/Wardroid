package com.example.ward.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ward.R;
import com.example.ward.util.WardUtils;

import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CustomMenuDemo extends Activity {
	private MyMenu myMenu;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.custommenu_main);
		// force show virtual menu key
		WardUtils.forceShowMenuKey(getWindow());
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		Map<String, String> map;
		for (int i = 0; i < 20; i++) {
			map = new HashMap<String, String>();
			map.put("name", "name" + i);
			list.add((HashMap<String, String>) map);
		}
		
		ListView listView = (ListView) findViewById(R.id.lv_customemenu);
		SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1});
		listView.setAdapter(adapter);
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.test_menu, menu);
		myMenu = new MyMenu(this, menu,R.style.PopupAnimation);
		myMenu.setOnMenuItemClickListener(new MyMenuClick());
		myMenu.update();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (myMenu != null) {
			if (myMenu.isShowing())
				myMenu.dismiss();
			else {
				myMenu.showAtLocation(findViewById(R.id.linearLayout01),
						Gravity.BOTTOM, 0, 0);
			}
		}
		return false;// 返回为true 则显示系统menu
	}
	
	class MyMenuClick implements OnMenuItemClickListener{

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			Toast.makeText(CustomMenuDemo.this, item.getTitle(), Toast.LENGTH_SHORT).show();
			return false;
		}
		
	}
}
