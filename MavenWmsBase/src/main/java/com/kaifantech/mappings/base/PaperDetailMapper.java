package com.kaifantech.mappings.base;

import java.util.List;

import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.mappings.AppBaseMapper;

public interface PaperDetailMapper<TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>>
		extends AppBaseMapper<TD> {
	public List<PaperDetailFormMap> findPage(PaperDetailFormMap formMap);

	public int add(PaperDetailFormMap formMap);
}
