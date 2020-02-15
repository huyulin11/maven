package com.calculatedfun.component.controller.app.conf;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.alloc.item.AppConfService;
import com.kaifantech.entity.AppConfFormMap;
import com.kaifantech.init.sys.dao.UdfDbs;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.AppConfMapper;

@Controller
@SystemLog(module = "KV数据管理")
@RequestMapping("/app/conf/")
public class AppConfController extends BaseADEController<AppConfFormMap> {
	@Inject
	protected AppConfMapper mapper;

	@Inject
	protected AppConfService service;

	@Override
	public AppConfFormMap getFormMap() {
		AppConfFormMap formMap = super.getFormMap(false);
		Object tableName = formMap.get(BaseSqlConstants.TABLE_KEY);
		if (AppTool.isNull(tableName)) {
			System.out.println("在" + formMap + " 没有找到数据库表对应该的注解!");
			return null;
		}
		AppSystemDao.validateOrCreate(UdfDbs.CONF_DB, tableName.toString(), false);
		formMap.set(BaseSqlConstants.TABLE_KEY, UdfDbs.CONF_DB + "." + tableName);
		return formMap;
	}

	@Override
	public List<AppConfFormMap> defaultFindByPage(AppConfFormMap formMap) throws Exception {
		return mapper.findByPage(formMap);
	}

	public AppBaseMapper<AppConfFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<AppConfFormMap> getService() {
		return service;
	}

	@RequestMapping("get")
	@ResponseBody
	public List<Map<String, Object>> conf(String table, String key) {
		return AppConf.worker().getList(table, key);
	}

	@RequestMapping("set")
	@ResponseBody
	public JSONObject setConf(String table, String key, String value) {
		if ("conf_key".equals(table) && "PICKED_TYPE".equals(key)) {
			AppConf.worker().hset(table, key, value);
			return AppMsg.success().toAlbbJson();
		}
		return AppMsg.fail("未开通配置权限").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteBySure")
	@SystemLog(methods = "确认撤销")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject deleteBySure() throws Exception {
		AppConfFormMap form = getFormMap();
		mapper.deleteBySure(form);
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("setByUser")
	@SystemLog(methods = "setByUser")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject setByUser() throws Exception {
		AppConfFormMap form = super.getFormMap(false);
		String value = form.getStr("value");
		JSONObject json = JSONObject.parseObject(value);
		AppConf.worker().hset(form.getStr(BaseSqlConstants.TABLE_KEY), AppSession.getUserId().toString(),
				json.toJSONString());
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("getByUser")
	@SystemLog(methods = "getByUser")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject getByUser() throws Exception {
		AppConfFormMap form = super.getFormMap(false);
		String value = AppConf.worker().get(form.getStr(BaseSqlConstants.TABLE_KEY), AppSession.getUserId().toString());
		return AppTool.isNull(value) ? new JSONObject() : JSONObject.parseObject(value);
	}
}