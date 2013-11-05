package com.example.ward.contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Xml;
import android.widget.Toast;

public class ImportSmsXml {
	private Context context;  
	  
    private List<SmsItem> smsItems;  
    private ContentResolver conResolver;  
  
    public ImportSmsXml(Context context) {  
        this.context = context;  
        conResolver = context.getContentResolver();  
    }  
    
    public List<SmsItem> getSmsItemsFromXml(){  
    	  
        SmsItem smsItem = null;  
        XmlPullParser parser = Xml.newPullParser();  
        String absolutePath = Environment.getExternalStorageDirectory() + "/SMSBackup/message.xml";  
        System.out.println("path=" + absolutePath);
        File file = new File(absolutePath);  
        if (!file.exists()) {  
  
            Looper.prepare();  
            Toast.makeText(context, "message.xml短信备份文件不在sd卡中", 1).show();  
            Looper.loop();//退出线程  
//          return null;  
        }  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            parser.setInput(fis, "UTF-8");  
            int event = parser.getEventType();  
            while (event != XmlPullParser.END_DOCUMENT) {  
                switch (event) {  
                case XmlPullParser.START_DOCUMENT:  
                    smsItems = new ArrayList<SmsItem>();  
                    break;  
  
                case XmlPullParser.START_TAG: // 如果遇到开始标记，如<smsItems>,<smsItem>等  
                    if ("item".equals(parser.getName())) {  
                        smsItem = new SmsItem();  
  
                        smsItem.setAddress(parser.getAttributeValue(0));  
                        smsItem.setPerson(parser.getAttributeValue(1));  
                        smsItem.setDate(parser.getAttributeValue(2));  
                        smsItem.setProtocol(parser.getAttributeValue(3));  
                        smsItem.setRead(parser.getAttributeValue(4));  
                        smsItem.setStatus(parser.getAttributeValue(5));  
                        smsItem.setType(parser.getAttributeValue(6));  
                        smsItem.setReply_path_present(parser.getAttributeValue(7));  
                        smsItem.setBody(parser.getAttributeValue(8));  
                        smsItem.setLocked(parser.getAttributeValue(9));  
                        smsItem.setError_code(parser.getAttributeValue(10));  
                        smsItem.setSeen(parser.getAttributeValue(11));  
  
                    }  
                    break;  
                case XmlPullParser.END_TAG:// 结束标记,如</smsItems>,</smsItem>等  
                    if ("item".equals(parser.getName())) {  
                        smsItems.add(smsItem);  
                        smsItem = null;  
                    }  
                    break;  
                }  
                event = parser.next();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            Looper.prepare();  
            Toast.makeText(context, "短信恢复出错", 1).show();  
            Looper.loop();  
            e.printStackTrace();  
              
        } catch (XmlPullParserException e) {  
            // TODO Auto-generated catch block  
            Looper.prepare();  
            Toast.makeText(context, "短信恢复出错", 1).show();  
            Looper.loop();  
            e.printStackTrace();          
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            Looper.prepare();  
            Toast.makeText(context, "短信恢复出错", 1).show();  
            Looper.loop();  
            e.printStackTrace();  
        }  
        return smsItems;  
    }
}
