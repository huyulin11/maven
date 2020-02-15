package com.kaifantech.component.timer.data.cache;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppFile;
import com.calculatedfun.util.DateFactory;
import com.kaifantech.init.sys.UdfBusinessInfo;
import com.kaifantech.init.sys.RelativeProgramsStart;
import com.kaifantech.init.sys.dao.UdfDbs;
import com.kaifantech.init.sys.dao.AppTablesInit;
import com.kaifantech.init.sys.dao.DbTypes;
import com.kaifantech.util.log.AppFileLogger;

public abstract class AbstractDataCacheTimer implements ApplicationListener<ContextRefreshedEvent> {
	protected static String timerType = "数据缓存器";
	protected final Logger logger = Logger.getLogger(AbstractDataCacheTimer.class);

	public AbstractDataCacheTimer() {
		logger.info(timerType + "开始启动！");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		AppFileLogger.warning(UdfBusinessInfo.CURRENT_PROJECT + "项目初始化………………");
		RelativeProgramsStart.open();
		try {
			UdfBusinessInfo.createStaticFile();
			if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL))
				AppTablesInit.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0/2 * * * * ?")
	public void sysInfo() throws Exception {
		sysInfo2Json();
	}

	protected void sysInfo2Json() throws Exception {
		JSONObject times = new JSONObject();
		times.put("current", DateFactory.getCurrentUnixTime());
		String info = times.toJSONString();
		AppFile.createFile(UdfBusinessInfo.getProjJsonsPath() + "", "sysInfo.json", info);
	}
}
