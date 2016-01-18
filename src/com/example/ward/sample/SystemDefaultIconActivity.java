package com.example.ward.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ward.R;

public class SystemDefaultIconActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.system_default_icon_main);

		ListView listView = (ListView) findViewById(R.id.list);

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < SystemDefaultIconList.ICONs.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>(); // 图像资源的ID
			map.put("ItemImage", SystemDefaultIconList.ICONs[i]);
			map.put("ItemTitle", SystemDefaultIconList.ICON_STR[i]);
			listItem.add(map);
		}

		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
				R.layout.system_default_icon_item,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.img, R.id.ItemTitle });
		// 添加并且显示
		listView.setAdapter(listItemAdapter);
	}
}
