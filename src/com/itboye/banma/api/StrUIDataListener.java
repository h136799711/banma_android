package com.itboye.banma.api;


import com.android.volley.VolleyError;

public interface StrUIDataListener
{
	public void onErrorHappened(VolleyError error);
	void onDataChanged(String data);
}
