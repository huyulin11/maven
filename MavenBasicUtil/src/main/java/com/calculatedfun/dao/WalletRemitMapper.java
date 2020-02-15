package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.WalletRemit;
import com.calculatedfun.dto.WalletRemitExample;

public interface WalletRemitMapper {
    int countByExample(WalletRemitExample example);

    int deleteByExample(WalletRemitExample example);

    int deleteByPrimaryKey(String id);

    int insert(WalletRemit record);

    int insertSelective(WalletRemit record);

    List<WalletRemit> selectByExample(WalletRemitExample example);

    WalletRemit selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") WalletRemit record, @Param("example") WalletRemitExample example);

    int updateByExample(@Param("record") WalletRemit record, @Param("example") WalletRemitExample example);

    int updateByPrimaryKeySelective(WalletRemit record);

    int updateByPrimaryKey(WalletRemit record);
}