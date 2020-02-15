package com.calculatedfun.util;

import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;

public class MD5EX {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String str = input.next();

		JSONObject obj = new JSONObject();
		obj.put("PROJECT_KEY", str);
		System.out.println(obj.toJSONString());
		System.out.println(MD5.GetMD5Code(obj.toJSONString()));
	}
}
