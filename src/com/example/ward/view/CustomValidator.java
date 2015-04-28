package com.example.ward.view;

import android.text.TextUtils;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

public class CustomValidator extends Validator {

	public CustomValidator(String customErrorMessage) {
		super(customErrorMessage);

	}

	@Override
	public boolean isValid(EditText et) {
		return TextUtils.equals(et.getText(), "ward");
	}

}