package com.kaifantech.component.service.alloc.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.dao.alloc.AllocItemDao;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.init.sys.qualifier.WmsQualifier;
import com.kaifantech.util.constant.taskexe.alloc.AllocationStatus;
import com.kaifantech.util.constant.taskexe.ctrl.AgvTaskType;

@Service(WmsQualifier.ALLOC_CHECK_SERVICE)
public class WmsAllocCheckService implements IAllocCheckService {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	protected IAllocInfoService allocService;

	@Autowired
	private AllocItemDao allocDao;

	public AppMsg checkTaskGroup(SingletaskBean singletaskBean) {
		return new AppMsg(0, "任务可以下达！");
	}

	public AppMsg checkTaskinfo(SingletaskBean singletaskBean) {
		if (AppTool.isNull(singletaskBean.getId()) || singletaskBean.getAllocid() <= 0) {
			return new AppMsg(-1, "任务基础信息有误！");
		}
		AllocItemInfoBean allocationInfoBean = allocService.getByTaskid(singletaskBean.getId());
		AppMsg msg = checkAllocInfo(allocationInfoBean, singletaskBean.getTasktype());
		if (msg.getCode() < 0) {
			return msg;
		}
		msg = checkAllocLogic(allocationInfoBean, singletaskBean.getTasktype());
		if (msg.getCode() < 0) {
			return msg;
		}
		return new AppMsg(0, "任务可以下达！");
	}

	public AppMsg checkAllocInfo(AllocItemInfoBean allocationInfoBean, String taskOpType) {
		try {
			if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.CSY_DAJ)) {
				return new AppMsg(0, "任务可以下达！");
			}
			if (AppTool.isNull(allocationInfoBean)) {
				return new AppMsg(-1, "无法找到任务对应的货位信息为空！");
			}
			if (AgvTaskType.RECEIPT.equals(taskOpType)) {
				if (AllocationStatus.YOUHUO.equals(allocationInfoBean.getStatus())) {
					return new AppMsg(-1, "任务对应货位有货，新上货任务无法下达！" + "(" + allocationInfoBean + ")");
				}
			} else if (AgvTaskType.SHIPMENT.equals(taskOpType)) {
				if (AllocationStatus.KONGWEI.equals(allocationInfoBean.getStatus())) {
					return new AppMsg(-1, "任务对应货位没有货品，新下货任务无法下达！" + "(" + allocationInfoBean + ")");
				}
			}
			if (AllocationStatus.SUODING.equals(allocationInfoBean.getStatus())) {
				return new AppMsg(-1, "任务对应货位处于锁定状态，新任务无法下达！" + "(" + allocationInfoBean + ")");
			}
		} catch (Exception e) {
			return new AppMsg(-1, "校验逻辑条件时出错，请咨询技术人员！");
		}
		return new AppMsg(0, "任务可以下达！");
	}

	public AppMsg checkAllocLogic(AllocItemInfoBean allocationInfoBean, String taskOpType) {
		try {
			Integer areaId = allocationInfoBean.getJsonItem("areaId", Integer.class);
			Integer colId = allocationInfoBean.getJsonItem("colId", Integer.class);
			Integer rowId = allocationInfoBean.getJsonItem("rowId", Integer.class);
			Integer zId = allocationInfoBean.getJsonItem("zId", Integer.class);
			if (allocDao.getAllocsBy(areaId).stream()
					.filter((bean) -> bean.getJsonItem("colId", Integer.class).equals(colId)
							&& bean.getJsonItem("rowId", Integer.class).compareTo(rowId) > 0
							&& !AllocationStatus.KONGWEI.equals(bean.getStatus()))
					.count() > 0) {
				return new AppMsg(-1, "同列下方货位状态为有货或者锁定，影响通行，新任务无法下达！" + "(" + allocationInfoBean + ")");
			}
			if (allocDao.getAllocsBy(areaId).stream()
					.filter((bean) -> bean.getJsonItem("colId", Integer.class).equals(colId)
							&& bean.getJsonItem("rowId", Integer.class).equals(rowId)
							&& bean.getJsonItem("zId", Integer.class).compareTo(zId) > 0
							&& !AllocationStatus.KONGWEI.equals(bean.getStatus()))
					.count() > 0) {
				return new AppMsg(-1, "同一货位上方位有货或者处于锁定状态，新任务无法下达！" + "(" + allocationInfoBean + ")");
			}
		} catch (Exception e) {
			return new AppMsg(-1, "校验逻辑条件时出错，请咨询技术人员！");
		}
		return new AppMsg(0, "任务可以下达！");
	}
}
