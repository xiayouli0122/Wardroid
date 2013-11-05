package com.example.ward.contact;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ward.R;

public class CallsLogAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<CallsLog> list;
	
	private DateFormatUtils mDateFormatUtils;
	
	public CallsLogAdapter(Context context, List<CallsLog> list){
//		System.out.println("callLogsAdapter");
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		
		this.list = list;
		
		mDateFormatUtils = new DateFormatUtils(mContext, System.currentTimeMillis());
	}
	
	static class ViewHolder{
		private ImageView mIconView;
		private ImageView mCallBtnView;
		
		private TextView mCallNosText;
		private TextView mCallNameText;
		private TextView mCallNumberText;
		private TextView mIncomingText;
		private TextView mSimInfoText;
		private TextView mTimeText;
	}

	@Override
	public int getCount() {
		//由这个决定list显示的列数
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.calllog_item, null);
			holder.mIconView = (ImageView) convertView.findViewById(R.id.icon_view);
			holder.mCallNameText = (TextView) convertView.findViewById(R.id.name_text);
			holder.mCallNumberText = (TextView)convertView.findViewById(R.id.number_text);
			holder.mTimeText = (TextView)convertView.findViewById(R.id.time_text);
			holder.mIncomingText = (TextView)convertView.findViewById(R.id.incoming_text);
			holder.mCallBtnView = (ImageView)convertView.findViewById(R.id.call_button);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (list.get(position).getName() == null) {
			holder.mCallNameText.setText(list.get(position).getPhoneNumber());
			holder.mCallNumberText.setVisibility(View.GONE);
		}else {
			holder.mCallNameText.setText(list.get(position).getName());
			holder.mCallNumberText.setVisibility(View.VISIBLE);
			holder.mCallNumberText.setText(list.get(position).getPhoneNumber());
		}
		
		
		if (list.get(position).getCallType() == 1) {//incoming
			holder.mIconView.setImageResource(R.drawable.call_log_type_incoming);
			holder.mIncomingText.setVisibility(View.GONE);
		}else if (list.get(position).getCallType() == 2) {//outgoing
			holder.mIconView.setImageResource(R.drawable.call_log_type_outgoing);
			holder.mIncomingText.setVisibility(View.GONE);
		}else if (list.get(position).getCallType() == 3) {//missed
			holder.mIconView.setImageResource(R.drawable.call_log_type_missed);
			holder.mIncomingText.setVisibility(View.VISIBLE);
		}
		
		long logTime = list.get(position).getDateTime();
		
		String dateText = mDateFormatUtils.getDateFormatString(logTime);
		holder.mTimeText.setText(dateText);
		
		holder.mCallBtnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "You Click me", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	
}
