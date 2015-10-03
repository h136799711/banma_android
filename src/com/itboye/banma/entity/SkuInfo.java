package com.itboye.banma.entity;

import java.util.Map;

public class SkuInfo {
	private String name;
	private String key;
	private Map<String, MapValue> value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, MapValue> getValue() {
		return value;
	}

	public void setValue(Map<String, MapValue> value) {
		this.value = value;
	}
	

	public SkuInfo(String name, String key, Map<String, MapValue> daMap) {
		super();
		this.name = name;
		this.key = key;
		this.value = daMap;
	}

	final public class MapValue {
		private String value;
		private String key;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public MapValue(String value, String key) {
			super();
			this.value = value;
			this.key = key;
		}
		
	}
	
}
