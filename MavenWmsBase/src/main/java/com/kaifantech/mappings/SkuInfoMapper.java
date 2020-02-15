package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.SkuInfoFormMap;

public interface SkuInfoMapper extends AppBaseMapper<SkuInfoFormMap> {
	public List<SkuInfoFormMap> findByName(SkuInfoFormMap formMap);

	public int add(SkuInfoFormMap formMap);
}
