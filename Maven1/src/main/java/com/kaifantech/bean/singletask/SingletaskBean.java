package com.kaifantech.bean.singletask;

import com.kaifantech.bean.base.BaseJsonBean;

public class SingletaskBean extends BaseJsonBean {
	private String id;
	private String taskname;
	private String tasktext;
	private String tasktype;
	private String allocoptype;
	private Integer allocid;
	private Integer environmentid;
	private Integer agvid;
	private Integer lapid;
	private int environment;
	private int issend;

	public int getEnvironment() {
		return environment;
	}

	public void setEnvironment(int environment) {
		this.environment = environment;
	}

	public String getAllocoptype() {
		return allocoptype;
	}

	public void setAllocoptype(String allocOpType) {
		this.allocoptype = allocOpType;
	}

	public Integer getAllocid() {
		return allocid;
	}

	public void setAllocid(Integer allocid) {
		this.allocid = allocid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskName) {
		this.taskname = taskName;
	}

	public String getTasktext() {
		return tasktext;
	}

	public void setTasktext(String tasktext) {
		this.tasktext = tasktext;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTaskType(String tasktype) {
		this.tasktype = tasktype;
	}

	public Integer getEnvironmentId() {
		return environmentid;
	}

	public void setEnvironmentId(Integer environmentId) {
		this.environmentid = environmentId;
	}

	public Integer getAgvid() {
		return agvid;
	}

	public void setAgvid(Integer agvid) {
		this.agvid = agvid;
	}

	public String toString() {
		return "编号" + id + ",名称：" + tasktext;
	}

	public int getIssend() {
		return issend;
	}

	public void setIssend(int issend) {
		this.issend = issend;
	}

	public Integer getLapid() {
		return lapid;
	}

	public void setLapid(Integer lapid) {
		this.lapid = lapid;
	}
}
