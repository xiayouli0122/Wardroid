package com.example.ward.gps;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ward.R;

public class GpsLocationDemo extends Activity{
	
	private LocationManager mLocationManager;
	private String mLocationProvider;
	
	private TextView mLocationTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mLocationTextView = (TextView) findViewById(R.id.location_text);
		
		//获取地理位置管理器  
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		//获取所有可用的位置提供器  
        List<String> providers = mLocationManager.getProviders(true);  
        for (String provder : providers) {
			System.out.println(">>>provder:" + provder);
		}
        if(providers.contains(LocationManager.GPS_PROVIDER)){  
            //如果是GPS  
        	mLocationProvider = LocationManager.GPS_PROVIDER;  
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){  
            //如果是Network  
        	//总结用GPS的话，要求太高，室内不好测试
        	//用network的话，据网上的人说，LocationProvider用的是google的一套定位服务，比较麻烦，建议直接用百度的定位服务
        	mLocationProvider = LocationManager.NETWORK_PROVIDER;  
        }else{  
        	System.out.println("error provider:" + providers.size());
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();  
//            return ;  
        }  
        System.out.println("onCreate.provider:"  + mLocationProvider);
        //获取Location  
        Location location = mLocationManager.getLastKnownLocation(mLocationProvider);  
        
        if(location!=null){  
            //不为空,显示地理位置经纬度  
            showLocation(location);  
        }  else {
			System.out.println("Location is null");
//			mLocationProvider = LocationManager.NETWORK_PROVIDER;
//			 //获取Location  
//	        location = mLocationManager.getLastKnownLocation(mLocationProvider);  
//	        if (location != null) {
//	        	//不为空,显示地理位置经纬度  
//	            showLocation(location);  
//			} else {
//				System.out.println("Location is null");
//			}
		}
        
        //监视地理位置变化  
        mLocationManager.requestLocationUpdates(mLocationProvider, 3000, 1, locationListener);  
	}
	
	/** `
     * 显示地理位置经度和纬度信息 
     * @param location 
     */  
    private void showLocation(Location location){  
        String locationStr = "维度：" + location.getLatitude() +"\n"   
                + "经度：" + location.getLongitude();  
        System.out.println("showLoaction:" + locationStr);
        mLocationTextView.setText(locationStr);  
    }  
    
    /** 
     * LocationListern监听器 
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器 
     */  
    LocationListener locationListener =  new LocationListener() {  
          
        @Override  
        public void onStatusChanged(String provider, int status, Bundle arg2) {  
        	System.out.println("onStatusChanged:" + provider + ",status:" + status);
        }  
          
        @Override  
        public void onProviderEnabled(String provider) {  
        	System.out.println("onProviderEnabled:" + provider);
        }  
          
        @Override  
        public void onProviderDisabled(String provider) {  
              System.out.println("onProviderDisabled:" + provider);
        }  
          
        @Override  
        public void onLocationChanged(Location location) {  
        	System.out.println("onLocationChanged");
            //如果位置发生变化,重新显示  
            showLocation(location);  
        }  
    };  
    
    protected void onDestroy() {
    	super.onDestroy();
    	mLocationManager.removeUpdates(locationListener);
    };
	
}
