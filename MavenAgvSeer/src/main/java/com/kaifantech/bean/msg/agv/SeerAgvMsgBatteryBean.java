package com.kaifantech.bean.msg.agv;

public class SeerAgvMsgBatteryBean {
	private double battery_level;// number 机器人电池电量, 范围 [0, 1] 是
	private double battery_temp;// number 机器人电池温度, 单位 ℃ 是
	private Boolean charging;// boolean 电池是否正在充电 是
	private double voltage;// number 电压, 单位 V 是
	private double current;// number 电流, 单位 A 是
	private Boolean manual_charge;// boolean
									// 仅指示机器人是否连接手动充电器，不保证电源是否接通，连接手动充电器时不能运动(仅SRC-2000支持)
									// 是
	private Boolean auto_charge;// boolean
								// 仅指示机器人是否连接自动充电桩，不保证电源是否接通(仅SRC-2000支持) 是
	private double battery_cycle;// number 电池循环次数(由电池供应商提供的数据，不保证正确) 是
	private double ret_code;// number API 错误码 是
	private String err_msg;// string 错误信息 是

	public double getBattery_level() {
		return battery_level;
	}

	public void setBattery_level(double battery_level) {
		this.battery_level = battery_level;
	}

	public double getBattery_temp() {
		return battery_temp;
	}

	public void setBattery_temp(double battery_temp) {
		this.battery_temp = battery_temp;
	}

	public Boolean getCharging() {
		return charging;
	}

	public void setCharging(Boolean charging) {
		this.charging = charging;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public Boolean getManual_charge() {
		return manual_charge;
	}

	public void setManual_charge(Boolean manual_charge) {
		this.manual_charge = manual_charge;
	}

	public Boolean getAuto_charge() {
		return auto_charge;
	}

	public void setAuto_charge(Boolean auto_charge) {
		this.auto_charge = auto_charge;
	}

	public double getBattery_cycle() {
		return battery_cycle;
	}

	public void setBattery_cycle(double battery_cycle) {
		this.battery_cycle = battery_cycle;
	}

	public double getRet_code() {
		return ret_code;
	}

	public void setRet_code(double ret_code) {
		this.ret_code = ret_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
}
