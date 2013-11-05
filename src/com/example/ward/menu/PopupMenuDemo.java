package com.example.ward.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ward.R;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PopupMenuDemo extends Activity implements OnItemClickListener {
	private ListView listView;
	private SimpleAdapter mAdapter;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
	private static final int ID_ADD = 0x01;
	private static final int ID_ACCEPT = 0x02;
	private static final int ID_UPLOAD = 0x03;
	private static final int ID_MORE = 0x04;
	
	/**
	 * Listview selected row
	 */
	private int mSelectedRow = 0;
	/**
	 * Right arrow icon on each listview row
	 */
	private ImageView mMoreIv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_main);
		
		listView = (ListView) findViewById(R.id.popup_listview);
		listView.setOnItemClickListener(this);
		
		for (int i = 0; i < 20; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("item", i+ "");
			list.add(map);
		}
		
		mAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, new String[]{"item"} , new int[]{R.id.popup_listview});
		listView.setAdapter(mAdapter);
		
		ActionItem addItem 		= new ActionItem(ID_ADD, "Add", getResources().getDrawable(R.drawable.ic_add));
		ActionItem acceptItem 	= new ActionItem(ID_ACCEPT, "Accept", getResources().getDrawable(R.drawable.ic_accept));
        ActionItem uploadItem 	= new ActionItem(ID_UPLOAD, "Upload", getResources().getDrawable(R.drawable.ic_up));
        ActionItem moreItem  	= new ActionItem(ID_MORE, "More", getResources().getDrawable(R.drawable.ic_up));
        
        final QuickAction mQuickAction 	= new QuickAction(this);
		
		mQuickAction.addActionItem(addItem);
		mQuickAction.addActionItem(acceptItem);
		mQuickAction.addActionItem(uploadItem);
		mQuickAction.addActionItem(moreItem);
		
		//setup the action item click listener
				mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos, int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);
						
						if (actionId == ID_ADD) { //Add item selected
							Toast.makeText(getApplicationContext(), "Add item selected on row " + mSelectedRow, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), actionItem.getTitle() + " item selected on row " 
									+ mSelectedRow, Toast.LENGTH_SHORT).show();
						}	
					}
				});
				
				//setup on dismiss listener, set the icon back to normal
				mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {			
					@Override
					public void onDismiss() {
//						mMoreIv.setImageResource(R.drawable.ic_list_more);
					}
				});
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						mSelectedRow = position; //set the selected row
						
						mQuickAction.show(view);
						
						//change the right arrow icon to selected state 
//						mMoreIv = (ImageView) view.findViewById(R.id.i_more);
//						mMoreIv.setImageResource(R.drawable.ic_list_more_selected);
					}
				});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}
}
