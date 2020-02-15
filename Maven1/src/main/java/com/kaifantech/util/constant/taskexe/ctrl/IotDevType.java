package com.kaifantech.util.constant.taskexe.ctrl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.kaifantech.util.log.AppFileLogger;

public class IotDevType {
	public static final String AGV_FANCY_IP = "0";
	public static final String AGV_FANCY_ZIGBEE = "A";
	public static final String AGV_HONGFU = "10";
	public static final String AGV_SEER_STATUS = "S1";
	public static final String AGV_SEER_CTRL = "S2";
	public static final String AGV_SEER_NAVI = "S3";
	public static final String AGV_SEER_CONF = "S4";
	public static final String AGV_SEER_OTHER = "S5";

	public static final String RONOT_ON_AGV = "1";
	public static final String SCAN_ON_AGV = "3";

	public static final String PLC_ON_AGV = "4";
	public static final String PLC_ON_WINDOW = "6";

	public static final String CHARGE_SITE = "5";
	public static final String ROBOT_GOODS_FROM = "9";

	public static final String ZIGBEE_AUTODOOR = "B";
	public static final String ZIGBEE_LIFT = "C";
	public static final String ZIGBEE_LIGHT = "D";

	public static final String DISTRIBUTION = "DS";

	public static List<String> allType(Class<?> cls) {
		List<String> list = new ArrayList<>();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			try {
				list.add(f.get(cls).toString());
				AppFileLogger.warning("属性名：", f.getName(), "；属性值：", f.get(cls), ";字段类型：", f.getGenericType());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
