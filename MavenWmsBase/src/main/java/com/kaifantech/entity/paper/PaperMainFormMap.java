package com.kaifantech.entity.paper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.kaifantech.bean.base.IBaseJsonBean;
import com.kaifantech.init.sys.SystemInitiator;

public class PaperMainFormMap<T extends PaperDetailFormMap> extends FormMap implements IBaseJsonBean {
	private static final long serialVersionUID = 1L;

	public PaperMainFormMap() {
	}

	public PaperMainFormMap(FormMap formMap) {
		super(formMap);
	}

	@Override
	public String getJson() {
		return getStr("json");
	}

	@Override
	public void setJson(String json) {
		put("json", json);
	}

	public String getPaperid() {
		return getStr("paperid");
	}

	public String getCompany() {
		return getStr("company");
	}

	private List<T> detailList;

	public String getId() {
		return getStr("id");
	}

	public Integer getName() {
		return getInt("name");
	}

	public String getStatus() {
		return getStr("status");
	}

	public String getTotalqty() {
		return getStr("totalqty");
	}

	public String getTotallines() {
		return getStr("totallines");
	}

	public List<T> getDetailList() {
		return detailList;
	}

	public String getDetailsAlloc() {
		if (AppTool.isNull(detailList)) {
			return null;
		}
		List<String> allocs = new ArrayList<>();
		for (T detail : detailList) {
			allocs.add(detail.getUserdef1());
		}
		return StringUtils.join(allocs.toArray(), SystemInitiator.SPLIT);
	}

	public void setDetailList(List<T> detailList) {
		this.detailList = detailList;
	}
}
