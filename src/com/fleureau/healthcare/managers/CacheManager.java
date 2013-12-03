package com.fleureau.healthcare.managers;

import java.io.BufferedInputStream;
import org.apache.commons.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CacheManager {
	
	Context context;
	
	public CacheManager(Activity activity){
		this.context = activity.getApplicationContext();
	}
		
	public static final String fileName = "HealthcareCache";
	
	public void writeCache(String data){
		try {
			FileOutputStream fos = this.context.openFileOutput(fileName, context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public SparseArray<Team> readCache(){
		SparseArray<Team> teams = new SparseArray<Team>();
		try {
			FileInputStream fis = this.context.openFileInput(fileName);
			String teamData = IOUtils.toString(fis);
	            JSONObject JSTeams = new JSONObject(teamData).getJSONObject("data");
	            Iterator keys = JSTeams.keys();
				while(keys.hasNext()){
					String Skey = (String) keys.next();
					int key = Integer.parseInt(Skey);
					teams.put(key, new Team(JSTeams.getJSONObject(Skey)));
				}
				fis.close();
				return teams;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teams;		
	}*/
		
	public boolean cacheIsEmpty(){
		try {
			FileInputStream fis = this.context.openFileInput(fileName);
			boolean result = fis.read() == -1;
			fis.close();
			return result;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}

