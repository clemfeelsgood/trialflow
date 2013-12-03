package com.fleureau.healthcare;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.appscumen.example.MySwitch;
import com.fleureau.healthcare.structure.Action;

public class TestActivity extends Activity {

	Action action;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		try {
			this.action = new Action(new JSONObject(getIntent().getStringExtra("action")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MySwitch mySwitch = (MySwitch) findViewById(R.id.testButton);
		
		  //attach a listener to check for changes in state
		  mySwitch.setChecked(true);
		  mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  
	      
		 
		   @Override
		   public void onCheckedChanged(CompoundButton buttonView,
		     boolean isChecked) {
		 
		    if(!isChecked){
		    	clickHandle(buttonView);
		    	new Handler().postDelayed(new Runnable()
		    	{
		    	   @Override
		    	   public void run()
		    	   {
		    		   ((MySwitch) findViewById(R.id.scanButton)).setChecked(true);
		    	   }
		    	}, 500);
		    }
		 
		   }
		  });
	}
	
	protected void onResume(){
		super.onResume();
		if(action.getCRF()!=null){
			Log.e("CRF",action.getCRF().toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	public void clickHandle(View view){
		
	}

}
