package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.IotRelationFormMap;

public interface IotRelationMapper extends AppBaseMapper<IotRelationFormMap> {

	public List<IotRelationFormMap> find(IotRelationFormMap formMap);

	public int add(IotRelationFormMap formMap);

}
