package com.itboye.banma.api;

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;

public  class VolleyInterface implements Response.Listener<JSONObject>, ErrorListener{
	public Context mContext;
	/*public static Listener<String> mListener;
	public static ErrorListener mErrorListener;*/
	
	private UIDataListener uiDataListener;
	public VolleyInterface(Context mContext){
		this.mContext = mContext;
	}
	public void setUiDataListener(UIDataListener uiDataListener)
	{
		this.uiDataListener = uiDataListener;
	}
	protected void notifyDataChanged(JSONObject data)
	{
		if(uiDataListener != null)
		{
			uiDataListener.onDataChanged(data);
		}
	}
	
	protected void notifyErrorHappened(VolleyError error)
	{
		if(uiDataListener != null)
		{
			uiDataListener.onErrorHappened(error);
		}
	}

	public void onErrorResponse(VolleyError error) {
		notifyErrorHappened(error);
		
	}

	@Override
	public void onResponse(JSONObject response) {
		notifyDataChanged(response);
		
	}
}
