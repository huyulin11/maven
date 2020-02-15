package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.UserAddress;
import com.calculatedfun.dto.UserAddressExample;
import com.calculatedfun.dto.UserMail;
import com.calculatedfun.dto.UserMobile;
import com.calculatedfun.dto.UserTel;

public interface UserAddressMapper {
	int countByExample(UserAddressExample example);

	int deleteByExample(UserAddressExample example);

	int deleteByPrimaryKey(String id);

	int insert(UserAddress record);

	int insertSelective(UserAddress record);

	List<UserAddress> selectByExample(UserAddressExample example);

	UserAddress selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") UserAddress record,
			@Param("example") UserAddressExample example);

	int updateByExample(@Param("record") UserAddress record,
			@Param("example") UserAddressExample example);

	int updateByPrimaryKeySelective(UserAddress record);

	int updateByPrimaryKey(UserAddress record);

	UserMail selectByUserMail(String id);

	UserMobile selectByUserMobile(String id);

	UserTel selectByUserTel(String id);

	UserAddress selectByUserid(@Param("userid") String userid);

}