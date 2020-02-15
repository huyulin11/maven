package com.calculatedfun.dao;

import java.util.List;

import com.calculatedfun.bean.ScoreSearch;
import com.calculatedfun.bean.SpaceDay;

public interface ScoreSearchMapper {
	List<ScoreSearch> getScoreSearch();
	List<ScoreSearch> getTopTwentySearch();
	List<SpaceDay> getSpaceCoin();
	List<SpaceDay> getSpaceBill();
	List<SpaceDay> getTwoAnniversary();
	int addModernCoin();
	int addFlowCoin();
	int addBillCoin();
	int addAllCoin();
}
