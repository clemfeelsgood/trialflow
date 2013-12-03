package com.fleureau.healthcare.structure;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class CRFItem {
	
	String name;
	String description;
	int input_type;
	int id;
	float minValue;
	float maxValue;
	int actionId;
	ArrayList<String> propositions;
	boolean completed;
	JSONObject backbone;
	public final static int MEASURE = 0;
	
	public CRFItem(JSONObject backbone) {
		this.backbone = backbone;
		try {
			this.name = backbone.getString("name");
			this.description = backbone.getString("description");
			this.input_type = backbone.getString("input_type").equals("measure")? 0: 1;
			this.actionId = backbone.getInt("assessment_id");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString(){
		return this.backbone.toString();
	}
}
