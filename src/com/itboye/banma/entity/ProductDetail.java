package com.itboye.banma.entity;

import java.util.List;
import java.util.Map;

public class ProductDetail {
	private int id;                     //商品ID
	private String product_code;		//商品编号
	private String name;				//商品名称
	private String main_img;			//商品主图
	private String buy_limit;				//限购
	private int template_id;			//运费模版id，目前都为 0 ，免邮
	private String loc_province;		//省份
	private String loc_city;			//城市
	private String loc_address;			//地区
	private String has_sku;				//是否多规格
	private Double ori_price;			//原价
	private Double price;				//现价
	private String is_time_limit;		//价格是否有时间限制
	private String price_begin_time;    //价格开始时间
	private String price_end_time;		//价格结束时间
	private int has_receipt;		//是否有发票 0否， 1是
	private int under_guaranty;		//是否保修 0否， 1是
	private int support_replace;	//是否支持退换货 0否， 1是
	private int quantity;				//商品库存
	private String cate_id;				//商品分类
	private String store_id;				//店铺ID
	private String properties;			//商品属性
	private String img;					//商品轮播图片，逗号隔开
	private String detail;					//商品详情
	private String sku_info;					//规格
	private Double weight;				//商品重量 （克）
	private String synopsis;			//商品简介
	private String source;			//产地
	private String img_post;			//
	private String img_post_bg;			//
	private String dt_origin_country;	//产地编码
	private String dt_goods_unit;		//计量单位编码
	private String dt_tariff_code;		//行邮税号
	private List<SkuStandard> sku_list;
	private List<SkuInfo> sku_info_list;   //商品型号
	
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

	public int getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
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

	public String getIs_time_limit() {
		return is_time_limit;
	}

	public void setIs_time_limit(String is_time_limit) {
		this.is_time_limit = is_time_limit;
	}

	public String getPrice_begin_time() {
		return price_begin_time;
	}

	public void setPrice_begin_time(String price_begin_time) {
		this.price_begin_time = price_begin_time;
	}

	public String getPrice_end_time() {
		return price_end_time;
	}

	public void setPrice_end_time(String price_end_time) {
		this.price_end_time = price_end_time;
	}

	public int getHas_receipt() {
		return has_receipt;
	}

	public void setHas_receipt(int has_receipt) {
		this.has_receipt = has_receipt;
	}

	public int getUnder_guaranty() {
		return under_guaranty;
	}

	public void setUnder_guaranty(int under_guaranty) {
		this.under_guaranty = under_guaranty;
	}

	public int getSupport_replace() {
		return support_replace;
	}

	public void setSupport_replace(int support_replace) {
		this.support_replace = support_replace;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSku_info() {
		return sku_info;
	}

	public void setSku_info(String sku_info) {
		this.sku_info = sku_info;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImg_post() {
		return img_post;
	}

	public void setImg_post(String img_post) {
		this.img_post = img_post;
	}

	public String getImg_post_bg() {
		return img_post_bg;
	}

	public void setImg_post_bg(String img_post_bg) {
		this.img_post_bg = img_post_bg;
	}

	public String getDt_origin_country() {
		return dt_origin_country;
	}

	public void setDt_origin_country(String dt_origin_country) {
		this.dt_origin_country = dt_origin_country;
	}

	public String getDt_goods_unit() {
		return dt_goods_unit;
	}

	public void setDt_goods_unit(String dt_goods_unit) {
		this.dt_goods_unit = dt_goods_unit;
	}

	public String getDt_tariff_code() {
		return dt_tariff_code;
	}

	public void setDt_tariff_code(String dt_tariff_code) {
		this.dt_tariff_code = dt_tariff_code;
	}

	public List<SkuStandard> getSku_list() {
		return sku_list;
	}

	public void setSku_list(List<SkuStandard> sku_list) {
		this.sku_list = sku_list;
	}

	public List<SkuInfo> getSku_info_list() {
		return sku_info_list;
	}

	public void setSku_info_list(List<SkuInfo> sku_info_list) {
		this.sku_info_list = sku_info_list;
	}

	final public class Sku_info{
		String id;
		String[] vid;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String[] getVid() {
			return vid;
		}
		public void setVid(String[] vid) {
			this.vid = vid;
		}
		
	}

}
