package com.example.ward.net;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ward.R;

public class GetNetworkInfoAcitivity extends Activity{
       
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_info);
        
        TextView textView = (TextView) findViewById(R.id.textview);
        
        textView.setText("IP:" + getIp());
        
    }
    
    private String getIp(){  
        WifiManager wm=(WifiManager)getSystemService(Context.WIFI_SERVICE);  
        //检查Wifi状态    
        if(!wm.isWifiEnabled())  
            wm.setWifiEnabled(true);  
        WifiInfo wi=wm.getConnectionInfo();  
        //获取32位整型IP地址    
        int ipAdd=wi.getIpAddress();  
        //把整型地址转换成“*.*.*.*”地址    
        String ip=intToIp(ipAdd);  
        return ip;  
    }  
    
    private String intToIp(int i) {  
        return (i & 0xFF ) + "." +  
        ((i >> 8 ) & 0xFF) + "." +  
        ((i >> 16 ) & 0xFF) + "." +  
        ( i >> 24 & 0xFF) ;  
    }   
}
