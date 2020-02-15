package com.kaifantech.component.service.de;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.info.agv.AgvInfoBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.component.service.status.agv.AgvCtrlInfoService;
import com.kaifantech.component.service.taskexe.info.TaskexeInfoService;

@Service
public class AcsRestfulService {

	@Autowired
	private AgvCtrlInfoService agvsCtrlInfoService;

	@Autowired
	private TaskexeInfoService taskexeInfoService;

	private static int flag = 0;

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAgvStatus() {
		List<Map<String, Object>> listRtn = new LinkedList<Map<String, Object>>();
		try {
			List<AgvInfoBean> list = agvsCtrlInfoService.getRtAgvInfoList();
			for (AgvInfoBean agvBean : list) {
				BeanMap beanmap = new BeanMap(agvBean);
				TaskexeBean taskexeBean = taskexeInfoService.getLatestOne(agvBean.getId());
				String status = "OVER";
				if (!AppTool.isNull(taskexeBean)) {
					status = taskexeBean.getOpflag();
				}
				Map<String, Object> rtnMap = new HashMap<>();
				rtnMap.putAll(beanmap);
				rtnMap.put("status", status);
				rtnMap.remove("class");
				listRtn.add(rtnMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRtn;
	}

	public List<Map<String, Object>> getAgvStatusTest() {
		List<Integer> agvStatusCode = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<String> agvStatusName = Arrays.asList("空闲", "执行入库", "执行出库", "执行盘点", "正在充电", "备用");
		List<String> agvBattery = Arrays.asList("54%", "35%", "23%", "88%");
		List<Map<String, Object>> allAgv = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			Map<String, Object> singleAgv = new HashMap<>();
			singleAgv.put("id", i + 1);
			singleAgv.put("statusCode", agvStatusCode.get(flag % agvStatusCode.size()));
			singleAgv.put("statusName", agvStatusName.get(flag % agvStatusName.size()));
			singleAgv.put("battery", agvBattery.get(i));
			flag++;
			allAgv.add(singleAgv);
		}
		return allAgv;
	}
}
