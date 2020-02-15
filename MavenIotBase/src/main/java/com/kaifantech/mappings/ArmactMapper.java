package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.ArmactFormMap;

public interface ArmactMapper extends AppBaseMapper<ArmactFormMap> {

	public List<ArmactFormMap> findPage(ArmactFormMap formMap);

	public int add(ArmactFormMap formMap);

}
