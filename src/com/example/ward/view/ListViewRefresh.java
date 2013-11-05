package com.example.ward.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ward.R;
import com.example.ward.view.RefreshListView.OnRefreshListener;

public class ListViewRefresh extends Activity {
	
	
	RefreshListView listView;
	View mFooter;
	
	List<Map<String,Object>> mList;
	List<String> mDataList = new ArrayList<String>();
	SimpleAdapter mAdapter;
	MyAdapter myAdapter;
	int count = 10;
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			myAdapter.notifyDataSetChanged();
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_refresh);
		
		listView = (RefreshListView) findViewById(R.id.refresh_lv);
//        mFooter = getLayoutInflater().inflate(R.layout.refresh_lv_footer,null);
//        mList = new ArrayList<Map<String,Object>>();
//        mList.addAll(DataService.getData(0,20));
        for(int i=0;i<count;i++) {
//        	Map<String,Object> map = new HashMap<String, Object>();
//            map = new HashMap<String, Object>();
//            map.put("name","bin" + i);
//            mList.add(map);
        	String name = "test " + i;
        	mDataList.add(name);
        }
        
//        mAdapter = new SimpleAdapter(getApplicationContext(), mList,
//        		R.layout.listview_refresh_item, new String[]{"name"},new int[]{R.id.text});
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        
//        listView.addFooterView(mFooter);
//        listView.setAdapter(mAdapter);
//        listView.removeFooterView(mFooter);
        
        listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new TopAsyncTask().execute();
			}
		});
        
//        listView.setOnScrollListener(new MyScrollListener());
	}
	
	private int mNumber = 20;//每次获取多少条数据
    private int mMaxpage = 5;//总共有多少页
    private int mTotalCount = 0;
    private boolean mLoadfinish = true;
    private final class MyScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
			mTotalCount = totalItemCount -1;
			if(mTotalCount < mNumber) {
				Toast.makeText(ListViewRefresh.this,"到底了！！！", Toast.LENGTH_LONG).show();
				return;  //如果第一次加载的数据量 小于 mNumber则不再进行加载(-1 代表除去header)
			}
			int lastItemid = listView.getLastVisiblePosition();
			if(lastItemid+1 == mTotalCount) {
				int currentpage = totalItemCount%mNumber == 0 ? totalItemCount/mNumber : totalItemCount/mNumber+1;
				int nextpage = currentpage + 1;//下一页
				if(nextpage <= mMaxpage && mLoadfinish) {
					mLoadfinish = false;
//					listView.addFooterView(mFooter);
					new ButtomAsyncTask().execute();
				}
			}
		}
    	
    }
    
    private class ButtomAsyncTask extends AsyncTask<Object,Integer,List<Map<String,Object>>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<Map<String,Object>> result) {
			super.onPostExecute(result);
			mList.addAll(result);
			mAdapter.notifyDataSetChanged();//告诉ListView数据已经发生改变，要求ListView更新界面显示
//			if(listView.getFooterViewsCount() > 0) listView.removeFooterView(mFooter);
			mLoadfinish = true;
		}

		@Override
		protected List<Map<String,Object>> doInBackground(Object... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getData(mTotalCount, mNumber);
		}
    	
    }
    
    private class TopAsyncTask extends AsyncTask<Object,Integer,Object> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			System.out.println("onPreExecute");
			listView.onPrepareRefresh();//耗时操作之前调用该方法
		}

		@Override
		protected Object doInBackground(Object... params) {
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.out.println("doInBackground");
			count ++ ;
			mDataList = getData();
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			System.out.println("onPostExecute");
//			mAdapter.notifyDataSetChanged();
//			myAdapter.notifyDataSetChanged();
			mHandler.sendMessage(mHandler.obtainMessage(0));
			listView.onCompleteRefresh();//耗时操作之后调用该方法
		}
		
	}
    
    public List<String> getData(){
    	List<String> data = new ArrayList<String>();
    	for (int i = 0; i < count; i++) {
			String name = "test " + i;
			data.add(name);
		}
    	return data;
    }
    
//    public List<Map<String, Object>> getData(){
//    	System.out.println("count=" + count);
//    	List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//    	for(int i=0; i < count; i++) {
//        	Map<String,Object> map = new HashMap<String, Object>();
//            map = new HashMap<String, Object>();
//            map.put("name","bin" + i);
//            data.add(map);
//        }
//    	return  data;
//    }
    
    class MyAdapter extends BaseAdapter{
    	LayoutInflater inflater = null;
    	MyAdapter(){
    		inflater = LayoutInflater.from(ListViewRefresh.this);
    	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = inflater.inflate(R.layout.listview_refresh_item, null);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			textView.setText(mDataList.get(position));
			return convertView;
		}
    	
    }
    
    public static List<Map<String,Object>> getData(int offset, int maxResult){//分页 limit 0,20
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		//修改这里的数值 设置 一页所显示的数量  
		for(int i=0;i<4;i++) {
        	Map<String,Object> map = new HashMap<String, Object>();
            map = new HashMap<String, Object>();
            map.put("name","bin" + i);
            data.add(map);
        }
		return data;
	}
}
