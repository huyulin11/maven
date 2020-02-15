package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.SysDistrict;
import com.calculatedfun.dto.SysDistrictExample;
import com.calculatedfun.dto.SysDistrictWithBLOBs;

public interface SysDistrictMapper {
    int countByExample(SysDistrictExample example);

    int deleteByExample(SysDistrictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysDistrictWithBLOBs record);

    int insertSelective(SysDistrictWithBLOBs record);

    List<SysDistrictWithBLOBs> selectByExampleWithBLOBs(SysDistrictExample example);

    List<SysDistrict> selectByExample(SysDistrictExample example);

    SysDistrictWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysDistrictWithBLOBs record, @Param("example") SysDistrictExample example);

    int updateByExampleWithBLOBs(@Param("record") SysDistrictWithBLOBs record, @Param("example") SysDistrictExample example);

    int updateByExample(@Param("record") SysDistrict record, @Param("example") SysDistrictExample example);

    int updateByPrimaryKeySelective(SysDistrictWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysDistrictWithBLOBs record);

    int updateByPrimaryKey(SysDistrict record);
}