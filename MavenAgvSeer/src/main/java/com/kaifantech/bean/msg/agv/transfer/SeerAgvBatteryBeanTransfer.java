package com.kaifantech.bean.msg.agv.transfer;

import com.alibaba.fastjson.JSONObject;
import com.kaifantech.bean.msg.agv.SeerAgvMsgBatteryBean;
import com.kaifantech.util.hex.AppByteUtil;

public class SeerAgvBatteryBeanTransfer {
	public static SeerAgvMsgBatteryBean transToBean(Integer agvId, String msgStr) {
		try {
			String hexDetail = msgStr.substring(32);
			JSONObject jsonDetail = JSONObject.parseObject(AppByteUtil.hexToStr(hexDetail));
			SeerAgvMsgBatteryBean bean = JSONObject.toJavaObject(jsonDetail, SeerAgvMsgBatteryBean.class);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}
