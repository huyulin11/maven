package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.PayRequestReceive;
import com.calculatedfun.dto.PayRequestReceiveExample;

public interface PayRequestReceiveMapper {
    int countByExample(PayRequestReceiveExample example);

    int deleteByExample(PayRequestReceiveExample example);

    int deleteByPrimaryKey(String id);

    int insert(PayRequestReceive record);

    int insertSelective(PayRequestReceive record);

    List<PayRequestReceive> selectByExample(PayRequestReceiveExample example);

    PayRequestReceive selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PayRequestReceive record, @Param("example") PayRequestReceiveExample example);

    int updateByExample(@Param("record") PayRequestReceive record, @Param("example") PayRequestReceiveExample example);

    int updateByPrimaryKeySelective(PayRequestReceive record);

    int updateByPrimaryKey(PayRequestReceive record);
}