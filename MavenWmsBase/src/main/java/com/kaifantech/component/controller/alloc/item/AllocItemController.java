package com.kaifantech.component.controller.alloc.item;

import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppXlsx;
import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.callback.CallBackValue;
import com.calculatedfun.util.msg.AppMsg;
import com.google.common.collect.ImmutableMap;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.alloc.item.AllocItemService;
import com.kaifantech.component.service.sysdic.SysDicService;
import com.kaifantech.entity.AllocItemFormMap;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.BaseBusinessInfo.Projects;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.mappings.AllocItemMapper;
import com.kaifantech.mappings.AppBaseMapper;

@Controller
@SystemLog(module = "货位管理")
@RequestMapping("/alloc/item/")
public class AllocItemController extends BaseADEController<AllocItemFormMap> {
	@Inject
	private AllocItemMapper allocItemMapper;

	@Inject
	@Qualifier(UdfQualifier.DEFAULT_ALLOC_ITEM_SERVICE)
	private AllocItemService service;

	@Inject
	protected SysDicService sysDicService;

	@Override
	public AllocItemFormMap getFormMap() {
		AllocItemFormMap formMap = super.getFormMap();
		if ("true".equals(formMap.get("inStock"))) {
			formMap.set(BaseSqlConstants.ADDED_SQL_KEY, "num>0 or status!='1'");
		}
		return formMap;
	}

	@RequestMapping("download")
	public void download(HttpServletResponse response) throws Exception {
		OutputStream output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + DateFactory.getCurrTime() + ".xlsx");
		response.setContentType("application/msexcel");
		AllocItemFormMap formMap = getFormMap();
		List<AllocItemFormMap> list = allocItemMapper.findWithTxm(formMap);
		CallBackValue warehouseCall = (param) -> {
			return sysDicService.getDicValue("WAREHOUSE", param);
		};
		ImmutableMap<String, Object> headMap = ImmutableMap.<String, Object>builder()
				.put("whid", ImmutableMap.of("name", "仓库", "fun", warehouseCall)).put("text", "货位").put("txm", "SU")
				.build();
		AppXlsx.createExcel(headMap, list, "", output);
		output.close();
	}

	public JSONObject defaultDeleteEntity() throws Exception {
		String[] ids = AppSession.getParaValue("ids").split(":");
		AllocItemFormMap formMap = getFormMap();
		for (String id : ids) {
			formMap.set("id", id);
			if (UdfBusinessInfo.CURRENT_PROJECT.equals(Projects.BJJK_HUIRUI)) {
				getMapper().deleteByNames(formMap);
			} else {
				getMapper().deleteByLogic(formMap);
			}
		}
		return AppMsg.success().toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("findWithTxm")
	public PageView findWithTxm(Integer pageNow, Integer pageSize) throws Exception {
		AllocItemFormMap formMap = getFormMap();
		formMap = toFormMap(formMap, pageNow, pageSize);
		List<AllocItemFormMap> data = allocItemMapper.findWithTxm(formMap);
		return getPageView().setRecords(data);
	}

	@ResponseBody
	@RequestMapping("import")
	public JSONObject importData() throws Exception {
		AllocItemFormMap form = getFormMap(false);
		return getService().doAddEntity(form).toAlbbJson();
	}

	@Override
	public List<AllocItemFormMap> defaultFindByPage(AllocItemFormMap formMap) throws Exception {
		return allocItemMapper.findPage(formMap);
	}

	public AppBaseMapper<AllocItemFormMap> getMapper() {
		return allocItemMapper;
	}

	@Override
	public IBaseService<AllocItemFormMap> getService() {
		return service;
	}
}