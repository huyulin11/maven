package com.calculatedfun.component.controller.sys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.sys.ResService;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.Common;
import com.calculatedfun.util.TreeObject;
import com.calculatedfun.util.TreeUtil;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.ButtomFormMap;
import com.kaifantech.entity.Params;
import com.kaifantech.entity.ResFormMap;
import com.kaifantech.entity.ResRoleFormMap;
import com.kaifantech.entity.ResUserFormMap;
import com.kaifantech.init.sys.dao.UdfDbs;
import com.kaifantech.init.sys.dao.DbTypes;
import com.kaifantech.mappings.AppButtonMapper;
import com.kaifantech.mappings.AppResMapper;

@Controller
@RequestMapping("/resources/")
@SystemLog(module = "系统资源管理")
public class ResController extends BaseController<ResFormMap> {
	@Inject
	private AppResMapper resMapper;
	@Inject
	private AppButtonMapper buttonMapper;
	@Inject
	private ResService resService;

	@ResponseBody
	@RequestMapping("treelists")
	public ResFormMap findByPage(Model model) {
		ResFormMap resFormMap = getFormMap();
		List<ResFormMap> mps = resMapper.selectTreeResourcess();
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0);
		resFormMap = new ResFormMap();
		resFormMap.put("treelists", ns);
		return resFormMap;
	}

	@ResponseBody
	@RequestMapping("reslists")
	public List<TreeObject> reslists(Model model) throws Exception {
		ResFormMap resFormMap = getFormMap();
		List<ResFormMap> mps = resMapper.findByWhere(resFormMap);
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0, "　");
		return ns;
	}

	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("res", resService.findByRes());
		return "/system/resources/list";
	}

	@RequestMapping("editUI")
	public String editUI(Model model) {
		String id = AppSession.getParaValue("id");
		if (Common.isNotEmpty(id)) {
			model.addAttribute("resources", resMapper.findbyFrist("id", id, ResFormMap.class));
		}
		return "/system/resources/edit";
	}

	@RequestMapping("addUI")
	public String addUI(Model model) {
		return "/system/resources/add";
	}

	@RequestMapping("permissions")
	public String permissions(Model model) {
		List<ResFormMap> mps = resMapper.selectTreeResourcess();
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (ResFormMap map : mps) {
			TreeObject ts = new TreeObject();
			Common.flushObject(ts, map);
			list.add(ts);
		}
		TreeUtil treeUtil = new TreeUtil();
		List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0);
		model.addAttribute("permissions", ns);
		return "/system/resources/permissions";
	}

	@RequestMapping("addEntity")
	@ResponseBody
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_ADD)
	public JSONObject addEntity() throws Exception {
		ResFormMap resFormMap = getFormMap();
		if ("2".equals(resFormMap.get("type"))) {
			resFormMap.put("description", Common.htmltoString(resFormMap.get("description") + ""));
		}
		Object o = resFormMap.get("ishide");
		if (null == o) {
			resFormMap.set("ishide", "0");
		}
		resMapper.addEntity(resFormMap);
		return AppMsg.success("success").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("editEntity")
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_EDIT)
	public JSONObject editEntity(Model model) throws Exception {
		ResFormMap resFormMap = getFormMap();
		if ("2".equals(resFormMap.get("type"))) {
			resFormMap.put("description", Common.htmltoString(resFormMap.get("description") + ""));
		}
		Object o = resFormMap.get("ishide");
		if (null == o) {
			resFormMap.set("ishide", "0");
		}
		resMapper.editEntity(resFormMap);
		return AppMsg.success("success").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("deleteEntity")
	@SystemLog(methods = METHOD_DELETE)
	public JSONObject deleteEntity(Model model) throws Exception {
		String[] ids = AppSession.getParaValues("ids");
		for (String id : ids) {
			resMapper.deleteByAttribute("id", id, ResFormMap.class);
		}
		;
		return AppMsg.success("success").toAlbbJson();
	}

	@RequestMapping("sortUpdate")
	@ResponseBody
	@Transactional(readOnly = false)
	public JSONObject sortUpdate(Params params) throws Exception {
		List<String> ids = params.getId();
		List<String> es = params.getRowId();
		List<ResFormMap> maps = new ArrayList<ResFormMap>();
		for (int i = 0; i < ids.size(); i++) {
			ResFormMap map = new ResFormMap();
			map.put("id", ids.get(i));
			map.put("level", es.get(i));
			maps.add(map);
		}
		resMapper.updateSortOrder(maps);
		return AppMsg.success("success").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("findRes")
	public List<ResFormMap> findUserRes() {
		ResFormMap resFormMap = getFormMap();
		List<ResFormMap> rs = resMapper.findRes(resFormMap);
		return rs;
	}

	@ResponseBody
	@RequestMapping("findMyRes")
	public List<ResFormMap> findMyRes() {
		return resService.findCurrentUserRes(null);
	}

	@ResponseBody
	@RequestMapping("addUserRes")
	@Transactional(readOnly = false)
	@SystemLog(methods = METHOD_EDIT)
	public JSONObject addUserRes() throws Exception {
		String userId = "";
		String u = AppSession.getParaValue("userId");
		String g = AppSession.getParaValue("roleId");

		if (null != u && !Common.isEmpty(u.toString())) {
			userId = AppTool.trimComma(u.toString());
			ResUserFormMap delForm = new ResUserFormMap();
			delForm.set("userId", userId);
			if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL)) {
				resMapper.deleteByLogic(delForm);
			} else {
				resMapper.deleteByNames(delForm);
			}
			String[] s = AppSession.getParaValues("resId[]");
			if (!AppTool.isNull(s)) {
				List<ResUserFormMap> resUserFormMaps = new ArrayList<ResUserFormMap>();
				for (String rid : s) {
					ResUserFormMap resUserFormMap = new ResUserFormMap();
					resUserFormMap.put("resId", rid);
					resUserFormMap.put("userId", userId);
					resUserFormMaps.add(resUserFormMap);
				}
				if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL)) {
					resMapper.batchSave(resUserFormMaps);
				} else {
					resMapper.batchInsert(resUserFormMaps);
				}
			}

		} else if (null != g && !Common.isEmpty(g.toString())) {
			ResRoleFormMap delForm = new ResRoleFormMap();
			delForm.set("roleId", g.toString());
			if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL)) {
				resMapper.deleteByLogic(delForm);
			} else {
				resMapper.deleteByNames(delForm);
			}
			String[] s = AppSession.getParaValues("resId[]");
			if (!AppTool.isNull(s)) {
				List<ResRoleFormMap> ResRoleFormMaps = new ArrayList<ResRoleFormMap>();
				for (String rid : s) {
					ResRoleFormMap ResRoleFormMap = new ResRoleFormMap();
					ResRoleFormMap.put("resId", rid);
					ResRoleFormMap.put("roleId", g.toString());
					ResRoleFormMaps.add(ResRoleFormMap);
				}
				if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL)) {
					resMapper.batchSave(ResRoleFormMaps);
				} else {
					resMapper.batchInsert(ResRoleFormMaps);
				}
			}
		}
		return AppMsg.success("success").toAlbbJson();
	}

	@ResponseBody
	@RequestMapping("findByButtom")
	public List<ButtomFormMap> findByButtom() {
		return buttonMapper.findByWhere(new ButtomFormMap());
	}

	@ResponseBody
	@RequestMapping("isExist")
	public boolean isExist(String name, String resKey) {
		ResFormMap resFormMap = getFormMap();
		List<ResFormMap> r = resMapper.findByNames(resFormMap);
		if (r.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@ResponseBody
	@RequestMapping("menuTree")
	public List<TreeObject> manager(Model model) throws Exception {
		return resService.getMenuTree();
	}
}