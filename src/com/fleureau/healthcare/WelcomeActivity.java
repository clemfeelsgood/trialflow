package com.fleureau.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.appscumen.example.MySwitch;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
        
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}
	
	public void clickHandle(View view){
		Intent intent = new Intent(this,LoginActivity.class);
		this.startActivity(intent);
	}

}
