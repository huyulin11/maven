package com.kaifantech.component.timer.pi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kaifantech.component.log.AgvStatusDBLogger;
import com.kaifantech.component.service.pi.work.IPiWorkTodoService;
import com.kaifantech.init.sys.params.AppSysParameters;
import com.kaifantech.init.sys.qualifier.UdfQualifier;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.seq.ThreadID;
import com.kaifantech.util.thread.ThreadTool;

@Component
@Lazy(false)
public class AcsPiCtrlTimer {
	private static String timerType = "AGV交通管制器";

	@Autowired
	@Qualifier(UdfQualifier.DEFAULT_PI_WORK_TODO_SERVICE)
	private IPiWorkTodoService piWorkService;

	private int tipsTime = 0;

	public void resolute() {
		Thread.currentThread().setName(timerType + ThreadID.num());
		while (true) {
			try {
				ThreadTool.sleep(1000);
				if (!AppSysParameters.isOpenPi()) {
					if (tipsTime++ > 10) {
						tipsTime = 0;
						AppFileLogger.setWarningTips(0, "系统交通管制功能关闭中，请注意AGV运行情况！！！", 0,
								AgvStatusDBLogger.MSG_LEVEL_INFO);
					}
				} else {
					piWorkService.work();
				}
			} catch (Exception e) {
			}
		}
	}
}
