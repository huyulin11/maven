package com.kaifantech.component.timer.taskexe.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kaifantech.component.service.taskexe.auto.IAutoModule;
import com.kaifantech.util.seq.ThreadID;
import com.kaifantech.util.thread.ThreadTool;

@Component
@Lazy(false)
public class AcsAutoTimer {
	private static String timerType = "任务生成定时器（业务->任务）";

	@Autowired
	private IAutoModule autoTaskexeModule;

	public void resolute() {
		Thread.currentThread().setName(timerType + ThreadID.num());
		while (true) {
			ThreadTool.sleep(2000);
			try {
				autoTaskexeModule.doWork();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
