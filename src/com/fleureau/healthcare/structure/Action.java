package com.fleureau.healthcare.structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Action {
	
	JSONObject backbone;
	public String name;
	int protocol_day;
	boolean completed;
	private CRF crf;
	public int id;
	
	public Action(JSONObject backbone){
		this.backbone = backbone;
		try {
			this.name = backbone.getString("name");
			this.protocol_day = backbone.getInt("protocol_day");
			this.id = backbone.getInt("id");
			this.crf = CRF.fromString((String) backbone.get("crf"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void setCRF(CRF crf){
		if(crf == null)
			return;
		this.crf = crf;
		Log.e("CRFSTE",id+"");
		try {
			this.backbone.put("crf", crf.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CRF getCRF(){
		return crf;
	}
	
	public boolean isCompleted(){
		return completed;
	}
	
	public boolean isToday(int patient_day){
		return protocol_day == patient_day;
	}
	
	public String toString(){
		return backbone.toString();
	}
	
	public static JSONArray convert(JSONArray data){
		JSONArray response = new JSONArray();
		for(int index = 0; index < data.length(); index++){
			try {
				response.put(new Action(new JSONObject(data.getString(index))));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return response;
	}
	
}
