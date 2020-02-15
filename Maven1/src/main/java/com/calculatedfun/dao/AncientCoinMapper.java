package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.CoinChart;
import com.calculatedfun.bean.CoinInfo;
import com.calculatedfun.bean.Entityview;
import com.calculatedfun.bean.RequestCoinBean;
import com.calculatedfun.bean.Review;
import com.calculatedfun.bean.SearchScore;
import com.calculatedfun.bean.Statement;
import com.calculatedfun.bean.SysAncientCoinBasicInfo;

public interface AncientCoinMapper {
	List<Review> selectAncientCoinDetail(@Param("id") String id);

	List<SysAncientCoinBasicInfo> getAncientCoinData(Entityview entityview);

	List<Statement> getAncientCoinStatementListByReqCode(
			@Param("requestcode") int requestcode);

	CoinInfo getAncientCoinInfo(String requestcoinid);

	List<CoinInfo> getAncientCoinInfoByCode(@Param("coincode") String coincode);

	List<CoinChart> getPrintData(@Param("requestCode") String requestCode);// 获得打印标签数据古币

	List<SearchScore> getSecondAncientCoinScores(String requestid);

	List<SearchScore> getFirstAncientCoinScores(
			@Param("requestid") String requestid,
			@Param("scoreby") String scoreby);

	List<RequestCoinBean> getAncientCoin(String requestid);

}
