/*
 * Phiee
 */
package com.example.ward.contact;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.text.format.DateUtils;

import com.example.ward.R;


public class DateFormatUtils {
	private int year_now;
	private int month_now;
	private int day_now;
	private int hours_now;
	
	private static SimpleDateFormat sfd_year = null;
	private static SimpleDateFormat sfd_month = null;
	private static SimpleDateFormat sfd_hour = null;
	
	private Context mContext;
	/**
	 * @param time current time in mills
	 */
	public DateFormatUtils(Context context,long time){
		Calendar calendar_now = Calendar.getInstance();
		calendar_now.setTimeInMillis(time);
		year_now = calendar_now.get(Calendar.YEAR);
		month_now = calendar_now.get(Calendar.MONTH) + 1;
		day_now = calendar_now.get(Calendar.DAY_OF_MONTH);
		hours_now = calendar_now.get(Calendar.HOUR_OF_DAY);//24H
		
		sfd_year = new SimpleDateFormat(context.getResources().getString(R.string.calls_log_year_sfd));
		sfd_month = new SimpleDateFormat(context.getResources().getString(R.string.calls_log_month_sfd));
		sfd_hour = new SimpleDateFormat(context.getResources().getString(R.string.calls_log_hours_sfd));
		
		this.mContext = context;
	}
	
	/**
	 * @param time the time that need format
	 * @return the time that formated like "42minutes ago"
	 */
	public String getDateFormatString(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);//24H
		
		String format_time = "";
		Date date;
		date = new Date(time);
		
		if (year == year_now ) {
			//this year,we do not need show year in UI
			if (month == month_now) {
				//this year & this month
				int day_dur = day - day_now;
				if (day_dur == 0) {
					//today
					int hours_dur = hours - hours_now;
					if (hours_dur == 0) {
						//the time is in an hour,so we show like "42miniutes ago" 
						format_time = DateUtils.getRelativeTimeSpanString(time,
			                    getCurrentTimeMillis(),
			                    DateUtils.MINUTE_IN_MILLIS,
			                    DateUtils.FORMAT_ABBREV_ALL).toString();
					}else if (hours_dur < 0) {
						//an hour ago,we just show like "19:20"
						format_time = sfd_hour.format(date);
					}else{
						//in the future
						format_time = DateUtils.getRelativeTimeSpanString(time,
			                    getCurrentTimeMillis(),
			                    DateUtils.MINUTE_IN_MILLIS,
			                    DateUtils.FORMAT_ABBREV_ALL).toString();
					}
				}else if (day_dur == -1) {
					//yestoday,we will show like "yestoday 12:11"
					format_time = mContext.getResources().getString(R.string.calls_log_yestoday, sfd_hour.format(date));
				}else if (day_dur < -1) {
					//one day ago,we just show like "Apirl 12,12:01"
					format_time = sfd_month.format(date);
				}else {
					// int the future
					format_time = DateUtils.getRelativeTimeSpanString(time,
		                    getCurrentTimeMillis(),
		                    DateUtils.MINUTE_IN_MILLIS,
		                    DateUtils.FORMAT_ABBREV_ALL).toString();
				}
			}else {
				//the other month,we show like "Apirl 12 14:03"
				format_time = sfd_month.format(date);
			}
		}else {
			//not this year,we show like "Apirl 12,2012 14:03"
			format_time = sfd_year.format(date);
		}
		
		return format_time;
	}
	
	private Long mCurrentTimeMillisForTest;
	public long getCurrentTimeMillis() {
        if (mCurrentTimeMillisForTest == null) {
            return System.currentTimeMillis();
        } else {
            return mCurrentTimeMillisForTest;
        }
    }	
}
