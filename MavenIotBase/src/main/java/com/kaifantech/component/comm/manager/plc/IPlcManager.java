package com.kaifantech.component.comm.manager.plc;

import java.util.List;
import java.util.Map;

import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.util.constants.plc.PlcParamBean;

public interface IPlcManager {
	public Map<String, String> fromStr(String jsonStr);

	public AppMsg work(Integer agvId, PlcParamBean plcParamBean) throws Exception;

	public AppMsg work(Integer agvId, Map<String, Object> params) throws Exception;

	public AppMsg sendNeedRtn(Integer agvId, String cmd, int repeatTimes);

	public AppMsg errorDeal(Integer agvId) throws Exception;

	public AppMsg forbidAct(Integer agvId) throws Exception;

	public AppMsg readyForCharge(Integer agvId) throws Exception;

	public AppMsg robotScan(Integer agvId, String robotSide, String forkSide, String frameNum) throws Exception;

	public AppMsg robotScanWindow(Integer agvId, String window) throws Exception;

	public AppMsg windowToAgv(Integer agvId, String agvLayer, String window, String windowLayer) throws Exception;

	public AppMsg agvToAlloc(Integer agvId, String agvLayer, String robotSide, String forkSide, String frameNum,
			String pieceNum) throws Exception;

	public AppMsg allocToAgv(Integer agvId, String agvLayer, String robotSide, String forkSide, String frameNum,
			String pieceNum) throws Exception;

	public AppMsg agvToWindow(Integer agvId, String agvLayer, String window, String windowLayer) throws Exception;

	public List<String> plcmsg(Integer agvId);
}
