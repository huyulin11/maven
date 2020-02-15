package com.kaifantech.bean.msg.agv;

public class SeerAgvMsgLocBean {
	private double x;// number 机器人的 x 坐标, 单位 m 是
	private double y;// number 机器人的 y 坐标, 单位 m 是
	private double angle;// number 机器人的 angle 坐标, 单位 rad 是
	private double confidence;// number 机器人的定位置信度, 范围 [0, 1] 是
	private String current_station;// string 机器人当前所在站点的
									// ID（该判断比较严格，机器人必须很靠近某一个站点(<50cm)，否则为空字符，即不处于任何站点）
									// 是
	private String last_station;// string 机器人上一个所在站点的 ID 是
	private Integer ret_code;// number API 错误码 是
	private String err_msg;// string 错误信息 是

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getCurrent_station() {
		return current_station;
	}

	public void setCurrent_station(String current_station) {
		this.current_station = current_station;
	}

	public String getLast_station() {
		return last_station;
	}

	public void setLast_station(String last_station) {
		this.last_station = last_station;
	}

	public Integer getRet_code() {
		return ret_code;
	}

	public void setRet_code(Integer ret_code) {
		this.ret_code = ret_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
}
