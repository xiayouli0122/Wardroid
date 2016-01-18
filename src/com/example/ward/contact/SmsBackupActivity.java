package com.example.ward.contact;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ward.R;

public class SmsBackupActivity extends Activity implements OnClickListener{
	private Button mBackupBtn,mRecoverBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_backup);
		
		mBackupBtn = (Button)findViewById(R.id.backup_btn);
		mRecoverBtn = (Button)findViewById(R.id.recover_btn);
		
		mBackupBtn.setOnClickListener(this);
		mRecoverBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backup_btn:
			SmsBakup smsBakup = new SmsBakup();
			smsBakup.execute("");
			break;
		case R.id.recover_btn:
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Looper.prepare();
					SmsRecover smsRecover = new SmsRecover();
					smsRecover.execute("");
					Looper.loop();
				}
			});
			thread.start();
			break;

		default:
			break;
		}
	}
	
	private class SmsBakup extends AsyncTask<String, String, String>{
		ProgressDialog progressDialog;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ExportSmsXml exportSmsXml = new ExportSmsXml(SmsBackupActivity.this);
			try {
				exportSmsXml.createXml();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			publishProgress("");
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = new ProgressDialog(SmsBackupActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("备份中...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			progressDialog.cancel();	
			super.onProgressUpdate(values);
		}
		
	}
	
	private List<SmsItem> smsItems;
	private class SmsRecover extends AsyncTask<String, String, String>{
		ProgressDialog progressDialog;
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ImportSmsXml importSmsXml =  new ImportSmsXml(SmsBackupActivity.this);
			smsItems = importSmsXml.getSmsItemsFromXml();
			ContentResolver conResolver = getContentResolver();
			for (SmsItem item : smsItems) {
				// 判断短信数据库中是否已包含该条短信，如果有，则不需要恢复 
				Cursor cursor = conResolver.query(Uri.parse("content://sms"), new String[] { SmsField.DATE }, SmsField.DATE + "=?",  
	                    new String[] { item.getDate() }, null);
				
				if (!cursor.moveToFirst()) {//
					ContentValues values = new ContentValues();  
	                values.put(SmsField.ADDRESS, item.getAddress());  
	                // 如果是空字符串说明原来的值是null，所以这里还原为null存入数据库  
	                values.put(SmsField.PERSON, item.getPerson().equals("") ? null : item.getPerson());  
	                values.put(SmsField.DATE, item.getDate());  
	                values.put(SmsField.PROTOCOL, item.getProtocol().equals("") ? null : item.getProtocol());  
	                values.put(SmsField.READ, item.getRead());  
	                values.put(SmsField.STATUS, item.getStatus());  
	                values.put(SmsField.TYPE, item.getType());  
	                values.put(SmsField.REPLY_PATH_PRESENT, item.getReply_path_present().equals("") ? null : item.getReply_path_present());  
	                values.put(SmsField.BODY, item.getBody());  
	                values.put(SmsField.LOCKED, item.getLocked());  
	                values.put(SmsField.ERROR_CODE, item.getError_code());  
	                values.put(SmsField.SEEN, item.getSeen());  
	                conResolver.insert(Uri.parse("content://sms"), values);
				}
				cursor.close();
			}
			
			publishProgress("");
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog = new ProgressDialog(SmsBackupActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("恢复中...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			progressDialog.cancel();	
			super.onProgressUpdate(values);
		}
		
	}
	
}
