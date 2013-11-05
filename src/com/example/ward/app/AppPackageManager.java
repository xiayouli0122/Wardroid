package com.example.ward.app;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ward.R;

public class AppPackageManager extends Activity {
	private ListView mListView;
	private PackageManager pm;
	private List<AppEntry> mAppList = new ArrayList<AppPackageManager.AppEntry>();

	private MyAdapter myAdapter;

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			myAdapter.notifyDataSetChanged();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_package);

		mListView = (ListView) findViewById(R.id.package_listview);
		pm = getPackageManager();
		
		myAdapter = new MyAdapter();
		mListView.setAdapter(myAdapter);
		
		AppListTask appListTask = new AppListTask();
		appListTask.execute("");
	}

	private class AppEntry {
		Drawable appIcon;
		String appLabel;
		String packageName;
	}

	public class AppListTask extends AsyncTask<String, String, List<AppEntry>> {

		@Override
		protected List<AppEntry> doInBackground(String... params) {
			// Retrieve all known applications.
			List<ApplicationInfo> allApps = pm.getInstalledApplications(0);
			if (allApps == null) {
				allApps = new ArrayList<ApplicationInfo>();
			}

			// Create corresponding array of entries and load their labels.
			List<AppEntry> entries = new ArrayList<AppEntry>(allApps.size());

			for (int i = 0; i < allApps.size(); i++) {
				ApplicationInfo appInfo = allApps.get(i);
				// if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				// //system app
				// }else {
				AppEntry entry = new AppEntry();
				// get label
				CharSequence label = appInfo.loadLabel(pm);
				String mLabel = label != null ? label.toString() : appInfo.packageName;

				// get icon
				Drawable icon = appInfo.loadIcon(pm);
				entry.appIcon = icon;
				entry.appLabel = mLabel;
				entry.packageName = appInfo.packageName;

				int index = getInsertIndex(mAppList, entry);
				if (mAppList.size() == index) {
					mAppList.add(entry);
				} else {
					mAppList.add(index, entry);
				}

				publishProgress("");
				// }
			}

			// Done!
			return entries;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<AppEntry> result) {
			super.onPostExecute(result);
//			myAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			mHandler.sendEmptyMessage(0);
		}

	}

	/**
	 * Perform alphabetical comparison of application entry objects.
	 */
	public static final Comparator<AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() {
		private final Collator sCollator = Collator.getInstance();

		@Override
		public int compare(AppEntry object1, AppEntry object2) {
			return sCollator.compare(object1.appLabel, object2.appLabel);
		}
	};

	class MyAdapter extends BaseAdapter {
		LayoutInflater inflater = null;

		MyAdapter() {
			inflater = LayoutInflater.from(AppPackageManager.this);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAppList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder {
			ImageView iconView;
			TextView lableView;
			TextView packageNameView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if (null == convertView || null == convertView.getTag()) {
				view = inflater.inflate(R.layout.app_package_item, null);
				holder = new ViewHolder();
				holder.iconView = (ImageView) view.findViewById(R.id.app_icon_imageview);
				holder.lableView = (TextView) view.findViewById(R.id.app_label_textview);
				holder.packageNameView = (TextView) view.findViewById(R.id.app_package_textview);

				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			holder.iconView.setImageDrawable(mAppList.get(position).appIcon);
			holder.lableView.setText(mAppList.get(position).appLabel);
			holder.packageNameView.setText(mAppList.get(position).packageName);

			return view;
		}
	}

	/**
	 * 插入一个数据到已经排好序的list中
	 * 
	 * @param list
	 *            已经排好序的list
	 * @param appEntry
	 *            要插入的数据
	 * @return 将要插入的位置
	 */
	public static int getInsertIndex(List<AppEntry> list, AppEntry appEntry) {
		Collator sCollator = Collator.getInstance();
		for (int i = 0; i < list.size(); i++) {
			int ret = sCollator.compare(appEntry.appLabel, list.get(i).appLabel);
			if (ret <= 0) {
				return i;
			}
		}
		return list.size();
	}
}
