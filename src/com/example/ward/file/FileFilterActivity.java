package com.example.ward.file;

import java.io.File;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

public class FileFilterActivity extends Activity {
	
	FilenameFileter filenameFileter;
	String[] docTypes;
	String[] ebookTypes;
	String[] apkTypes;
	String[] archiveType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		filenameFileter = new FilenameFileter(docTypes);
//		docTypes = getResources().getStringArray(R.array.doc_file);
//		ebookTypes= getResources().getStringArray(R.array.ebook_file);
//		apkTypes = getResources().getStringArray(R.array.apk_file);
//		archiveType = getResources().getStringArray(R.array.archive_file);
		new GetFilesTask().execute();
	}
	
	protected void listFile(final File[] files){
		if (files != null && files.length > 0) {
			for(int i = 0; i < files.length; i++){
				if (files[i].isDirectory()) {
					final int tag = i;
					new Thread(new Runnable() {
						@Override
						public void run() {
							listFile(files[tag].listFiles());
						}
					}).start();
				}else {
					if (files[i].getName().endsWith(".apk")) {
						System.out.println(files[i].getAbsolutePath());
					}
				}
				
				if (i == files.length - 1) {
//					System.out.println("==================");
				}
			}
		}
	}
	
	class GetFilesTask extends AsyncTask<Void, Integer, Object>{

		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("doInBackground");
			File file = Environment.getExternalStorageDirectory().getAbsoluteFile();
			System.out.println("onCreate:"+ file.getAbsolutePath());
			File[] files = file.listFiles();
			listFile(files);
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("onPostExecute");
		}
		
	}
}
