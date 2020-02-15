package com.kaifantech.component.controller.lap;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.annotation.SystemLog;
import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.form.BaseADEController;
import com.kaifantech.component.service.lap.LapInfoService;
import com.kaifantech.entity.LapInfoFormMap;
import com.kaifantech.mappings.AppBaseMapper;
import com.kaifantech.mappings.LapInfoMapper;

@Controller
@SystemLog(module = "地点管理")
@RequestMapping("/sys/lap/")
public class LapInfoController extends BaseADEController<LapInfoFormMap> {
	@Inject
	private LapInfoMapper mapper;

	@Inject
	private LapInfoService service;

	public LapInfoFormMap getFormMap() {
		LapInfoFormMap form = getFormMap(false);
		return form;
	}

	@Override
	public AppBaseMapper<LapInfoFormMap> getMapper() {
		return mapper;
	}

	@Override
	public IBaseService<LapInfoFormMap> getService() {
		return service;
	}

	@ResponseBody
	@RequestMapping("pickedSetting")
	@Transactional(readOnly = false)
	@SystemLog(methods = "拣货设置")
	public JSONObject pickedSetting() {
		try {
			LapInfoFormMap formMap = getFormMap();
			String jsonArrStr = formMap.getStr("arr");
			JSONArray arr = JSONArray.parseArray(jsonArrStr);
			for (Object obj : arr) {
				JSONObject jsonObj = (JSONObject) obj;
				LapInfoFormMap item = new LapInfoFormMap(jsonObj);
				getMapper().editEntity(item);
			}
			return AppMsg.success().toAlbbJson();
		} catch (Exception e) {
			e.printStackTrace();
			return AppMsg.fail("修改失败").toAlbbJson();
		}
	}
}