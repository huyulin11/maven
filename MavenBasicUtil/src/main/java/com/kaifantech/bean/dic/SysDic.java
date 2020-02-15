package com.kaifantech.bean.dic;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.base.IBaseJsonBean;

public class SysDic implements Comparable<SysDic>, IBaseJsonBean {
	private Object key;
	private Object value;
	private Object alias;
	private Object valueUs;
	private Object type;
	private Object defau;
	private Object isNormalUser;
	private String json;

	public SysDic() {
	}

	public SysDic(Object type) {
		this.type = type;
	}

	public SysDic(Object type, Object key) {
		this.type = type;
		this.key = key;
	}

	public SysDic(Object type, Object key, Object isNormalUser) {
		this.type = type;
		this.key = key;
		this.isNormalUser = isNormalUser;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValueUs() {
		return valueUs;
	}

	public void setValueUs(Object valueUs) {
		this.valueUs = valueUs;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	/** 按照key排序 */
	public int compareTo(SysDic o) {
		if (AppTool.isNull(this.key)) {
			return -1;
		}
		if (AppTool.isNull(o.key)) {
			return 1;
		}
		if (Integer.parseInt(this.getKey().toString()) > Integer.parseInt(o.getKey().toString())) {
			return 1;
		}
		if (Integer.parseInt(this.getKey().toString()) < Integer.parseInt(o.getKey().toString())) {
			return -1;
		}
		return 0;
	}

	public Object getDefau() {
		return defau;
	}

	public void setDefau(Object defau) {
		this.defau = defau;
	}

	public Object getIsNormalUser() {
		return isNormalUser;
	}

	public void setIsNormalUser(Object isNormalUser) {
		this.isNormalUser = isNormalUser;
	}

	public Object getAlias() {
		return alias;
	}

	public void setAlias(Object alias) {
		this.alias = alias;
	}

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}

}
