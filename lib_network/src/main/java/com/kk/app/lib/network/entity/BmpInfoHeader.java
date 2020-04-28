package com.kk.app.lib.network.entity;

/**
 * @author kk
 * @datetime: 2020/4/24
 * @desc:Bmp文件信息头部
 */
public class BmpInfoHeader {

	/**
	 * 位图信息头部所需要的字数，4个字节
	 */
	private byte[] biSize;
	/**
	 * 图像的宽度，像素为单位，4个字节
	 */
	private byte[] biWidth;
	/**
	 * 图像的高度，像素为单位，4个字节
	 */
	private byte[] biHeight;
	/**
	 * 为目标设备说明颜色平面数，其值将总是设为1，2个字节
	 */
	private byte[] biPlans;
	/**
	 * 说明比特数/像素，其值为1、4、8、16、24、32，2个字节
	 */
	private byte[] biBitCount;
	/**
	 * 说明图像数据压缩的类型，0 不压缩，4个字节
	 */
	private byte[] biCompression;
	/**
	 * 说明图像的大小,字节为单位，当压缩格式为0时，可设置为0，4个字节
	 */
	private byte[] biSizeImage;
	/**
	 * 说明水平分辨率，像素/米表示,有符号整数，4个字节
	 */
	private byte[] biXPelsPerMeter;
	/**
	 * 说明垂直分辨率，像素/米表示，有符号整数，4个字节
	 */
	private byte[] biYPelsPerMeter;
	/**
	 * 说明位图实际使用的彩色表中的颜色索引数，4个字节
	 */
	private byte[] biClrUsed;
	/**
	 * 说明对图像显示有重要影响的颜色索引的数目，如果是0，表示都重要
	 * 4个字节
	 */
	private byte[] biClrImportant;
	public BmpInfoHeader() {
		biSize = new byte[4];
		biWidth = new byte[4];
		biHeight = new byte[4];
		biPlans = new byte[2];
		biBitCount = new byte[2];
		biCompression = new byte[4];
		biSizeImage = new byte[4];
		biXPelsPerMeter = new byte[4];
		biYPelsPerMeter = new byte[4];
		biClrUsed = new byte[4];
		biClrImportant = new byte[4];
	}
	public byte[] getBiSize() {
		return biSize;
	}
	public void setBiSize(byte[] biSize) {
		this.biSize = biSize;
	}
	public byte[] getBiWidth() {
		return biWidth;
	}
	public void setBiWidth(byte[] biWidth) {
		this.biWidth = biWidth;
	}
	public byte[] getBiHeight() {
		return biHeight;
	}
	public void setBiHeight(byte[] biHeight) {
		this.biHeight = biHeight;
	}
	public byte[] getBiPlans() {
		return biPlans;
	}
	public void setBiPlans(byte[] biPlans) {
		this.biPlans = biPlans;
	}
	public byte[] getBiBitCount() {
		return biBitCount;
	}
	public void setBiBitCount(byte[] biBitCount) {
		this.biBitCount = biBitCount;
	}
	public byte[] getBiCompression() {
		return biCompression;
	}
	public void setBiCompression(byte[] biCompression) {
		this.biCompression = biCompression;
	}
	public byte[] getBiSizeImage() {
		return biSizeImage;
	}
	public void setBiSizeImage(byte[] biSizeImage) {
		this.biSizeImage = biSizeImage;
	}
	public byte[] getBiXPelsPerMeter() {
		return biXPelsPerMeter;
	}
	public void setBiXPelsPerMeter(byte[] biXPelsPerMeter) {
		this.biXPelsPerMeter = biXPelsPerMeter;
	}
	public byte[] getBiYPelsPerMeter() {
		return biYPelsPerMeter;
	}
	public void setBiYPelsPerMeter(byte[] biYPelsPerMeter) {
		this.biYPelsPerMeter = biYPelsPerMeter;
	}
	public byte[] getBiClrUsed() {
		return biClrUsed;
	}
	public void setBiClrUsed(byte[] biClrUsed) {
		this.biClrUsed = biClrUsed;
	}
	public byte[] getBiClrImportant() {
		return biClrImportant;
	}
	public void setBiClrImportant(byte[] biClrImportant) {
		this.biClrImportant = biClrImportant;
	}

}
