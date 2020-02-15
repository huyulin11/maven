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
import com.calculatedfun.bean.SysMechanismCoinBasicInfo;

public interface MechanismCoinMapper {
	List<Review> selectMechanismCoinDetail(@Param("id") String id);

	List<SysMechanismCoinBasicInfo> getMechanismCoinData(Entityview entityview);

	List<CoinInfo> getMechanismCoinInfoByCode(@Param("coincode") String coincode);

	CoinInfo getMechanismCoinInfo(String requestcoinid);

	public List<Statement> getMechanismCoinListByReqCode(
			@Param("requestcode") int requestcode);

	List<SearchScore> getFirstMechanismCoinScores(
			@Param("requestid") String requestid,
			@Param("scoreby") String scoreby);

	List<SearchScore> getSecondMechanismCoinScores(String requestid);

	List<RequestCoinBean> getMechanismCoin(String requestid);

	List<CoinChart> getChartMechanismCoins(String requestCode);

	List<CoinChart> getPrintData(@Param("requestCode") String requestCode);// 获得打印标签数据机制币

}
