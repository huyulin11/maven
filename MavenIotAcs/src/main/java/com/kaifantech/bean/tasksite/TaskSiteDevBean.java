package com.kaifantech.bean.tasksite;

import com.kaifantech.bean.base.BaseJsonBean;

public class TaskSiteDevBean extends BaseJsonBean implements Cloneable {
	private Integer siteid;
	private Integer devid;

	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	public Integer getDevid() {
		return devid;
	}

	public void setDevid(Integer devid) {
		this.devid = devid;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
