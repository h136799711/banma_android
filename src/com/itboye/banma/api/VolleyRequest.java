package com.itboye.banma.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.itboye.banma.app.AppContext;

public class VolleyRequest {
	public static StringRequest strRequest;
	public static JsonRequest<JSONObject> jsonRequest;
	public static JsonRequest<JSONArray> jsonArrayRequest;
	public static StringRequest stringRequest;
	public static Context context;
	public AppContext appContext;

	public static void StrRequestGet(Context mContext, String url, String tag,
			StrVolleyInterface vif) {
		AppContext.getHttpQueues().cancelAll(tag);
		strRequest = new StringRequest(Method.GET, url, vif, vif);
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}

	public static void StrRequestPost(Context mContext, String url, String tag,
			final Map<String, String> params, StrVolleyInterface vif) {

		strRequest = new StringRequest(Method.POST, url, vif, vif){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}
	
}
