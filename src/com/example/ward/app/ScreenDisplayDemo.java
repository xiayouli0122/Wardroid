package com.example.ward.app;

import com.example.ward.R;
import com.example.ward.util.DensityUtil;
import com.zhaoyan.common.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScreenDisplayDemo extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_display);
        
        TextView textView = (TextView) findViewById(R.id.textview);
        
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        
        String text = "";
        text = "Width.px:" + DensityUtil.getWidthInPx(this) + ",Width.dp:" + DensityUtil.getWidthInDp(this) + "\n";
        text += "height.px:" + DensityUtil.getHeightInPx(this) + ",height.dp:" + DensityUtil.getHeightInDp(this) + "\n";
        text += "densityDpi:" + displayMetrics.densityDpi + "\n";
        text += "density:" + displayMetrics.density + "\n";
        
        textView.setText(text);
        
        final EditText editText = (EditText) findViewById(R.id.edittext);
        Button button = (Button) findViewById(R.id.changeButton);
        final TextView resultView = (TextView) findViewById(R.id.resultText);
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = editText.getText().toString().trim();
                int dp = DensityUtil.px2dip(getApplicationContext(), Float.parseFloat(text));
                resultView.setText(dp+ " dp");
            }
        });
    }
}
