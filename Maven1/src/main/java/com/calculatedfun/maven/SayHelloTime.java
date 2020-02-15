package com.calculatedfun.maven;

public class SayHelloTime {
	public String sayTime(String name, String time) {
		SayHello say = new SayHello();
		return say.say("zs") + "," + time;
	}
}
