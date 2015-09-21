package com.itboye.banma.api;

import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;

public  class StrVolleyInterface implements Response.Listener<String>, ErrorListener{
	public Context mContext;
	/*public static Listener<String> mListener;
	public static ErrorListener mErrorListener;*/
	
	private StrUIDataListener uiDataListener;
	public StrVolleyInterface(Context mContext){
		this.mContext = mContext;
	}
	public void setStrUIDataListener(StrUIDataListener uiDataListener)
	{
		this.uiDataListener = uiDataListener;
	}
	protected void notifyDataChanged(String data)
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
	public void onResponse(String response) {
		notifyDataChanged(response);
		
	}
}
