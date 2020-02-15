package com.kaifantech.bean.msg.agv.transfer;

import com.alibaba.fastjson.JSONObject;
import com.kaifantech.bean.msg.agv.SeerAgvMsgAll1Bean;
import com.kaifantech.util.hex.AppByteUtil;

public class SeerAgvAll1BeanTransfer {
	public static SeerAgvMsgAll1Bean transToBean(Integer agvId, String msgStr) {
		try {
			String hexDetail = msgStr.substring(32);
			JSONObject jsonDetail = JSONObject.parseObject(AppByteUtil.hexToStr(hexDetail));
			SeerAgvMsgAll1Bean bean = JSONObject.toJavaObject(jsonDetail, SeerAgvMsgAll1Bean.class);
			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}
