package com.itboye.banma.bean;

import android.text.format.Time;


public class AccessToken {
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	String grant_type;//��Ȩ���ͣ�Ĭ��Ϊ��client_credentials��
	int  client_id;//�ͻ���ID��Ĭ��Ϊby55bec42a8e5431
	String client_secret;//�ͻ�����Կ��Ĭ��Ϊf63515ffc8c1a200dedeffce9bd63492
	Time time;//��ʼ��ʱϵͳʱ��
}
