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
import com.calculatedfun.bean.SysBillBasicInfo;

public interface BillMapper {

	List<Review> selectBillDetail(@Param("id") String id);

	List<CoinInfo> getBillInfoByCode(@Param("coincode") String coincode);

	CoinInfo getBillInfo(String requestcoinid);

	public List<Statement> getStatementBillListByReqCode(
			@Param("requestcode") int requestcode);

	List<SysBillBasicInfo> getBillData(Entityview entityview);

	List<SearchScore> selectBillScores(@Param("requestid") String requestid,
			@Param("scoreby") String scoreby);

	List<SearchScore> selectSecondBillScores(String requestid);

	List<RequestCoinBean> selectBills(String requestid);

	List<CoinChart> getChartBills(String requestCode);

	List<CoinChart> getPrintData(@Param("requestCode") String requestCode,
			@Param("country") String country);

	List<CoinChart> getPrintDataBack(@Param("requestCode") String requestCode,
			@Param("country") String country);

}
