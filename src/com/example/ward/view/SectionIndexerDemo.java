package com.example.ward.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.example.ward.R;
import com.yuri.utilslib.LogUtil;
import com.yuri.utilslib.ViewUtil.ViewHolder;

import android.R.integer;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SectionIndexerDemo extends Activity {
	private ListView mListView;
	private AsyncQueryHandler asyncQueryHandler;
	private DemoAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sectionindexer_main);
		
		mListView = (ListView) findViewById(R.id.listview);
		
		Uri uri = Uri.parse("content://com.android.contacts/data/phones");
		String[] projections = {"_id", "display_name", "data1", "phonebook_label"};
		asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
		asyncQueryHandler.startQuery(0, null, uri, projections, null, null, 
				"sort_key COLLATE LOCALIZED asc");
	}
	
	private class MyAsyncQueryHandler extends AsyncQueryHandler{

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			// TODO Auto-generated method stub
			super.onQueryComplete(token, cookie, cursor);
			if (cursor != null && cursor.getCount() > 0) {
				List<ContentValues> list = new ArrayList<ContentValues>();
				cursor.moveToFirst();
				ContentValues cv = null;
				do {
					cv = new ContentValues();
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					
					cv.put("name", name);
					cv.put("number", number);
					cv.put("sort_key", sortKey);
					
					list.add(cv);
				} while (cursor.moveToNext());
				
				if (list.size() > 0) {
					mAdapter = new DemoAdapter(getApplicationContext(), list);
					mListView.setAdapter(mAdapter);
				}
			}
		}
	}
	
	private class DemoAdapter extends BaseAdapter implements SectionIndexer{
		private LayoutInflater mInflater;
		private List<ContentValues> mList;
		//save the index of thelist
		//like:#-0,A-4,B-10
		private HashMap<String, Integer> alphaIndexer;
		//indexer
		private String[] sections;
		
		public DemoAdapter(Context context, List<ContentValues> list){
			mInflater = LayoutInflater.from(context);
			mList = list;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String name = list.get(i).getAsString("sort_key");
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.sectionindexer_item, null);
			}
			
			TextView alphaView = ViewHolder.get(convertView, R.id.tv_alpha);
			TextView nameView = ViewHolder.get(convertView, R.id.tv_contact_name);
			TextView numberView = ViewHolder.get(convertView, R.id.tv_contact_number);
			
			ContentValues cv = mList.get(position);
			String name = cv.getAsString("name");
			String number = cv.getAsString("number");
			nameView.setText(name);
			numberView.setText(number);
			
			String currentStr = mList.get(position).getAsString("sort_key");
			String previewStr = (position - 1) >= 0 ? 
					mList.get(position - 1).getAsString("sort_key") : " ";
			
			if (!previewStr.equals(currentStr)) {
				alphaView.setVisibility(View.VISIBLE);
				alphaView.setText(currentStr);
			} else {
				alphaView.setVisibility(View.GONE);
			}
			
			return convertView;
		}
		
		@Override
		public Object[] getSections() {
			// TODO Auto-generated method stub
			return sections;
		}

		@Override
		public int getPositionForSection(int section) {
			// TODO Auto-generated method stub
			String later = section - 2 >= 0 ? sections[section - 2] :sections[section];
			int position = alphaIndexer.get(later);
			LogUtil.d(getApplicationContext(), 
					"getPositionForSection:" + section + "," + later + ","+ position + ",title=" + mList.get(position).getAsString("name"));
			return position;
		}

		@Override
		public int getSectionForPosition(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		private String getAlpha(String str){
			System.out.println("getAlphat.str=" + str);
			if (str == null) {
				return "#";
			}
			
			if (str.trim().length() == 0) {
				return "#";
			}
			
			char c = str.trim().substring(0, 1).charAt(0);
			//正则表达式，判断首字母是否为英文
			Pattern pattern = Pattern.compile("^[A-za-z]+$");
			if (pattern.matcher(c + "").matches()) {
				return (c + "").toUpperCase();
			} else {
				return "#";
			}
		}
		
	}

}
