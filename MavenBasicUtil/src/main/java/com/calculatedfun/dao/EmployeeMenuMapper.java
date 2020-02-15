package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.EmployeeMenu;
import com.calculatedfun.dto.EmployeeMenuExample;

public interface EmployeeMenuMapper {
    int countByExample(EmployeeMenuExample example);

    int deleteByExample(EmployeeMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeMenu record);

    int insertSelective(EmployeeMenu record);
    
    List<EmployeeMenu> selectByExample(EmployeeMenuExample example);

    EmployeeMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmployeeMenu record, @Param("example") EmployeeMenuExample example);

    int updateByExample(@Param("record") EmployeeMenu record, @Param("example") EmployeeMenuExample example);

    int updateByPrimaryKeySelective(EmployeeMenu record);

    int updateByPrimaryKey(EmployeeMenu record);
}