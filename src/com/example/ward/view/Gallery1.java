/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ward.view;

import com.example.ward.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class Gallery1 extends Activity {
	private Context mContext;
	private Uri[] uri =ImageBrowser.uriArray;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//影藏顶部程序名称 写在setContentView(R.layout.gallery_1);之前，不然报错
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gallery_1);
		//影藏顶部电量等图标
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
		//设置显示第几张图片 参数是GetImagesFromSDCard中的静态变量
		g.setSelection(ImageBrowser.imagePosition);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		public ImageAdapter(Context c) {
			mContext = c;
			/*
			 * 使用在res/values/attrs.xml中的<declare-styleable>定义 的Gallery属性.
			 */
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			/* 取得Gallery属性的Index id */
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			/* 让对象的styleable属性能够反复使用 */
			a.recycle();
		}

		/* 重写的方法getCount,返回图片数目 */
		public int getCount() {
			return uri.length;
		}

		/* 重写的方法getItemId,返回图像的数组id */

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/* 重写的方法getView,返回一View对象 */
		public View getView(int position, View convertView, ViewGroup parent){
			ImageView view = new ImageView(Gallery1.this);
			//设置所有图片的资源地址
			view.setImageURI(uri[position]);
			view.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setLayoutParams(new Gallery.LayoutParams(240, 320));
			/* 设置Gallery背景图 */
			view.setBackgroundResource(mGalleryItemBackground);
			return view;
		}

	}
}