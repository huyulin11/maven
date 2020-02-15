package com.kaifantech.bean.base;

public class BaseJsonBean implements IBaseJsonBean {
	private String json;

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}
}
