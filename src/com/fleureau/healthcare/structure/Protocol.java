package com.fleureau.healthcare.structure;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

public class Protocol {

	Action[] actions;
	public String name;
	public String startDate;
	public JSONObject backbone;
	
	public Protocol(JSONObject backbone){
		this.backbone = backbone;
		try {
			this.name = backbone.getString("name");
			this.startDate = backbone.getString("start_date");
			JSONArray mActions = backbone.getJSONArray("actions");
			this.actions = new Action[mActions.length()];
			JSONObject crf_linker = backbone.getJSONObject("crf_linked");
			SparseArray<CRF> crf_linked = new SparseArray<CRF>();
	        Iterator<?> keys = crf_linker.keys();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
				String parsedCRFs = crf_linker.getString(key);
				Log.e("CRFMM",crf_linker.toString());
				String[] itmsS= parsedCRFs.split("&&");
				Log.e("CRFCLLLL",itmsS.length+"");
				CRF crf = new CRF();
				int actionId = -1;
				for(String str : itmsS){
					CRFItem itm = new CRFItem(new JSONObject(str));
					Log.e("CRFITM",itm.toString());
					actionId = itm.actionId;
					crf.addItem(itm);
				}
				crf_linked.append(actionId, crf);
			}
			for(int index = 0; index < mActions.length(); index++){
				this.actions[index] = new Action(new JSONObject(mActions.getString(index)));
				this.actions[index].setCRF(crf_linked.get(this.actions[index].id));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	public Protocol(String name, Action[] actions){
		this.actions = new Action[actions.length];
		for(int index=0; index < actions.length; index++){
			this.actions[index] = actions[index];
		}
		this.name = name;
		this.backbone = new JSONObject();
		try {
			this.backbone.put("name",name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray act = new JSONArray();		
		for(Action action : actions){
			act.put(action.toString());
		}
		try {
			this.backbone.put("actions", act);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public boolean isCompleted(){
		return getPendingActions().size() == 0;
	}
	
	public ArrayList<Action> getPendingActions(){
		ArrayList<Action> tmp = new ArrayList<Action>();
		for(int index=0; index < actions.length; index++){
			if(!actions[index].isCompleted())
				tmp.add(actions[index]);
		}
		return tmp;
	}
	
	public Action findActionById(int id){
		for(Action action : actions){
			if(action.id == id)
				return action;
		}
		throw new IllegalArgumentException("Wrong action id : " + id);
	}
	
	public ArrayList<Action> getTodayActions(int patient_day){
		ArrayList<Action> tmp = new ArrayList<Action>();
		for(int index=0; index < actions.length; index++){
			if(!actions[index].isCompleted() && actions[index].isToday(patient_day))
				tmp.add(actions[index]);
		}
		return tmp;
	}
	
	public String toString(){
		return this.backbone.toString();
	}
	
}
