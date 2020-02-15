package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.SingletaskFormMap;

public interface SingletaskMapper extends AppBaseMapper<SingletaskFormMap> {

	public List<SingletaskFormMap> findPage(SingletaskFormMap formMap);

	public int addTaskMgr(SingletaskFormMap billFormMap);

}
