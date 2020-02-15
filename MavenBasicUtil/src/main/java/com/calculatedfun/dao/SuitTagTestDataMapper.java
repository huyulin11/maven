package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.CoinChart;

public interface SuitTagTestDataMapper {
	List<CoinChart> getChartSuits(@Param("requestCode")String requestcode);
}
