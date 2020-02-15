package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.SysCoinMunits;
import com.calculatedfun.dto.SysCoinMunitsExample;
import com.calculatedfun.dto.SysCoinMunitsWithBLOBs;

public interface SysCoinMunitsMapper {
    int countByExample(SysCoinMunitsExample example);

    int deleteByExample(SysCoinMunitsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCoinMunitsWithBLOBs record);

    int insertSelective(SysCoinMunitsWithBLOBs record);

    List<SysCoinMunitsWithBLOBs> selectByExampleWithBLOBs(SysCoinMunitsExample example);

    List<SysCoinMunits> selectByExample(SysCoinMunitsExample example);

    SysCoinMunitsWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCoinMunitsWithBLOBs record, @Param("example") SysCoinMunitsExample example);

    int updateByExampleWithBLOBs(@Param("record") SysCoinMunitsWithBLOBs record, @Param("example") SysCoinMunitsExample example);

    int updateByExample(@Param("record") SysCoinMunits record, @Param("example") SysCoinMunitsExample example);

    int updateByPrimaryKeySelective(SysCoinMunitsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysCoinMunitsWithBLOBs record);

    int updateByPrimaryKey(SysCoinMunits record);
}