package com.example.ward.gps;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.ward.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Location2 extends Activity{
	
	LocationClient mLocationClient;
	TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.location1_main);
		
		mTextView = (TextView) findViewById(R.id.location_text);
		
		mLocationClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");    
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setScanSpan(5000);
		
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(new BDLocationListener() {
			
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					System.out.println("onReciveLocation location is null");
					return;
				}
				
				StringBuffer sb = new StringBuffer(256); 
                sb.append("time : "); 
                sb.append(location.getTime()); 
                sb.append("\nerror code : "); 
                sb.append(location.getLocType()); 
                sb.append("\nlatitude : "); 
                sb.append(location.getLatitude()); 
                sb.append("\nlontitude : "); 
                sb.append(location.getLongitude()); 
                sb.append("\nradius : "); 
                sb.append(location.getRadius()); 
                if (location.getLocType() == BDLocation.TypeGpsLocation){ 
                    sb.append("\nspeed : "); 
                    sb.append(location.getSpeed()); 
                    sb.append("\nsatellite : "); 
                    sb.append(location.getSatelliteNumber()); 
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){ 
                    sb.append("\naddr : "); 
                    sb.append(location.getAddrStr()); 
                } 
                sb.append("\nsdk version : "); 
                sb.append(mLocationClient.getVersion()); 
                mTextView.setText(sb.toString());
                
                LatLng startLat = null;
				LatLng endLatLng = null;
				try {
					double testLat = 31.25122;
					double testLong = 121.458235;
					
					startLat = new LatLng(location.getLatitude(), location.getLongitude());
					endLatLng = new LatLng(testLat, testLong);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                //计算两点之间的距离
                double distance = DistanceUtil.getDistance(startLat, endLatLng);
                System.out.println("distance:" + distance);
                
                //下一步根据经纬度反编码出当前所在城市,使用定位sdk的BDLocation
                BDLocation bdLocation = new BDLocation();
                System.out.println("city:" + bdLocation.getCity());
			}
		});
		
		mLocationClient.start();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mLocationClient.isStarted()) {
			mLocationClient.stop();
			mLocationClient = null;
		}
	}
}
