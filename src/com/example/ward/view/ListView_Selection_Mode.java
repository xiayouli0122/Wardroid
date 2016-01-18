package com.example.ward.view;

import com.example.ward.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class ListView_Selection_Mode extends ListActivity implements OnItemLongClickListener, OnItemClickListener {
	private ActionMode mActionMode = null;
	/**
	 * 监测屏幕旋转变化
	 */
	private int mOrientationConfig;
	private boolean isConfigChanaged = false;

	private ListView mListView;
	private SelectionModeAdapter mAdapter;

	private ActionModeCallBack mActionModeCallBack = new ActionModeCallBack();

	//	private boolean mSelectedAll = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOrientationConfig = this.getResources().getConfiguration().orientation;
		mListView = getListView();
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        lv.setMultiChoiceModeListener(new ModeCallback());
		mListView.setOnItemLongClickListener(this);
		mListView.setOnItemClickListener(this);
//        setListAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_activated_1, Cheeses.sCheeseStrings));
		mAdapter = new SelectionModeAdapter(ListView_Selection_Mode.this, Cheeses.sCheeseStrings);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation != mOrientationConfig) {
			isConfigChanaged = true;
			mOrientationConfig = newConfig.orientation;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getActionBar().setSubtitle("Long press to start selection");
	}

	private class ModeCallback implements ListView.MultiChoiceModeListener {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			System.out.println("onCreateActionMode");
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.list_select_menu, menu);
			mode.setTitle("Select Items");
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			System.out.println("onCreateActionMode");
			return true;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				case R.id.share:
					Toast.makeText(ListView_Selection_Mode.this, "Shared " + getListView().getCheckedItemCount() +
							" items", Toast.LENGTH_SHORT).show();
					mode.finish();
					break;
				default:
					Toast.makeText(ListView_Selection_Mode.this, "Clicked " + item.getTitle(),
							Toast.LENGTH_SHORT).show();
					break;
			}
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
			System.out.println("onCreateActionMode");
		}

		public void onItemCheckedStateChanged(ActionMode mode,
											  int position, long id, boolean checked) {
			final int checkedCount = getListView().getCheckedItemCount();
			switch (checkedCount) {
				case 0:
					mode.setSubtitle(null);
					break;
				case 1:
					mode.setSubtitle("One item selected");
					break;
				default:
					mode.setSubtitle("" + checkedCount + " items selected");
					break;
			}
		}


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		System.out.println("id=" + item.getItemId());
		switch (item.getItemId()) {
			case android.R.id.home:

				break;

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}



	/**
	 * Selection action mode call back
	 * @author Yuri
	 *
	 */
	protected class ActionModeCallBack implements Callback, OnMenuItemClickListener{
		/**
		 *  用户弹出复制，剪切等菜单的
		 */
		private PopupMenu mEditPopupMenu = null;
		/**
		 * 用户弹出全选和取消全选的菜单的
		 */
		private PopupMenu mSelectPopupMenu = null;
		private boolean mSelectedAll = true;
		/**
		 * 显示选中多少个，以及全选和取消全选
		 */
		private Button mSelectBtn = null;

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			System.out.println("onCreateActionMode");
			// 11111111111111111111111
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 自定义ActionBar菜单
			View customView = layoutInflater.inflate(
					R.layout.listview_actionbar_edit, null);
			mode.setCustomView(customView);
			mSelectBtn = (Button) customView.findViewById(R.id.select_button);
			mSelectBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null == mSelectPopupMenu) {
						// 创建
						mSelectPopupMenu = createSelectPopupMenu(mSelectBtn);
					} else {
						// Update
						updateSelectPopupMenu();
						mSelectPopupMenu.show();
					}
				}
			});
			MenuInflater menuInflater = mode.getMenuInflater();
			menuInflater.inflate(R.menu.edit_view_menu, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			System.out.println("onPrepareActionMode");
			//22222222222222222222222222
			//获得当前选中的数目
//			final int selectedCount = getListView().getCheckedItemCount();
			final int selectedCount = mAdapter.getCheckedCount();
			// enable(disable) copy, cut, and delete icon
//            if (selectedCount == 0) {
//                menu.findItem(R.id.copy).setEnabled(false);
//                menu.findItem(R.id.delete).setEnabled(false);
//                menu.findItem(R.id.cut).setEnabled(false);
//                menu.findItem(R.id.popup_menu_more).setEnabled(false);
//                menu.findItem(R.id.share).setEnabled(false);
//            } else {
			menu.findItem(R.id.copy).setEnabled(true);
			menu.findItem(R.id.delete).setEnabled(true);
			menu.findItem(R.id.cut).setEnabled(true);
			menu.findItem(R.id.popup_menu_more).setEnabled(true);
			menu.findItem(R.id.share).setEnabled(true);
//            }

//            if (selectedCount == 0) {
			menu.findItem(R.id.share).setEnabled(true);
//			}
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			//3333333333333333333
			switch (item.getItemId()) {
				case R.id.share:
					System.out.println("you clicked share menu");
					break;
				case R.id.copy:
					System.out.println("you clicked copy menu");
					break;
				case R.id.cut:
					System.out.println("you clicked cut menu");
					break;
				case R.id.delete:
					System.out.println("you clicked delete menu");
					break;
				case R.id.popup_menu_more:
					System.out.println("you clicked popup_menu_more menu");
					showEditPopupMenu();
					break;

				default:
					System.out.println("what menu does you clicked:" + item.getItemId());
					return false;
			}
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			System.out.println("onDestroyActionMode");
			//444444444444444444444444444
			if (mActionMode != null) {
				mActionMode = null;
			}
			if (mSelectPopupMenu != null) {
				mSelectPopupMenu.dismiss();
				mSelectPopupMenu = null;
			}
			if (mEditPopupMenu != null) {
				mEditPopupMenu.dismiss();
				mEditPopupMenu = null;
			}
		}

		private PopupMenu createEditPopupMenu(View anchorView) {
			final PopupMenu popupMenu = new PopupMenu(ListView_Selection_Mode.this, anchorView);
			popupMenu.inflate(R.menu.popup_menu_more);
			popupMenu.setOnMenuItemClickListener(this);
			return popupMenu;
		}

		private void updateEditPopupMenu(){
			final Menu menu = mEditPopupMenu.getMenu();
//            int selectedCount = getListView().getCheckedItemCount();
			int selectedCount = mAdapter.getCheckedCount();

			// remove (disable) protection info icon
			menu.removeItem(R.id.protection_info);
//            if (selectedCount == 0) {
//				menu.findItem(R.id.rename).setEnabled(false);
//				menu.findItem(R.id.details).setEnabled(false);
//			}else {
			menu.findItem(R.id.rename).setEnabled(true);
			menu.findItem(R.id.details).setEnabled(true);
//			}
		}

		private void showEditPopupMenu(){
			View popupMenuBaseLine;
			if (mOrientationConfig == Configuration.ORIENTATION_LANDSCAPE) {
				popupMenuBaseLine = (View) findViewById(R.id.popup_menu_base_line_landscape);
			} else {
				popupMenuBaseLine = (View) findViewById(R.id.popup_menu_base_line_portrait);
			}
			mEditPopupMenu = createEditPopupMenu(popupMenuBaseLine);
			mEditPopupMenu.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(PopupMenu menu) {
					if (isConfigChanaged) {
						isConfigChanaged = false;
						showEditPopupMenu();
					}
				}
			});

			if (mEditPopupMenu != null) {
				updateEditPopupMenu();
				isConfigChanaged = false;
				mEditPopupMenu.show();
			}
		}

		private PopupMenu createSelectPopupMenu(View anchorView) {
			final PopupMenu popupMenu = new PopupMenu(ListView_Selection_Mode.this, anchorView);
			popupMenu.inflate(R.menu.select_popup_menu);
			popupMenu.setOnMenuItemClickListener(this);
			return popupMenu;
		}

		private void updateSelectPopupMenu(){
			if (mSelectPopupMenu == null) {
				mSelectPopupMenu = createSelectPopupMenu(mSelectBtn);
				return;
			}
			final Menu menu = mSelectPopupMenu.getMenu();
//			int selectedCount = getListView().getCheckedItemCount();
			if (mSelectedAll) {
				menu.findItem(R.id.select).setTitle(R.string.deselect_all);
				mSelectedAll = true;
			}else {
				menu.findItem(R.id.select).setTitle(R.string.select_all);
				mSelectedAll = false;
			}
		}

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
				case R.id.rename:
					System.out.println("you clicked rename menu");
					break;
				case R.id.details:
					System.out.println("you clicked details menu");
					break;
				case R.id.protection_info:
					System.out.println("you clicked protection_info menu");
					break;
				case R.id.select:
					System.out.println("you clicked select menu");
					updateActionMode();
					invalidateOptionsMenu();
					break;

				default:
					return false;
			}
			return true;
		}

		public void updateActionMode() {
			int selectedCount = mAdapter.getCheckedCount();
			String selected = getResources().getString(R.string.selected);
			selected = "" + selectedCount + " " + selected;
			mSelectBtn.setText(selected);

			mActionModeCallBack.updateSelectPopupMenu();
			if (mActionMode != null) {
				mActionMode.invalidate();
			}
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
								   long id) {
		int top = view.getTop();
		mAdapter.setChecked(position);
		switchToEditView(position, top);
		return true;
	}

	private void switchToEditView(int position, int top) {
		mListView.setSelectionFromTop(position, top);
		switchToEditView();
	}

	private void switchToEditView() {
//        LogUtils.d(TAG, "Switch to edit view");
		mListView.setFastScrollEnabled(false);
		mAdapter.changeMode(SelectionModeAdapter.MODE_EDIT);
		mActionMode = startActionMode(mActionModeCallBack);
		mActionModeCallBack.updateActionMode();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
//		mListView.setItemChecked(position, true);
		mAdapter.setChecked(position);
		mActionModeCallBack.updateActionMode();
//		mListView.notify();
	}
}