package com.example.ward.view;

import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ExpandListViewDemo extends Activity {
	
	ExpandableListView  eListView;
	ExpandListViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandlistview_main);
		
		eListView = (ExpandableListView) findViewById(R.id.expandableListView);
		eListView.setGroupIndicator(null);//remove the arrow icon
		
		List<String> groupList = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			groupList.add("���ǵ�"+ i + "��");
		}
		mAdapter = new ExpandListViewAdapter(getApplicationContext(), groupList);
		eListView.setAdapter(mAdapter);
		eListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				int len = mAdapter.getGroupCount();
				eListView.setSelection(groupPosition);
				for (int i = 0; i < len; i++) {
					if (i != groupPosition) {
						//��һ��չ��ʱ��������item������
						eListView.collapseGroup(i);
					}
				}
			}
		});
		
	}
	
}
