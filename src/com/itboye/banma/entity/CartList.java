package com.itboye.banma.entity;

public class CartList {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_desc() {
		return sku_desc;
	}
	public void setSku_desc(String sku_desc) {
		this.sku_desc = sku_desc;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOri_price() {
		return ori_price;
	}
	public void setOri_price(String ori_price) {
		this.ori_price = ori_price;
	}
	public String getPsku_id() {
		return psku_id;
	}
	public void setPsku_id(String psku_id) {
		this.psku_id = psku_id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}
	public String id;//购物车id
	public String uid;
	public String create_time;//创建时间
	public String update_time;
	public String store_id;//店铺id
	public String p_id;//商品id
	public String sku_id;//商品sku_id
	public String sku_desc ;//商品的文字描述
	public String icon_url;//商品图片地址
	public String count;
	public String name;
	public String express;//运费
	public String price;//现价
	public String ori_price;
	public String psku_id;//商品规格id
	public String weight;//重量
	public String taxrate;//税率
}
