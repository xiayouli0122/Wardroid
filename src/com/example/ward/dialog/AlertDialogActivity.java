package com.example.ward.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.ward.R;


//对话框测试
public class AlertDialogActivity extends Activity {

	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;

	private static final int DIALOG_TWO_BUTTON = 0;
	private static final int DIALOG_THREE_BUTTON = 1;
	private static final int DIALOG_EDIT_ENTRY = 2;
	private static final int DIALOG_PROGRESS = 3;
	private static final int DIALOG_SHOW_SEEKBAR = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		setTitle("对话框Samples！");

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_TWO_BUTTON, null);
			}
		});
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_THREE_BUTTON, null);
			}
		});
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_EDIT_ENTRY, null);
			}
		});
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_PROGRESS, null);
			}
		});
		
		Button bar_btn = (Button)findViewById(R.id.button_bar);
		bar_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DIALOG_SHOW_SEEKBAR,null);
			}
		});
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG_TWO_BUTTON:
			Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
			builder.setIcon(R.drawable.alert_dialog_icon);
			builder.setTitle("哇哈哈！");
			builder.setMessage("去不去？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(AlertDialogActivity.this, "你选择了确定按钮！", Toast.LENGTH_SHORT).show();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(AlertDialogActivity.this, "你选择了取消按钮！", Toast.LENGTH_SHORT).show();
				}
			});
			builder.show();
			break;
		case DIALOG_THREE_BUTTON:
			new AlertDialog.Builder(AlertDialogActivity.this).setIcon(R.drawable.alert_dialog_icon).setTitle("温馨提示")
					.setMessage("提示内容：三个按钮").setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(AlertDialogActivity.this, "你选择了确定按钮！", Toast.LENGTH_SHORT).show();
						}
					}).setNeutralButton("详情", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(AlertDialogActivity.this, "你选择了详情按钮！", Toast.LENGTH_SHORT).show();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(AlertDialogActivity.this, "你选择了取消按钮！", Toast.LENGTH_SHORT).show();
						}
					}).show();
			break;

		case DIALOG_EDIT_ENTRY:
			LayoutInflater inflater = LayoutInflater.from(AlertDialogActivity.this);
			final View textEntryView = inflater.inflate(R.layout.alert_dialog_text_entry, null);

			final EditText usernameET = (EditText) textEntryView.findViewById(R.id.username_value);
			final EditText passwordET = (EditText) textEntryView.findViewById(R.id.password_value);
			// final String username=usernameET.getText().toString();

			new AlertDialog.Builder(AlertDialogActivity.this).setIcon(R.drawable.alert_dialog_icon).setTitle("温馨提醒").setView(textEntryView)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(AlertDialogActivity.this,
									"用户名=" + usernameET.getText().toString() + "\n密码=" + passwordET.getText().toString(), Toast.LENGTH_LONG)
									.show();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(AlertDialogActivity.this, "你选择了确定取消！", Toast.LENGTH_SHORT).show();
						}
					}).show();
			break;
		case DIALOG_PROGRESS:
			ProgressDialog dialog = new ProgressDialog(AlertDialogActivity.this);
			dialog.setTitle("处理中。。。");
			dialog.setMessage("请稍后。。。");
			dialog.show();
			break;
			
		case DIALOG_SHOW_SEEKBAR:
			LayoutInflater bar_inflater = LayoutInflater.from(AlertDialogActivity.this);
			
			final View barView = bar_inflater.inflate(R.layout.alert_dialog_seek_bar, null);
			final SeekBar seekBar01 = (SeekBar)barView.findViewById(R.id.seekbar_1);
			seekBar01.setMax(100);
			seekBar01.setProgress(50);
			final SeekBar seekBar02 = (SeekBar)barView.findViewById(R.id.seekbar_2);
			seekBar02.setMax(80);
			seekBar02.setProgress(10);
			final SeekBar seekBar03= (SeekBar)barView.findViewById(R.id.seekbar_3);
			seekBar03.setMax(200);
			seekBar03.setProgress(30);
			new AlertDialog.Builder(AlertDialogActivity.this)
			.setTitle("SeekBar")
			.setView(barView)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("get seekbar1_progess=" + seekBar01.getProgress() + "\n"
							+ "get seekbar2_progess=" + seekBar02.getProgress() + "\n"
							+ "get seekbar3_progess=" + seekBar03.getProgress() + "\n");
				}
			})
			.setNegativeButton(android.R.string.cancel, null)
			.create().show();
			break;

		default:
			break;
		}
		return super.onCreateDialog(id, args);
	}
}
