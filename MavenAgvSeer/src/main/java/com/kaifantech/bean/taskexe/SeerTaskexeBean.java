package com.kaifantech.bean.taskexe;

import java.util.ArrayList;
import java.util.List;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.tasksite.TaskSiteInfoBean;

public class SeerTaskexeBean extends TaskexeBean {
	public SeerAgvMsgTaskBean msg;
	public List<TaskexeDetailBean> detailList;
	public IotClientBean agv;

	public SeerTaskexeBean(TaskexeBean taskexeBean) {
		this.setAddeddesc(taskexeBean.getAddeddesc());
		this.setUuid(taskexeBean.getUuid());
		this.setTime(taskexeBean.getTime());
		this.setTaskexesid(taskexeBean.getTaskexesid());
		this.setOpflag(taskexeBean.getOpflag());
		this.setAgvId(taskexeBean.getAgvId());
		this.setLapId(taskexeBean.getLapId());
		this.setTasktype(taskexeBean.getTasktype());
		this.setAutoFlag(taskexeBean.getAutoFlag());
		this.setSkuId(taskexeBean.getSkuId());
		this.setAllocid(taskexeBean.getAllocid());
		this.setAddedinfo(taskexeBean.getAddedinfo());
		this.setAddeddesc(taskexeBean.getAddeddesc());
		this.setTasksequence(taskexeBean.getTasksequence());
	}

	public static SeerTaskexeBean get(TaskexeBean taskexeBean, List<TaskexeDetailBean> list) {
		if (AppTool.isNull(list)) {
			return null;
		}
		SeerTaskexeBean bean = new SeerTaskexeBean(taskexeBean);
		bean.detailList = list;
		return bean;
	}

	public AgvInfoBean agvBean = null;
	public TaskexeDetailBean nextDetail = null;
	public TaskSiteInfoBean nextSite = null;
	public TaskexeDetailBean currentDetail = null;
	public TaskSiteInfoBean currentSite = null;
	public double currentYaxis = 0;
	public double nextYaxis = 0;
	public String currentArea = "D";
	public String nextArea = "";
	public List<Double> nextYaxisList = new ArrayList<>();

	public String toString() {
		return "(" + "AGV:" + getAgvId() + ",CURRENT:" + currentYaxis + "->" + "NEXT:" + nextYaxis + ")";
	}
}
