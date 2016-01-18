package com.example.ward.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsMenuActivity extends Activity {

	private final static int ITEM0=Menu.FIRST;
	private final static int ITEM1=Menu.FIRST+1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("OptionsMenuActivity");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,ITEM0,0,"000");
		menu.add(0,ITEM1,0,"111");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM0:
			setTitle("000");
			break;
		case ITEM1:
			setTitle("111");
			break;
		default:
			break;
		}
		return true;
	}

}
