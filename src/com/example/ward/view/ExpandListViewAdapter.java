package com.example.ward.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ward.R;

import android.R.anim;
import android.content.Context;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 私念
 * 
 * @author Administrator
 * 
 */
public class ExpandListViewAdapter extends BaseExpandableListAdapter {
	private List<String> mGroupTitleList = new ArrayList<String>();
	private Context parentContext;
	private LayoutInflater layoutInflater;

	public ExpandListViewAdapter(Context context, List<String> groupData) {
		parentContext = context;
		mGroupTitleList = groupData;
		layoutInflater = LayoutInflater.from(context);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	/**
	 * 可自定义ExpandableListView
	 */
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		System.out.println("getChildView.groupPosition=" + groupPosition);
		View view = null;
//		if (null == convertView) {
			switch (groupPosition) {
			case 0:
				view = layoutInflater.inflate(R.layout.bubble_main, null);
				break;
			case 1:
				view = layoutInflater.inflate(R.layout.context_menu, null);
				break;
			case 2:
				view = layoutInflater.inflate(R.layout.expandliste_item_test1, null);
				break;
			case 3:
				view = layoutInflater.inflate(R.layout.crashtest_layout, null);
				break;
			case 4:
				view = layoutInflater.inflate(R.layout.auto_complete_text_view, null);
				break;
			case 5:
				view = layoutInflater.inflate(R.layout.custom_dialog, null);
				break;
			default:
				break;
			}
//		}else {
//			view = convertView;
//		}
		System.out.println("getChildView.grouopos=" + groupPosition + ",view=" + view);

		return view;
	}

	/**
	 * 可自定义list
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = layoutInflater.inflate(R.layout.explandlistview_group, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_elistview_icon);
		TextView textView = (TextView) view.findViewById(R.id.tv_elistview_title);
		if (isExpanded) {
			imageView.setImageResource(R.drawable.profile_arrow2);
		}else {
			imageView.setImageResource(R.drawable.profile_arrow1);
		}
		
		textView.setText(mGroupTitleList.get(groupPosition));
		return view;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return null;
	}

	public int getGroupCount() {
		return mGroupTitleList.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}