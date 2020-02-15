package com.kaifantech.init.sys.params;

public class AppCtrlParameters extends AppBaseParameters {
	private static boolean isOneUserOneLogin = flag(conf().getOrInit("IS_ONE_USER_ONE_LOGIN", "FALSE"));;

	public synchronized static boolean isOneUserOneLogin() {
		return isOneUserOneLogin;
	}

	private static boolean isOneUserTickOff = flag(conf().getOrInit("IS_ONE_USER_TICK_OFF", "FALSE"));;

	public synchronized static boolean isOneUserTickOff() {
		return isOneUserTickOff;
	}

	private static boolean isCheckFromStatusWhenUpdateAlloc = flag(
			conf().getOrInit("IS_CHECK_FROM_STATUS_WHEN_UPDATE_ALLOC", "FALSE"));

	public synchronized static boolean isCheckFromStatusWhenUpdateAlloc() {
		return isCheckFromStatusWhenUpdateAlloc;
	}

	private static int timesPreStartup = 0;
	private static int timesProtectFromStartup = 0;

	static {
		timesPreStartup = integer(conf().getOrInit("TIMES_PRE_STARTUP", 5000));
		timesProtectFromStartup = integer(conf().getOrInit("TIMES_PROTECT_FROM_STARTUP", 5000));
	}

	public static int timesPreStartup() {
		return timesPreStartup;
	}

	public static int timesProtectFromStartup() {
		return timesProtectFromStartup;
	}
}