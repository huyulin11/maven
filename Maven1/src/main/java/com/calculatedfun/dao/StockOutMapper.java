package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.StockOut;
import com.calculatedfun.dto.StockOutExample;

public interface StockOutMapper {
    int countByExample(StockOutExample example);

    int deleteByExample(StockOutExample example);

    int deleteByPrimaryKey(String id);

    int insert(StockOut record);

    int insertSelective(StockOut record);

    List<StockOut> selectByExample(StockOutExample example);

    StockOut selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StockOut record, @Param("example") StockOutExample example);

    int updateByExample(@Param("record") StockOut record, @Param("example") StockOutExample example);

    int updateByPrimaryKeySelective(StockOut record);

    int updateByPrimaryKey(StockOut record);
}