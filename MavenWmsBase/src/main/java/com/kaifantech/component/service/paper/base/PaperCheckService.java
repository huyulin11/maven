package com.kaifantech.component.service.paper.base;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.service.paper.AppCombinedInfo;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;
import com.kaifantech.util.constant.wms.WmsPaperStatus;
import com.kaifantech.util.log.AppFileLogger;

public abstract class PaperCheckService<TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>>
		extends PaperBasicService<TD, TM> {
	public AppMsg deleteByLogic() throws Exception {
		List<TM> list = findBeans(true);
		for (TM bean : list) {
			if (!WmsPaperStatus.NEW.equals(bean.getStatus())) {
				return AppMsg.fail(bean.getPaperid() + "不处于新建状态，无法删除！");
			}
		}
		getMainMapper().deleteByLogic(list);
		return AppMsg.success("删除成功！");
	}

	public AppMsg deleteByLogic(TM bean) throws Exception {
		if (!WmsPaperStatus.NEW.equals(bean.getStatus())) {
			return AppMsg.fail(bean.getPaperid() + "不处于新建状态，无法删除！");
		}
		getMainMapper().deleteByLogic(bean);
		return AppMsg.success("删除成功！");
	}

	public AppMsg over() throws Exception {
		TM bean = findBean(true);
		return over(bean);
	}

	public AppMsg over(TM bean) throws Exception {
		String tasktype = wmsPaperService.getTasktype(bean);
		String target = WmsPaperStatus.OVER, from = WmsPaperStatus.PICKING;
		AppMsg msg = checkStatus(target, from, bean);
		if (msg.getCode() < 0) {
			return msg;
		}
		if (AgvTaskType.RECEIPT.equals(tasktype) || AgvTaskType.SHIPMENT.equals(tasktype)) {
			for (PaperDetailFormMap tempBean : findDetailsByPaperid(bean.getPaperid())) {
				AllocItemInfoBean allocItem = allocInfoService.getByNameFromDB(tempBean.getUserdef1());
				if (!AppTool.isNull(allocItem)) {
					msg = allocStatusService.end(allocItem, tasktype);
					if (msg.getCode() < 0) {
						AppFileLogger.warning(msg);
						return msg;
					}
				}
			}
		}
		return updateStatus(bean, target, from);
	}

	public AppMsg execute() throws Exception {
		TM bean = findBean(true);
		return execute(bean);
	}

	public AppMsg execute(TM bean) throws Exception {
		String target = WmsPaperStatus.EXECUTING, from = WmsPaperStatus.TOSEND;
		AppMsg msg = checkStatus(target, from, bean);
		if (!msg.isSuccess()) {
			return msg;
		}
		return updateStatus(bean, target, from);
	}

	public AppMsg send() throws Exception {
		TM bean = findBean(true);
		return send(bean);
	}

	public AppMsg send(TM bean) throws Exception {
		String target = WmsPaperStatus.TOSEND, from = WmsPaperStatus.NEW;
		AppMsg msg = checkStatus(target, from, bean);
		if (!msg.isSuccess()) {
			return msg;
		}
		return updateStatus(bean, target, from);
	}

	public AppMsg taked() throws Exception {
		TM bean = findBean(true);
		return taked(bean);
	}

	public AppMsg taked(TM bean) throws Exception {
		String target = WmsPaperStatus.TAKED, from = WmsPaperStatus.TOSEND;
		AppMsg msg = checkStatus(target, from, bean);
		if (!msg.isSuccess()) {
			return msg;
		}
		return updateStatus(bean, target, from);
	}

	public AppMsg cancel() throws Exception {
		TM bean = findBean(true);
		return cancel(bean);
	}

	public AppMsg cancel(TM bean) throws Exception {
		String target = WmsPaperStatus.NEW, from = WmsPaperStatus.TOSEND;
		AppMsg msg = checkStatus(target, from, bean);
		if (!msg.isSuccess()) {
			return msg;
		}
		return updateStatus(bean, target, from);
	}

	public AppMsg deleteBySub() throws Exception {
		TM bean = findBean(true);
		return deleteBySub(bean);
	}

	public AppMsg deleteBySure(String detailid) throws Exception {
		TD bean = findDetailByDetailid(detailid);
		return deleteBySure(bean);
	}

	public AppMsg updateStatus(TM formMap) {
		try {
			int v = getMainMapper().updateStatus(formMap);
			if (v > 0) {
				return AppMsg.success("更新成功！");
			} else if (v == 0) {
				return AppMsg.fail("更新失败！未能更新数据，请检查原始状态！");
			} else {
				return AppMsg.fail("更新失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.success("更新成功！");
		}
	}

	public AppMsg updateStatus(TM bean, String target, String from) throws Exception {
		bean.set(BaseSqlConstants.FROM_STATUS, from);
		bean.set(BaseSqlConstants.TARGET_STATUS, target);
		return updateStatus(bean);
	}

	public AppMsg deleteBySub(TM bean) throws Exception {
		getMainMapper().deleteBySub(bean);
		return AppMsg.success();
	}

	public AppMsg deleteBySub(TD bean) throws Exception {
		if (bean instanceof ShipmentDetailFormMap && !AppTool.isNull(bean.getUserdef4())) {
			JSONObject combined = AppCombinedInfo.get(bean.getUserdef4());
			if (!AppTool.isNull(combined)) {
				if (AppTool.notEquals(combined.get("status"), WmsPaperStatus.OVER_PCS, WmsPaperStatus.OVER)) {
					AppCombinedInfo.deleteSub(bean.getUserdef4());
				}
			}
		}
		getMainMapper().deleteBySub(bean);
		return AppMsg.success();
	}

	public AppMsg deleteBySure(TD bean) throws Exception {
		int i = getDetailMapper().deleteBySure(bean);
		if (i > 0)
			return AppMsg.success();
		else
			return AppMsg.fail("未达到更新条件！");
	}

	public AppMsg deleteBySure(TM bean) throws Exception {
		int i = getDetailMapper().deleteBySure(bean);
		if (i > 0)
			return AppMsg.success();
		else
			return AppMsg.fail("未达到更新条件！");
	}

	public AppMsg updateStatusById(String id, String target, String from) {
		TM formMap = createMainObj();
		formMap.set("id", id);
		formMap.set(BaseSqlConstants.FROM_STATUS, from);
		formMap.set(BaseSqlConstants.TARGET_STATUS, target);
		return updateStatus(formMap);
	}

	public AppMsg updateStatusByPaperid(String paperid, String target, String from) {
		TM formMap = createMainObj();
		formMap.set("paperid", paperid);
		formMap.set(BaseSqlConstants.FROM_STATUS, from);
		formMap.set(BaseSqlConstants.TARGET_STATUS, target);
		return updateStatus(formMap);
	}

	public AppMsg checkStatus(String target, String from, TM bean) {
		return checkStatus(from, bean);
	}

	public AppMsg checkStatus(String from, TM bean) {
		if (AppTool.isNull(bean)) {
			return AppMsg.fail("无法找到单据数据！");
		}
		if (AppTool.isNull(bean.getDetailList())) {
			return AppMsg.fail("无法找到明细数据！");
		}
		if (!from.equals(bean.getStatus())) {
			return AppMsg.fail("当前订单状态与指定初始状态不符，无法更改状态！");
		}
		String tasktype = wmsPaperService.getTasktype(bean);
		for (TD detail : bean.getDetailList()) {
			AppMsg msg = allocCheckService.checkAllocName(detail.getUserdef1(), tasktype);
			if (msg.getCode() < 0) {
				return msg;
			}
		}
		return AppMsg.success();
	}
}