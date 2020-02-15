package com.kaifantech.component.controller.sys.data;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.dao.AppEmployeeMapper;
import com.kaifantech.bean.dic.SysDic;
import com.kaifantech.component.service.sysdic.SysDicService;
import com.kaifantech.entity.LapInfoFormMap;
import com.kaifantech.entity.UserRoleFormMap;
import com.kaifantech.mappings.AppUserMapper;
import com.kaifantech.mappings.AppUserRoleMapper;
import com.kaifantech.mappings.LapInfoMapper;

@Controller
@RequestMapping("/bd/")
public class BaseDataController {
	@Autowired
	private SysDicService sysDicService;
	@Autowired
	AppEmployeeMapper employeeMapper;
	@Autowired
	AppUserMapper userMapper;
	@Autowired
	AppUserRoleMapper userRoleMapper;
	@Inject
	LapInfoMapper lapInfoMapper;

	@RequestMapping("userRole")
	@ResponseBody
	public List<?> userRole(String userid) {
		UserRoleFormMap form = new UserRoleFormMap();
		form.set("userId", userid);
		return userRoleMapper.findByNames(form);
	}

	@RequestMapping("info")
	@ResponseBody
	public JSONObject info() {
		JSONObject obj = new JSONObject();
		obj.put("dict", sysDicService.getAllSysDictionaries());
		obj.put("role", employeeMapper.selectAllEmployeeRole());
		obj.put("res", employeeMapper.selectAllResourcess());
		try {
			obj.put("lap", lapInfoMapper.findByWhere(new LapInfoFormMap()));
		} catch (Exception e) {
		}
		return obj;
	}

	@RequestMapping("operator")
	@ResponseBody
	public List<?> getOperator(Integer userid) {
		return userid == null ? null : userMapper.searchOperator(userid);
	}

	@RequestMapping("searchEntityList")
	public List<?> searchEntity(SysDic sysDictionary, HttpServletResponse response) {
		return sysDicService.getSysDictionaries(new SysDic(sysDictionary.getType(), sysDictionary.getValue()));
	}
}
