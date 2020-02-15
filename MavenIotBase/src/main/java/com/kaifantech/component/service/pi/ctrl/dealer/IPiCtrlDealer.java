package com.kaifantech.component.service.pi.ctrl.dealer;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.util.agv.msg.PiCommand;

public interface IPiCtrlDealer {
	public PiCommand check2Agvs(IotClientBean one, IotClientBean another) throws Exception;
}
