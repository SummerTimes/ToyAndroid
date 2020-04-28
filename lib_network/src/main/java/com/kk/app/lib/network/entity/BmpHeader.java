package com.kk.app.lib.network.entity;

/**
 * @author kk
 * @datetime: 2020/4/24
 * @desc:Bmp文件头部
 */
public class BmpHeader {

	/**
	 * 文件的类型，2个字节
	 */
	private byte[] bfType;
	/**
	 * 位图文件的大小，字节为单位，4个字节
	 */
	private byte[] bfSize;
	/**
	 * 保留，2个字节
	 */
	private byte[] bfReserved1;
	/**
	 * 保留，2个字节
	 */
	private byte[] bfReserved2;
	/**
	 * 说明从文件开始到实际的图像数据之间的字节的偏移量
	 * 4个字节
	 */
	private byte[] bfOffBits;
	public BmpHeader() {
		bfType = new byte[2];
		bfSize = new byte[4];
		bfReserved1 = new byte[2];
		bfReserved2 = new byte[2];
		bfOffBits = new byte[4];
	}

	public byte[] getBfType() {
		return bfType;
	}
	public void setBfType(byte[] bfType) {
		this.bfType = bfType;
	}
	public byte[] getBfSize() {
		return bfSize;
	}
	public void setBfSize(byte[] bfSize) {
		this.bfSize = bfSize;
	}
	public byte[] getBfReserved1() {
		return bfReserved1;
	}
	public void setBfReserved1(byte[] bfReserved1) {
		this.bfReserved1 = bfReserved1;
	}
	public byte[] getBfReserved2() {
		return bfReserved2;
	}
	public void setBfReserved2(byte[] bfReserved2) {
		this.bfReserved2 = bfReserved2;
	}
	public byte[] getBfOffBits() {
		return bfOffBits;
	}
	public void setBfOffBits(byte[] bfOffBits) {
		this.bfOffBits = bfOffBits;
	}


}
