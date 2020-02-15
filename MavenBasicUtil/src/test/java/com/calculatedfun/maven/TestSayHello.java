package com.calculatedfun.maven;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSayHello {
	@Test
	public void test() {
		SayHelloTime say = new SayHelloTime();
		String result = say.sayTime("zs", "morning");
		assertEquals("hello,zs,morning", result);
	}
}
