package com.example.ward.media;

import java.io.File;

import com.example.ward.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 检查视频是否可以正常播放
 * @author Yuri
 *
 */
public class VideoCheckActivity extends Activity{
    String path = "/sdcard/videotest";
    
    String path1 = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_check);
        
        TextView textView = (TextView) findViewById(R.id.textview);
        
        File file = new File(path);
        File[] files = file.listFiles();
        
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Bitmap bitmap = null;
        String vidoeinfoString = "";
        for (File file2 : files) {
            Log.d("Yuri", "path:" + file2.getAbsolutePath());
            try {
                retriever.setDataSource(file2.getAbsolutePath());
                bitmap = retriever.getFrameAtTime();
                vidoeinfoString= retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
                Log.d("Yuri", "bitmap:" + bitmap);
                Log.d("Yuri", "videoInfo:" + vidoeinfoString);
                if (vidoeinfoString == null) {
                    Log.w("Yuri", "This video cannot to play");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Log.w("Yuri", "This video cannot to play:" + e.toString());
            }
            
            
        }
        
        
        
    }
}
