package com.kaifantech.component.comm.manager.agv;

import org.springframework.stereotype.Service;

import com.calculatedfun.util.msg.AppMsg;

@Service
public interface IAgvManager {
	public AppMsg pause(int agvId);

	public AppMsg startup(int agvId);

	public AppMsg doSend(int agvId, String cmd);
}
