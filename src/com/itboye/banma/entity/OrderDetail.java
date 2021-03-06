package com.itboye.banma.entity;

import java.util.List;

import android.widget.ListView;

/**
 * 订单详情实体类
 * @author Administrator
 *
 */
public class OrderDetail {

	private int id;                     
	private String order_code;		//
	private String tax_amount;		//税收
	private String post_price;		//运费
	private String discount_money;	//优惠金额
	private String createtime;
	private String updatetime;
	private String uid;
	private String price;
	private String status;
	private String pay_status;
	private String order_status;
	private String wxno;
	private String contactname;
	private String country;
	private String city;
	private String province;
	private String detailinfo;
	private String area;
	private String mobile;
	private String username;
	private String store_name;		//店铺名称
	private String store_desc;		//店铺描述
	private String store_banner ;		//店铺店招
	private String store_logo_url;		//店铺logo图片地址
	private String service_phone;		//店铺联系电话
	
	private List<OrderItem> items;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getPost_price() {
		return post_price;
	}
	public void setPost_price(String post_price) {
		this.post_price = post_price;
	}
	public String getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(String discount_money) {
		this.discount_money = discount_money;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getWxno() {
		return wxno;
	}
	public void setWxno(String wxno) {
		this.wxno = wxno;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDetailinfo() {
		return detailinfo;
	}
	public void setDetailinfo(String detailinfo) {
		this.detailinfo = detailinfo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public String getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(String tax_amount) {
		this.tax_amount = tax_amount;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getStore_desc() {
		return store_desc;
	}
	public void setStore_desc(String store_desc) {
		this.store_desc = store_desc;
	}
	public String getStore_banner() {
		return store_banner;
	}
	public void setStore_banner(String store_banner) {
		this.store_banner = store_banner;
	}
	public String getStore_logo_url() {
		return store_logo_url;
	}
	public void setStore_logo_url(String store_logo_url) {
		this.store_logo_url = store_logo_url;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	
	
}
