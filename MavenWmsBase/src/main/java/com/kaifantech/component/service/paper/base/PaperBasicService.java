package com.kaifantech.component.service.paper.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.google.common.collect.ImmutableMap;
import com.kaifantech.component.controller.base.IAppCompnent;
import com.kaifantech.component.dao.ControlInfoDao;
import com.kaifantech.component.service.alloc.check.IAllocCheckService;
import com.kaifantech.component.service.alloc.info.IAllocInfoService;
import com.kaifantech.component.service.alloc.status.IAllocStatusService;
import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.entity.paper.ShipmentDetailFormMap;
import com.kaifantech.entity.paper.ShipmentMainFormMap;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.base.PaperDetailMapper;
import com.kaifantech.mappings.base.PaperMainMapper;

public abstract class PaperBasicService<TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>>
		implements IAppCompnent {
	public List<TM> find(TM bean, boolean withDetail) {
		List<TM> mains = getMainMapper().findByNames(bean);
		if (!withDetail || AppTool.isNull(mains)) {
			return mains;
		}
		for (TM receiptMainFormMap : mains) {
			List<TD> receiptDetailList = findDetailsByPaperid(receiptMainFormMap.getPaperid());
			receiptMainFormMap.setDetailList(receiptDetailList);
		}
		return mains;
	}

	public List<TM> findAllToIFS() {
		List<TM> mainList = null;
		try {
			mainList = getMainMapper().findAllToIFS();
			for (TM mainFormMap : mainList) {
				List<TD> detailList = findDetailsByPaperid(mainFormMap.getPaperid());
				mainFormMap.setDetailList(detailList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainList;
	}

	public List<TM> findByWarehouse(String warehouse, String status, boolean withDetail) {
		if (AppTool.isNull(warehouse)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("warehouse", warehouse);
		bean.set("status", status);
		return find(bean, withDetail);
	}

	public List<TM> findByStatus(String status, boolean withDetail) {
		return findByStatus(status, null, withDetail);
	}

	public List<TM> findByStatus(String status, String orderBy, boolean withDetail) {
		if (AppTool.isNull(status)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("status", status);
		bean.put("delflag", 0);
		if (!AppTool.isNull(orderBy)) {
			bean.put(BaseSqlConstants.ORDER_BY_KEY, orderBy);
		}
		return find(bean, withDetail);
	}

	public List<TM> findByCompany(String company, boolean withDetail) {
		if (AppTool.isNull(company)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("company", company);
		return find(bean, withDetail);
	}

	public List<TM> findByName(String name, boolean withDetail) {
		if (AppTool.isNull(name)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("name", name);
		return find(bean, withDetail);
	}

	public TM findByPaperid(String paperid, boolean withDetail) {
		if (AppTool.isNull(paperid)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("paperid", paperid);
		List<TM> list = find(bean, withDetail);
		return AppSet.first(list);
	}

	public TM findById(String id, boolean withDetail) {
		if (AppTool.isNull(id)) {
			return null;
		}
		TM bean = createMainObj();
		bean.set("id", id);
		List<TM> list = find(bean, withDetail);
		return AppSet.first(list);
	}

	public TM findBean(boolean withDetail) {
		String id = AppSession.getParaValue("id");
		String paperid = AppSession.getParaValue("paperid");
		TM bean;
		if (!AppTool.isNull(id)) {
			bean = findById(id, withDetail);
		} else {
			bean = findByPaperid(paperid, withDetail);
		}
		return bean;
	}

	public List<TM> findBeans(boolean withDetail) {
		String id = AppSession.getParaValue("id");
		String paperid = AppSession.getParaValue("paperid");
		String ids = AppSession.getParaValue("ids");
		String paperids = AppSession.getParaValue("paperids");
		List<TM> list = new ArrayList<>();
		if (!AppTool.isNull(id)) {
			list.add(findById(id, withDetail));
		} else if (!AppTool.isNull(paperid)) {
			list.add(findByPaperid(paperid, withDetail));
		} else if (!AppTool.isNull(ids)) {
			String[] idArr = ids.split(",");
			for (String tmp : idArr) {
				list.add(findById(tmp, withDetail));
			}
		} else if (!AppTool.isNull(paperids)) {
			String[] paperidArr = paperids.split(",");
			for (String tmp : paperidArr) {
				list.add(findByPaperid(tmp, withDetail));
			}
		} else if (!AppTool.isNull(paperid)) {
			list.add(findByPaperid(paperid, withDetail));
		}
		return list;
	}

	public AppMsg findOneData(String paperid) throws Exception {
		if (AppTool.isNull(paperid)) {
			return AppMsg.fail("无有效的订单编号！");
		}
		TM mainData = findByPaperid(paperid, false);
		List<TD> detailData = findDetailsByPaperid(paperid);
		if (AppTool.isNull(mainData)) {
			return AppMsg.fail("无有效数据！");
		}
		Map<String, Object> map = ImmutableMap.of("main", mainData, "detail", detailData);
		return AppMsg.success().setObject(map);
	}

	public List<TD> findDetailsById(String id) {
		if (AppTool.isNull(id)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set("id", id);
		List<TD> list = find(bean);
		return list;
	}

	public List<TD> findDetails(List<TM> mains) {
		List<TD> list = new ArrayList<>();
		for (TM main : mains) {
			list.addAll(main.getDetailList());
			if (main instanceof ShipmentMainFormMap) {
				for (TD detail : main.getDetailList()) {
					((ShipmentDetailFormMap) detail).setParent((ShipmentMainFormMap) main);
				}
			}
		}
		return list;
	}

	public List<TD> findDetails(TM search) {
		List<TM> mains = find(search, true);
		if (AppTool.isNull(mains)) {
			return null;
		}

		List<TD> list = findDetails(mains);
		return list;
	}

	public List<TD> findDetailsByPaperid(String paperid) {
		if (AppTool.isNull(paperid)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set("paperid", paperid);
		List<TD> list = find(bean);
		return list;
	}

	public TD findDetailByDetailid(String detailid) {
		if (AppTool.isNull(detailid)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set("id", detailid);
		List<TD> list = find(bean);
		return AppSet.first(list);
	}

	public TD findDetailByRelationid(String relationid) {
		if (AppTool.isNull(relationid)) {
			return null;
		}
		TD bean = createDetailObj();
		if (!(bean instanceof ShipmentDetailFormMap)) {
			return null;
		}
		bean.set("relationid", relationid);
		List<TD> list = find(bean);
		return AppSet.first(list);
	}

	public List<TD> findDetailsByCompany(String company) {
		if (AppTool.isNull(company)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set(BaseSqlConstants.WHERE, "paperid in (select paperid from " + AppTables.WMS_SHIPMENT_REQUEST_MAIN
				+ " where company=" + BaseDao.s(company) + " AND delflag='0')");
		List<TD> list = find(bean);
		return list;
	}

	public List<TD> findDetailsBySu(String su) {
		if (AppTool.isNull(su)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set("item", su);
		List<TD> list = find(bean);
		return list;
	}

	public List<TD> findDetailsByDetailIds(List<Object> detailids) {
		if (AppTool.isNull(detailids)) {
			return null;
		}
		TD bean = createDetailObj();
		bean.set("id", AppTool.join(":", detailids));
		List<TD> list = find(bean);
		return list;
	}

	public List<TD> findDetailsByItem(String company, String item) {
		TD bean = createDetailObj();
		bean.set("item", item);
		bean.set(BaseSqlConstants.WHERE, "paperid in (select paperid from " + AppTables.WMS_SHIPMENT_REQUEST_MAIN
				+ " where company=" + BaseDao.s(company) + " AND delflag='0')");
		List<TD> list = find(bean);
		return list;
	}

	public List<TD> find(TD bean) {
		return getDetailMapper().findByNames(bean);
	}

	public abstract TM createMainObj();

	public abstract TD createDetailObj();

	public abstract TM createMainObj(FormMap formMap);

	public abstract TD createDetailObj(FormMap formMap);

	public abstract PaperMainMapper<TD, TM> getMainMapper();

	public abstract PaperDetailMapper<TD, TM> getDetailMapper();

	@Inject
	protected PaperService wmsPaperService;

	@Inject
	protected ControlInfoDao controlInfoDao;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_INFO_SERVICE)
	protected IAllocInfoService allocInfoService;

	@Autowired
	protected IAllocStatusService allocStatusService;

	@Inject
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_CHECK_SERVICE)
	protected IAllocCheckService allocCheckService;
}