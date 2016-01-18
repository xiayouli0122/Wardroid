package com.example.ward.media;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import com.baidu.android.bbalbs.common.a.c;
import com.example.ward.R;
import com.example.ward.media.image.AsyncImageLoader2;
import com.zhaoyan.common.utils.Log;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoFrameDemo extends Activity implements OnClickListener{
    
    private ImageView mImageView;
    private VideoView mVideoView;
    private TextView mDurationView,mTotalView;
    
    private GridView mGridView;
    
    private Button mPlayButton;
    private Button mButton;
    
    private List<Bitmap> mBitmaps = new ArrayList<Bitmap>();
    private MyAdapter myAdapter ;
    
    String videoPath = "/sdcard/default10.mp4";
    private float mScreenWidth;
    
    private static final float  WIDHT = 720.0f;
    private static final float HEIGHT = 404.0f;
    
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    
    private static final int MSG_UPDATE_PROGRESS = 0;
    private static final int MSG_UPDATE_GETING = 1;
    private Handler mHandler = new Handler(){
      public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case MSG_UPDATE_PROGRESS:
                int pos = setProgress();
                if (mVideoView.isPlaying()) {
                    msg = obtainMessage(MSG_UPDATE_PROGRESS);
                    sendMessageDelayed(msg, 1000 - (pos % 1000));
                }
                break;
            case MSG_UPDATE_GETING:
                int pos1 = msg.arg1;
                break;

            default:
                break;
            }
          
      };  
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_video_frame);
        
        mImageView = (ImageView) findViewById(R.id.imageview);
        mGridView = (GridView) findViewById(R.id.gridview);
        mButton = (Button) findViewById(R.id.getFrameButton);
        mButton.setOnClickListener(this);
        
        mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(this);
        
        mDurationView = (TextView) findViewById(R.id.textview_duration);
        mTotalView = (TextView) findViewById(R.id.textview_total);
        
        mVideoView = (VideoView) findViewById(R.id.videoView);
        
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        
        resizeView();
        
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, Thumbnails.FULL_SCREEN_KIND);
        mVideoView.setBackgroundDrawable(new BitmapDrawable(bitmap));
        
        myAdapter = new MyAdapter();
        
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        
        mVideoView.setVideoPath(videoPath);
        mVideoView.requestFocus();
        setProgress();
        mVideoView.setOnPreparedListener(new OnPreparedListener() {
            
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mDurationView.setText(stringForTime(0));
                mTotalView.setText(stringForTime(mp.getDuration()));
            }
        });
        mVideoView.setOnCompletionListener(new OnCompletionListener() {
            
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                
            }
        });
        
    }
    
    private void resizeView(){
        ViewGroup.LayoutParams lp = mVideoView.getLayoutParams();
        lp.width = (int) mScreenWidth;
        lp.height =  (int) (mScreenWidth * HEIGHT / WIDHT);
        mVideoView.setLayoutParams(lp);
    }
    
    private int setProgress() {
        if (mVideoView == null) {
            return 0;
        }
        int position = mVideoView.getCurrentPosition();
        mDurationView.setText(stringForTime(position));
        return position;
    }
    
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;

        mFormatBuilder.setLength(0);
        return mFormatter.format("%02d:%02d", minutes, seconds).toString();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.playButton:
            if (mVideoView.isPlaying()) {
                mVideoView.pause();
                mPlayButton.setText("Play");
            } else {
                mVideoView.start();
                mVideoView.setBackgroundDrawable(null);
                mPlayButton.setText("Pause");
            }
            mHandler.sendEmptyMessage(MSG_UPDATE_PROGRESS);
            break;
        case R.id.getFrameButton:
            new MyAsyncTask(videoPath).execute(videoPath);
            break;

        default:
            break;
        }
        
    }
    
    private class MyAsyncTask extends AsyncTask<String, Integer, Void>{
        ProgressDialog mProgressDialog;
        MediaMetadataRetriever mRetriever;
        
        // 取得视频的长度(单位为毫秒) 
        int mTime;
        // 取得视频的长度(单位为秒) 
        int mSeconds;
        
        
        public MyAsyncTask(String path) {
            // TODO Auto-generated constructor stub
            mRetriever = new MediaMetadataRetriever();
            mRetriever.setDataSource(path);
            
            mTime = Integer.valueOf(mRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            Log.d("Yuri", "mTime:" + mTime);
            mSeconds = Integer.valueOf(mTime) / 1000; 
            Log.d("Yuri", "mSeconds:" + mSeconds);
        }
        
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(VideoFrameDemo.this);
            mProgressDialog.setMessage("Get Framing...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();
            
        }
        
        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap bitmap = null;
            //获取每一秒的缩略图
            //15s
            int uTime = 0;
            //这样算法，是算出总共15s内每一帧的缩略图，但是有一个问题，视频按秒算，不精确，所以要按毫秒算，见方法二
//            for (int i = 0; i < mSeconds; i++) {
//                Log.d("Yuri", "doInBackground>>" + i);
//                for (int j = 0; j < 25; j++) {
//                    //1s 25帧
//                    uTime = (i * 1000 + j * (1000 / 25)) * 1000;
//                    //微秒
//                    Log.d("Yuri", "doInBackground.uTime:" + uTime);
//                    //getFrameAtTime 第一个参数是微秒
//                    bitmap = mRetriever.getFrameAtTime(uTime, MediaMetadataRetriever.OPTION_CLOSEST);
//                    mBitmaps.add(bitmap);
//                }
////                onProgressUpdate(i);
//            }
            //方法二
            //假设视频一共15394ms，已知视频是25帧/s，那么是每40ms一帧，所以我们没40ms取一帧
            int count = 0;
            int uTime2= 0;
            do {
                uTime2 = count * 1000;
                Log.d("Yuri", "uTime2:" + uTime2);
                bitmap = mRetriever.getFrameAtTime(uTime2, MediaMetadataRetriever.OPTION_CLOSEST);
                mBitmaps.add(bitmap);
                count = count + 40;
            } while (count <= mTime);
            
            // 最后一帧
            if (count < mTime) {
                bitmap = mRetriever.getFrameAtTime(mTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                mBitmaps.add(bitmap);
            }
            
            
            
            return null;
        }
        
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            
            Message message = new Message();
            message.arg1 = values[0];
            message.what = MSG_UPDATE_GETING;
            message.setTarget(mHandler);
            message.sendToTarget();
        }
        
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog = null;
            }
            
            mGridView.setAdapter(myAdapter);
            mButton.setText("GetFrame(" + mBitmaps.size() + ")");
        }
        
    }
    
    private class MyAdapter extends BaseAdapter{
        
        private AsyncImageLoader2 mAsyncImageLoader2;
        
        public MyAdapter() {
            // TODO Auto-generated constructor stub
            mAsyncImageLoader2 = new AsyncImageLoader2(getApplicationContext());
        }
        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mBitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            ViewHolder holder = null;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_video_frame, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.imageview);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            
            holder.imageView.setImageBitmap(mBitmaps.get(position));
            
            return view;
        }
        
        class ViewHolder{
            ImageView imageView;
        }
    }
}
