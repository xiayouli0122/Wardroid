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
		
		lengthFilter(EditTextLengthTest.this, editText, 20, "Ӣ�Ĳ��ܳ���20���ַ������Ĳ��ܳ���10���ַ�");
	}
	
	//�ֶ��������뷨
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
				int destLen = getCharacterNum(dest.toString()); // ��ȡ�ַ�����(һ��������2���ַ�)
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
	 * @description ��ȡһ���ַ������ַ�������������Ӣ�ģ�һ��������2���ַ���
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
	* @description �����ַ����������ֻ���ȫ���ַ��ĸ���
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
