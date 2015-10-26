package com.itboye.banma.entity;

import java.util.List;

/**
 * 订单查询的实体类
 * @author Administrator
 *
 */
public class OrderDetailListItem {
	private String taxamount;
	private String goodsamount;
	private String id;
	private String order_code;
	private String post_price;
	private String discount_money;
	private String createtime;
	private String updatetime;
	private String uid;
	private String price;
	private String status;
	private String storeid;
	private String pay_status;
	private String order_status;
	private String store_name;
	private String store_desc;
	private String store_banner;
	private String store_logo;
	private String service_phone;
	private List<OrderItem> _items;
	public String getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(String taxamount) {
		this.taxamount = taxamount;
	}
	public String getGoodsamount() {
		return goodsamount;
	}
	public void setGoodsamount(String goodsamount) {
		this.goodsamount = goodsamount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
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
	public String getStore_logo() {
		return store_logo;
	}
	public void setStore_logo(String store_logo) {
		this.store_logo = store_logo;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public List<OrderItem> get_items() {
		return _items;
	}
	public void set_items(List<OrderItem> _items) {
		this._items = _items;
	}
	
}
