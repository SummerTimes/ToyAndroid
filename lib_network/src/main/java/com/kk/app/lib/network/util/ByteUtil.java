package com.kk.app.lib.network.util;

/**
 * @author kk
 * @date 2015-11-18
 * @desc 字节操作工具
 */
public class ByteUtil {

	/**
	 * 将byte数组转换为16进制字符串
	 * <br/>
	 * 实现思路：
	 * 先将byte转换成int,再使用Integer.toHexString(int)
	 * @param data	byte数组
	 * @return
	 */
	public static String byteToHex(byte[] data, int start, int end) {
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < end; i++) {
			int tmp = data[i] & 0xff;
			String hv = Integer.toHexString(tmp);
			if(hv.length() < 2) {
				builder.append("0");
			}
			builder.append(hv);
			/*builder.append(" ");*/
			if(i % 16 == 15) {
				/*builder.append("\n");*/
			}
		}
		return builder.toString();
	}

	/**
	 * 将byte数组转换为16进制字符串(该字符串方便查看)
	 * 输出信息版：16个字节一行显示
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 */
	public static String byteToHexforPrint(byte[] data, int start, int end) {
		StringBuilder builder = new StringBuilder();
		for(int i = start; i < end; i++) {
			int tmp = data[i] & 0xff;
			String hv = Integer.toHexString(tmp);
			if(hv.length() < 2) {
				builder.append("0");
			}
			builder.append(hv);
			builder.append(" ");
			if(i % 16 == 15) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	/**
	 * 十六进制字符串转换为字节数组
	 * @param hexStr	十六进制字符串
	 * @return			字节数组
	 */
	public static byte[] hexToByte(String hexStr) {
		byte[] datas = new byte[(hexStr.length() - 1) / 2 + 1];
		hexStr = hexStr.toUpperCase();
		int pos = 0;
		for(int i = 0; i < hexStr.length(); i+=2) {
			if(i + 1 < hexStr.length()) {
				datas[pos] = (byte) ((indexOf(hexStr.charAt(i)+"") << 4) + indexOf(hexStr.charAt(i+1)+""));
			}
			pos++;
		}
		return datas;
	}

	/**
	 * 计算指定字符串（这里要求是字符）的16进制所表示的数字
	 * @param str
	 * @return
	 */
	public static int indexOf(String str) {
		return "0123456789ABCDEF".indexOf(str);
	}

	/**
	 * 计算byte数组所表示的值，字节数组的值以小端表示，低位在低索引上，高位在高索引
	 * <br/>
	 * 例：data = {1,2},那么结果为: 2 << 8 + 1 = 513
	 * @param data	byte数组
	 * @return		计算出的值
	 */
	public static long lowByteToLong(byte[] data) {
		long sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * i));
			sum += value;
		}
		return sum;
	}

	/**
	 * 计算byte数组所表示的值，字节数组的值以大端表示，低位在高索引上，高位在低索引
	 * <br/>
	 * 例：data = {1,2},那么结果为: 1 << 8 + 2 = 258
	 * @param data	byte数组
	 * @return		计算出的值
	 */
	public static long highByteToLong(byte[] data) {
		long sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * (data.length - i - 1)));
			sum += value;
		}
		return sum;
	}

	/**
	 * 计算byte数组所表示的值，字节数组的值以小端表示，低位在低索引上，高位在高索引
	 * <br/>
	 * 例：data = {1,2},那么结果为: 2 << 8 + 1 = 513
	 * @param data	byte数组
	 * @return		计算出的值
	 */
	public static int lowByteToInt(byte[] data) {
		int sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * i));
			sum += value;
		}
		return sum;
	}

	/**
	 * 计算byte数组所表示的值，字节数组的值以大端表示，低位在高索引上，高位在低索引
	 * <br/>
	 * 例：data = {1,2},那么结果为: 1 << 8 + 2 = 258
	 * @param data	byte数组
	 * @return		计算出的值
	 */
	public static int highByteToInt(byte[] data) {
		int sum = 0;
		for(int i = 0; i < data.length; i++) {
			long value = ((data[i] & 0xff) << (8 * (data.length - i - 1)));
			sum += value;
		}
		return sum;
	}

	/**
	 * long值转换为指定长度的小端字节数组
	 * @param data		long值
	 * @param len		长度
	 * @return			字节数组,小端形式展示
	 */
	public static byte[] longToLowByte(long data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * i )) & 0xff);
		}
		return value;
	}

	/**
	 * long值转换为指定长度的大端字节数组
	 * @param data		long值
	 * @param len		长度
	 * @return			字节数组,大端形式展示
	 */
	public static byte[] longToHighByte(long data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * (len - 1 - i) )) & 0xff);
		}
		return value;
	}

	/**
	 * int值转换为指定长度的小端字节数组
	 * @param data		int值
	 * @param len		长度
	 * @return			字节数组,小端形式展示
	 */
	public static byte[] intToLowByte(int data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * i )) & 0xff);
		}
		return value;
	}

	/**
	 * int值转换为指定长度的大端字节数组
	 * @param data		int值
	 * @param len		长度
	 * @return			字节数组,大端形式展示
	 */
	public static byte[] intToHighByte(int data, int len) {
		byte[] value = new byte[len];
		for(int i = 0; i < len; i++) {
			value[i] = (byte) ((data >> (8 * (len - 1 - i) )) & 0xff);
		}
		return value;
	}

	/**
	 * 计算base的exponent次方
	 * @param base  	基数
	 * @param exponent	指数
	 * @return
	 */
	public static long power(int base, int exponent) {
		long sum = 1;
		for(int i = 0; i < exponent; i++) {
			sum *= base;
		}
		return sum;
	}

	public static void main(String[] args) {
		byte[] data = new byte[]{1,2};
		System.out.println(highByteToInt(data));
		System.out.println(lowByteToInt(data));
		System.out.println(byteToHex(intToHighByte(258, 4), 0, 4));
		System.out.println(byteToHex(intToLowByte(258, 4), 0, 4));
	}
}
