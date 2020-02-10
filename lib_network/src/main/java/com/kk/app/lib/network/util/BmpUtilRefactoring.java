package com.kk.app.lib.network.util;

import com.kk.app.lib.network.entity.Bmp;
import com.kk.app.lib.network.entity.BmpHeader;
import com.kk.app.lib.network.entity.BmpInfoHeader;
import com.kk.app.lib.network.entity.BmpPalette;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * @author yp2
 * @date 2015-11-18
 * @description 重构后的Bmp工具
 * <br/>
 * 主要做了几件事： <br/>
 * 1.位图数据可以不存储，在需要写入的时候再去读原文件的位图数据部分<br/>
 * 2.抽除掉重复功能的代码<br/>
 */
public class BmpUtilRefactoring {

	/**
	 * 读取指定bmp文件的信息到对象中
	 * @param bmpFile		bmp文件路径
	 * @return				代表Bmp文件信息的对象
	 */
	private static Bmp readBmp(String bmpFile) {
		Bmp bmp = new Bmp();
		File file = new File(bmpFile);
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			in.mark(0);
			readBmpHeader(bmp, in);

			long bfOffBits = ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
			long biSize = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiSize());
			long biBitCount = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiBitCount());
			int index = (int) (14 + biSize);
			//重新定位到调色板
			in.reset();
			in.skip(index);
			if(bfOffBits - biSize - 14 == 0) {
				//没有调色板
				System.out.println(ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiBitCount()) + "位色无调色板");
			} else {
				//有调色板
				byte[][] palettes = new byte[(int) ByteUtil.power(2, (int) biBitCount)][4];
				for(int i = 0; i < palettes.length && index < bfOffBits; i++) {
					in.read(palettes[i], 0, palettes[i].length);
					index += palettes[i].length;
				}

				BmpPalette bmpPalette = new BmpPalette();
				bmpPalette.setPalettes(palettes);
				bmp.setBmpPalette(bmpPalette);
			}
			//记录bmp文件位图数据
			/*
			int len = -1;
			byte[] buf = new byte[1024];
			StringBuilder data = new StringBuilder();
			while((len = in.read(buf, 0, buf.length)) > 0) {
				data.append(ByteUtil.byteToHex(buf,0, len));
			}
			bmp.setDatas(ByteUtil.hexToByte(data.toString()));*/


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bmp;
	}

	/**
	 * 读取bmp文件输入流的头部信息到Bmp中的头部信息中，要求输入流处于文件的开头
	 * @param bmp				Bmp对象
	 * @param in				bmp文件输入流
	 * @throws IOException
	 */
	private static void readBmpHeader(Bmp bmp, InputStream in) throws IOException {
		BmpHeader bmpHeader = new BmpHeader();
		in.read(bmpHeader.getBfType(), 0, bmpHeader.getBfType().length);
		in.read(bmpHeader.getBfSize(), 0, bmpHeader.getBfSize().length);
		in.read(bmpHeader.getBfReserved1(), 0, bmpHeader.getBfReserved1().length);
		in.read(bmpHeader.getBfReserved2(), 0, bmpHeader.getBfReserved2().length);
		in.read(bmpHeader.getBfOffBits(), 0, bmpHeader.getBfOffBits().length);
		bmp.setBmpHeader(bmpHeader);

		BmpInfoHeader bmpInfoHeader = new BmpInfoHeader();
		in.read(bmpInfoHeader.getBiSize(), 0, bmpInfoHeader.getBiSize().length);
		in.read(bmpInfoHeader.getBiWidth(), 0, bmpInfoHeader.getBiWidth().length);
		in.read(bmpInfoHeader.getBiHeight(), 0, bmpInfoHeader.getBiHeight().length);
		in.read(bmpInfoHeader.getBiPlans(), 0, bmpInfoHeader.getBiPlans().length);
		in.read(bmpInfoHeader.getBiBitCount(), 0, bmpInfoHeader.getBiBitCount().length);
		in.read(bmpInfoHeader.getBiCompression(), 0, bmpInfoHeader.getBiCompression().length);
		in.read(bmpInfoHeader.getBiSizeImage(), 0, bmpInfoHeader.getBiSizeImage().length);
		in.read(bmpInfoHeader.getBiXPelsPerMeter(), 0, bmpInfoHeader.getBiXPelsPerMeter().length);
		in.read(bmpInfoHeader.getBiYPelsPerMeter(), 0, bmpInfoHeader.getBiYPelsPerMeter().length);
		in.read(bmpInfoHeader.getBiClrUsed(), 0, bmpInfoHeader.getBiClrUsed().length);
		in.read(bmpInfoHeader.getBiClrImportant(), 0, bmpInfoHeader.getBiClrImportant().length);
		bmp.setBmpInfoHeader(bmpInfoHeader);
	}

	/**
	 * 写入要隐藏文件的内容和原Bmp文件信息到指定数据文件中
	 * @param bmp			 	原Bmp文件信息
	 * @param inputFileName		要隐藏的文件
	 * @param outFileName		输出的文件
	 * @throws IOException
	 */
	private static void writeFileToBmp(Bmp bmp, String bmpFileName, String inputFileName, String outFileName) throws IOException {
		File inputFile = new File(inputFileName);
		writeStreamToBmp(bmp, bmpFileName, new FileInputStream(inputFile), outFileName);
	}



	private static void writeStreamToBmp(Bmp bmp, String bmpFileName, InputStream in, String outFileName) throws IOException {
		File outFile = new File(outFileName);
		File bmpFile = new File(bmpFileName);
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		//记录原来bmp文件的数据偏移位置
		long oldbfOffBits = ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
		//计算出新的数据偏移位置：= 原来的偏移位置 + 要隐藏文件的总字节数
		long bfOffBits = in.available() + ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
		//设置新的数据偏移位置，以便写入新的文件中
		bmp.getBmpHeader().setBfOffBits(ByteUtil.longToLowByte(bfOffBits, 4));

		InputStream bmpIn = null;
		OutputStream out = null;

		try {
			bmpIn = new BufferedInputStream(new FileInputStream(bmpFile));
			out = new FileOutputStream(outFile);
			//将bmp头部信息写入输入流中
			writeBmpHeader(bmp, out);
			//写入要隐藏的文件内容
			int len = -1;
			byte[] buf = new byte[1024];
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			//跳过头部和调色板信息
			bmpIn.skip(oldbfOffBits);
			len = -1;
			//写入原有位图数据
			while((len = bmpIn.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				bmpIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将字符串写入到指定的位图文件内，并改变输出文件名
	 * @param bmpFileName
	 * @param inputString
	 * @param outFileName
	 * @throws IOException
	 */
	public static void writeStringToBmpFile(String bmpFileName, String inputString, String outFileName) throws IOException {
		Bmp bmp = readBmp(bmpFileName);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
		writeStreamToBmp(bmp, bmpFileName, inputStream, outFileName);
	}

	/**
	 * 读取bmp文件中隐藏的字符串内容
	 * @param bmpFileName bmp文件
	 * @return 隐藏的字符串内容
	 * @throws IOException
	 */
	public static String readStringFromBmpFile(String bmpFileName) throws IOException {
		File bmpFile = new File(bmpFileName);
		Bmp bmp = new Bmp();

		InputStream in = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = -1;
		try {
			in = new BufferedInputStream(new FileInputStream(bmpFile));
			//标记当前输入流位置，方便后面reset跳转到当前输入流读取位置
			in.mark(1);
			//读取输入流中包含的头部信息
			readBmpHeader(bmp, in);
			//数据偏移位置
			long bfOffBits = ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
			//使用的颜色索引数目
			long biClrUsed = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiClrUsed());
			//位图信息头部字节数
			long biSize = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiSize());
			//比特/像素
			long biBitCount = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiBitCount());
			//重置到mark标记的位置，这里是跳转到输入流的开头
			in.reset();
			//保存当前文件输入流位置(字节位置)
			long sumLen = 0;
			if(biBitCount < 24) {
				if(biClrUsed == 0) {
					//索引全部都重要
					//跳过输入流中的54 + 调色板所占字节数 个字节，这样其实就跳转到了保存隐藏文件内容的位置
					in.skip(14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4);
					sumLen = 14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4;
				} else {
					//部分重要
					in.skip(14 + biSize + biClrUsed * 4);
					sumLen = 14 + biSize + biClrUsed * 4;
				}

			} else {
				//没有调色板
				in.skip(14 + biSize);
				sumLen = 14 + biSize;
			}
			byte[] buf = new byte[1024];
			while((len = in.read(buf)) > 0) {
				if((sumLen + len) > bfOffBits) {
					//如果超过了数据偏移位置，则截取剩余的字节进行保存
					bos.write(buf, 0, (int) (bfOffBits - sumLen));
					break;
				} else {
					//没有超过数据偏移位置，则截取读取到的字节
					bos.write(buf, 0, len);
				}
				sumLen += len;
			}
			return bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			in.close();
			bos.close();
		}
		return null;
	}


	public static String readStringFromInputStream(InputStream inputStream) throws IOException {
		Bmp bmp = new Bmp();

		InputStream in = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len = -1;
		try {
			in = new BufferedInputStream(inputStream);
			//标记当前输入流位置，方便后面reset跳转到当前输入流读取位置
			in.mark(1);
			//读取输入流中包含的头部信息
			readBmpHeader(bmp, in);
			//数据偏移位置
			long bfOffBits = ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
			//使用的颜色索引数目
			long biClrUsed = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiClrUsed());
			//位图信息头部字节数
			long biSize = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiSize());
			//比特/像素
			long biBitCount = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiBitCount());
			//重置到mark标记的位置，这里是跳转到输入流的开头
//			in.mark(0);
			in.reset();
			//保存当前文件输入流位置(字节位置)
			long sumLen = 0;
			if(biBitCount < 24) {
				if(biClrUsed == 0) {
					//索引全部都重要
					//跳过输入流中的54 + 调色板所占字节数 个字节，这样其实就跳转到了保存隐藏文件内容的位置
					in.skip(14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4);
					sumLen = 14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4;
				} else {
					//部分重要
					in.skip(14 + biSize + biClrUsed * 4);
					sumLen = 14 + biSize + biClrUsed * 4;
				}

			} else {
				//没有调色板
				in.skip(14 + biSize);
				sumLen = 14 + biSize;
			}
			byte[] buf = new byte[1024];
			while((len = in.read(buf)) > 0) {
				if((sumLen + len) > bfOffBits) {
					//如果超过了数据偏移位置，则截取剩余的字节进行保存
					bos.write(buf, 0, (int) (bfOffBits - sumLen));
					break;
				} else {
					//没有超过数据偏移位置，则截取读取到的字节
					bos.write(buf, 0, len);
				}
				sumLen += len;
			}
			return bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			in.close();
			bos.close();
		}
		return null;
	}

	/**
	 * 将文件内容写入到指定的位图文件内，并改变输出文件名
	 * @param bmpFileName		位图文件名
	 * @param inputFileName		要隐藏的文件名
	 * @param outFileName		输出文件名
	 * @throws IOException
	 */
	public static void writeFileToBmpFile(String bmpFileName, String inputFileName, String outFileName) throws IOException {
		Bmp bmp = readBmp(bmpFileName);
		writeFileToBmp(bmp, bmpFileName, inputFileName, outFileName);
	}

	/**
	 * 读取bmp文件中隐藏的文件内容到指定的输出文件中去
	 * @param bmpFileName		bmp文件名
	 * @param outFileName		输出文件名
	 * @throws IOException
	 */
	public static void readFileFromBmpFile(String bmpFileName, String outFileName) throws IOException {
		File bmpFile = new File(bmpFileName);
		File outFile = new File(outFileName);
		Bmp bmp = new Bmp();
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		int len = -1;
		try {
			in = new BufferedInputStream(new FileInputStream(bmpFile));
			out = new FileOutputStream(outFile);
			//标记当前输入流位置，方便后面reset跳转到当前输入流读取位置
			in.mark(0);
			//读取输入流中包含的头部信息
			readBmpHeader(bmp, in);
			//数据偏移位置
			long bfOffBits = ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
			//使用的颜色索引数目
			long biClrUsed = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiClrUsed());
			//位图信息头部字节数
			long biSize = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiSize());
			//比特/像素
			long biBitCount = ByteUtil.lowByteToLong(bmp.getBmpInfoHeader().getBiBitCount());
			//重置到mark标记的位置，这里是跳转到输入流的开头
			in.reset();
			//保存当前文件输入流位置(字节位置)
			long sumLen = 0;
			if(biBitCount < 24) {
				if(biClrUsed == 0) {
					//索引全部都重要
					//跳过输入流中的54 + 调色板所占字节数 个字节，这样其实就跳转到了保存隐藏文件内容的位置
					in.skip(14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4);
					sumLen = 14 + biSize + ByteUtil.power(2, (int) biBitCount) * 4;
				} else {
					//部分重要
					in.skip(14 + biSize + biClrUsed * 4);
					sumLen = 14 + biSize + biClrUsed * 4;
				}

			} else {
				//没有调色板
				in.skip(14 + biSize);
				sumLen = 14 + biSize;
			}
			byte[] buf = new byte[1024];
			while((len = in.read(buf)) > 0) {
				if((sumLen + len) > bfOffBits) {
					//如果超过了数据偏移位置，则截取剩余的字节进行保存
					out.write(buf, 0, (int) (bfOffBits - sumLen));
					break;
				} else {
					//没有超过数据偏移位置，则截取读取到的字节
					out.write(buf, 0, len);
				}
				sumLen += len;
			}
		} catch (Exception e) {
			e.printStackTrace();
			in.close();
			out.close();
		}
	}

	/**
	 * 将bmp头部信息和调色板信息写入输入流中
	 * @param out
	 * @param bmp
	 * @throws IOException
	 */
	private static void writeBmpHeader(Bmp bmp, OutputStream out) throws IOException {
		BmpHeader bmpHeader = bmp.getBmpHeader();
		out.write(bmpHeader.getBfType());
		out.write(bmpHeader.getBfSize());
		out.write(bmpHeader.getBfReserved1());
		out.write(bmpHeader.getBfReserved2());
		out.write(bmpHeader.getBfOffBits());

		BmpInfoHeader bmpInfoHeader = bmp.getBmpInfoHeader();
		out.write(bmpInfoHeader.getBiSize());
		out.write(bmpInfoHeader.getBiWidth());
		out.write(bmpInfoHeader.getBiHeight());
		out.write(bmpInfoHeader.getBiPlans());
		out.write(bmpInfoHeader.getBiBitCount());
		out.write(bmpInfoHeader.getBiCompression());
		out.write(bmpInfoHeader.getBiSizeImage());
		out.write(bmpInfoHeader.getBiXPelsPerMeter());
		out.write(bmpInfoHeader.getBiYPelsPerMeter());
		out.write(bmpInfoHeader.getBiClrUsed());
		out.write(bmpInfoHeader.getBiClrImportant());

		BmpPalette bmpPalette = bmp.getBmpPalette();
		if(bmpPalette != null && bmpPalette.getPalettes() != null) {
			for(int i = 0; i < bmpPalette.getPalettes().length; i++) {
				out.write(bmpPalette.getPalettes()[i]);
			}
		}

	}
}
