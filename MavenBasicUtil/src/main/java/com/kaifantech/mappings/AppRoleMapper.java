package com.kaifantech.mappings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaifantech.entity.RoleFormMap;

public interface AppRoleMapper extends AppBaseMapper<RoleFormMap> {
	public List<RoleFormMap> seletUserRole(RoleFormMap map);

	public List<RoleFormMap> seletAllUserRole();

	public void deleteById(RoleFormMap map);

	public int checkButton(@Param("resId") int resId, @Param("userId") int userId);
}
