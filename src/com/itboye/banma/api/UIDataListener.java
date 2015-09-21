package com.itboye.banma.api;

import org.json.JSONObject;

import com.android.volley.VolleyError;

public interface UIDataListener
{
	public void onErrorHappened(VolleyError error);
	void onDataChanged(JSONObject data);
}
