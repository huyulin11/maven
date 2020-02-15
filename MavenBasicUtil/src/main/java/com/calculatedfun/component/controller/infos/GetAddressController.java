package com.calculatedfun.component.controller.infos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.calculatedfun.dao.AddressMapper;

@Controller
@RequestMapping("/address/")
public class GetAddressController {
	@Autowired
	AddressMapper addressMapper;

	@RequestMapping("getProvince")
	public List<?> getProvinceByParent(@RequestParam("parent") Integer parent) {
		return addressMapper.getAllProvince(parent);
	}

	@RequestMapping("getCity")
	public List<?> getCityByParent(@RequestParam("parent") Integer parent) {
		return addressMapper.getAllCity(parent);
	}

	@RequestMapping("getDistrict")
	public List<?> getDistrictByParent(@RequestParam("parent") Integer parent) {
		return addressMapper.getAllDistrict(parent);
	}
}
