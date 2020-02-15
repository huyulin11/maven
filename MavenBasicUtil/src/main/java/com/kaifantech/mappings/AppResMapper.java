package com.kaifantech.mappings;

import java.util.List;

import com.kaifantech.entity.ResFormMap;

public interface AppResMapper extends AppBaseMapper<ResFormMap> {
	public List<ResFormMap> findChildlists(ResFormMap map);

	public List<ResFormMap> findRes(ResFormMap map);

	public void updateSortOrder(List<ResFormMap> map);

	public List<ResFormMap> findUserResourcess(String userId);

	public List<ResFormMap> selectTreeResourcess();
}
