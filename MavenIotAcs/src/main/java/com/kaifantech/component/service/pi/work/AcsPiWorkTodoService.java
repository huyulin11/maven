package com.kaifantech.component.service.pi.work;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.component.dao.agv.info.AgvInfoDao;
import com.kaifantech.component.service.iot.client.IIotClientService;
import com.kaifantech.component.service.pi.ctrl.dealer.IPiCtrlDealer;
import com.kaifantech.component.service.taskexe.add.ITaskexeAddService;
import com.kaifantech.init.sys.qualifier.AcsQualifier;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.agv.msg.PiCommand;
import com.kaifantech.util.log.AppFileLogger;

@Service(AcsQualifier.PI_WORK_TODO_SERVICE)
public class AcsPiWorkTodoService implements IPiWorkTodoService {

	@Autowired
	protected IPiWorkDoneService piWorkDoneService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_AGV_INFO_DAO)
	protected AgvInfoDao agvInfoDao;

	@Autowired
	protected IPiCtrlDealer piCtrlDealer;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_TASKEXE_ADD_SERVICE)
	protected ITaskexeAddService taskexeService;

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_IOT_CLIENT_SERVICE)
	protected IIotClientService iotClientService;

	public void doControl() {
		List<IotClientBean> agvs = iotClientService.getAgvCacheList();

		for (int i = 0; i < agvs.size(); i++) {
			IotClientBean one = agvs.get(i);
			for (int j = i + 1; j < agvs.size(); j++) {
				IotClientBean another = agvs.get(j);
				control2Agvs(one, another);
			}
		}
	}

	public void control2Agvs(IotClientBean one, IotClientBean another) {
		if (!isNeedCtrl(one, another)) {
			return;
		}
		PiCommand command = null;
		try {
			command = piCtrlDealer.check2Agvs(one, another);
		} catch (Exception e) {
			e.printStackTrace();
			AppFileLogger.setPiTips(0, "对", one.getId(), "号agv和", another.getId(), "号agv进行交管时发生错误：", e.getMessage());
			return;
		}
		if (AppTool.isNull(command)) {
			return;
		}
		List<Integer> dangerMsgs = command.getDangers();
		for (Integer id : dangerMsgs) {
			piWorkDoneService.addToStop(id);
		}

		// List<Integer> safeMsgs = command.getSafes();
		// for (Integer id : safeMsgs) {
		// if (!dangerMsgs.contains(id)) {
		// piWorkDoneService.addToContinue(id);
		// }
		// }

		List<IotClientBean> agvs = iotClientService.getAgvCacheList();
		for (IotClientBean single : agvs) {
			if (!dangerMsgs.contains(single.getId())) {
				piWorkDoneService.addToContinue(single.getId());
			}
		}
	}

	public boolean isNeedCtrl(IotClientBean one, IotClientBean another) {
		if (one.getEnvironment() != another.getEnvironment()) {
			return false;
		}
		return true;
	}

	@Override
	public void work() {
		doControl();
		piWorkDoneService.doneControl();
	}
}