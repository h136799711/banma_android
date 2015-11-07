package com.itboye.banma.entity;

public class ProductItem {
	private int id;                     //商品ID
	private String name;				//商品名称
	private String main_img;			//商品主图
	private Double ori_price;			//原价
	private Double price;				//现价
	private Double weight;				//商品重量 （克）
	private String img_post;
	private String img_post_bg;
	private String img;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}
