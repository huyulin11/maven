package com.kaifantech.util.agv.msg;

import java.util.ArrayList;
import java.util.List;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.util.agv.taskpath.DistanceStatus;

public class PiCommandMsg extends PiCommand {
	private DistanceStatus distanceStatus;

	private List<SeerAgvMsgTaskBean> safeMsgs = new ArrayList<>();

	private List<SeerAgvMsgTaskBean> dangerMsgs = new ArrayList<>();

	public DistanceStatus getDistanceStatus() {
		return distanceStatus;
	}

	public void setDistanceStatus(DistanceStatus distanceStatus) {
		this.distanceStatus = distanceStatus;
	}

	public List<SeerAgvMsgTaskBean> getSafeMsgs() {
		return safeMsgs;
	}

	public List<SeerAgvMsgTaskBean> getDangerMsgs() {
		return dangerMsgs;
	}

	public PiCommand dangerous(SeerAgvMsgTaskBean msgOne) {
		if (!AppTool.isNull(msgOne)) {
			getDangerMsgs().add(msgOne);
		}
		return this;
	}

	public PiCommand safe(SeerAgvMsgTaskBean msgOne) {
		if (!AppTool.isNull(msgOne)) {
			getSafeMsgs().add(msgOne);
		}
		return this;
	}
}
