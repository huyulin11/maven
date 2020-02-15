package com.calculatedfun.util.msg;

import com.calculatedfun.util.AppTool;

public class AppMsg {
	private Integer code;
	private String msg = "";
	private boolean isSuccess = false;
	private Object object;

	public AppMsg() {
	}

	public AppMsg(MsgFromWMS msgFromWMS) {
	}

	public static AppMsg success() {
		return success("");
	}

	public static AppMsg success(String msgStr) {
		AppMsg msg = new AppMsg();
		msg.setSuccess(true);
		msg.setMsg(msgStr);
		msg.setCode(1);
		return msg;
	}

	public static AppMsg fail() {
		return fail("");
	}

	public static AppMsg fail(String msgStr) {
		AppMsg msg = new AppMsg();
		msg.setSuccess(false);
		msg.setMsg(msgStr);
		msg.setCode(-1);
		return msg;
	}

	public AppMsg(Integer code, Object... msgs) {
		for (Object msg : msgs) {
			this.msg += msg;
		}
		this.code = code;
		setSuccess(code >= 0);
	}

	public Integer getCode() {
		return code;
	}

	public AppMsg setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public AppMsg setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getObject() {
		return object;
	}

	public AppMsg setObject(Object object) {
		this.object = object;
		return this;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public boolean isFail() {
		return !isSuccess;
	}

	public AppMsg setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		return this;
	}

	public net.sf.json.JSONObject toJson() {
		net.sf.json.JSONObject json = new net.sf.json.JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("object", net.sf.json.JSONObject.fromObject(object));
		return json;
	}

	public com.alibaba.fastjson.JSONObject toAlbbJson() {
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("object", com.alibaba.fastjson.JSONObject.toJSON(object));
		return json;
	}

	public String toString() {
		return (!AppTool.isNull(code) ? "code：" + code : "") + (!AppTool.isNull(msg) ? "msg：" + msg : "");
	}
}