package com.fleureau.healthcare.structure;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

public class CRF {

	ArrayList<CRFItem> items;
	
	public CRF(){
		this.items = new ArrayList<CRFItem>();
	}
	
	public void addItem(CRFItem crf){
		this.items.add(crf);
	}
	
	public String toString(){
		String ret = "";
		for(CRFItem itm : items){
			ret += itm.toString();
			ret+= "&&";
		}
		return ret;
	}
	
	public static CRF fromString(String source){
			String[] itmsS= source.split("&&");
			Log.e("CRFCLLLL",itmsS.length+"");
			CRF crf = new CRF();
			int actionId = -1;
			for(String str : itmsS){
				CRFItem itm = null;
				try {
					itm = new CRFItem(new JSONObject(str));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("CRFITM",itm.toString());
				actionId = itm.actionId;
				crf.addItem(itm);
			}
			return crf;
		}
	
}
