package com.kaifantech.component.controller.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.msg.agv.SeerAgvMsgGetter;

@Controller
@RequestMapping("/json/data/")
public class SeerMsgController {
	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	private AgvInfoDao agvInfoDao;

	@RequestMapping("getLatestMsg")
	@ResponseBody
	public Object getLatestMsg() {
		List<AgvInfoBean> agvList = agvInfoDao.getList();
		Map<Integer, SeerAgvMsgTaskBean> latestMsg = new HashMap<Integer, SeerAgvMsgTaskBean>();
		for (AgvInfoBean bean : agvList) {
			SeerAgvMsgTaskBean msg = SeerAgvMsgGetter.getStatusBean(bean.getId());
			latestMsg.put(bean.getId(), msg);
		}
		return JSONArray.toJSON(latestMsg);
	}
}
