package com.kaifantech.mappings;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.system.SystemOperator;
import com.kaifantech.entity.UserFormMap;

public interface AppUserMapper extends AppBaseMapper<UserFormMap> {

	public List<UserFormMap> findUserPage(UserFormMap userFormMap);

	public List<SystemOperator> searchOperator(@Param("id") int id);
}
