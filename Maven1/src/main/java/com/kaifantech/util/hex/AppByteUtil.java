package com.kaifantech.util.hex;

import com.calculatedfun.util.AppTool;

import io.netty.buffer.ByteBuf;

public class AppByteUtil {
	public static short bytesToShort2(byte[] b) {
		short i = (short) (b[0] & 0xff);
		return i;
	}

	public static int charToHex(char c) {
		int k = 0;
		if (c >= '0' && c <= '9') {
			k = c - '0';
		} else if (c >= 'A' && c <= 'F') {
			k = c - 'A' + 10;
		} else if (c >= 'a' && c <= 'f') {
			k = c - 'a' + 10;
		} else {
			return -1;
		}
		return k;
	}

	public static String bytesToHex(byte[] bytes) {
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (byte b : bytes) {
			hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(b & 0x0F));
			result += hex + " ";
		}
		return result;
	}

	public static String intToHex8(int i) {
		return String.format("%8s", String.format("%02x", i)).replaceAll("\\s", "0");
	}

	public static String intToHex4Reverse(int i) {
		if (i >= 256 * 256 || i < 0) {
			return null;
		}
		int pre = i % 256;
		int suf = i / 256 % 256;
		return intToHex(pre) + intToHex(suf);
	}

	public static String intToHex2(int i) {
		int pre = i % 256;
		return intToHex(pre);
	}

	public static String intToHex(int i) {
		return String.format("%2s", String.format("%x", i)).replaceAll("\\s", "0");
	}

	public static String intToHex4(int i) {
		if (i >= 256 * 256 || i < 0) {
			return null;
		}
		int pre = i % 256;
		int suf = i / 256 % 256;
		return intToHex(suf) + intToHex(pre);
	}

	public static Integer str4ToIntReverse(String s) {
		if (s.length() != 4) {
			return null;
		}
		return hexToInt(s.substring(2, 4) + s.substring(0, 2));
	}

	public static byte[] hexToBytes(String hex) {
		if (hex == null || hex.equals("")) {
			return null;
		}
		hex = hex.toUpperCase();
		int length = hex.length() / 2;
		char[] hexChars = hex.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	public static byte[] intToBytes(int val) {
		byte[] b = new byte[4];
		b[0] = (byte) (val & 0xff);
		b[1] = (byte) ((val >> 8) & 0xff);
		b[2] = (byte) ((val >> 16) & 0xff);
		b[3] = (byte) ((val >> 24) & 0xff);
		return b;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String getHexStrFrom(ByteBuf in) {
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		String s = bytesToHex(bytes);
		if (!AppTool.isNull(s.trim())) {
			s = s.replaceAll(" ", "");
		}
		return s;
	}

	public static int bytesToInt(byte[] src, int offset) {
		int length = src.length;
		int value = 0x00;
		for (int i = 0; i < length; i++) {
			value = ((src[i] & 0xFF) << ((length - i - 1) * 8)) | value;
		}
		return value;
	}

	public static int hexToInt(String hex) {
		byte[] bs = hexToBytes(hex);
		int val = bytesToInt(bs, 0);
		return val;
	}

	public static double hex4ToPoint2(String hex) {
		if (hex.length() != 4) {
			return 0.0;
		}
		int val1 = hexToInt(hex.substring(0, 2));
		int val2 = hexToInt(hex.substring(2, 4));

		return val1 + ((double) (val2) / 10);
	}

	public static String xorStep2(String hex) {
		int finalVal = 0;
		for (int i = 0; i < hex.length(); i = i + 2) {
			int step = hexToInt(hex.substring(i, i + 2));
			finalVal = finalVal ^ step;
		}
		return intToHex2(finalVal);
	}

	public static String strToUnicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static String unicodeToStr(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			string.append((char) data);
		}
		return string.toString();
	}

	public static String strToHex(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	public static String hexToStr(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	public static void main(String[] args) {
		System.out.println(strToHex("我"));
		System.out.println(hexToStr("7B2273696D706C65223A747275657D"));
		System.out.println(intToHex8(1644));
		System.out.println(intToHex4Reverse(1644));
		System.out.println(intToHex4(1644));
	}
}