package com.kaifantech.component.controller.base.form;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.calculatedfun.erp.plugin.PageView;
import com.calculatedfun.util.AppXlsx;
import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.FormMap;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.component.controller.base.IQueryController;

@Controller
public abstract class BaseQueryController<TT extends FormMap> extends BaseController<TT>
		implements IQueryController<TT> {
	@ResponseBody
	@RequestMapping("findByPage")
	public PageView findByPage(Integer pageNow, Integer pageSize) throws Exception {
		return defaultFindByPage(pageNow, pageSize, true);
	}

	@RequestMapping("download")
	public void download(HttpServletResponse response) throws Exception {
		OutputStream output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + DateFactory.getCurrTime() + ".xlsx");
		response.setContentType("application/msexcel");
		AppXlsx.createExcel(null, findJsonList(), "", output);
		output.close();
	}

	@ResponseBody
	@RequestMapping("findFirstPage")
	public PageView findFirstPage() throws Exception {
		return defaultFindFirstPage();
	}

	@ResponseBody
	@RequestMapping("findJsonList")
	public JSONArray findJsonList() throws Exception {
		List<TT> list = defaultFindList(getFormMap());
		return (JSONArray) JSONArray.toJSON(list);
	}
}