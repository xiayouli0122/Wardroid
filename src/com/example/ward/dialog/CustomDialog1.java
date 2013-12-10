package com.example.ward.dialog;

import java.util.ArrayList;
import java.util.List;

import com.example.ward.R;

import android.R.anim;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomDialog1 extends Dialog {
	
	private ListView mListView;
	private Context mContext;
	private int size;
	
	public CustomDialog1(Context context, int size) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.size = size;
	}

	public CustomDialog1(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_custon_dialog1);
		
		mListView = (ListView) findViewById(R.id.listview);
		View footerView = getLayoutInflater().inflate(R.layout.ui_dialog_button, null);
		
		List<String> strs = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			strs.add("wardroid test" + i);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, 
				android.R.layout.simple_list_item_1, strs);
//		mListView.addFooterView(footerView);
		mListView.setAdapter(adapter);
		
	}

}
