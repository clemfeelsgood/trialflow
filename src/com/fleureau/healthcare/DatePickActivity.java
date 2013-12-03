package com.fleureau.healthcare;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DatePickActivity extends Activity {
	
	public Calendar calendar;
	public Date selectedDate;
	public String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.calendar = Calendar.getInstance();
		Intent intent = getIntent();
		this.title = getIntent().getStringExtra("title");
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_date_pick);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.nav_header_title);
	}
	
	public void onResume(){
		super.onResume();
		TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
		headerTitle.setText(this.title);
		LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(this.LAYOUT_INFLATER_SERVICE);
    	//nature pour le moment
    	LinearLayout calendarView = (LinearLayout) findViewById(R.id.weeksFrame);
    	Date currentDate = new Date();
    	calendar.setTime(currentDate);
    	while(calendar.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
    		calendar.roll(Calendar.DAY_OF_YEAR, false);
    	}
    	for(int index=0; index<50; index++){
    		View week = inflater.inflate(R.layout.week_view, null);
    		for(int dayIndex=0; dayIndex<7; dayIndex++){
    			boolean isBeforeToday = calendar.before(Calendar.getInstance());
    			boolean isToday = currentDate.compareTo(calendar.getTime())==0;
    			boolean isFirstDayOfMonth = calendar.get(Calendar.DATE) == 1;
    			boolean isLastDayOfYear = calendar.get(Calendar.MONTH)==Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH)==31;
    			TextView dayView = (TextView) week.findViewById(matchDay(calendar.get(Calendar.DAY_OF_WEEK)));
	    		dayView.setClickable(true);
	    		dayView.setText(calendar.get(Calendar.DAY_OF_MONTH)+"");
	    	
	    		if(calendar.get(Calendar.MONTH) % 2 == 0) {
	    			dayView.setBackgroundResource(R.color.$menuGrey);
	    		}
	    		if(isBeforeToday && !isToday){
	    			dayView.setBackgroundColor(Color.WHITE);
	    			dayView.setTextColor(getResources().getColor(R.color.$menuGrey));
	    			dayView.setClickable(false);
	    		}
	    		if(isToday){
	    			//dayView.setBackgroundColor(Color.BLACK);
	    			//dayView.setTextColor(Color.WHITE);
	    			dayView.setTextSize(12);
	    			dayView.setText(getResources().getString(getMonth(calendar.get(Calendar.MONDAY)))+"\n"+calendar.get(Calendar.DAY_OF_MONTH)+"");
	    			dayView.setClickable(true);
	    		}
	    		
	    		if(isFirstDayOfMonth){
	    			dayView.setTextSize(12);
	    			dayView.setText(getResources().getString(getMonth(calendar.get(Calendar.MONDAY)))+"\n"+calendar.get(Calendar.DAY_OF_MONTH)+"");
	    		}
	    		
	    		dayView.setTag(calendar.getTime());
	    		calendar.roll(Calendar.DAY_OF_YEAR, true);
	    		if(isLastDayOfYear)
	    			calendar.roll(Calendar.YEAR, true);
    		}
    		calendarView.addView(week);
    	}
	}
	
	public int matchDay(int day){
		switch(day){
		case Calendar.MONDAY :
			return R.id.weekMon;
		case Calendar.TUESDAY :
			return R.id.weekTue;
		case Calendar.WEDNESDAY :
			return R.id.weekWed;
		case Calendar.THURSDAY :
			return R.id.weekThu;
		case Calendar.FRIDAY :
			return R.id.weekFri;
		case Calendar.SATURDAY :
			return R.id.weekSat;
		case Calendar.SUNDAY :
			return R.id.weekSun;
		default : throw new IllegalArgumentException("Wrong Calendar.DAY " + day );	
		}
	}

	
	public void clickOnCalendar(View view){
		Date attachedDate = (Date) view.getTag();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(attachedDate);
		Intent returnIntent = new Intent();
		String date = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
		Log.e("Date",date);
		returnIntent.putExtra("date",date);
		setResult(RESULT_OK,returnIntent);     
		finish();
		
	}
	
	public static int getMonth(int nb){
    	switch(nb){
	    	case Calendar.JANUARY : return R.string.short_month_jan;
	    	case Calendar.FEBRUARY : return R.string.short_month_feb;
	    	case Calendar.MARCH : return R.string.short_month_mar;
	    	case Calendar.APRIL : return R.string.short_month_apr;
	    	case Calendar.MAY : return R.string.short_month_may;
	    	case Calendar.JUNE : return R.string.short_month_jun;
	    	case Calendar.JULY : return R.string.short_month_jul;
	    	case Calendar.AUGUST : return R.string.short_month_aug;
	    	case Calendar.SEPTEMBER : return R.string.short_month_sep;
	    	case Calendar.OCTOBER : return R.string.short_month_oct;
	    	case Calendar.NOVEMBER : return R.string.short_month_nov;
	    	case Calendar.DECEMBER : return R.string.short_month_dec;
    	}
    	throw new IllegalArgumentException("Unhandled month nb : " + nb);
    }
	
	
}
