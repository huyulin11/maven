package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.SysDicTypeFormMap;

public interface SysDicTypeMapper extends AppBaseMapper<SysDicTypeFormMap> {

	public List<SysDicTypeFormMap> find(SysDicTypeFormMap formMap);

	public int add(SysDicTypeFormMap formMap);

}
