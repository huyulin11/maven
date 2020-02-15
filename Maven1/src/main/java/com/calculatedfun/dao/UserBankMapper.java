package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.UserBank;
import com.calculatedfun.dto.UserBankExample;

public interface UserBankMapper {
    int countByExample(UserBankExample example);

    int deleteByExample(UserBankExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserBank record);

    int insertSelective(UserBank record);

    List<UserBank> selectByExample(UserBankExample example);

    UserBank selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserBank record, @Param("example") UserBankExample example);

    int updateByExample(@Param("record") UserBank record, @Param("example") UserBankExample example);

    int updateByPrimaryKeySelective(UserBank record);

    int updateByPrimaryKey(UserBank record);
}