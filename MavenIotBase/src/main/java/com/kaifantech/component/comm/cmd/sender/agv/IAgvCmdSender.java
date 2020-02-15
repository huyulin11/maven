package com.kaifantech.component.comm.cmd.sender.agv;

import com.calculatedfun.util.msg.AppMsg;

public interface IAgvCmdSender {
	AppMsg send(Integer agvId, String... cmds);
}
