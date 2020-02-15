package com.kaifantech.component.service.taskexe.ctrl;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;

public interface ISeerCtrlModule extends ICtrlModule {
	public void control(IotClientBean agvBean, SeerAgvMsgTaskBean agvMsg);
}
