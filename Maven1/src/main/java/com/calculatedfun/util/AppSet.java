package com.calculatedfun.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.kaifantech.util.log.AppFileLogger;

public class AppSet {
	public static <T> List<T> valuesToList(Map<?, T> map) {
		List<T> list = new ArrayList<>(map.values());
		return list;
	}

	public static void map2Bean2(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			AppFileLogger.warning("transMap2Bean2 Error " + e);
		}
	}

	public static void map2Bean(Map<String, Object> map, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			AppFileLogger.warning("transMap2Bean Error " + e);
		}
		return;
	}

	public static Map<String, Object> bean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!key.equals("class")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			AppFileLogger.warning("transBean2Map Error " + e);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	/** 泛型转换成数组时尚有问题 */
	public static <T> T[] addInFirst(T[] cmds, T... elements) {
		ArrayList<T> list = new ArrayList<>();
		for (T element : elements) {
			list.add(element);
		}
		for (T cmd : cmds) {
			list.add(cmd);
		}
		if (elements[0] instanceof String) {
			String[] newCmds = new String[list.size()];
			return (T[]) list.toArray(newCmds);
		}
		T[] newCmds = (T[]) new Object[list.size()];
		return list.toArray(newCmds);
	}

	public static String join(List<?> objs) {
		return join("", objs);
	}

	public static String join(String separator, List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i < list.size() - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	public static <T> T first(List<T> list) {
		return first(list, false);
	}

	public static <T> T first(List<T> list, boolean onlyOne) {
		if (AppTool.isNull(list)) {
			return null;
		}
		if (onlyOne && list.size() > 1) {
			System.out.println("数据数量超出预期！");
			return null;
		}
		T taskexeBean = list.get(0);
		return taskexeBean;
	}

	public List<Object> trimList(List<Object> list) {
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();
		for (int i = list.size() - 1; i >= 0; i--) {
			Object o = list.get(i);
			if (list1.indexOf(o) == -1) {
				list1.add(0, o);
				list2.add(i);
			}
		}
		return list2;
	}
}
