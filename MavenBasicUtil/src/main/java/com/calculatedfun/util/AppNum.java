package com.calculatedfun.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppNum {
	@SuppressWarnings("unchecked")
	public static double max(Object... objs) {
		double max = Double.MIN_VALUE;
		for (Object obj : objs) {
			if (obj instanceof Double) {
				max = Math.max(max, (double) obj);
			} else if (obj instanceof Collection) {
				List<Double> list = (List<Double>) obj;
				if (!AppTool.isNull(list))
					max = Math.max(max, Collections.max(list));
			} else {
				throw new ClassCastException();
			}
		}
		return max;
	}

	@SuppressWarnings("unchecked")
	public static double min(Object... objs) {
		double min = Double.MAX_VALUE;
		for (Object obj : objs) {
			if (obj instanceof Double) {
				min = Math.min(min, (double) obj);
			} else if (obj instanceof Collection) {
				List<Double> list = (List<Double>) obj;
				if (!AppTool.isNull(list))
					min = Math.min(min, Collections.min(list));
			} else {
				throw new ClassCastException();
			}
		}
		return min;
	}
}
