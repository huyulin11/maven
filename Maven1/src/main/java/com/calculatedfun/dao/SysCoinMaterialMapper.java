package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.SysCoinMaterial;
import com.calculatedfun.dto.SysCoinMaterialExample;
import com.calculatedfun.dto.SysCoinMaterialWithBLOBs;

public interface SysCoinMaterialMapper {
    int countByExample(SysCoinMaterialExample example);

    int deleteByExample(SysCoinMaterialExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCoinMaterialWithBLOBs record);

    int insertSelective(SysCoinMaterialWithBLOBs record);

    List<SysCoinMaterialWithBLOBs> selectByExampleWithBLOBs(SysCoinMaterialExample example);

    List<SysCoinMaterial> selectByExample(SysCoinMaterialExample example);

    SysCoinMaterialWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCoinMaterialWithBLOBs record, @Param("example") SysCoinMaterialExample example);

    int updateByExampleWithBLOBs(@Param("record") SysCoinMaterialWithBLOBs record, @Param("example") SysCoinMaterialExample example);

    int updateByExample(@Param("record") SysCoinMaterial record, @Param("example") SysCoinMaterialExample example);

    int updateByPrimaryKeySelective(SysCoinMaterialWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysCoinMaterialWithBLOBs record);

    int updateByPrimaryKey(SysCoinMaterial record);
}