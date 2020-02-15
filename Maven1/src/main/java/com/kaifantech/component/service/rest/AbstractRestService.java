package com.kaifantech.component.service.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.kaifantech.cache.manager.AppConf;
import com.kaifantech.init.sys.SystemURL;
import com.kaifantech.init.sys.params.AppConfParameters;
import com.kaifantech.util.log.AppFileLogger;
import com.kaifantech.util.thread.ThreadTool;

@Component
public class AbstractRestService {
	protected synchronized String load(String url, String str, int repeatTimes) {
		if (!AppConfParameters.sendToWms()) {
			return null;
		}

		if (AppTool.isNull(str)) {
			return null;
		}

		int times = 0;
		String rtnInfo = "";
		while (times++ < repeatTimes) {
			try {
				String response = load(url, str);
				rtnInfo = response;
				JSONObject json = JSONObject.parseObject(response);
				if (json.get("Success").equals("true")) {
					break;
				}
			} catch (Exception e) {
				rtnInfo = e.getMessage() + "同步失败，内容" + str;
			}
			ThreadTool.sleep(5000);
		}
		str = str.length() > 100 ? str.substring(0, 100) : str;
		rtnInfo = rtnInfo.length() > 500 ? rtnInfo.substring(0, 500) : rtnInfo;
		AppConf.worker().hset("DE_LOGS", str, rtnInfo);
		return rtnInfo;
	}

	private synchronized static String load(String url, String query) throws Exception {
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(false);
		conn.setConnectTimeout(2000);
		conn.setReadTimeout(2000);
		PrintStream ps = new PrintStream(conn.getOutputStream(), false, "UTF-8");
		ps.print(new String(query.getBytes("UTF-8")));
		ps.close();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line, resultStr = "";
		while (null != (line = bReader.readLine())) {
			resultStr += line;
		}
		bReader.close();
		return new String(resultStr);
	}

	public static void main2(String[] args) throws Exception {
		String ss = "哈哈哈";
		AppFileLogger.warning(ss);
		AppFileLogger.warning(new String(ss.getBytes("UTF-8")));
	}

	public static void main(String[] args) {
		try {
			while (true) {
				String tosend = "{\"PaperId\":\"122333333\",\"ItemList\": [" + "{\"AllocInfo\":\"02-02-02-03\"},"
						+ "{\"AllocInfo\":\"02-02-02-04\"},{\"AllocInfo\":\"02-02-02-06\"}" + "]}";
				new AbstractRestService().load(SystemURL.INVENTORY_RESULT_TO_WMS, tosend, 0);
				ThreadTool.sleepOneSecond();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
