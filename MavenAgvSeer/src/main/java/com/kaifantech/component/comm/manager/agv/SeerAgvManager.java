package com.kaifantech.component.comm.manager.agv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.comm.cmd.sender.agv.SeerAgvConfSender;
import com.kaifantech.component.comm.cmd.sender.agv.SeerAgvCtrlSender;
import com.kaifantech.component.comm.cmd.sender.agv.SeerAgvNaviSender;
import com.kaifantech.component.comm.cmd.sender.agv.SeerAgvOtherSender;
import com.kaifantech.component.comm.cmd.sender.agv.SeerAgvStatusSender;
import com.kaifantech.component.comm.manager.IRtnManager;

@Service
public class SeerAgvManager implements IAgvManager, IRtnManager {
	@Autowired
	protected SeerAgvStatusSender agvStatusSender;
	@Autowired
	protected SeerAgvCtrlSender agvCtrlSender;
	@Autowired
	protected SeerAgvNaviSender agvNaviSender;
	@Autowired
	protected SeerAgvConfSender agvConfSender;
	@Autowired
	protected SeerAgvOtherSender agvOtherSender;

	@Override
	public AppMsg doSend(int agvId, String command) {
		return agvStatusSender.send(agvId, command);
	}

	@Override
	public AppMsg pause(int agvId) {
		return agvNaviSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_TASK_PAUSE_REQ));
	}

	public AppMsg generalSearch(int agvId) {
		return agvStatusSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_STATUS_ALL1_REQ));
	}

	public AppMsg battery(int agvId) {
		return agvStatusSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_STATUS_BATTERY_REQ));
	}

	public AppMsg location(int agvId) {
		return agvStatusSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_STATUS_LOC_REQ));
	}

	@Override
	public AppMsg startup(int agvId) {
		return agvNaviSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_TASK_RESUME_REQ));
	}

	public AppMsg doTask(int agvId, String taskName) {
		JSONObject json = new JSONObject();
		json.put("id", taskName);
		return agvNaviSender.send(agvId, SeerUtil.cmd(SeerUtil.ROBOT_TASK_GOTARGET_REQ, json), "0");
	}

	public boolean isCmdSendWithCmd(int devId, String command, String... targetStatuss) {
		return true;
	}
}
