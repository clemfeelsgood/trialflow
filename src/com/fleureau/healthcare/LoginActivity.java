package com.fleureau.healthcare;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.appscumen.example.MySwitch;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.nav_header_title);
        
        MySwitch mySwitch = (MySwitch) findViewById(R.id.logInButton);
			
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
		    		   ((MySwitch) findViewById(R.id.logInButton)).setChecked(true);
		    	   }
		    	}, 500);
		    }
		 
		   }
		  });
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
		headerTitle.setText(getResources().getString(R.string.title_activity_login));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void clickHandle(View view){
		
	}
}
