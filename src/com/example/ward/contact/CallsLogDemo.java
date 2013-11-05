 package com.example.ward.contact;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ListView;

import com.example.ward.R;

public class CallsLogDemo extends Activity {
	
	private ListView mListView;
	private CallsLogAdapter mAdapter;
	
	private List<CallsLog> mList = new ArrayList<CallsLog>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.calllog);
		
		mListView = (ListView) findViewById(R.id.listview_log);
		
		getData();
		mAdapter = new CallsLogAdapter(CallsLogDemo.this,mList);
		mListView.setAdapter(mAdapter);
	}
	
	public void getData(){
		Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,  
			    null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);  
		
		startManagingCursor(cursor);
		
		while (cursor.moveToNext()) {
			CallsLog callsLog;
			int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
			callsLog = new CallsLog(id);
			
			String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			callsLog.setPhoneNumber(number);
			
			String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			callsLog.setName(name);
			
//			Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))));.
			long date = Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
			callsLog.setDateTime(date);
			
			int dur = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
			callsLog.setDuration(dur);
			
			int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
			callsLog.setCallType(type);
			mList.add(callsLog);
			
			String label = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL));
			String label_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NUMBER_TYPE));
			
//			System.out.println("id=" + id + "\n"
//					+ "number=" + number + "\n"
//					+ "name=" + name + "\n"
//					+ "date=" + date.getDate() + " " + date.getTime() + "\n"
//					+ "type= " + type + "\n"
//					+ "duration=" + dur + "\n"
//					+ "label=" + label + "\n"
//					+ "label.type=" + label_type);
//			System.out.println("=====================" + CallLog.Calls.getLastOutgoingCall(this));
			
		}
		
		cursor.close();
	}
	
}
