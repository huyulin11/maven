package com.kaifantech.component.service.lap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.calculatedfun.component.service.erp.system.IBaseService;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.entity.LapInfoFormMap;
import com.kaifantech.mappings.LapInfoMapper;
import com.kaifantech.mappings.AppBaseMapper;

@Service
public class LapInfoService implements IBaseService<LapInfoFormMap> {
	@Inject
	private LapInfoMapper mapper;

	public AppMsg doAddEntity(LapInfoFormMap formMap) throws Exception {
		mapper.addEntity(formMap);
		return AppMsg.success();
	}

	@Override
	public AppBaseMapper<LapInfoFormMap> getMapper() {
		return mapper;
	}

}
