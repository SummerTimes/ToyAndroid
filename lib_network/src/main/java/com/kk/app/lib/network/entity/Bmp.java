package com.kk.app.lib.network.entity;

/**
 * @author yp2
 * @date 2015-11-17
 * @description Bmp文件格式
 */
public class Bmp {

	private BmpHeader bmpHeader;
	private BmpInfoHeader bmpInfoHeader;
	private BmpPalette bmpPalette;
	/**
	 * bmp位图数据
	 */
	private byte[] datas;
	public BmpHeader getBmpHeader() {
		return bmpHeader;
	}
	public void setBmpHeader(BmpHeader bmpHeader) {
		this.bmpHeader = bmpHeader;
	}
	public BmpInfoHeader getBmpInfoHeader() {
		return bmpInfoHeader;
	}
	public void setBmpInfoHeader(BmpInfoHeader bmpInfoHeader) {
		this.bmpInfoHeader = bmpInfoHeader;
	}
	public BmpPalette getBmpPalette() {
		return bmpPalette;
	}
	public void setBmpPalette(BmpPalette bmpPalette) {
		this.bmpPalette = bmpPalette;
	}
	public byte[] getDatas() {
		return datas;
	}
	public void setDatas(byte[] data) {
		this.datas = data;
	}

}
