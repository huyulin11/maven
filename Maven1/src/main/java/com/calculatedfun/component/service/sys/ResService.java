package com.calculatedfun.component.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.TreeObject;
import com.calculatedfun.util.TreeUtil;
import com.kaifantech.entity.ResFormMap;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppResMapper;

@Service
public class ResService {
	@Autowired
	private AppResMapper resourcesMapper;

	public List<TreeObject> getMenuTree() throws Exception {
		List<ResFormMap> mps = this.findCurrentUserRes(null);
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		return new TreeUtil().getChildTreeObjects(list, 0);
	}

	public List<ResFormMap> findCurrentUserRes(String addedSql) {
		ResFormMap res = new ResFormMap();
		Object userId = AppSession.getUserId();
		String whereSql = " where " + " (id in (SELECT resId from " + AppTables.LY_RES_USER
				+ " where delflag=0 AND userid=" + userId + ") " + " OR " + " id IN ( SELECT resId FROM "
				+ AppTables.LY_ROLE_RES + " WHERE delflag=0 AND roleId IN ( " + "SELECT roleId FROM "
				+ AppTables.LY_USER_ROLE + " WHERE userId = " + userId + " ))) " + " and ishide = 0 "
				+ (AppTool.isNullStr(addedSql) ? "" : addedSql);
		res.put(BaseSqlConstants.WHERE, whereSql);
		res.put(BaseSqlConstants.ORDER_BY_KEY, " id desc ");
		List<ResFormMap> mps = resourcesMapper.findByWhere(res);
		return mps;
	}

	public List<ResFormMap> findByRes() {
		String id = AppSession.getParaValue("id");
		List<ResFormMap> rse = findCurrentUserRes(" and parentId = " + id);
		for (ResFormMap resFormMap : rse) {
			Object o = resFormMap.get("description");
			if (o != null && !AppTool.isNull(o.toString())) {
				resFormMap.put("description", Common.stringtohtml(o.toString()));
			}
		}
		return rse;
	}
}
