package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.SkuTypeFormMap;

public interface SkuTypeMapper extends AppBaseMapper<SkuTypeFormMap> {

	public List<SkuTypeFormMap> findByPage(SkuTypeFormMap formMap);

	public SkuTypeFormMap findOne(SkuTypeFormMap formMap);

	public int add(SkuTypeFormMap formMap);

}
