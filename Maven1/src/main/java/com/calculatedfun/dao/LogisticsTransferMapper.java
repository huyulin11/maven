package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.LogisticsTransfer;
import com.calculatedfun.dto.LogisticsTransferExample;

public interface LogisticsTransferMapper {
    int countByExample(LogisticsTransferExample example);

    int deleteByExample(LogisticsTransferExample example);

    int deleteByPrimaryKey(String id);

    int insert(LogisticsTransfer record);

    int insertSelective(LogisticsTransfer record);

    List<LogisticsTransfer> selectByExample(LogisticsTransferExample example);

    LogisticsTransfer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LogisticsTransfer record, @Param("example") LogisticsTransferExample example);
    int updateByExample(@Param("record") LogisticsTransfer record, @Param("example") LogisticsTransferExample example);

    int updateByPrimaryKeySelective(LogisticsTransfer record);

    int updateByPrimaryKey(LogisticsTransfer record);
    
    List<LogisticsTransfer> getTransferRecords(@Param("requestcode")int requestcode,@Param("operater")int operater);
}