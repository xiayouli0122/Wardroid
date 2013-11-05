package com.example.ward.media;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.TextView;

import com.example.ward.R;
import com.example.ward.media.DevMountInfo.DevInfo;

public class StorageDemo extends Activity {
	private static final String TAG = "Yuri";
	
	private StorageManager mStorageManager = null; 

    protected boolean mSDCardMounted = true; 
    protected boolean mSDCard2Mounted = false; 
    protected String mSDCardPath = null; 
    protected String mSDCard2Path = null;
    
    private TextView textView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storage_demo);
		textView = (TextView) findViewById(R.id.storage_textview);
		StorageManager storageManager;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String line;
			String mount = new String();
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.contains("secure")) continue;
				if (line.contains("asec")) continue;
				
				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat("*" + columns[1] + "\n");
					}
				} else if (line.contains("fuse")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
			textView.setText(mount + "\n"
					+ Environment.getExternalStorageDirectory().getAbsolutePath() + "\n"
					+ Environment.getExternalStorageState());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.getenv(); // 返回的是一个map  
		Map<String, String> map = System.getenv();  
		  
		//遍历出来可以看到最后一项是外置SD卡路径  
		  
//		Set<String> set = map.keySet();  
//		        Iterator<String> key = set.iterator();  
//		        while(key.hasNext())  
//		            Log.d("123", key.next());  
//		          
//		        Collection<String> col = map.values();  
//		        Iterator<String> val = col.iterator();  
//		        while(val.hasNext())  
//		            Log.d("123", val.next());  
		  
		//不同的机型获得的会有所不同，先试试！！  

		        
		        DevMountInfo dev = DevMountInfo.getInstance();  
		        DevInfo info = dev.getInternalInfo();//Internal SD Card Informations  
		        if (null == info) {
					Log.e(TAG, "interanl.info is null");
				}else {
					Log.d(TAG, "===internal===");
			        Log.d(TAG, "label:" + info.getLabel());
			        Log.d(TAG, "point:" + info.getMount_point());
			        Log.d(TAG, "path:" + info.getPath());
			        Log.d(TAG, "sys_path:" + info.getSysfs_path());
				}
		        
		        info = dev.getExternalInfo();//External SD Card Informations  
		        if (null == info) {
					Log.e(TAG, "external.info is null");
				}else {
					Log.d(TAG, "===external===");
			        Log.d(TAG, "label:" + info.getLabel());
			        Log.d(TAG, "point:" + info.getMount_point());
			        Log.d(TAG, "path:" + info.getPath());
			        Log.d(TAG, "sys_path:" + info.getSysfs_path());
				}
	}		
}
