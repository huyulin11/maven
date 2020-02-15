package com.kaifantech.init.sys.params;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.DateFactory;
import com.calculatedfun.util.MD5;
import com.kaifantech.init.sys.UdfBusinessInfo;

public class AppConfParameters extends AppBaseParameters {
	private static int agvGeneralSearchInteval = 0;
	private static int agvJudgedTimesInteval = 0;

	private static int detaJudgeSite = 0;

	private static int distanceWaiting = 0;
	private static int distanceTarget = 0;
	private static boolean sendToWms = true;
	private static long expireTime = 0;

	static {
		detaJudgeSite = integer(conf().getOrInit("DETA_JUDGE_SITE", 500));
		sendToWms = flag(conf().getOrInit("SEND_TO_WMS", "FALSE"));
		distanceWaiting = integer(conf().getOrInit("DISTANCE_WAITING", 8000));
		distanceTarget = integer(conf().getOrInit("DISTANCE_TARGET", 5000));
		agvJudgedTimesInteval = integer(conf().getOrInit(CacheKeys.AGV_JUDGED_TIMES_INTEVAL, "500"));
	}

	public static int getGeneralSearchInteval() {
		return agvGeneralSearchInteval;
	}

	public static int setAgvGeneralSearchInteval(int agvGeneralSearchInteval) {
		conf().set(CacheKeys.AGV_GENERAL_SEARCH_INTEVAL, flag(agvGeneralSearchInteval));
		AppConfParameters.agvGeneralSearchInteval = agvGeneralSearchInteval;
		return getGeneralSearchInteval();
	}

	public static void setAgvGeneralSearchInteval() {
		agvGeneralSearchInteval = integer(conf().getOrInit(CacheKeys.AGV_GENERAL_SEARCH_INTEVAL, "200"));
	}

	public static int getAgvJudgedTimesInteval() {
		return agvJudgedTimesInteval;
	}

	public static int detaJudgeSite() {
		return detaJudgeSite;
	}

	public static int distanceWaiting() {
		return distanceWaiting;
	}

	public static int distanceTarget() {
		return distanceTarget;
	}

	public static boolean sendToWms() {
		return sendToWms;
	}

	public static long expireTime() {
		if (verifyAuthorizationCode())
			return 0;
		return expireTime;
	}

	public static boolean verifyAuthorizationCode() {
		JSONObject obj = new JSONObject();
		obj.put("PROJECT_KEY", UdfBusinessInfo.CURRENT_PROJECT);
		String checkCode = MD5.GetMD5Code(obj.toJSONString());
		return checkCode.equals(conf().get("AUTHORIZATION_CODE"));
	}

	public static long expireTime(boolean isNeedAuth) {
		if (verifyAuthorizationCode())
			return 0;

		if (expireTime == 0 && isNeedAuth) {
			expireTime = (System.currentTimeMillis() + DateFactory.getMillSecondsByDat(15));
			expireTime = Long.parseLong(conf().getOrInit("EXPIRE_TIME", expireTime));
			if (expireTime == 0) {
				expireTime = (System.currentTimeMillis() + (DateFactory.getMillSecondsByDat(15)));
				conf().set("EXPIRE_TIME", expireTime);
			} else if (expireTime >= System.currentTimeMillis() + DateFactory.getMillSecondsByDat(60)) {
				expireTime = (System.currentTimeMillis() + (DateFactory.getMillSecondsByDat(60)));
				conf().set("EXPIRE_TIME", expireTime);
			}
		}
		return expireTime;
	}
}