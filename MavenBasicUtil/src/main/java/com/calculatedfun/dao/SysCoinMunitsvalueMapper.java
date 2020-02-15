package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.SysCoinMunitsvalue;
import com.calculatedfun.dto.SysCoinMunitsvalueExample;

public interface SysCoinMunitsvalueMapper {
    int countByExample(SysCoinMunitsvalueExample example);

    int deleteByExample(SysCoinMunitsvalueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCoinMunitsvalue record);

    int insertSelective(SysCoinMunitsvalue record);

    List<SysCoinMunitsvalue> selectByExample(SysCoinMunitsvalueExample example);

    SysCoinMunitsvalue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCoinMunitsvalue record, @Param("example") SysCoinMunitsvalueExample example);

    int updateByExample(@Param("record") SysCoinMunitsvalue record, @Param("example") SysCoinMunitsvalueExample example);

    int updateByPrimaryKeySelective(SysCoinMunitsvalue record);

    int updateByPrimaryKey(SysCoinMunitsvalue record);
}