package com.kaifantech.bean.tasksite;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.base.BaseJsonBean;
import com.kaifantech.component.cache.worker.AppBeanFactory;
import com.kaifantech.component.service.tasksite.info.AcsTaskSiteInfoService;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.constant.taskexe.TaskSiteType;

public class TaskSiteInfoBean extends BaseJsonBean implements Cloneable {
	private Integer id;
	private String sitename;
	private String sitecode;
	private String sitetype;
	private Integer line;
	private Integer keyId;

	private TaskSiteInfoBean pre;

	private Map<String, TaskSiteInfoBean> relativeSiteMap = new TreeMap<>();

	public String getSitetype() {
		return sitetype;
	}

	public String getIndex() {
		return "";
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public boolean isAllocSite() {
		return TaskSiteType.ALLOC.equals(getSitetype());
	}

	public boolean isWindowSite() {
		return TaskSiteType.WINDOW.equals(getSitetype());
	}

	public boolean isInitSite() {
		return TaskSiteType.INIT.equals(getSitetype());
	}

	public boolean isChargeSite() {
		return TaskSiteType.CHARGE.equals(getSitetype());
	}

	public boolean isOutSite() {
		return TaskSiteType.OUT.equals(getSitetype());
	}

	public boolean isOutOrWindowSite() {
		return TaskSiteType.OUT.equals(getSitetype()) || TaskSiteType.WINDOW.equals(getSitetype());
	}

	public boolean isBackSite() {
		return TaskSiteType.BACK.equals(getSitetype());
	}

	public boolean isBaySite() {
		return TaskSiteType.BAY.equals(getSitetype());
	}

	public boolean isAutodoorSite() {
		return TaskSiteType.AUTODOOR.equals(getSitetype());
	}

	public boolean isLiftSite() {
		return TaskSiteType.LIFT.equals(getSitetype());
	}

	public boolean isLightSite() {
		return TaskSiteType.LIGHT.equals(getSitetype());
	}

	public boolean isHookUpSite() {
		return TaskSiteType.HOOK_UP.equals(getSitetype());
	}

	public boolean isHookDownSite() {
		return TaskSiteType.HOOK_DOWN.equals(getSitetype());
	}

	public boolean isRevolveSite() {
		return TaskSiteType.REVOLVE.equals(getSitetype());
	}

	public boolean isWaitSite() {
		return TaskSiteType.WAIT.equals(getSitetype());
	}

	public boolean isPISite() {
		return TaskSiteType.PI.equals(getSitetype());
	}

	public boolean isInitOrBaySite() {
		return TaskSiteType.INIT.equals(getSitetype()) || TaskSiteType.BAY.equals(getSitetype());
	}

	public static boolean isCorner(TaskSiteInfoBean current, TaskSiteInfoBean next) {
		if (AppTool.isAllNull(current, next, current.getSitetype(), next.getSitetype())) {
			return true;
		}
		if (current.getSitetype().equals(next.getSitetype())) {
			return false;
		}
		if (current.isOutOrWindowSite() && next.isOutOrWindowSite()) {
			return false;
		}
		return true;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public boolean isGotoRight(Integer siteid) {
		for (Entry<String, TaskSiteInfoBean> entry : relativeSiteMap.entrySet()) {
			if (siteid.equals(entry.getValue().getId()) && entry.getKey().startsWith("RIGHT")) {
				return true;
			}
		}
		return false;
	}

	public boolean isGotoLeft(Integer siteid) {
		for (Entry<String, TaskSiteInfoBean> entry : relativeSiteMap.entrySet()) {
			if (siteid.equals(entry.getValue().getId()) && entry.getKey().startsWith("LEFT")) {
				return true;
			}
		}
		return false;
	}

	public Map<String, TaskSiteInfoBean> getNexts() {
		return relativeSiteMap;
	}

	public void setRelative(String key, TaskSiteInfoBean relativedSite) {
		this.relativeSiteMap.put(key, relativedSite);
	}

	public TaskSiteInfoBean getPre() {
		return pre;
	}

	public void setPre(TaskSiteInfoBean pre) {
		this.pre = pre;
	}

	public String toString() {
		String preStr = "";
		return "" + id + preStr;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static TaskSiteInfoBean getBean(Integer siteid) {
		if (AppTool.isNull(taskSiteInfoService)) {
			taskSiteInfoService = AppBeanFactory
					.get(UdfQualifier.DEFAULT_TASK_SITE_INFO_SERVICE);
		}
		return taskSiteInfoService.getBean(siteid);
	}

	protected static AcsTaskSiteInfoService taskSiteInfoService;
}
