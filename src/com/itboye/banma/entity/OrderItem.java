package com.itboye.banma.entity;

/**
 * 订单内部item
 * @author Administrator
 *
 */
public class OrderItem {
	private int id;                     
	private String has_sku;
	private String name;
	private String img;
	private String price;
	private String ori_price;
	private String post_price;
	private String sku_id;
	private String sku_desc;
	private String count;
	private String order_code;
	private String createtime;
	private String p_id;
	private String pay_status;
	private String dt_tariff_code;
	private String goodsunit;
	private String dt_origin_country;
	private String weight;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHas_sku() {
		return has_sku;
	}
	public void setHas_sku(String has_sku) {
		this.has_sku = has_sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
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
	public String getPost_price() {
		return post_price;
	}
	public void setPost_price(String post_price) {
		this.post_price = post_price;
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
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getDt_tariff_code() {
		return dt_tariff_code;
	}
	public void setDt_tariff_code(String dt_tariff_code) {
		this.dt_tariff_code = dt_tariff_code;
	}
	public String getGoodsunit() {
		return goodsunit;
	}
	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
	}
	public String getDt_origin_country() {
		return dt_origin_country;
	}
	public void setDt_origin_country(String dt_origin_country) {
		this.dt_origin_country = dt_origin_country;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}
