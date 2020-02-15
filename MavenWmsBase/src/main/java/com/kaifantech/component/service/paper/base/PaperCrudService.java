package com.kaifantech.component.service.paper.base;

import java.util.List;

import com.calculatedfun.util.AppSequence;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.init.sys.dao.BaseSqlJoint;
import com.kaifantech.util.log.AppFileLogger;

public abstract class PaperCrudService<TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>>
		extends PaperCheckService<TD, TM> {
	public AppMsg doEditEntity(FormMap formMap, String paperid) throws Exception {
		return doSave(formMap, paperid, false);
	}

	public AppMsg doAddItem(FormMap formMap, String paperid) throws Exception {
		AppMsg appMsg = doAddDetailItem(formMap, paperid);
		if (appMsg.getCode() < 0) {
			return appMsg;
		}
		appMsg = doSaveMainItem(formMap, paperid);
		return appMsg;
	}

	public TM getSearchParam() {
		return null;
	}

	public AppMsg doAddDetailItem(FormMap formMap, String paperid) throws Exception {
		TD detailFormMap = createDetailObj(formMap);
		detailFormMap.set("paperid", paperid);
		detailFormMap.set("sequence", 1);
		initFormMap(detailFormMap);
		getDetailMapper().addEntity(detailFormMap);
		return AppMsg.success("保存成功！");
	}

	public AppMsg doSaveMainItem(FormMap formMap, String paperid) throws Exception {
		TM main = createMainObj(null);
		String condition = " where paperid=" + BaseSqlJoint.s(paperid);
		main.set(BaseSqlConstants.WHERE, condition);
		TM mainData = findByPaperid(paperid, true);
		int totalqty = mainData.getInt("totalqty");
		int totallines = mainData.getInt("totallines");
		Integer itemcount = formMap.getInt("itemcount");
		TM mainFormMap = createMainObj(null);
		mainFormMap.set("paperid", paperid);
		mainFormMap.set("totallines", totallines + 1);
		mainFormMap.set("totalqty", totalqty + (AppTool.isNull(itemcount) ? 1 : itemcount));
		getMainMapper().editEntity(mainFormMap);

		return AppMsg.success("保存成功！");
	}

	public AppMsg doAddEntity(FormMap formMap) throws Exception {
		String paperid = getPaperid();
		return doAddEntity(formMap, paperid);
	}

	public AppMsg doEditSeqByCompany(String company, int seq) throws Exception {
		return null;
	}

	public AppMsg doEditSeqBySu(String company, String userdef4, String item, int seq) throws Exception {
		return null;
	}

	public AppMsg doAddEntity(FormMap formMap, String paperid) throws Exception {
		AppMsg appMsg = doSave(formMap, paperid, true);
		if (appMsg.isSuccess()) {
			appMsg.setObject(paperid);
		}
		return appMsg;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AppMsg doSave(FormMap formMap, String paperid, boolean addFlag) throws Exception {
		TM mainFormMap = createMainObj(formMap);
		Object targetPlace = formMap.get("targetPlace");
		mainFormMap.set("paperid", paperid);
		if (!AppTool.isNull(targetPlace)) {
			mainFormMap.set("name", targetPlace);
		}

		List<FormMap> valueList = formMap.getList();
		if (valueList == null) {
			if (formMap instanceof PaperMainFormMap)
				valueList = ((PaperMainFormMap) formMap).getDetailList();
		}
		int totalqty = 0;
		int totallines = 0;

		if (!AppTool.isNull(valueList)) {
			totallines = valueList.size();
			try {
				valueList.sort((a1, a2) -> {
					if (AppTool.isAnyNull(a1.get("allocItem"), a2.get("allocItem"))) {
						return 0;
					}
					return a1.get("allocItem").toString().compareTo(a2.get("allocItem").toString());
				});
			} catch (Exception e) {
				AppFileLogger.warning("接口数据排序失败，沿用老的排序规则");
			}

			for (FormMap allocItem : valueList) {
				if (AppTool.isAnyNull(allocItem.get("allocItem"))) {
					continue;
				}
				AppMsg msg = allocCheckService.checkAllocName(allocItem.get("allocItem").toString(),
						wmsPaperService.getTasktype(mainFormMap));
				if (msg.getCode() < 0) {
					return msg;
				}
			}

			if (!addFlag) {
				TD detailFormMap = createDetailObj(null);
				detailFormMap.set("paperid", paperid);
				getDetailMapper().deleteByNames(detailFormMap);
			}
			AppSequence seq = getSequence();
			for (FormMap tmpFormMap : valueList) {
				TD detailFormMap = createDetailObj(tmpFormMap);
				detailFormMap.set("paperid", paperid);
				detailFormMap.set("sequence", seq.getNextSeq());
				initFormMap(detailFormMap);
				totalqty += Integer.parseInt(detailFormMap.get("itemcount").toString());
				getDetailMapper().addEntity(detailFormMap);
			}
		}

		mainFormMap.set("totallines", totallines);
		mainFormMap.set("totalqty", totalqty);
		initFormMap(mainFormMap);
		if (addFlag) {
			getMainMapper().addEntity(mainFormMap);
		} else {
			getMainMapper().editEntity(mainFormMap);
		}

		return AppMsg.success("保存成功！");
	}

	public AppSequence getSequence() {
		return new AppSequence();
	}

	protected abstract void initFormMap(TM formMap);

	protected abstract void initFormMap(TD detailFormMap);

	public abstract String getPaperid();
}