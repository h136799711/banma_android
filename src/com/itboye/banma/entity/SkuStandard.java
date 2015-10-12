package com.itboye.banma.entity;

import java.io.Serializable;

public class SkuStandard implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String sku_id;             //规格ID
	private Double ori_price;			//原价
	private Double price;				//现价
	private int quantity;				//商品库存
	private String product_code;		//商品编号
	private String createtime;
	private String product_id;
	private String icon_url;
	private String sku;				//规格
	private String num = "1";         //购买数量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public Double getOri_price() {
		return ori_price;
	}
	public void setOri_price(Double ori_price) {
		this.ori_price = ori_price;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
}
