package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.bean.dic.SysDic;
import com.kaifantech.entity.SysDicDataFormMap;

public interface SysDicDataMapper extends AppBaseMapper<SysDicDataFormMap> {
	public List<SysDicDataFormMap> findPage(SysDicDataFormMap formMap);

	public List<SysDicDataFormMap> findByValue(SysDicDataFormMap formMap);

	List<SysDic> getSysDictionaries(SysDic sysDictionary);

	List<SysDic> getAllDictionaries();

	public int add(SysDicDataFormMap formMap);

	public int delete(SysDicDataFormMap formMap);
}
