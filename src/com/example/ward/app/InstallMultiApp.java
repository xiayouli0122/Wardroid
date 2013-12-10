package com.example.ward.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class InstallMultiApp extends Activity implements OnScrollListener, OnItemClickListener, OnItemLongClickListener, OnClickListener {
	private ListView mListView;
	private InstallAppAdapter mAdapter;
	private List<File> mAppList = new ArrayList<File>();
	
	private LinearLayout mMenuLayout;
	private Button mCancelBtn,mInstallBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_install);
		setTitle("实现静默安装要用源码编译，Eclipse没法做，以后再说吧");
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		
		mMenuLayout = (LinearLayout) findViewById(R.id.ll_menu_layout);
		mMenuLayout.setVisibility(View.GONE);
		mCancelBtn = (Button) findViewById(R.id.btn_cancel);
		mCancelBtn.setOnClickListener(this)	;
		mInstallBtn = (Button) findViewById(R.id.btn_install);
		mInstallBtn.setOnClickListener(this);
		
		new GetFileTask().execute(0);
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			System.out.println("handleMessage");
			mAdapter = new InstallAppAdapter(InstallMultiApp.this, mAppList);
			mListView.setAdapter(mAdapter);
		};
	};
	
	/**get sdcard classify files*/
	class GetFileTask extends AsyncTask<Integer, Integer, Object>{
		List<File> fileList = new ArrayList<File>();
		String[]  filterType = null;
		int type = -1;
		ProgressDialog dialog = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(InstallMultiApp.this);
			dialog.setMessage("Searching App Install Package");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
		}
		
		@Override
		protected Object doInBackground(Integer... params) {
			System.out.println("doInBackground");
			File[] files = Environment.getExternalStorageDirectory().getAbsoluteFile().listFiles();
			listFile(files);
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			System.out.println("onPostExecute");
			if (dialog != null) {
				dialog.cancel();
				dialog = null;
			}
			mHandler.sendMessage(mHandler.obtainMessage(0));
		}
		
		protected void listFile(final File[] files){
			if (null != files && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						final int tag = i;
						new Thread(new Runnable() {
							@Override
							public void run() {
								listFile(files[tag].listFiles());
							}
						}).start();
					}else {
						String name = files[i].getName();
						if (isAppFile(name)) {
							mAppList.add(files[i]);
						}
					}
				}
			}
		}
		
		public boolean isAppFile(String name) {
			if (name.endsWith(".apk")) {
				return true;
			}
			return false;
		}
	}
	
	
	public class AppInfo{
		
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case SCROLL_STATE_FLING:
			System.out.println("SCROLL_STATE_FLING");
			mAdapter.setFlag(false);
			break;
		case SCROLL_STATE_IDLE:
			System.out.println("SCROLL_STATE_IDLE");
			mAdapter.setFlag(true);
			mAdapter.notifyDataSetChanged();
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			System.out.println("SCROLL_STATE_TOUCH_SCROLL");
			mAdapter.setFlag(false);
			break;

		default:
			break;
		}
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		mAdapter.changeMode(InstallAppAdapter.MODE_EDIT);
		
		mAdapter.setSelected(position);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (mAdapter.isMode(InstallAppAdapter.MODE_EDIT)) {
			mAdapter.setSelected(position);
		}
		
		String resultString = install(mAppList.get(position).getAbsolutePath());
		System.out.println("r==>"+ resultString);
	}
	
	public String install(String path) {
		System.out.println("install:path:" + path);
		String[] args = { "pm", "install", "-r", path };
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write('\n');
			inIs = process.getInputStream();
			while ((read = inIs.read()) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (process != null) {
				process.destroy();
			}
		}
		return result;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			mMenuLayout.setVisibility(View.GONE);
			break;
		case R.id.btn_install:
			break;

		default:
			break;
		}
	}
}
