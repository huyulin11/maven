package com.kaifantech.util.msg.agv;

import java.util.HashMap;
import java.util.Map;

import com.kaifantech.bean.msg.agv.SeerAgvMsgBatteryBean;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.msg.agv.SeerAgvMsgAll1Bean;
import com.kaifantech.bean.msg.agv.SeerAgvMsgTaskBean;
import com.kaifantech.bean.msg.agv.transfer.SeerAgvBatteryBeanTransfer;
import com.kaifantech.bean.msg.agv.transfer.SeerAgvAll1BeanTransfer;
import com.kaifantech.bean.msg.agv.transfer.SeerAgvMsgBeanTransfer;
import com.kaifantech.component.comm.manager.agv.SeerUtil;
import com.kaifantech.util.agv.msg.Direction;
import com.kaifantech.util.hex.AppByteUtil;

public class SeerAgvMsgGetter extends AcsAgvMsgGetter {
	private static Map<Integer, SeerAgvMsgTaskBean> latestStatusMap = new HashMap<>();
	private static Map<Integer, SeerAgvMsgBatteryBean> latestBatteryMap = new HashMap<>();
	private static Map<Integer, SeerAgvMsgAll1Bean> latestAll1Map = new HashMap<>();

	public static void setMsg(Integer agvId, String msg) {
		AcsAgvMsgGetter.setMsg(agvId, msg);
		cal(agvId, msg);
	}

	public static SeerAgvMsgTaskBean getStatusBean(Integer agvId) {
		SeerAgvMsgTaskBean msg = latestStatusMap.get(agvId);
		return msg;
	}

	public static SeerAgvMsgBatteryBean getBatteryBean(Integer agvId) {
		SeerAgvMsgBatteryBean msg = latestBatteryMap.get(agvId);
		return msg;
	}

	public static SeerAgvMsgAll1Bean getAll1Bean(Integer agvId) {
		SeerAgvMsgAll1Bean msg = latestAll1Map.get(agvId);
		return msg;
	}

	public static Direction getDirection(Integer agvId) {
		return null;
	}

	public static void cal(Integer agvId, String msgStr) {
		try {
			if (AppTool.isNull(msgStr) || msgStr.length() < 20)
				return;
			int msgType = AppByteUtil.hexToInt(msgStr.substring(16, 20));
			switch (msgType) {
			case SeerUtil.ROBOT_STATUS_TASK_RES: {
				SeerAgvMsgTaskBean currentMsgBean = SeerAgvMsgBeanTransfer.transToBean(agvId, msgStr);
				latestStatusMap.put(agvId, currentMsgBean);
				break;
			}
			case SeerUtil.ROBOT_STATUS_BATTERY_RES: {
				SeerAgvMsgBatteryBean currentBatteryBean = SeerAgvBatteryBeanTransfer.transToBean(agvId, msgStr);
				latestBatteryMap.put(agvId, currentBatteryBean);
				break;
			}
			case SeerUtil.ROBOT_STATUS_ALL1_RES: {
				SeerAgvMsgAll1Bean currentAll1Bean = SeerAgvAll1BeanTransfer.transToBean(agvId, msgStr);
				latestAll1Map.put(agvId, currentAll1Bean);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
