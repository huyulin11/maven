package com.kaifantech.bean.iot.client;

import com.kaifantech.bean.base.BaseJsonBean;

public class IotClientBean extends BaseJsonBean implements Cloneable {
	private Integer id;
	private String ip;
	private String port;
	private String name;
	private int environment;
	private String devtype;

	public IotClientBean() {
	}

	public IotClientBean(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public IotClientBean(String ip, String port, String devtype) {
		this.ip = ip;
		this.port = port;
		this.devtype = devtype;
	}

	public int getEnvironment() {
		return environment;
	}

	public void setEnvironment(int environment) {
		this.environment = environment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDevtype() {
		return devtype;
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
