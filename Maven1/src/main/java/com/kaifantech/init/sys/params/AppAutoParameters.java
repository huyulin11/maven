package com.kaifantech.init.sys.params;

public class AppAutoParameters extends AppBaseParameters {
	private static boolean isAutoTask = false;
	private static boolean isAutoCharge = false;
	private static boolean isErrBack = false;

	private static String chargeQuantity = "24.7";

	public static boolean isAutoTask() {
		return isAutoTask;
	}

	public static boolean setAutoTask(boolean isAutoTask) {
		if (!isAutoTask) {
			cache().set(CacheKeys.IS_AUTO_CHARGE, flag(false));
			conf().set(CacheKeys.IS_AUTO_CHARGE, flag(false));
			cache().set(CacheKeys.IS_ERR_BACK, flag(false));
			conf().set(CacheKeys.IS_ERR_BACK, flag(false));
		}
		cache().set(CacheKeys.IS_AUTO_TASK, flag(isAutoTask));
		conf().set(CacheKeys.IS_AUTO_TASK, flag(isAutoTask));
		AppAutoParameters.isAutoTask = isAutoTask;
		return isAutoTask;
	}

	public static void setAutoTask() {
		String flag = cache().getOrInit(CacheKeys.IS_AUTO_TASK, conf().getOrInit(CacheKeys.IS_AUTO_TASK, flag(true)));
		AppAutoParameters.isAutoTask = flag(flag);
	}

	public static boolean isAutoCharge() {
		if (!isAutoTask) {
			return false;
		}
		return isAutoCharge;
	}

	public static boolean setAutoCharge(boolean isAutoCharge) {
		cache().set(CacheKeys.IS_AUTO_CHARGE, flag(isAutoCharge));
		conf().set(CacheKeys.IS_AUTO_CHARGE, flag(isAutoCharge));
		AppAutoParameters.isAutoCharge = isAutoCharge;
		return isAutoCharge();
	}

	public static void setAutoCharge() {
		String flag = cache().getOrInit(CacheKeys.IS_AUTO_CHARGE, conf().getOrInit(CacheKeys.IS_AUTO_CHARGE, "TRUE"));
		AppAutoParameters.isAutoCharge = flag(flag);
	}

	public static boolean isErrBack() {
		if (!isAutoTask) {
			return false;
		}
		return isErrBack;
	}

	public static boolean setErrBack(boolean isErrBack) {
		cache().set(CacheKeys.IS_ERR_BACK, flag(isErrBack));
		conf().set(CacheKeys.IS_ERR_BACK, flag(isErrBack));
		AppAutoParameters.isErrBack = isErrBack;
		return isErrBack();
	}

	public static void setErrBack() {
		AppAutoParameters.isErrBack = flag(
				cache().getOrInit(CacheKeys.IS_ERR_BACK, conf().getOrInit(CacheKeys.IS_ERR_BACK, "FALSE")));
	}

	public static Double getChargeQuantity() {
		AppAutoParameters.chargeQuantity = cache().getOrInit(CacheKeys.AGV_CHAGRE_BATTRY_QUANTITY, "24.7");
		return Double.parseDouble(chargeQuantity);
	}

	public static void setChargeQuantity(String chargeQuantity) {
		cache().set(CacheKeys.AGV_CHAGRE_BATTRY_QUANTITY, chargeQuantity);
	}
}