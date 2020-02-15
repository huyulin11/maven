package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.WalletRefund;
import com.calculatedfun.dto.WalletRefundExample;

public interface WalletRefundMapper {
    int countByExample(WalletRefundExample example);

    int deleteByExample(WalletRefundExample example);

    int deleteByPrimaryKey(String id);

    int insert(WalletRefund record);

    int insertSelective(WalletRefund record);

    List<WalletRefund> selectByExample(WalletRefundExample example);

    WalletRefund selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WalletRefund record, @Param("example") WalletRefundExample example);

    int updateByExample(@Param("record") WalletRefund record, @Param("example") WalletRefundExample example);

    int updateByPrimaryKeySelective(WalletRefund record);

    int updateByPrimaryKey(WalletRefund record);
}