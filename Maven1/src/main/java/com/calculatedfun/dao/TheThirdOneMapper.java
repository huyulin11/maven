package com.calculatedfun.dao;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.RequestCoinBean;
import com.calculatedfun.dto.CoinN;
import com.calculatedfun.dto.Request;
import com.calculatedfun.dto.RequestCoinScore;

public interface TheThirdOneMapper {
	int checkEnterpriseUser(@Param("username") String username,
			@Param("password") String password);

	RequestCoinBean getRequestCoinByCoincode(@Param("coincode") String coincode);

	RequestCoinScore getReqCoinScoreByCoincode(
			@Param("coincode") String coincode);

	Request getRequestByrequestcode(@Param("requestcode") String requestcode);

	CoinN getCoinInfoBycoinId(@Param("id") Integer id);
}
