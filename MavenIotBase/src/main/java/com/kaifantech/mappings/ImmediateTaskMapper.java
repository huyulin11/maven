package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.ImmediateTaskFormMap;

public interface ImmediateTaskMapper extends AppBaseMapper<ImmediateTaskFormMap> {
	public List<ImmediateTaskFormMap> findByPage(ImmediateTaskFormMap formMap);
	
	public void editEntity(ImmediateTaskFormMap formMap);
}