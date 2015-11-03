package com.itboye.banma.entity;

import java.util.List;

public class SkuInfo {
	private int id;
	private String name;
	private List<MapValue> value_list;

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

	public List<MapValue> getValue_list() {
		return value_list;
	}

	public void setValue_list(List<MapValue> value_list) {
		this.value_list = value_list;
	}

	final public class MapValue {
		private int id;
		private String name;
		private String sku_id;
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
		public String getSku_id() {
			return sku_id;
		}
		public void setSku_id(String sku_id) {
			this.sku_id = sku_id;
		}

	}

}
