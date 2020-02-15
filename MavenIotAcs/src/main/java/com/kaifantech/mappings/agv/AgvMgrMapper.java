package com.kaifantech.mappings.agv;

import java.util.List;

import com.kaifantech.entity.agv.AgvMgrFormMap;
import com.kaifantech.mappings.AppBaseMapper;

public interface AgvMgrMapper extends AppBaseMapper<AgvMgrFormMap> {

	public List<AgvMgrFormMap> find(AgvMgrFormMap formMap);

	public int add(AgvMgrFormMap formMap);

}
