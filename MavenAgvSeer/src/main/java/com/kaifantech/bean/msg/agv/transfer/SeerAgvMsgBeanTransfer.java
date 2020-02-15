package com.kaifantech.bean.msg.agv.transfer;

import com.alibaba.fastjson.JSONObject;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.util.hex.AppByteUtil;

public class SeerAgvMsgBeanTransfer {
	public static SeerAgvMsgTaskBean transToBean(Integer agvId, String msgStr) {
		try {
			String hexDetail = msgStr.substring(32);
			JSONObject jsonDetail = JSONObject.parseObject(AppByteUtil.hexToStr(hexDetail));
			SeerAgvMsgTaskBean bean = JSONObject.toJavaObject(jsonDetail, SeerAgvMsgTaskBean.class);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}
