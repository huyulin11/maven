package com.kaifantech.component.service.alloc.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.component.dao.alloc.AllocItemDao;
import com.kaifantech.util.constant.taskexe.alloc.AllocationStatus;

@Service
public class AllocStatusService implements IAllocStatusService {
	@Autowired
	private AllocItemDao allocDao;

	@Override
	public AppMsg startStack(AllocItemInfoBean bean) {
		String from = bean.getStatus();
		String target = (bean.getNum() > 0) ? target = AllocationStatus.YOUHUO : AllocationStatus.KONGWEI;
		if (AppTool.isNull(bean.getSkuId())) {
			System.err.println("skuId为空！");
		}
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "上架锁定货位成功！")) : (new AppMsg(-1, "上架锁定货位失败！"));
	}

	@Override
	public AppMsg releaseFromStack(AllocItemInfoBean bean) {
		String from = AllocationStatus.SUODING;
		String target = (bean.getNum() > 0) ? target = AllocationStatus.YOUHUO : AllocationStatus.KONGWEI;
		if (AppTool.isNull(bean.getSkuId())) {
			System.err.println("skuId为空！");
		}
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "解除上架锁定货位成功！")) : (new AppMsg(-1, "解除上架锁定货位失败！"));
	}

	@Override
	public AppMsg startWithdraw(AllocItemInfoBean bean) {
		String from = AllocationStatus.YOUHUO;
		String target = (bean.getNum() > 0) ? target = AllocationStatus.YOUHUO : AllocationStatus.KONGWEI;
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "下架锁定货位成功！")) : (new AppMsg(-1, "下架锁定货位失败！"));
	}

	@Override
	public AppMsg releaseFromWithdraw(AllocItemInfoBean bean) {
		String target = AllocationStatus.YOUHUO;
		String from = AllocationStatus.SUODING;
		if (AppTool.isNull(bean.getSkuId())) {
			System.err.println("skuId为空！");
		}
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "解除上架锁定货位成功！")) : (new AppMsg(-1, "解除上架锁定货位失败！"));
	}

	@Override
	public AppMsg endStack(AllocItemInfoBean bean) {
		String target = AllocationStatus.YOUHUO;
		String from = AllocationStatus.SUODING;
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "上架成功！")) : (new AppMsg(-1, "上架失败！"));
	}

	@Override
	public AppMsg endWithdraw(AllocItemInfoBean bean) {
		String target = AllocationStatus.KONGWEI;
		if (bean.getNum() > 0) {
			target = AllocationStatus.YOUHUO;
		}
		String from = AllocationStatus.SUODING;
		int rtnNum = allocDao.update(bean, from, target);
		return rtnNum == 1 ? (new AppMsg(0, "下架成功！")) : (new AppMsg(-1, "下架失败！"));
	}
}
