package com.example.ward;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UreyActivity extends ListActivity {
	private static final String TAG = "UreyActivity";
	private static final String EXTRA_PATH = "com.example.ward";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String path = intent.getStringExtra(EXTRA_PATH);
        
        if (path == null) {
			path = "";
		}
        
        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }
    
    List<Map<String, Object>> getData(String  prefix){
    	Log.d(TAG, "getData:prefix-->" + prefix);
    	List<Map<String, Object>> myData = new ArrayList<Map<String,Object>>();
    	
    	Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
    	mainIntent.addCategory(Contants.UREY_CATEGORY);
    	
    	PackageManager pm = getPackageManager();
    	List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
    	
    	if (null == list) {
			return myData;
		}
    	
    	String[] prefixPath;
    	String prefixWithSlash = prefix;
    	
    	if (prefix == "") {
//    		Log.d(TAG, "prefix is null!");
			prefixPath = null;
		}else {
			prefixPath = prefix.split("/");
			prefixWithSlash = prefix + "/";
//			Log.d(TAG, "getData:prefixpath-->" + prefixPath + "getData:prefixWithSlash-->" + prefixWithSlash);
		}
    	
    	int len = list.size();
    	
    	Map<String, Boolean> entries = new HashMap<String, Boolean>();
    	
    	for (int i = 0; i < len; i++) {
			ResolveInfo info = list.get(i);
			CharSequence labelSeq = info.loadLabel(pm);
			String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;
//			Log.d(TAG, "labelSeq=" + labelSeq.toString() + "\n"
//            		+ "name=" + info.activityInfo.name + "\n"
//            		+ "label=" + label);
			
			if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
//				Log.d(TAG, "prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)");
				String[] labelPath = label.split("/");
				String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
//				Log.d(TAG, "nextLabel=" + nextLabel);
				
				if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
//					Log.d(TAG, "(prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1");
					addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
				}else {
					if (entries.get(nextLabel) == null) {
//						Log.d(TAG, "entries.get(nextLabel) == null");
						addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
					}else {
//						Log.d(TAG, "entries.get(nextLabel) != null");
					}
//					Log.e(TAG, "=======================");
				}
			}
		}
    	
    	Collections.sort(myData, sDisplayNameComparator);
    	
    	return myData;
    }
    
	private final static Comparator<Map<String, Object>> sDisplayNameComparator = new Comparator<Map<String, Object>>() {
		private final Collator collator = Collator.getInstance();

		public int compare(Map<String, Object> map1, Map<String, Object> map2) {
			return collator.compare(map1.get("title"), map2.get("title"));
		}
	};
			
	private Intent activityIntent(String pkg, String componentName) {
		Intent result = new Intent();
		result.setClassName(pkg, componentName);
		return result;
	}
	
	private Intent browseIntent(String path){
//		Log.d(TAG, "browseIntent-->" + path);
		Intent result = new Intent();
		result.setClass(this, UreyActivity.class);
		result.putExtra(EXTRA_PATH, path);
		return result;
	}
	
	private void addItem(List<Map<String, Object>> data,String name,Intent intent){
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		data.add(temp);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
		
		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
