package com.kaifantech.bean.wms.alloc;

import com.kaifantech.bean.base.BaseJsonBean;

public class AllocItemInfoBean extends BaseJsonBean {
	private Integer id;
	private String whid;
	private Integer siteId;
	private Integer skuId;
	private Double num;
	private String text;
	private String status;
	private String delflag;

	private Integer siteid;
	private String userdef1;

	public Integer getSiteid() {
		return siteid;
	}

	public void setSiteid(Integer siteid) {
		this.siteid = siteid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String toString() {
		return "text:" + text + ",status:" + status;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getWhid() {
		return whid;
	}

	public void setWhid(String whid) {
		this.whid = whid;
	}

	public String getUserdef1() {
		return userdef1;
	}

	public void setUserdef1(String userdef1) {
		this.userdef1 = userdef1;
	}
}
