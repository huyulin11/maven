package com.kaifantech.component.controller.paper.base;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.AppXlsx;
import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.FormMapTableUtil;
import com.calculatedfun.util.callback.CallBackValue;
import com.calculatedfun.util.msg.AppMsg;
import com.google.common.collect.ImmutableMap;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.component.controller.base.form.BaseQueryController;
import com.kaifantech.component.service.paper.base.PaperCrudService;
import com.kaifantech.component.service.paper.base.PaperService;
import com.kaifantech.component.service.sysdic.SysDicService;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.init.sys.dao.BaseSqlJoint;
import com.kaifantech.util.thread.ThreadTool;

public abstract class PaperMainController<TM extends PaperMainFormMap<TD>, TD extends PaperDetailFormMap>
		extends BaseQueryController<TM> {
	@Inject
	protected PaperService wmsPaperService;

	@Inject
	protected SysDicService sysDicService;

	@SuppressWarnings({ "unchecked" })
	private PaperCrudService<TD, TM> getService() {
		return (PaperCrudService<TD, TM>) wmsPaperService.getService(this);
	}

	@ResponseBody
	@RequestMapping("find")
	public PageView find(Integer pageNow, Integer pageSize) throws Exception {
		TM formMap = getFormMap(false);
		formMap = toFormMap(formMap, pageNow, pageSize);
		pageView.setRecords(getMapper().findByPage(formMap));
		return pageView;
	}

	@ResponseBody
	@RequestMapping("findWithDetail")
	public PageView findWithDetail(Integer pageNow, Integer pageSize) throws Exception {
		TM formMap = getFormMap(false);
		formMap = toFormMap(formMap, pageNow, pageSize);
		formMap = findWithDetail(formMap);
		List<TM> list = getMapper().findByPage(formMap);
		pageView.setRecords(list);
		return pageView;
	}

	public TM findWithDetail(TM formMap) {
		String where = " m.paperid=d.paperid AND m.delflag!='1' AND d.delflag!='1' ";

		Object type = formMap.get("type");
		if ("PICKED_NORMAL".equals(type))
			where += " AND m.warehouse!=" + BaseDao.s(2) + " ";

		Object detailstatus = formMap.get("detailstatus");
		if (!AppTool.isNull(detailstatus)) {
			String[] detailStatuss = detailstatus.toString().split(":");
			String statuss = "";
			for (String s : detailStatuss) {
				statuss += BaseDao.s(s) + ",";
			}
			where += " AND d.status in (" + statuss + "'TEST') ";
		}

		List<String> detailParams = Arrays.asList("item", "userdef3", "userdef4");
		for (String col : detailParams) {
			String key = formMap.getStr(col);
			if (!AppTool.isNull(key)) {
				where += " AND d." + col + "=" + BaseDao.s(key) + " ";
				formMap.remove(col);
			}
		}

		Object warehouse = formMap.get("warehouse");
		if (!AppTool.isNull(warehouse)) {
			where += " AND m.warehouse=" + BaseDao.s(warehouse) + " ";
		} else {
			Object showInPick = formMap.get("PICK");
			if ("PICK".equals(showInPick)) {
				TM search = getService().getSearchParam();
				List<Object> picksWhere = new ArrayList<>();
				Object tmpWhere = search.get(BaseSqlConstants.WHERE);
				Object picks = search.get("picks");
				if (!AppTool.isNull(tmpWhere))
					picksWhere.add(tmpWhere);
				if (!AppTool.isNull(picks))
					picksWhere.add(" d.userdef3 in (SELECT " + BaseSqlJoint.i("TEXT") + " FROM "
							+ AppTables.ALLOCATION_ITEM_INFO + " WHERE userdef1 IN (" + picks.toString() + "))");
				if (!AppTool.isNull(picksWhere))
					where += " AND (" + BaseDao.or(picksWhere) + ")";
			}
		}

		String mainTable = FormMapTableUtil.getTableName(getTClass(0)),
				detailTable = FormMapTableUtil.getTableName(getTClass(1));

		String selfHelpSql = "SELECT d.*,m.warehouse,m.company,m.status mainstatus,m.name FROM " + mainTable + " m, "
				+ detailTable + " d";
		formMap.set(BaseSqlConstants.SELF_HELP_SQL, selfHelpSql);
		formMap.set(BaseSqlConstants.ORDER_BY_KEY, " d.sequence DESC, m.updatetime DESC");
		formMap.set(BaseSqlConstants.WHERE, where);
		formMap.set(BaseSqlConstants.ALIAS, "m");
		return formMap;
	}

	@RequestMapping("download")
	public void download(HttpServletResponse response) throws Exception {
		OutputStream output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + DateFactory.getCurrTime() + ".xlsx");
		response.setContentType("application/msexcel");
		TM formMap = getFormMap(false);
		formMap = findWithDetail(formMap);
		List<TM> list = getMapper().findByNames(formMap);
		CallBackValue warehouseCall = (param) -> {
			return sysDicService.getDicValue("WAREHOUSE", param);
		};
		CallBackValue statusCall = (param) -> {
			return sysDicService.getDicValue("ACS_STATUS", param);
		};
		CallBackValue filterCall = (param) -> {
			return "1".equals(param) ? "" : param;
		};
		ImmutableMap<String, Object> headMap = ImmutableMap.<String, Object>builder()
				.put("warehouse", ImmutableMap.of("name", "仓库", "fun", warehouseCall)).put("paperid", "单号")
				.put("company", "拣货点").put("name", "目的地").put("item", "SU").put("userdef3", "TU")
				.put("status", ImmutableMap.of("name", "单据状态", "fun", statusCall))
				.put("detailstatus", ImmutableMap.of("name", "明细状态", "fun", statusCall))
				.put("userdef4", ImmutableMap.of("name", "组盘托盘", "fun", filterCall)).put("sequence", "优先级")
				.put("updatetime", "更新时间").put("createtime", "创建时间").build();
		AppXlsx.createExcel(headMap, list, "", output);
		output.close();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_DELETE)
	public JSONObject deleteEntity() throws Exception {
		return getService().deleteByLogic().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("send")
	@SystemLog(methods = "下达")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject send() throws Exception {
		return getService().send().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("execute")
	@SystemLog(methods = "执行")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject execute() throws Exception {
		return getService().execute().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("over")
	@SystemLog(methods = "执行")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject over() throws Exception {
		return getService().over().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("taked")
	@SystemLog(methods = "接单")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject taked() throws Exception {
		return getService().taked().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("editSeq")
	@SystemLog(methods = "接单")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject editSeq() throws Exception {
		TM form = getFormMap(false);
		Object type = form.get("type");
		Integer sequence = form.getInt("sequence");
		if (AppTool.isNull(sequence)) {
			return AppMsg.fail("输入的序号非法！").toAlbbJson();
		}
		if ("BY_PAPER".equals(type)) {
			String company = form.getCompany();
			if (AppTool.isNull(company)) {
				return AppMsg.fail("按单号修改时，单号不能为空！").toAlbbJson();
			}
			return getService().doEditSeqByCompany(company, sequence).toAlbbJson();
		} else if ("BY_SU".equals(type)) {
			String company = form.getCompany();
			String item = form.getStr("item");
			String userdef4 = form.getStr("userdef4");
			if (AppTool.isAllNull(item, userdef4)) {
				return AppMsg.fail("按SU修改时，SU与TU不能同时为空！").toAlbbJson();
			}
			return getService().doEditSeqBySu(company, userdef4, item, sequence).toAlbbJson();
		}
		return AppMsg.fail("未能识别的修改类型！").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteBySub")
	@SystemLog(methods = "请求撤销")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject deleteBySub() throws Exception {
		return getService().deleteBySub().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteBySure")
	@SystemLog(methods = "确认撤销")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject deleteBySure() throws Exception {
		TM form = getFormMap(false);
		String detailid = form.getStr("detailid");
		return getService().deleteBySure(detailid).toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("agvOk")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject agvOk() throws Exception {
		AppCache.worker().set("AGV_OK", "" + 1);
		ThreadTool.run(() -> {
			ThreadTool.sleep(5000);
			AppCache.worker().set("AGV_OK", "" + 0);
		});
		return AppMsg.success("模拟结束信号发送成功").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("findOneData")
	public JSONObject findOneData(String paperid) throws Exception {
		return getService().findOneData(paperid).toAlbbJson();
	}
}