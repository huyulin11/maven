package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.UserMail;
import com.calculatedfun.dto.UserMailExample;

public interface UserMailMapper {
    int countByExample(UserMailExample example);

    int deleteByExample(UserMailExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserMail record);

    int insertSelective(UserMail record);

    List<UserMail> selectByExample(UserMailExample example);

    UserMail selectByPrimaryKey(String id);  
    UserMail selectUsermailByname(@Param("mail")String mail,@Param("userid")String userid,@Param("delflag")String delflag);
    int updataMailStatus(@Param("id")String id,@Param("status")String status);
    int updateByExampleSelective(@Param("record") UserMail record, @Param("example") UserMailExample example);

    int updateByExample(@Param("record") UserMail record, @Param("example") UserMailExample example);

    int updateByPrimaryKeySelective(UserMail record);

    int updateByPrimaryKey(UserMail record);
}