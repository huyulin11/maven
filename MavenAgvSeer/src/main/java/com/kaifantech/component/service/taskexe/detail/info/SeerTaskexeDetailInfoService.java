
package com.kaifantech.component.service.taskexe.detail.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.singletask.SingletaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.taskexe.TaskexeDetailBean;
import com.kaifantech.component.service.singletask.info.SingleTaskInfoService;

@Service
public class SeerTaskexeDetailInfoService extends AcsTaskexeDetailInfoService {
	private Map<Integer, Integer> map = new HashMap<>();

	@Autowired
	private SingleTaskInfoService singleTaskInfoService;

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
	public List<TaskexeDetailBean> getList(TaskexeBean taskexeBean) throws Exception {
		List<TaskexeDetailBean> taskexeDetailList = find(taskexeBean);
		if (AppTool.isNull(taskexeDetailList)) {
			SingletaskBean singletaskBean = singleTaskInfoService.get(taskexeBean.getJsonItem("taskid"));
			JSONArray sites = singletaskBean.getJsonArray("sites");
			sites.forEach((item) -> {
				Integer index = map.get(taskexeBean.getAgvId());
				if (AppTool.isNull(index)) {
					index = 1;
					map.put(taskexeBean.getAgvId(), index);
				}
				TaskexeDetailBean param = new TaskexeDetailBean(taskexeBean.getTaskexesid(),
						taskexeBean.getTasksequence());
				param.setDetailsequence(index);
				map.put(taskexeBean.getAgvId(), index + 1);
				param.setSiteid(Integer.parseInt(item.toString()));
				taskexeDetailMapper.add(param);
			});
		}
		return find(taskexeBean);
	}
}
