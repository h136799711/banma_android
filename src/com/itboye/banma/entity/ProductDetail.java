package com.itboye.banma.entity;

import com.itboye.banma.R.string;

public class ProductDetail {
	private int id;                     //商品ID
	private String product_code;		//商品编号
	private int uid;					//用户ID
	private String name;				//商品名称
	private String main_img;			//商品主图
	private Double weight;				//商品重量 （克）
	private String buy_limit;				//限购
	private String delivery_type;			//运费类型(  )
	private String template_id;			//邮费模板ID
	private String express;				//统一快递运费
	private int attrext_ispostfree;		//是否包邮 0否， 1是
	private int attrext_ishasreceipt;	//是否开发票 0否， 1是
	private int attrext_isunderguaranty;	//是否保修 0否， 1是
	private int attrext_issupportreplace;	//是否支持退换货 0否， 1是
	private String loc_country;			//国家
	private String loc_province;		//省份
	private String loc_city;			//城市
	private String loc_address;			//地区
	private String has_sku;				//是否多规格
	private Double ori_price;			//原价
	private Double price;				//现价
	private int quantity;				//商品库存
	private int cate_id;				//商品分类
	private String createtime;			//创建时间
	private String updatetime;			//更新时间
	private int onshelf;				//是否上架0否 1是
	private String status;					//商品状态
	private String storeid;				//店铺ID
	private String properties;			//商品属性
	private String img;					//商品图片
	//private Object sku_info;			//
	private String[] detail;					//商品详情
	
	private String icon_url;				//多规格颜色对应的图片

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMain_img() {
		return main_img;
	}
	public void setMain_img(String main_img) {
		this.main_img = main_img;
	}
	public String getBuy_limit() {
		return buy_limit;
	}
	public void setBuy_limit(String buy_limit) {
		this.buy_limit = buy_limit;
	}
	public String getDelivery_type() {
		return delivery_type;
	}
	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public int getAttrext_ispostfree() {
		return attrext_ispostfree;
	}
	public void setAttrext_ispostfree(int attrext_ispostfree) {
		this.attrext_ispostfree = attrext_ispostfree;
	}
	public int getAttrext_ishasreceipt() {
		return attrext_ishasreceipt;
	}
	public void setAttrext_ishasreceipt(int attrext_ishasreceipt) {
		this.attrext_ishasreceipt = attrext_ishasreceipt;
	}
	public int getAttrext_isunderguaranty() {
		return attrext_isunderguaranty;
	}
	public void setAttrext_isunderguaranty(int attrext_isunderguaranty) {
		this.attrext_isunderguaranty = attrext_isunderguaranty;
	}
	public int getAttrext_issupportreplace() {
		return attrext_issupportreplace;
	}
	public void setAttrext_issupportreplace(int attrext_issupportreplace) {
		this.attrext_issupportreplace = attrext_issupportreplace;
	}
	public String getLoc_country() {
		return loc_country;
	}
	public void setLoc_country(String loc_country) {
		this.loc_country = loc_country;
	}
	public String getLoc_province() {
		return loc_province;
	}
	public void setLoc_province(String loc_province) {
		this.loc_province = loc_province;
	}
	public String getLoc_city() {
		return loc_city;
	}
	public void setLoc_city(String loc_city) {
		this.loc_city = loc_city;
	}
	public String getLoc_address() {
		return loc_address;
	}
	public void setLoc_address(String loc_address) {
		this.loc_address = loc_address;
	}
	public String getHas_sku() {
		return has_sku;
	}
	public void setHas_sku(String has_sku) {
		this.has_sku = has_sku;
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
	public int getCate_id() {
		return cate_id;
	}
	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
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
	public int getOnshelf() {
		return onshelf;
	}
	public void setOnshelf(int onshelf) {
		this.onshelf = onshelf;
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
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String[] getDetail() {
		return detail;
	}
	public void setDetail(String[] detail) {
		this.detail = detail;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	@Override
	public String toString() {
		return "ProductDetail [id=" + id + ", product_code=" + product_code
				+ ", uid=" + uid + ", name=" + name + ", main_img=" + main_img
				+ ", buy_limit=" + buy_limit + ", delivery_type="
				+ delivery_type + ", template_id=" + template_id + ", express="
				+ express + ", attrext_ispostfree=" + attrext_ispostfree
				+ ", attrext_ishasreceipt=" + attrext_ishasreceipt
				+ ", attrext_isunderguaranty=" + attrext_isunderguaranty
				+ ", attrext_issupportreplace=" + attrext_issupportreplace
				+ ", loc_country=" + loc_country + ", loc_province="
				+ loc_province + ", loc_city=" + loc_city + ", loc_address="
				+ loc_address + ", has_sku=" + has_sku + ", ori_price="
				+ ori_price + ", price=" + price + ", quantity=" + quantity
				+ ", cate_id=" + cate_id + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", onshelf=" + onshelf
				+ ", status=" + status + ", storeid=" + storeid
				+ ", properties=" + properties + ", img=" + img + ", detail="
				+ detail + ", weight=" + weight + ", icon_url=" + icon_url
				+ "]";
	}
	
	

}
