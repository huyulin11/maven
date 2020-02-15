package com.kaifantech.util.agv.msg;

import java.util.ArrayList;
import java.util.List;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.bean.taskexe.TaskexeBean;

public class PiCommand {
	protected List<Integer> safes = new ArrayList<>();

	protected List<Integer> dangers = new ArrayList<>();

	protected String info = "";

	public List<Integer> getSafes() {
		return safes;
	}

	public List<Integer> getDangers() {
		return dangers;
	}

	public PiCommand d(Integer... ones) {
		for (Integer one : ones) {
			dangers.add(one);
		}
		return this;
	}

	public PiCommand s(Integer... ones) {
		for (Integer one : ones) {
			safes.add(one);
		}
		return this;
	}

	/** danger */
	public PiCommand d(TaskexeBean... ones) {
		for (TaskexeBean one : ones) {
			dangers.add(one.getAgvId());
		}
		return this;
	}

	/** danger */
	public PiCommand d(IotClientBean... ones) {
		for (IotClientBean one : ones) {
			dangers.add(one.getId());
		}
		return this;
	}

	/** safe */
	public PiCommand s(TaskexeBean... ones) {
		for (TaskexeBean one : ones) {
			safes.add(one.getAgvId());
		}
		return this;
	}

	/** safe */
	public PiCommand s(IotClientBean... ones) {
		for (IotClientBean one : ones) {
			safes.add(one.getId());
		}
		return this;
	}

	public String getInfo() {
		return info;
	}

	public PiCommand setInfo(Object... piInfos) {
		for (Object piInfo : piInfos) {
			this.info += piInfo;
		}
		return this;
	}

}
