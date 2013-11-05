package com.example.ward.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ward.R;

public class RefreshListView extends ListView {

	public static final int NORMAL     = 0;
	public static final int PULL       = 1;
	public static final int RELEASE    = 2;
	public static final int REFRESHING = 3;
	
	private int state = PULL; //���ڼ�¼��ǰ״̬
	private int preState;//���ڼ�¼֮ǰһ�ε�״̬
	
	private View 			mHeader;
	private ProgressBar 	mHeaderBar;
	private ImageView 		mHeaderArrow;
	private TextView 		mHeaderText;
	private TextView 		mHeaderDate;
	
	
	private Drawable mPullDraw;
	private Drawable mReleaseDraw;
	
	private Context 		mContext;
	private LayoutInflater mInflater;
	private OnRefreshListener mRefreshListener;
	
	private float mDownY;
	private float mMoveY;
	
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	private void initialize(Context context) {
		mContext 	 = context;
		mInflater 	 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mHeader 	 = mInflater.inflate(R.layout.refresh_lv_header,null);
		mHeaderBar 	 = (ProgressBar) mHeader.findViewById(R.id.header_bar);
		mHeaderArrow = (ImageView) mHeader.findViewById(R.id.arrow);
		mHeaderText  = (TextView) mHeader.findViewById(R.id.text);
		mHeaderDate  = (TextView) mHeader.findViewById(R.id.date);
		
		
		mPullDraw = getResources().getDrawable(R.drawable.pull_refresh);
		mReleaseDraw = getResources().getDrawable(R.drawable.realease_refresh);
		
		//�������ֲ������ݸ���Ӧ��view
		ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 100);
		mHeader.setLayoutParams(params);
		
		addHeaderView(mHeader);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownY = ev.getY();
			mHeaderDate.setText(mDateFormat.format(new Date()));
			break;
		case MotionEvent.ACTION_MOVE:
			mMoveY = ev.getY();
			if(state != REFRESHING) {
				int instance = (int) ((mMoveY-mDownY)/3);
				if(getFirstVisiblePosition() > 0) {
					mDownY = mMoveY;
					state = NORMAL;
					break;
				}
				if(instance < 0) break;
				setPadding(0,-mHeader.getLayoutParams().height+instance, 0, 0);
				if(state != NORMAL)setSelection(0); //��һ���൱��Ҫ �����ݵĸ߶ȴ�����Ļʱ ��������ʱ lisview�Ĺ����ٶȱ�setpaddingҪ�� ���Ա���������һ��ʹlistviewʼ���ڵ�һ��
				if(instance > 100) {
					state = RELEASE;
					mHeaderText.setText("���ּ���ˢ��");
					mHeaderArrow.setImageDrawable(mReleaseDraw);
				} else if((state == NORMAL || state == RELEASE) ){
					preState = state;    state = PULL;
					if(preState == NORMAL && state == PULL && instance >= 18) mDownY = mMoveY;
					else if(preState != RELEASE && instance < 20) state = NORMAL;
					mHeaderText.setText("�����϶�ˢ��");
					mHeaderArrow.setImageDrawable(mPullDraw);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(state == RELEASE) {
				if(mRefreshListener!=null) mRefreshListener.onRefresh();
				else {
					setPadding(0, -mHeader.getLayoutParams().height, 0, 0);
					state = PULL;
				}
			}
			if(state == PULL) setPadding(0, -mHeader.getLayoutParams().height, 0, 0);
			if(mMoveY == mDownY) state = NORMAL;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setPadding(0,-mHeader.getLayoutParams().height, 0, 0);
	}
	
	public interface OnRefreshListener {
		 public void onRefresh();
	}
	
	public void setOnRefreshListener(OnRefreshListener listener) {
		this.mRefreshListener = listener;
	}
	
	//ʹ�øĹ�����ʱ  �����߳̽��к�ʱ����ǰ������ø÷���
	public void onPrepareRefresh() {
		setPadding(0,0,0,0);
		state = REFRESHING;
		mHeaderArrow.setImageDrawable(mPullDraw);
		mHeaderText.setText("������......");
		mHeaderArrow.setVisibility(View.INVISIBLE);
		mHeaderBar.setVisibility(View.VISIBLE);
	}
	
	//ʹ�øĹ�����ʱ  �����߳̽��к�ʱ�����������ø÷���
	public void onCompleteRefresh() {
		setPadding(0,-mHeader.getLayoutParams().height, 0, 0);
		state = PULL;
		mHeaderText.setText("�����϶�ˢ��");
		mHeaderArrow.setVisibility(View.VISIBLE);
		mHeaderBar.setVisibility(View.INVISIBLE);
	}
}
