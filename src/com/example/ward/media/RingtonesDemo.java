package com.example.ward.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ward.R;

public class RingtonesDemo extends Activity {
	
	private Context mContext;
	
	private List<Ringtone> ringtone_list = new ArrayList<Ringtone>();
	private List<Ringtone> ringtone_list_2 = new ArrayList<Ringtone>();
	
	private List<Map<String, String>> lists = new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ringtones_main);
		
		mContext = this;
		
		ListView listView = (ListView)findViewById(R.id.ringtone_listview);
		
		ringtone_list = getRingtoneList(RingtoneManager.TYPE_RINGTONE);
//		ringtone_list_2 = getRingtoneList(RingtoneManager.TYPE_NOTIFICATION);
		
		Map<String, String> map;
		for(Ringtone ringtone : ringtone_list){
			String title = ringtone.getTitle(mContext);
			map = new HashMap<String, String>();
			Uri uri = RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_RINGTONE);
			map.put("title", RingtoneManager.getRingtone(mContext, uri).getTitle(mContext));
			lists.add(map);
		}
		
		for (Ringtone ringtone : ringtone_list_2) {
			String title = ringtone.getTitle(mContext);
			map = new HashMap<String, String>();
			map.put("title", title);
			lists.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(mContext, lists, R.layout.ringtones_item, new String[]{"title"}, new int[]{R.id.ringtone_text});
		listView.setAdapter(adapter);
	}
	
	/**
	 * 使用AsyncTask时，需要传入三个泛型
	 * */
//	class AsyncTaskTest extends AsyncTask<Params, Progress, Result>
	
	public List<Ringtone> getRingtoneList(int type) {
		List<Ringtone> resArr = new ArrayList<Ringtone>();
		RingtoneManager manager = new RingtoneManager(mContext);
		manager.setType(type);
		Cursor cursor = manager.getCursor();
//		manager.getCursor();
		int count = cursor.getCount();

		for (int i = 0; i < count; i++) {
			resArr.add(manager.getRingtone(i));
		}
//		resArr.add(manager.getRingtone(0));
		
		return resArr;
	}
}
