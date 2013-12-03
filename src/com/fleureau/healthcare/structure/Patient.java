package com.fleureau.healthcare.structure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;


public class Patient {
	
	JSONObject backbone;
	String uid;
	Protocol protocol;
	int protocol_day;
	
	
	public Patient (JSONObject backbone){	
		Log.e("STR",backbone.toString());	
		try {
			this.backbone = backbone;
			this.uid = this.backbone.getString("uid");
			this.protocol = new Protocol(this.backbone.getJSONObject("protocol"));
			this.protocol_day = this.backbone.getInt("protocol_day");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Patient fromServer(String response){
		JSONObject structure;
		JSONObject backPatient = null;
		try {
			structure = new JSONObject(response).getJSONObject("data");
			backPatient= new JSONObject();
			backPatient.put("uid", structure.getJSONObject("patient").get("uid"));
			JSONArray actions = Action.convert(structure.getJSONArray("assessments"));
			JSONObject protocolStr = new JSONObject();
			protocolStr.put("name",structure.getJSONObject("clinical_study").getString("name"));
			protocolStr.put("actions", actions);
			SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date parsedDate = parserSDF.parse(structure.getJSONObject("protocole").getString("date").split("T")[0]);
			protocolStr.put("start_date", structure.getJSONObject("protocole").getString("date").split("T")[0]);
			backPatient.put("protocol_day", daysBetween(parsedDate,cal.getTime()));
			JSONArray crfs = structure.getJSONArray("crf_items");
			Log.e("ORIGINALCRF",crfs.toString());
			JSONObject crf_linked = new JSONObject();
			for(int index = 0; index < crfs.length(); index++){
				CRF crf = new CRF();
				JSONArray crfs_action = crfs.getJSONArray(index);
				if(crfs_action.length()!=0){
					int actionId = -1;
					for(int index2 = 0; index2 < crfs_action.length(); index2++){
						JSONObject crf_back = crfs_action.getJSONObject(index2);
						actionId = crf_back.getInt("assessment_id");
						CRFItem itm = new CRFItem(crf_back);
						crf.addItem(itm);
					}
					crf_linked.put(actionId+"", crf.toString());
				}
			}
			protocolStr.put("crf_linked", crf_linked);
			backPatient.put("protocol", protocolStr);
			Log.e("STR",backPatient.toString());	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Patient(backPatient);
	}
	
	public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public String getUid(){
		return uid;
	}
	
	public Protocol getProtocol(){
		return protocol;
	}
	
	public int getProtocolDay(){
		return protocol_day;
	}
	
	public int getProtocolDay(Date date){
		try {
			return daysBetween(new SimpleDateFormat("yyyy-MM-dd").parse(this.protocol.startDate),date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public String toString(){
		return this.backbone.toString();
	}
}