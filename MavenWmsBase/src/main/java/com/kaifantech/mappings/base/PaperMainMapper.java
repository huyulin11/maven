package com.kaifantech.mappings.base;

import java.util.List;

import com.kaifantech.entity.paper.PaperDetailFormMap;
import com.kaifantech.entity.paper.PaperMainFormMap;
import com.kaifantech.mappings.AppBaseMapper;

public interface PaperMainMapper<TD extends PaperDetailFormMap, TM extends PaperMainFormMap<TD>>
		extends AppBaseMapper<TM> {
	public List<TM> findAllToIFS();
}
