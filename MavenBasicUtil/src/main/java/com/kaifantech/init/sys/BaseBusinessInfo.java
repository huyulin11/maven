package com.kaifantech.init.sys;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppFile;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.DateFactory;
import com.kaifantech.cache.manager.AppCache;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.init.sys.params.AppConfParameters;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.os.OSinfo;

public class BaseBusinessInfo {
	static {
		AppCache.worker().del(UdfQualifier.DEFAULT_SERVICE_PREFIX + "_socket%");
	}

	public enum Clients {
		CSY, YUFENG, INOMA, LAO, HONGFU, TAIKAI, BJJK, KF
	}

	public enum Projects {
		HAITIAN_DRIVER, CANGZHOU, HAITIAN_JINGGONG, INOMA_WEIWEI, CSY_CDBP, CSY_DAJ, YUFENG_WUXI, LAO_FOXCONN, LAO_DBWY, HONGFU_ZHENMU, TAIKAI_JY, BJJK_HUIRUI, KF_TEST
	}

	public static String getJsonsPath() {
		String defaultPath = OSinfo.isWindows() ? "D:/acs/nginx-1.10.1/html/static/s/jsons/"
				: "/usr/local/nginx/html/static/s/jsons/";
		String path = AppConf.worker().getOrInit("NGINX_PATH", defaultPath);
		return path;
	}

	public static String getProjJsonsPath() {
		return getJsonsPath() + UdfBusinessInfo.CURRENT_PROJECT + "/";
	}

	public static void createStaticFile() throws IOException {
		try {
			String filename = "projectKey.json";
			JSONObject jsonMap = new JSONObject();
			jsonMap.put("PROJECT_KEY", UdfBusinessInfo.CURRENT_PROJECT);
			jsonMap.put("TIME_STAMP", DateFactory.getCurrTime());
			jsonMap.put("ACS_CONTROL", getFromDBConf("ACS_CONTROL"));
			jsonMap.put("AGV_CONTROL", getFromDBConf("AGV_CONTROL"));
			long expireTime = 0;
			if (!AppTool.equals(UdfBusinessInfo.CURRENT_PROJECT, Projects.CSY_DAJ)) {
				expireTime = AppConfParameters.expireTime(true);
			}
			jsonMap.put("EXPIRE_TIME", expireTime);
			String content = jsonMap.toJSONString();
			AppFile.createJsonFile(getJsonsPath(), filename, content);

			content = getFromDBConf("PROJECT_INFO");
			filename = "projectInfo.json";
			AppFile.createJsonFile(getProjJsonsPath(), filename, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getFromDBConf(String key) {
		String content = AppConf.worker().get(key);
		if (AppTool.isNull(content)) {
			AppFileLogger.warning("项目前端配置信息" + key + "为空，请注意维护！");
			AppConf.worker().set(key, "");
			System.exit(0);
		}
		return content;
	}
}
