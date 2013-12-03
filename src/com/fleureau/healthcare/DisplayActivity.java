package com.fleureau.healthcare;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fleureau.healthcare.structure.Action;
import com.fleureau.healthcare.structure.Patient;
import com.fleureau.healthcare.structure.Protocol;

public class DisplayActivity extends Activity{
	
	Patient patient;
	Protocol protocol;
	String designation;
	int day;
	
	public static final int PATIENT_CODE = 0;
	public static final int KIT_CODE = 1;
	public static final int DRUG_CODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_display);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.nav_header_title);
                	
        	try {
				this.patient = new Patient(new JSONObject(getIntent().getStringExtra("patient_data")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	this.designation = this.patient.getUid();
        	this.protocol = this.patient.getProtocol();
        	this.day = this.patient.getProtocolDay();
        
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onResume(){
		super.onResume();
		redraw(day);
		
	}
	
	@SuppressLint("NewApi")
	private void redraw(int day){
		Log.e("DAY",day+"");
		((TextView) findViewById(R.id.headerTitle)).setText(protocol.name);
		((TextView) findViewById(R.id.displayName)).setText(designation);
		((TextView) findViewById(R.id.displayDay)).setText(getResources().getString(R.string.day)+" "+day);
		View dottedLine =findViewById(R.id.dotted);
		dottedLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		dottedLine =findViewById(R.id.dotted2);
		dottedLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	
		LinearLayout actionsFrame = (LinearLayout) findViewById(R.id.displayActionsFrame);
		actionsFrame.removeAllViews();
		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		int $index = 0;
		for(Action action : protocol.getTodayActions(day)){
			RelativeLayout actionLayout = (RelativeLayout) inflater.inflate(R.layout.action, null);
			if($index % 2 == 0 ){
				actionLayout.setBackgroundResource(R.color.$menuBack);
				((ImageView) actionLayout.findViewById(R.id.displayActionDo)).setBackground(getResources().getDrawable(R.drawable.menu_my_account_white));
			}
			((ImageView) actionLayout.findViewById(R.id.displayActionDo)).setTag(action.id);
			((TextView) actionLayout.findViewById(R.id.displayActionName)).setText(action.name);
			actionsFrame.addView(actionLayout);
			$index++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}
	
	public void clickHandle(View view){
		switch(view.getId()){
		case R.id.calendar :
			Intent intent = new Intent(this,DatePickActivity.class);
		    intent.putExtra("title",protocol.name + "-" + designation );
		    startActivityForResult(intent, 1);			
			break;
		case R.id.displayActionDo :
			intent = new Intent(this,TestActivity.class);
		    intent.putExtra("action",protocol.findActionById((Integer) view.getTag()).toString());
		    Log.e("ACTIONBEF",protocol.findActionById((Integer) view.getTag()).toString());
		    startActivity(intent);			
			break;
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        super.onActivityResult(requestCode, resultCode, data);
      
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("date");
                Log.v("scanned_url",contents);
                try {
                	day = this.patient.getProtocolDay(new SimpleDateFormat("yyyy-MM-dd").parse(contents));
					redraw(day);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }    

}
