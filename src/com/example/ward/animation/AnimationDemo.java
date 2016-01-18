package com.example.ward.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ward.R;

public class AnimationDemo extends Activity implements OnItemClickListener{
    
    private ListView mListView;
    private SimpleAdapter mAdapter;
    
    private static final String[] TEXTS = {
        ScaleAnimationActivity.class.getSimpleName(), 
        ScaleAnimationActivity2.class.getSimpleName(),
        ScaleAnimationActivity3.class.getSimpleName(),
        SwitchAnimationActivity.class.getSimpleName()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        
        mListView = (ListView) findViewById(R.id.listview);
        
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> map= null;
        for (int i = 0; i < TEXTS.length; i++) {
            map = new HashMap<String, String>();
            map.put("text", TEXTS[i]);
            data.add(map);
        }
        
        mAdapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, 
                new String[]{"text"}, new int[]{android.R.id.text1});
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        HashMap<String, String> map = (HashMap<String, String>) mAdapter.getItem(position);
        String text = map.get("text");
        
        Intent intent = new Intent();
        
        if (ScaleAnimationActivity.class.getSimpleName().equals(text)) {
            intent.setClass(this, ScaleAnimationActivity.class);
        } else if (ScaleAnimationActivity2.class.getSimpleName().equals(text)) {
            intent.setClass(this, ScaleAnimationActivity2.class);
        } else if (ScaleAnimationActivity3.class.getSimpleName().equals(text)) {
            intent.setClass(this, ScaleAnimationActivity3.class);
        }else if (SwitchAnimationActivity.class.getSimpleName().equals(text)) {
            intent.setClass(this, SwitchAnimationActivity.class);
        }
        
        startActivity(intent);
    }
}
