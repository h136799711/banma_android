package com.itboye.banma.entity;

import java.io.Serializable;

public class MailingAdress  implements Serializable {
	private static final long serialVersionUID = 1L;
	String country;       	//国家
	String province;       	//省份
	String city;       		//城市
	String area;       		//区
	String detailinfo;  	//详细地址
	String contactname;  	//联系人姓名
	String mobile;       	//联系电话
	String wxno;       		//暂时可以不用管
	String postal_code; 	//邮政编码
	
	public MailingAdress(){
		
	}
	
	public MailingAdress(String country, String province, String city,
			String area, String detailinfo, String contactname, String mobile,
			String wxno, String postal_code) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.area = area;
		this.detailinfo = detailinfo;
		this.contactname = contactname;
		this.mobile = mobile;
		this.wxno = wxno;
		this.postal_code = postal_code;
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
	
	
	
}
