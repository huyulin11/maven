package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.Coin;
import com.calculatedfun.dto.CoinExample;
import com.calculatedfun.dto.CoinWithBLOBs;

public interface CoinMapper {
    int countByExample(CoinExample example);
    int deleteByExample(CoinExample example);
    int deleteByPrimaryKey(Integer id);
    int insert(CoinWithBLOBs record);
    int insertSelective(CoinWithBLOBs record);
    List<CoinWithBLOBs> selectByExampleWithBLOBs(CoinExample example);
    List<Coin> selectByExample(CoinExample example);
    CoinWithBLOBs selectByPrimaryKey(Integer id);
    int updateByExampleSelective(@Param("record") CoinWithBLOBs record, @Param("example") CoinExample example);
    int updateByExampleWithBLOBs(@Param("record") CoinWithBLOBs record, @Param("example") CoinExample example);
    int updateByExample(@Param("record") Coin record, @Param("example") CoinExample example);
    int updateByPrimaryKeySelective(CoinWithBLOBs record);
    int updateByPrimaryKeyWithBLOBs(CoinWithBLOBs record);
    int updateByPrimaryKey(Coin record);
}