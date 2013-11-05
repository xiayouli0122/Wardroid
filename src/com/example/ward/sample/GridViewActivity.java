package com.example.ward.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.DialerFilter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.ward.R;
import com.example.ward.menu.MenuItemDemo;
import com.example.ward.sample.GridViews.GridInfos;
import com.example.ward.sqlite.BaseProvider;

/**NOTE
 * 20120716 目前 添加功能已经实现
 * 待完成的有：删除 指定item，删除全部
 * 思考：
 * 1.删除指定item：第一，明显不能通过ID 来删除，因为ID不好确定
 * 	   那么通过文字，图片？都有可能重复，目前数据库中 唯一的也只有ID,
 *    怎么办呢？
 * 2. 删除全部能实现，只要把整个表删除即可，但是 目前好像删除完以后，不能即使刷新UI
 *       有待继续探究
 *     下班前，突然顿悟：
 *     全部删除，数据库能刷除，但是UI没有更新，是因为UI有临时保存的数组，所以要想数据库和UI 都看到被删除了
 *     在删除数据库前，需要把保存信息的数组清空，itemList.clear()，但是 这样作会把第一个也删除点，而我们的
 *     最初想法是不允许这样做的，那么我就想到，在清空之后，再把第一个给添加上去，就实现了全部删除的功能，Oh yeah，可以安心下班了
 *明天继续思考怎样删除单个?
 *20120717
 *删除指定Item，还是没有头绪。。。09时23分10秒
 *现在缺少的是一个可唯一标识每个item信息内容的   09时24分23秒
 *目前每个item的信息有两个：1）图片ID，代表了该item显示的图片 2）显示的文字，代表了该item下方显示的文字
 *现在肯定需要一个属性来唯一代表每个item，但是 又是什么呢？继续思考吧 10时24分54秒
 *
 *20120725
 *在listview 或者 gridview的onItemClickListen和onItemLongClickList的方法中都有一个参数
 *long arg3//the row id of the item that clicked
 *这个参数，就是表示该元数在数据库中列的ID
 *这个id是唯一的
 *所有获得了它就可以删除单个了
 */
//gridview 网格视图
public class GridViewActivity extends Activity{
	GridView gridView;
	ArrayList<HashMap<String, Object>> itemList ;
	
	//listview ==>
	ListView listView;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	//<==
	
	SimpleAdapter simpleAdapter;
	
//	DBAdapter dbAdapter;
	
	BaseProvider baseProvider;
	
	private Uri mUri ;
    /**
     * The columns needed by the cursor adapter
     */
    private static final String[] PROJECTION = new String[] {
            GridViews.GridInfos._ID, // 0
            GridViews.GridInfos.COLUMN_NAME_IMG_ID, // 1
            GridViews.GridInfos.COLUMN_NAME_NAME, //2
    };
	private static final String TAG = "GridView";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);
		setTitle("GridView 网格视图 ");
		
//		changeToListView();
		gridView =(GridView)findViewById(R.id.grid_view);
		
		//使用SimpleAdapter
		//生成动态数组,以保存图片和文字信息
		itemList = new ArrayList<HashMap<String, Object>>();
		
		//初始化数据
//		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImg", R.drawable.th_add);
			map.put("itemText", "打开书籍");
			itemList.add(map);
//		}
			
		//生成适配器
		simpleAdapter = new SimpleAdapter(this, itemList, R.layout.grid_list, 
				new String[]{"itemImg","itemText"}, new int[]{R.id.image,R.id.text});
		gridView.setAdapter(simpleAdapter);
			
		//单击事件监听器
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, //the adapterView where the click happend
					View arg1,//The View within the adapterView that clicked
					int position, //the position of the view that int the adapter
					long arg3//the row id of the item that clicked
					) {
//				HashMap<String, Object> item = (HashMap<String, Object>)arg0.getItemAtPosition(position);
				String result = itemList.get(position).get("itemText") + "";
//				String result = item.get("itemText")+ "";
				if ("打开书籍".equals(result)) {
//					addItem(R.drawable.nobody, "New Add");
//					addItem(0x7f020009, "XXXX"); //和上一行是一样的
				}
			}
		});
		
		//长按事件监听器，弹出对话框
		//与setOnCreateContextMenuListener  重复 二选一即可
//		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int position, long id) {
//				System.out.println("position = " + position + "id = " + id);
//				//不对啊，跟数据库的ID 对不上啊
//				showItemLongClickDialog(position,id);
//				return false;
//			}
//		});
		
		//长按gridview item 弹出菜单选项
		gridView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				System.out.println("长按弹出我来了？");
				menu.add(0, 0, 0, "添加");
                menu.add(0, 1, 0, "删除");
                menu.add(0, 2, 0, "全部删除");
			}
		});
		
		baseProvider = new BaseProvider(GridViewActivity.this);
		baseProvider.open();
		
	}
	
	private void showItemLongClickDialog(final long position,final long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final CharSequence[] items = { "添加", "删除" , "全部删除"};
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					System.out.println("ADD");
					break;
				case 1:
					System.out.println("Delete");
					
					System.out.println("before = " + itemList.size());
					itemList.remove(position);
					System.out.println("after = " + itemList.size());
					
					baseProvider.delete(GridViews.GridInfos.CONTENT_URI, "_ID = ?",  new String[] { String.valueOf(id) });
					simpleAdapter.notifyDataSetChanged()	;
//					itemList.clear();
//					//这个不能删除，所以要重新添加上去
//					HashMap<String, Object> map = new HashMap<String, Object>();
//					map.put("itemImg", R.drawable.th_add);
//					map.put("itemText", "打开书籍");
//					itemList.add(map);
//					queryData();
					break;
				case 2:
					System.out.println("ALl Delete");
					break;
				default:
					break;
				}

			}
		}).setTitle("ID:" + id);
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void changeToListView(){
//		gridView.setVisibility(View.GONE);
		listView = (ListView)findViewById(R.id.listview);
		//使用SimpleAdapter
		//生成动态数组,以保存图片和文字信息
		listItem = new ArrayList<HashMap<String, Object>>();
		
		//init 
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImg", R.drawable.th_add);
		map.put("itemText", "打开书籍");
		listItem.add(map);
		
		//生成适配器
		listItemAdapter = new SimpleAdapter(this, listItem, R.layout.listview_list, 
				new String[]{"itemImg","itemText"}, new int[]{
				R.id.image,R.id.text});
		listView.setAdapter(listItemAdapter);
	}
	
	//给长按弹出的菜单添加监听器
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		System.out.println("" + info.id + "\n"
				+ info.position + "\n"
				);

		
		switch (item.getItemId()) {
		case 0:   //添加
//			addItem(R.drawable.alert_dialog_icon, "Long Add");
			break;
		case 1:  //删除
//			itemList.get(index)
			break;
		case 2:  //全部删除
			deleteIAllItem();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		queryData();
	}
	
	public void queryData(){
		GridInfos[] gridInfos = baseProvider.queryInfos(GridViews.GridInfos.CONTENT_URI, PROJECTION);
//		
		if (gridInfos == null) {
			System.out.println("shen ma dou meiyou ");
			return;
		}
		
		System.out.println("length = " + gridInfos.length);
//		HashMap<String, Object> map = new HashMap<String, Object>(); //不能在此处创建对象
		HashMap<String, Object> map;
		for (int i = 0; i < gridInfos.length; i++) {
			map = new HashMap<String, Object>();//必须在此处创建对象，否则UI更新会出错
			map.put("itemImg", gridInfos[i].COLUMN_NAME_IMG_ID);
			map.put("itemText", gridInfos[i].COLUMN_NAME_NAME);
			System.out.println("IMG_ID=" + gridInfos[i].COLUMN_NAME_IMG_ID + "NAME=" + gridInfos[i].COLUMN_NAME_NAME);
			itemList.add(map);
		}
		
		System.out.println("itemList.szie= " + itemList.size());
		simpleAdapter.notifyDataSetChanged();
	}
	
	public void addItem(String resId,String title){
		System.out.println("resid=" + resId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImg", resId);
		map.put("itemText", title);
		itemList.add(map);
		simpleAdapter.notifyDataSetChanged();
//		
//		dbAdapter.insert(gridInfo);
//		baseProvider.insert(GridViews.GridInfos.CONTENT_URI, values)
		
//		Intent intent = getIntent();
//		
//		mUri = getContentResolver().insert(intent.getData(), null);
		
		 // Sets up a map to contain values to be updated in the provider.
        ContentValues values = new ContentValues();
        
        values.put("img_id", resId);
        values.put("name", title);
        
        baseProvider.insert(GridViews.GridInfos.CONTENT_URI, values);
//        getContentResolver().insert(
//                GridViews.GridInfos.CONTENT_URI,    // The URI for the record to update.
//                values // The map of column names and new values to apply to them.
////                null,    // No selection criteria are used, so no where columns are necessary.
////                null     // No where columns are used, so no where arguments are necessary.
//            );
	}
	
	public void deleteItem(int id){
//		dbAdapter.deleteOneData(id);
		
	}
	
	public void deleteIAllItem(){
		itemList.clear();  //先清空
		
		//这个不能删除，所以要重新添加上去
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImg", R.drawable.th_add);
		map.put("itemText", "打开书籍");
		itemList.add(map);
		
		simpleAdapter.notifyDataSetChanged();
		
//		dbAdapter.deleteAllData();
	}
	/**
	 * 创建MENU
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "添加");
        menu.add(0, 2, 2, "删除");
		return super.onCreateOptionsMenu(menu);
	}
	
	//菜单点击事件监听器
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			addItem(R.drawable.th + "", "1111");
		}else {
			if (item.getItemId() == 2) {
				changeToListView();
				System.out.println("222222222222222");
			}
		}
		return super.onOptionsItemSelected(item);
	}

}
