package com.kaifantech.entity.paper;

import java.util.Comparator;

import com.calculatedfun.util.FormMap;

public class PaperDetailFormMap extends FormMap {
	private static final long serialVersionUID = 1L;

	public PaperDetailFormMap() {
	}

	public PaperDetailFormMap(FormMap formMap) {
		super(formMap);
	}

	public PaperDetailFormMap(FormMap formMap, boolean withId) {
		super(formMap, withId);
	}

	public static Comparator<PaperDetailFormMap> comparator = (h1, h2) -> {
		int i = h1.getSequence().compareTo(h2.getSequence());
		if (i != 0) {
			return i;
		}
		return h1.getUserdef1().compareTo(h2.getUserdef1());
	};

	public String getId() {
		return getStr("id");
	}

	public String getUserdef1() {
		return getStr("userdef1");
	}

	public String getUserdef3() {
		return getStr("userdef3");
	}

	public String getUserdef4() {
		return getStr("userdef4");
	}

	public Integer getSequence() {
		return getInt("sequence");
	}

	public String getPaperid() {
		return getStr("paperid");
	}

	public String getStatus() {
		return getStr("status");
	}

	public String getOpflag() {
		return getStr("opflag");
	}

	public String getItem() {
		return getStr("item");
	}
}
