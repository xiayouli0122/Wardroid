package com.example.ward.pullview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ward.R;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PullViewDemoActivity extends Activity implements OnScrollListener{

    private ListView mListView;
    private View mHeaderView;
    
    private SimpleAdapter maAdapter;
    
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = -1;// 标记上次滑动位置
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullview);
        
        mListView = (ListView) findViewById(R.id.lsitview);
        mHeaderView = findViewById(R.id.rl_header);
        
        List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> map;
        
        for (int i = 0; i < 12; i++) {
            map = new HashMap<String, String>();
            map.put("title", "test" +i);
            list.add(map);
        }
        
        maAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, 
                new String[]{"title"}, new int[]{android.R.id.text1});
        mListView.setAdapter(maAdapter);
        
        mListView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
            scrollFlag = true;
        } else {
            scrollFlag = false;
        }
    }

    private int count = 0;
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
//        Log.d("Yuri", "onScroll.firstVisibleItem:" + firstVisibleItem + ",visibleItemCount:" + visibleItemCount);
        if (scrollFlag) {
            float alpha = 0;
            if (firstVisibleItem > lastVisibleItemPosition) {
                count++;
                if (alpha >= 0) {
                    Log.d("Yuri", "mcurrent.alpha:" + mHeaderView.getAlpha());
                    alpha= mHeaderView.getAlpha() - 1/10f;
                } else {
                    alpha = 0;
                }
                Log.d("Yuri", "上滑:" + alpha);
                mHeaderView.setAlpha(alpha);
//                ViewHelper.setScaleY(mHeaderView, mHeaderView.getScaleY() - 1/10f);
                ViewHelper.setTranslationY(mHeaderView, 0.1f);
            } else if (firstVisibleItem < lastVisibleItemPosition) {
                count --;
                if (alpha <= 1) {
                    Log.d("Yuri", "mcurrent.alpha:" + mHeaderView.getAlpha());
                    alpha = mHeaderView.getAlpha() + 1/10f;
                } else {
                    alpha = 1;
                }
                Log.d("Yuri", "下滑:" + alpha);
                mHeaderView.setAlpha(alpha);
            } else {
                return;
            }
        }
        lastVisibleItemPosition = firstVisibleItem;
    }
    
}
