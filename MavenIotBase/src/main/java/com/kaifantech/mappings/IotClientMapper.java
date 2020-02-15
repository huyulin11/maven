package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.IotClientFormMap;

public interface IotClientMapper extends AppBaseMapper<IotClientFormMap> {
	public List<IotClientFormMap> find(IotClientFormMap formMap);

	public int add(IotClientFormMap formMap);
}
