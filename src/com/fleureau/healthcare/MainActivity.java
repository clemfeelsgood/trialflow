package com.fleureau.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.appscumen.example.MySwitch;
import com.fleureau.healthcare.managers.SessionManager;
import com.fleureau.healthcare.structure.Patient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.nav_header_title);
        MySwitch mySwitch = (MySwitch) findViewById(R.id.scanButton);
		
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
		  
		  MySwitch myCalSwitch = (MySwitch) findViewById(R.id.calButton);
			
		  //attach a listener to check for changes in state
		  myCalSwitch.setChecked(true);
		  myCalSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  
	      
		 
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
		    		   ((MySwitch) findViewById(R.id.calButton)).setChecked(true);
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
		headerTitle.setText(getResources().getString(R.string.app_name));
    	//checkLogin();
    }
    
    public boolean checkLogin() {
		Intent intent;
		if (!SessionManager.checkLogin(getApplicationContext())) {
			intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);
			return false;
		}
		return true;
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
            	RequestParams params = new RequestParams();
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.v("scanned_url",contents);
                AsyncHttpClient client = new AsyncHttpClient();
    		    final Activity activity = this;
    		    String url = "http://healthcare-hackathon.herokuapp.com/patients"+contents;
    		    Log.e("zou",url);
    		    client.get(url, params, new AsyncHttpResponseHandler() {
    		        @Override
    		        public void onSuccess(String response) {
    		        	Log.e("RESP",response);
    		        	Patient fromServer = Patient.fromServer(response);
    		        	Intent intent = new Intent(activity,DisplayActivity.class);
    	                intent.putExtra("patient_data", fromServer.toString());
    	                activity.startActivity(intent);
    		        }                                                                                                                                                                     
    		    });               
                
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("date");
                Log.v("scanned_url",contents);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void clickHandle(View view){
    	switch(view.getId()){
    	case R.id.scanButton : 
    			
    			try {
    		    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    		    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

    		    startActivityForResult(intent, 0);

    		} catch (Exception e) {

    		    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
    		    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
    		    startActivity(marketIntent);

    		}
    			break;
    			
    	case R.id.calButton : 
			
		    Intent intent = new Intent(this,DatePickActivity.class);
		    intent.putExtra("title","My calendar");
		    startActivityForResult(intent, 1);
			break;	
    			
    	}
    }
    
}
