package com.example.ward.view;

import com.example.ward.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextLengthTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editlen_test);

		EditText editText = (EditText)findViewById(R.id.test_edit);
//		editText.setFocusable(true);
//		editText.requestFocus();
//		editText.setInputType(InputType.TYPE_CLASS_TEXT);
		onFocusChange(editText, true);

		lengthFilter(EditTextLengthTest.this, editText, 20, "英文不能超过20个字符，中文不能超过10个字符");
	}

	//手动弹出输入法
	void onFocusChange(final View v, boolean hasFocus)
	{
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager)
						v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if(isFocus)
				{
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				}
				else
				{
					imm.hideSoftInputFromWindow(v.getWindowToken(),0);
				}
			}
		}, 500);
	}

	public  void lengthFilter(final Context context, final EditText editText, final int max_length, final String err_msg) {
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(max_length) {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				int destLen = getCharacterNum(dest.toString()); // 获取字符个数(一个中文算2个字符)
				int sourceLen = getCharacterNum(source.toString());
				if (destLen + sourceLen > max_length) {
					Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
					return "";
				}
				return source;
			}
		};
		editText.setFilters(filters);
	}

	/**
	 * @description 获取一段字符串的字符个数（包含中英文，一个中文算2个字符）
	 * @param content
	 * @return
	 */
	public static int getCharacterNum(final String content) {
		if (null == content || "".equals(content)) {
			return 0;
		} else {
			return (content.length() + getChineseNum(content));
		}
	}


	/**
	 * @description 返回字符串里中文字或者全角字符的个数
	 * @param s
	 * @return
	 */
	public static int getChineseNum(String s) {
		int num = 0;
		char[] myChar = s.toCharArray();
		for (int i = 0; i < myChar.length; i++) {
			if ((char) (byte) myChar[i] != myChar[i]) {
				num++;
			}
		}
		return num;
	}
}