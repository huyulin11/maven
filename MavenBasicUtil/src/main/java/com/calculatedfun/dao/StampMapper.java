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
import com.calculatedfun.bean.SysStampBasicInfo;

public interface StampMapper {
	// 查询古钱币request_detail
	List<Review> selectStampDetail(@Param("id") String id);

	List<SysStampBasicInfo> getStampData(Entityview entityview);

	List<Statement> getStampStatementListByReqCode(
			@Param("requestcode") int requestcode);

	CoinInfo getStampInfo(String requestcoinid);

	List<CoinInfo> getStampInfoByCode(@Param("coincode") String coincode);

	List<CoinChart> getPrintData(@Param("requestCode") String requestCode);// 获得打印标签数据古币

	List<SearchScore> getSecondStampScores(String requestid);

	List<SearchScore> getFirstStampScores(@Param("requestid") String requestid,
			@Param("scoreby") String scoreby);

	List<RequestCoinBean> getStamp(String requestid);

	SysStampBasicInfo selectStampInfo(int id);
}
