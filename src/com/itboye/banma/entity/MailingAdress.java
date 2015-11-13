package com.itboye.banma.entity;

import java.io.Serializable;

import android.R.integer;

public class MailingAdress  implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;                 //地址ID
	int uid;				//用户ID
	String id_card;       	//身份证
	String country;       	//国家
	String province;       	//省份
	String city;       		//城市
	String area;       		//区
	String detailinfo;  	//详细地址
	String contactname;  	//联系人姓名
	String mobile;       	//联系电话
	String wxno;       		//暂时可以不用管
	String postal_code; 	//邮政编码
	String cityid;			//城市编号
	String provinceid;		//省份编号
	String areaid;			//区编号
	int default_address;	//是否为默认地址，0否 1是
	
	
	public MailingAdress(){
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getId_card() {
		return id_card;
	}


	public void setId_card(String id_card) {
		this.id_card = id_card;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getDetailinfo() {
		return detailinfo;
	}


	public void setDetailinfo(String detailinfo) {
		this.detailinfo = detailinfo;
	}


	public String getContactname() {
		return contactname;
	}


	public void setContactname(String contactname) {
		this.contactname = contactname;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getWxno() {
		return wxno;
	}


	public void setWxno(String wxno) {
		this.wxno = wxno;
	}


	public String getPostal_code() {
		return postal_code;
	}


	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}


	public String getCityid() {
		return cityid;
	}


	public void setCityid(String cityid) {
		this.cityid = cityid;
	}


	public String getProvinceid() {
		return provinceid;
	}


	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}


	public String getAreaid() {
		return areaid;
	}


	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}


	public int getDefault_address() {
		return default_address;
	}


	public void setDefault_address(int default_address) {
		this.default_address = default_address;
	}
	
}
