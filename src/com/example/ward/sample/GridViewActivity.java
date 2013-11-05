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
 * 20120716 Ŀǰ ��ӹ����Ѿ�ʵ��
 * ����ɵ��У�ɾ�� ָ��item��ɾ��ȫ��
 * ˼����
 * 1.ɾ��ָ��item����һ�����Բ���ͨ��ID ��ɾ������ΪID����ȷ��
 * 	   ��ôͨ�����֣�ͼƬ�����п����ظ���Ŀǰ���ݿ��� Ψһ��Ҳֻ��ID,
 *    ��ô���أ�
 * 2. ɾ��ȫ����ʵ�֣�ֻҪ��������ɾ�����ɣ����� Ŀǰ����ɾ�����Ժ󣬲��ܼ�ʹˢ��UI
 *       �д�����̽��
 *     �°�ǰ��ͻȻ����
 *     ȫ��ɾ�������ݿ���ˢ��������UIû�и��£�����ΪUI����ʱ��������飬����Ҫ�����ݿ��UI ��������ɾ����
 *     ��ɾ�����ݿ�ǰ����Ҫ�ѱ�����Ϣ��������գ�itemList.clear()������ ��������ѵ�һ��Ҳɾ���㣬�����ǵ�
 *     ����뷨�ǲ������������ģ���ô�Ҿ��뵽�������֮���ٰѵ�һ���������ȥ����ʵ����ȫ��ɾ���Ĺ��ܣ�Oh yeah�����԰����°���
 *�������˼������ɾ������?
 *20120717
 *ɾ��ָ��Item������û��ͷ��������09ʱ23��10��
 *����ȱ�ٵ���һ����Ψһ��ʶÿ��item��Ϣ���ݵ�   09ʱ24��23��
 *Ŀǰÿ��item����Ϣ��������1��ͼƬID�������˸�item��ʾ��ͼƬ 2����ʾ�����֣������˸�item�·���ʾ������
 *���ڿ϶���Ҫһ��������Ψһ����ÿ��item������ ����ʲô�أ�����˼���� 10ʱ24��54��
 *
 *20120725
 *��listview ���� gridview��onItemClickListen��onItemLongClickList�ķ����ж���һ������
 *long arg3//the row id of the item that clicked
 *������������Ǳ�ʾ��Ԫ�������ݿ����е�ID
 *���id��Ψһ��
 *���л�������Ϳ���ɾ��������
 */
//gridview ������ͼ
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
		setTitle("GridView ������ͼ ");
		
//		changeToListView();
		gridView =(GridView)findViewById(R.id.grid_view);
		
		//ʹ��SimpleAdapter
		//���ɶ�̬����,�Ա���ͼƬ��������Ϣ
		itemList = new ArrayList<HashMap<String, Object>>();
		
		//��ʼ������
//		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImg", R.drawable.th_add);
			map.put("itemText", "���鼮");
			itemList.add(map);
//		}
			
		//����������
		simpleAdapter = new SimpleAdapter(this, itemList, R.layout.grid_list, 
				new String[]{"itemImg","itemText"}, new int[]{R.id.image,R.id.text});
		gridView.setAdapter(simpleAdapter);
			
		//�����¼�������
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, //the adapterView where the click happend
					View arg1,//The View within the adapterView that clicked
					int position, //the position of the view that int the adapter
					long arg3//the row id of the item that clicked
					) {
//				HashMap<String, Object> item = (HashMap<String, Object>)arg0.getItemAtPosition(position);
				String result = itemList.get(position).get("itemText") + "";
//				String result = item.get("itemText")+ "";
				if ("���鼮".equals(result)) {
//					addItem(R.drawable.nobody, "New Add");
//					addItem(0x7f020009, "XXXX"); //����һ����һ����
				}
			}
		});
		
		//�����¼��������������Ի���
		//��setOnCreateContextMenuListener  �ظ� ��ѡһ����
//		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int position, long id) {
//				System.out.println("position = " + position + "id = " + id);
//				//���԰��������ݿ��ID �Բ��ϰ�
//				showItemLongClickDialog(position,id);
//				return false;
//			}
//		});
		
		//����gridview item �����˵�ѡ��
		gridView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				System.out.println("�������������ˣ�");
				menu.add(0, 0, 0, "���");
                menu.add(0, 1, 0, "ɾ��");
                menu.add(0, 2, 0, "ȫ��ɾ��");
			}
		});
		
		baseProvider = new BaseProvider(GridViewActivity.this);
		baseProvider.open();
		
	}
	
	private void showItemLongClickDialog(final long position,final long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final CharSequence[] items = { "���", "ɾ��" , "ȫ��ɾ��"};
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
//					//�������ɾ��������Ҫ���������ȥ
//					HashMap<String, Object> map = new HashMap<String, Object>();
//					map.put("itemImg", R.drawable.th_add);
//					map.put("itemText", "���鼮");
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
		//ʹ��SimpleAdapter
		//���ɶ�̬����,�Ա���ͼƬ��������Ϣ
		listItem = new ArrayList<HashMap<String, Object>>();
		
		//init 
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImg", R.drawable.th_add);
		map.put("itemText", "���鼮");
		listItem.add(map);
		
		//����������
		listItemAdapter = new SimpleAdapter(this, listItem, R.layout.listview_list, 
				new String[]{"itemImg","itemText"}, new int[]{
				R.id.image,R.id.text});
		listView.setAdapter(listItemAdapter);
	}
	
	//�����������Ĳ˵���Ӽ�����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		System.out.println("" + info.id + "\n"
				+ info.position + "\n"
				);

		
		switch (item.getItemId()) {
		case 0:   //���
//			addItem(R.drawable.alert_dialog_icon, "Long Add");
			break;
		case 1:  //ɾ��
//			itemList.get(index)
			break;
		case 2:  //ȫ��ɾ��
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
//		HashMap<String, Object> map = new HashMap<String, Object>(); //�����ڴ˴���������
		HashMap<String, Object> map;
		for (int i = 0; i < gridInfos.length; i++) {
			map = new HashMap<String, Object>();//�����ڴ˴��������󣬷���UI���»����
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
		itemList.clear();  //�����
		
		//�������ɾ��������Ҫ���������ȥ
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImg", R.drawable.th_add);
		map.put("itemText", "���鼮");
		itemList.add(map);
		
		simpleAdapter.notifyDataSetChanged();
		
//		dbAdapter.deleteAllData();
	}
	/**
	 * ����MENU
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "���");
        menu.add(0, 2, 2, "ɾ��");
		return super.onCreateOptionsMenu(menu);
	}
	
	//�˵�����¼�������
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
