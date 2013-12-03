package com.fleureau.healthcare.managers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

public class SessionManager {

	public SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
	Context context;
	
	public String userToken, userFirstName, userLastName;
	public long userId;

    public static final int PRIVATE_MODE = 0;
    public static final int NOTIFIABLE_OK = 0;
    public static final int NOTIFIABLE_FAIL = 1;
    public static final int NOTIFIABLE_UNCHECKED = 2;
	private static final String IS_LOGIN = "IsLoggedIn";
    public static final String PREF_NAME = "WePoppSessionPref";
    public static final String KEY_USER_ID = "UserId";
    public static final String KEY_USER_TOKEN = "UserToken";
    public static final String KEY_USER_FIRST_NAME = "UserFirstName";
    public static final String KEY_USER_LAST_NAME = "UserLastName";
    public static final String ANDROID_DEVICE_TOKEN = "registration_id";
    public static final String NOTIFIABILITY = "Notif";
    public static int isNotifiable; 
    
    public SessionManager(Context context){
    	this.context = context;
    	pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    	this.editor = pref.edit();
    	this.editor.commit();
    	this.updateSession();
    	
    }
    
    public void createSession(long userId, String userToken, String userFirstName, String userLastName){		
        this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        
        this.editor = pref.edit();
        this.editor.putBoolean(IS_LOGIN, true);
        this.editor.putLong(KEY_USER_ID,userId);
        this.editor.putString(KEY_USER_TOKEN, userToken);
        this.editor.putString(KEY_USER_FIRST_NAME, userFirstName);
        this.editor.putString(KEY_USER_LAST_NAME, userLastName);
        this.editor.putInt(NOTIFIABILITY, pref.getInt(NOTIFIABILITY, NOTIFIABLE_UNCHECKED));
        	
        this.editor.commit();
        
        this.updateSession();
        
	}
    
    public void destroySession() {
    	this.editor.clear();
    	this.editor.commit();
    }
    
    public void updateSession(){
    	this.userFirstName = pref.getString(KEY_USER_FIRST_NAME,null);
    	this.userLastName = pref.getString(KEY_USER_LAST_NAME,null);
    	this.userToken = pref.getString(KEY_USER_TOKEN,null);
    	this.userId = pref.getLong(KEY_USER_ID,-1);
    }
   
    public static boolean checkLogin(Context context){		
		return context.getSharedPreferences(PREF_NAME,PRIVATE_MODE).getBoolean(IS_LOGIN, false);
	}
    
}