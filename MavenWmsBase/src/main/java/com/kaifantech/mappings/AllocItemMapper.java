package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.AllocItemFormMap;

public interface AllocItemMapper extends AppBaseMapper<AllocItemFormMap> {
	public List<AllocItemFormMap> findPage(AllocItemFormMap formMap);

	public List<AllocItemFormMap> findWithTxm(AllocItemFormMap formMap);

	public List<AllocItemFormMap> findFirstPage(AllocItemFormMap AllocItemFormMap);
}
