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
			HashMap<String, Object> map = new HashMap<String, Object>(); // ͼ����Դ��ID
			map.put("ItemImage", SystemDefaultIconList.ICONs[i]);
			map.put("ItemTitle", SystemDefaultIconList.ICON_STR[i]);
			listItem.add(map);
		}

		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// ����Դ
				R.layout.system_default_icon_item,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemTitle" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.img, R.id.ItemTitle });
		// ��Ӳ�����ʾ
		listView.setAdapter(listItemAdapter);
	}
}
