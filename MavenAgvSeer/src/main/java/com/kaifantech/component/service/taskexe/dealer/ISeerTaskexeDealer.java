package com.kaifantech.component.service.taskexe.dealer;

import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.taskexe.TaskexeBean;

public interface ISeerTaskexeDealer {
	public void deal(TaskexeBean taskexeBean, SeerAgvMsgTaskBean agvMsg) throws Exception;
}
