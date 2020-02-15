package com.kaifantech.bean.taskexe;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.base.BaseJsonBean;
import com.kaifantech.bean.tasksite.TaskSiteInfoBean;
import com.kaifantech.util.constant.taskexe.WmsDetailOpFlag;

public class TaskexeDetailBean extends BaseJsonBean {
	private Integer id;
	private String taskexesid;
	private Integer siteid;
	private String sitecode;
	private Integer detailsequence;
	private Integer tasksequence;
	private double distancetozero;
	private String arrivedact;
	private String opflag;
	private String addedinfo;
	private String addeddesc;
	private String delflag;

	private TaskSiteInfoBean siteInfo;

	private TaskexeDetailBean past;
	private TaskexeDetailBean next;

	public static final String ADDED_INFO = "ADDED_INFO";
	public static final String ADDED_DESC = "ADDED_DESC";
	public static final String TURN_SIDE = "TURN_SIDE";

	public boolean matchThisSite(Integer siteId) {
		return siteId.equals(getSiteid());
	}

	public boolean matchPastSite(Integer siteId) {
		if (AppTool.isNull(past)) {
			return false;
		}
		return past.matchThisSite(siteId);
	}

	public boolean matchNextSite(Integer siteId) {
		if (AppTool.isNull(next)) {
			return false;
		}
		return next.matchThisSite(siteId);
	}

	public boolean matchRelevantSite(Integer siteId) {
		return matchThisSite(siteId) || matchPastSite(siteId) || matchNextSite(siteId);
	}

	public boolean isNew() {
		return WmsDetailOpFlag.NEW.equals(getOpflag());
	}

	public boolean isSend() {
		return WmsDetailOpFlag.SEND.equals(getOpflag());
	}

	public boolean isStart() {
		return WmsDetailOpFlag.START.equals(getOpflag());
	}

	public boolean isExeing() {
		return WmsDetailOpFlag.EXEING.equals(getOpflag());
	}

	public boolean isReady() {
		return WmsDetailOpFlag.READY.equals(getOpflag());
	}

	public boolean isNoticedPartner() {
		return WmsDetailOpFlag.NOTICED_PARTNER.equals(getOpflag());
	}

	public boolean isLiftClose() {
		return WmsDetailOpFlag.LIFT_CLOSE.equals(getOpflag());
	}

	public boolean isRevolve() {
		return WmsDetailOpFlag.REVOLVE.equals(getOpflag());
	}

	public boolean isOver() {
		return WmsDetailOpFlag.OVER.equals(getOpflag());
	}

	public TaskexeDetailBean getPast() {
		return past;
	}

	public void setPast(TaskexeDetailBean past) {
		this.past = past;
	}

	public TaskexeDetailBean getNext() {
		return next;
	}

	public void setNext(TaskexeDetailBean next) {
		this.next = next;
	}

	public TaskexeDetailBean() {
	}

	public TaskexeDetailBean(String taskexesid, Integer tasksequence) {
		this.taskexesid = taskexesid;
		this.tasksequence = tasksequence;
	}

	public TaskexeDetailBean(String taskexesid, TaskSiteInfoBean tasksiteBean) {
		this.taskexesid = taskexesid;
		this.sitecode = tasksiteBean.getSitecode();
		this.siteid = tasksiteBean.getId();
	}

	public String getTaskexesid() {
		return taskexesid;
	}

	public void setTaskexesid(String taskexesid) {
		this.taskexesid = taskexesid;
	}

	public Integer getDetailsequence() {
		return detailsequence;
	}

	public TaskexeDetailBean setDetailsequence(Integer sequence) {
		this.detailsequence = sequence;
		return this;
	}

	public String getArrivedact() {
		return arrivedact;
	}

	public void setArrivedact(String arrivedact) {
		this.arrivedact = arrivedact;
	}

	public String getOpflag() {
		return opflag;
	}

	public TaskexeDetailBean setOpflag(String opflag) {
		this.opflag = opflag;
		return this;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public boolean sameSite(TaskexeDetailBean obj) {
		if (!AppTool.isNull(taskexesid) && !AppTool.isNull(sitecode) && !AppTool.isNull(arrivedact)
				&& !AppTool.isNull(obj.getTaskexesid()) && !AppTool.isNull(obj.getSitecode())
				&& !AppTool.isNull(obj.getArrivedact()) && taskexesid.equals(obj.getTaskexesid())
				&& sitecode.equals(obj.getSitecode()) && arrivedact.equals(obj.getArrivedact())) {
			return true;
		}
		return false;
	}

	public Object clone() {
		TaskexeDetailBean o = null;
		try {
			o = (TaskexeDetailBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public String getAddedinfo() {
		return addedinfo;
	}

	public void setAddedinfo(String addedinfo) {
		this.addedinfo = addedinfo;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public Integer getSiteid() {
		return siteid;
	}

	public TaskexeDetailBean setSiteid(Integer siteid) {
		this.siteid = siteid;
		return this;
	}

	public static double getDistanceOf(TaskexeDetailBean current, TaskexeDetailBean next) {
		if (AppTool.isAnyNull(current, next)) {
			return 0;
		}
		if (AppTool.isAnyNull(current.getDistancetozero(), next.getDistancetozero())) {
			return 0;
		}
		return Math.abs(current.getDistancetozero() - next.getDistancetozero());
	}

	public String toString() {
		return "taskexesid:" + AppTool.v(taskexesid) + "," + "siteid:" + AppTool.v(siteid) + "," + "sitecode:"
				+ AppTool.v(sitecode) + "," + "sequence:" + AppTool.v(detailsequence) + "," + "arrivedact:"
				+ AppTool.v(arrivedact) + "," + "opflag:" + AppTool.v(opflag) + "," + "addedinfo:"
				+ AppTool.v(addedinfo);
	}

	public String getAddeddesc() {
		return addeddesc;
	}

	public void setAddeddesc(String addeddesc) {
		this.addeddesc = addeddesc;
	}

	public double getDistancetozero() {
		return distancetozero;
	}

	public void setDistancetozero(double distancetozero) {
		this.distancetozero = distancetozero;
	}

	public Integer getTasksequence() {
		return tasksequence;
	}

	public TaskexeDetailBean setTasksequence(Integer tasksequence) {
		this.tasksequence = tasksequence;
		return this;
	}

	public TaskSiteInfoBean site() {
		if (AppTool.isNull(siteInfo)) {
			this.siteInfo = TaskSiteInfoBean.getBean(siteid);
		}
		return siteInfo;
	}

	public void setSiteInfo(TaskSiteInfoBean siteInfo) {
		this.siteInfo = siteInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return getTaskexesid() + "-" + getTasksequence() + "-" + getDetailsequence() + "-" + getSiteid();
	}
}
