package com.calculatedfun.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class AppFile {
	public static String Write(String content, String filepath) {
		try {
			FileOutputStream o = null;
			o = new FileOutputStream(filepath, true);
			o.write(content.getBytes("UTF-8"));
			o.write("\r\n".getBytes("UTF-8"));
			o.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getOrCreateFile(String path, String filename) throws IOException {
		String newPath = path;
		File dir = new File(newPath);
		if (!AppTool.isNullStr(newPath)) {
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		newPath = newPath + "/" + filename;
		File file = new File(newPath);
		if (!file.exists()) {
			file.createNewFile();
		}
		return newPath;
	}

	public static void createFile(String path, String filename, String content) throws IOException {
		FileOutputStream o = null;
		AppFile.getOrCreateFile(path, filename);
		if (!AppTool.isNull(content)) {
			o = new FileOutputStream(path + "/" + filename, false);
			o.flush();
			o.write(content.getBytes("UTF-8"));
			o.write("\r\n".getBytes("UTF-8"));
			o.close();
		}
	}

	public static void createJsonFile(String path, String filename, String content) throws IOException {
		JSONObject jsonMap = (JSONObject) JSONObject.parse(content);
		createFile(path, filename, jsonMap.toJSONString());
	}
}
