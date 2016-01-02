package com.itboye.banma.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RedEnvelope {
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUse_status() {
		return use_status;
	}
	public void setUse_status(String use_status) {
		this.use_status = use_status;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGet_time() {
		return get_time;
	}
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDtree_type_name() {
		return dtree_type_name;
	}
	public void setDtree_type_name(String dtree_type_name) {
		this.dtree_type_name = dtree_type_name;
	}
	public String getDtree_type() {
		return dtree_type;
	}
	public void setDtree_type(String dtree_type) {
		this.dtree_type = dtree_type;
	}
	public String getUse_condition() {
		return use_condition;
	}
	public void setUse_condition(String use_condition) {
		this.use_condition = use_condition;
	}
	public String getTpl_status() {
		return tpl_status;
	}
	public void setTpl_status(String tpl_status) {
		this.tpl_status = tpl_status;
	}
	public String getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTpl_notes() {
		return tpl_notes;
	}
	public void setTpl_notes(String tpl_notes) {
		this.tpl_notes = tpl_notes;
	}
	public String nickname;
	public String use_status;//是否使用，0使用，1未使用
	public String uid;//
	public String get_time;//"1451280215"
	public String notes;//发给你一个红包，备注
	public String id;//红包id
	public String dtree_type_name;//"邀请别人注册时送红包"
	public String dtree_type;//"6014",
	public String use_condition;//使用条件，订单满多少才能用，0时 无条件.
	public String tpl_status;// "1",应该是手机是否验证
	public String expire_time;//"2147483647"过期时间，时间戳，大于当前时间10年以上 表示无限期
	public String money;//红包金额
	public String tpl_notes;//红包模板备注
	
}
