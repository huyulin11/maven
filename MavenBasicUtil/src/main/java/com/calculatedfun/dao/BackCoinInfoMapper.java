package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.RequestDetailBean;
import com.calculatedfun.dto.BackRequestCoin;
import com.calculatedfun.dto.BackRequestCoinScore;
import com.calculatedfun.dto.BackRequestDetail;
import com.calculatedfun.dto.BackRequestLogistics;
import com.calculatedfun.dto.BackStock;
import com.calculatedfun.dto.RevokeRequest;
import com.calculatedfun.dto.Stock;

public interface BackCoinInfoMapper {
    int deleteByPrimaryKey(String id);
    int insertSelective(BackRequestCoin record);
    BackRequestCoin selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(BackRequestCoin record);
    int updateByPrimaryKeyWithBLOBs(BackRequestCoin record);
    int updateByPrimaryKey(BackRequestCoin record);
    
    List<RequestDetailBean> getRqeustDetails(String requestid);
    int insertBackRequestDetail(BackRequestDetail backRequestDetail);	
    int insertBackRequestCoin(BackRequestCoin backRequestCoin);	
    int insertBackRequestCoinScore(BackRequestCoinScore backRequestCoinScore);	
    int insertBackRevokeReq(RevokeRequest request);
    int updateRquestState(@Param("requestcode")int requestcode,@Param("ownid")String ownid);
    //获取仓库记录
    Stock getStockbystockno(String stockno);
    //备份入库记录
    int insertBackStock(BackStock backStock);
    //删除原记录
    int delReqBackStock(String stockno);
    //备份包裹登记
    int insertBackRequestLogistics(BackRequestLogistics backRequestLogistics);
    //删除原记录
    int delReqlogistics(String stockno);
}