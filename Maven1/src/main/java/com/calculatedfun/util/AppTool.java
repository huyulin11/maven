package com.calculatedfun.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class AppTool {
	public static boolean isNullStr(String str) {
		if (null == str || "".equals(str) || " ".equals(str) || "undefined".equals(str)) {
			return true;
		}
		return false;
	}

	public static <T> T v(T obj, T value) {
		if (null == obj || "".equals(obj) || " ".equals(obj) || "undefined".equals(obj)) {
			return value;
		}
		return obj;
	}

	public static String v(Object obj) {
		if (null == obj || "".equals(obj) || " ".equals(obj) || "undefined".equals(obj)) {
			return "";
		}
		return obj.toString();
	}

	public static String v(String str) {
		if (isNullStr(str)) {
			return "";
		}
		return str;
	}

	public static boolean isNull(List<?> obj) {
		if (null == obj || obj.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Map<?, ?> obj) {
		if (null == obj || obj.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Object obj) {
		if (null == obj || "".equals(obj.toString())) {
			return true;
		}
		return false;
	}

	public static boolean and(boolean... ifs) {
		for (Boolean one : ifs) {
			if (!one) {
				return false;
			}
		}
		return true;
	}

	public static boolean or(boolean... ifs) {
		for (Boolean one : ifs) {
			if (one) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAllNull(Object... objs) {
		if (null == objs || "".equals(objs.toString())) {
			return true;
		}
		if (objs.length > 0) {
			for (Object obj : objs) {
				if (!isNull(obj)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isAnyNull(Object... objs) {
		if (null == objs || "".equals(objs.toString())) {
			return false;
		}
		if (objs.length > 0) {
			for (Object obj : objs) {
				if (isNull(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isAnyNull(List<?>... objs) {
		if (null == objs) {
			return false;
		}
		if (objs.length > 0) {
			for (List<?> obj : objs) {
				if (isNull(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean equals(Object obj, Object... objs) {
		if (null == objs || obj == null || objs.length == 0) {
			return false;
		}
		for (Object tmp : objs) {
			if (obj.equals(tmp)) {
				return true;
			}
		}
		return false;
	}

	public static boolean notEquals(Object obj, Object... objs) {
		if (null == objs || obj == null || objs.length == 0) {
			return true;
		}
		for (Object tmp : objs) {
			if (obj.equals(tmp)) {
				return false;
			}
		}
		return true;
	}

	/** 从小到大 */
	public static boolean inOrder(double... objs) {
		for (int i = 0; i < objs.length - 1; i++) {
			if (objs[i + 1] < objs[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean isNullObj(Object obj) {
		if (null == obj || (obj instanceof String && "".equals(obj.toString()))) {
			return true;
		}
		return false;
	}

	public static BigDecimal strTobigDecimal(String str) {
		str = str.replace(",", "");
		return new BigDecimal(str);
	}

	public static String join(Object... objs) {
		return join("", objs);
	}

	public static String join(Object separator, List<Object> list) {
		Object[] objs = new Object[list.size()];
		list.toArray(objs);
		return join(separator, objs);
	}

	public static String join(Object separator, Object... objs) {
		if (null == objs || "".equals(objs.toString())) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if (objs.length > 0) {
			for (Object obj : objs) {
				if (!isNull(obj)) {
					if (sb.length() != 0)
						sb.append(separator);
					sb.append(obj);
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws ParseException {
		Integer id = 479;
		long ii = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
			AppTool.equals(id, 464, 465, 392, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361,
					362, 363, 364, 365, 366, 367, 368, 369);
		}
		System.out.println(System.currentTimeMillis() - ii);
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String digitUppercase(BigDecimal m) {
		double n = m.doubleValue();
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("^(零.)+", "").replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "零").replaceAll("^整$",
				"零元整");
	}

	/**
	 * 去除字符串最后一个逗号,若传入为空则返回空字符串
	 * 
	 * @descript
	 * @param para
	 * @return
	 * 
	 * @date 2015年3月29日
	 * @version 1.0
	 */
	public static String trimComma(String para) {
		if (StringUtils.isNotBlank(para)) {
			if (para.endsWith(",")) {
				return para.substring(0, para.length() - 1);
			} else {
				return para;
			}
		} else {
			return "";
		}
	}

	/**
	 * 金额格式化
	 * 
	 * @param getMoney
	 *            金额
	 * @return 格式后的金额
	 */
	public static String formatMoney(Double getMoney) {
		String balance = "0.00";
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		if (null == getMoney || 0 == getMoney) {
			balance = "0.00";
		} else if (getMoney > 0 & getMoney < 1) {
			balance = "0" + myformat.format(getMoney);
		} else if (getMoney < 0 & getMoney > -1) {
			balance = "-0" + myformat.format(getMoney).substring(1);
		} else {
			balance = myformat.format(getMoney);
		}
		return balance;
	}

	/**
	 * 金额去掉“,”
	 * 
	 * @param s
	 *            金额
	 * @return 去掉“,”后的金额
	 */
	public static String delComma(String s) {
		String formatString = "";
		if (s != null && s.length() >= 1) {
			formatString = s.replaceAll(",", "");
		}
		return formatString;
	}

	public static String formatStr(String str) {
		if ("".equals(str)) {
			return str;
		} else if (str.startsWith("\"")) {
			return str;
		} else if (str.startsWith("[")) {
			str = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
			if (str.contains("\"\"")) {
				str = str.replace("\"\"", "%%");
			}
			str = str.replace("\"", "");
			str = str.replace("%%", "");
			return str;
		}
		return "";
	}
}
