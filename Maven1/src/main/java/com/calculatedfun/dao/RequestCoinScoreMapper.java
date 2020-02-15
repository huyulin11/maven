package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.RequestCoinScore;
import com.calculatedfun.dto.RequestCoinScoreExample;

public interface RequestCoinScoreMapper {
    int countByExample(RequestCoinScoreExample example);

    int deleteByExample(RequestCoinScoreExample example);

    int deleteByPrimaryKey(String id);

    int insert(RequestCoinScore record);

    int insertSelective(RequestCoinScore record);

    List<RequestCoinScore> selectByExample(RequestCoinScoreExample example);

    RequestCoinScore selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RequestCoinScore record, @Param("example") RequestCoinScoreExample example);

    int updateByExample(@Param("record") RequestCoinScore record, @Param("example") RequestCoinScoreExample example);

    int updateByPrimaryKeySelective(RequestCoinScore record);

    int updateByPrimaryKey(RequestCoinScore record);
}