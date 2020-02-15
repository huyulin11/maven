package com.kaifantech.bean.taskexe;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.base.BaseJsonTimeBean;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.init.sys.params.AppBaseParameters;
import com.kaifantech.init.sys.params.CacheKeys;
import com.kaifantech.util.constant.taskexe.ArrivedActType;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

public class TaskexeBean extends BaseJsonTimeBean {
	private String uuid;
	private String time;
	private String taskexesid;
	private Integer agvId;
	private String tasktype;
	private Integer lapId;
	private Integer skuId;
	private Integer allocid;
	private String opflag;
	private Integer autoFlag;

	private Integer tasksequence;
	private String addedinfo;
	private String addeddesc;

	public TaskexeBean() {
	}

	public boolean isShutdown() {
		boolean isShutdown = AppBaseParameters
				.flag(AppCache.worker().getOrInit(CacheKeys.taskShutdown(), getTaskKey(), "FALSE"));
		return isShutdown;
	}

	public void setShutdown() {
		AppCache.worker().hset(CacheKeys.taskShutdown(), getTaskKey(), "TRUE");
	}

	public String getArrivedActType() {
		if (AgvTaskType.RECEIPT.equals(getTasktype())) {
			return ArrivedActType.ALLOC_STOCK;
		} else if (AgvTaskType.SHIPMENT.equals(getTasktype())) {
			return ArrivedActType.ALLOC_GET;
		} else if (AgvTaskType.INVENTORY.equals(getTasktype())) {
			return ArrivedActType.ALLOC_SCAN;
		}
		return null;
	}

	public TaskexeBean(String taskexesid, Integer allocid, Integer agvId, Integer lapId, Integer skuId, int autoFlag) {
		this.taskexesid = taskexesid;
		this.allocid = allocid;
		this.agvId = agvId;
		this.lapId = lapId;
		this.skuId = skuId;
		this.autoFlag = autoFlag;
	}

	public TaskexeBean(String taskexesid, Integer agvId, int autoFlag) {
		this.taskexesid = taskexesid;
		this.agvId = agvId;
		this.autoFlag = autoFlag;
	}

	public TaskexeBean(JSONObject json, Integer agvId, int autoFlag) {
		this.setJson(json);
		this.agvId = agvId;
		this.autoFlag = autoFlag;
	}

	public TaskexeBean(String taskexesid, Integer agvId) {
		this.taskexesid = taskexesid;
		this.agvId = agvId;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTaskexesid(String taskexesid) {
		this.taskexesid = taskexesid;
	}

	public String getTaskexesid() {
		return taskexesid;
	}

	public TaskexeBean setOpflag(String opflag) {
		this.opflag = opflag;
		return this;
	}

	public String getOpflag() {
		return opflag;
	}

	public Integer getAgvId() {
		return agvId;
	}

	public void setAgvId(Integer agvId) {
		this.agvId = agvId;
	}

	public Integer getLapId() {
		return lapId;
	}

	public void setLapId(Integer lapId) {
		this.lapId = lapId;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getAllocid() {
		return allocid;
	}

	public void setAllocid(Integer allocid) {
		this.allocid = allocid;
	}

	public String getAddedinfo() {
		return addedinfo;
	}

	public void setAddedinfo(String addedinfo) {
		this.addedinfo = addedinfo;
	}

	public String getAddeddesc() {
		return addeddesc;
	}

	public void setAddeddesc(String addeddesc) {
		this.addeddesc = addeddesc;
	}

	public Integer getTasksequence() {
		return tasksequence;
	}

	public void setTasksequence(Integer sequence) {
		this.tasksequence = sequence;
	}

	private String taskKey;

	public String getTaskKey() {
		if (AppTool.isNull(taskKey)) {
			taskKey = "A-S-Q:" + getAgvId() + "-" + getTaskexesid() + "-" + getTasksequence();
			return taskKey;
		}
		return taskKey;
	}

	public String toString() {
		return agvId + "-" + taskexesid + "-" + tasksequence;
	}
}
