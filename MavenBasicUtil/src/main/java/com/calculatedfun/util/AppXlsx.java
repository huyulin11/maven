package com.calculatedfun.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.callback.CallBackValue;

public class AppXlsx {
	public static void createExcel(Map<String, Object> headsMap, List<?> list, String uploadFileName,
			OutputStream output) {
		if (AppTool.isNull(list))
			return;

		createExcel(headsMap, (JSONArray) JSONArray.toJSON(list), uploadFileName, output);
	}

	@SuppressWarnings({ "resource", "unchecked" })
	public static void createExcel(Map<String, Object> headsMap, JSONArray jsonArray, String uploadFileName,
			OutputStream output) {
		if (AppTool.isNull(jsonArray))
			return;

		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row titleRow = sheet.createRow(0);
		if (AppTool.isNull(headsMap)) {
			List<String> headList = new ArrayList<String>(jsonArray.getJSONObject(0).keySet());
			for (String item : headList) {
				headsMap.put(item, item);
			}
		}
		int t = 0;
		for (Entry<String, Object> entry : headsMap.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof String) {
				titleRow.createCell(t++).setCellValue(value.toString());
			} else if (value instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				titleRow.createCell(t++).setCellValue(map.get("name").toString());
			}
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			Row row = sheet.createRow(i + 1);
			JSONObject json = jsonArray.getJSONObject(i);
			int j = 0;
			for (Entry<String, Object> entry : headsMap.entrySet()) {
				Object value = entry.getValue();
				Object input = json.getString(entry.getKey());
				if (value instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) entry.getValue();
					Object fun = map.get("fun");
					if (!AppTool.isNull(fun) && fun instanceof CallBackValue) {
						CallBackValue callBack = (CallBackValue) fun;
						input = callBack.execute(input);
					}
				}
				row.createCell(j++).setCellValue(input == null ? "" : input.toString());
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			workbook.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}