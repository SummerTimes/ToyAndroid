package com.kk.app.lib.network.util;

import com.kk.app.lib.network.entity.Bmp;
import com.kk.app.lib.network.entity.BmpHeader;
import com.kk.app.lib.network.entity.BmpInfoHeader;
import com.kk.app.lib.network.entity.BmpPalette;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BmpUtil {

	/**
	 * 读取指定bmp文件的信息到对象中
	 * @param bmpFile		bmp文件路径
	 * @return				代表Bmp文件信息的对象
	 */
	public static Bmp readBmp(String bmpFile) {
		Bmp bmp = new Bmp();
		File file = new File(bmpFile);
		InputStream in = null;
		int len = -1;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			in.mark(54);
			BmpHeader bmpHeader = new BmpHeader();
			len = in.read(bmpHeader.getBfType(), 0, bmpHeader.getBfType().length);
			len = in.read(bmpHeader.getBfSize(), 0, bmpHeader.getBfSize().length);
			len = in.read(bmpHeader.getBfReserved1(), 0, bmpHeader.getBfReserved1().length);
			len = in.read(bmpHeader.getBfReserved2(), 0, bmpHeader.getBfReserved2().length);
			len = in.read(bmpHeader.getBfOffBits(), 0, bmpHeader.getBfOffBits().length);
			bmp.setBmpHeader(bmpHeader);

			BmpInfoHeader bmpInfoHeader = new BmpInfoHeader();
			len = in.read(bmpInfoHeader.getBiSize(), 0, bmpInfoHeader.getBiSize().length);
			len = in.read(bmpInfoHeader.getBiWidth(), 0, bmpInfoHeader.getBiWidth().length);
			len = in.read(bmpInfoHeader.getBiHeight(), 0, bmpInfoHeader.getBiHeight().length);
			len = in.read(bmpInfoHeader.getBiPlans(), 0, bmpInfoHeader.getBiPlans().length);
			len = in.read(bmpInfoHeader.getBiBitCount(), 0, bmpInfoHeader.getBiBitCount().length);
			len = in.read(bmpInfoHeader.getBiCompression(), 0, bmpInfoHeader.getBiCompression().length);
			len = in.read(bmpInfoHeader.getBiSizeImage(), 0, bmpInfoHeader.getBiSizeImage().length);
			len = in.read(bmpInfoHeader.getBiXPelsPerMeter(), 0, bmpInfoHeader.getBiXPelsPerMeter().length);
			len = in.read(bmpInfoHeader.getBiYPelsPerMeter(), 0, bmpInfoHeader.getBiYPelsPerMeter().length);
			len = in.read(bmpInfoHeader.getBiClrUsed(), 0, bmpInfoHeader.getBiClrUsed().length);
			len = in.read(bmpInfoHeader.getBiClrImportant(), 0, bmpInfoHeader.getBiClrImportant().length);
			bmp.setBmpInfoHeader(bmpInfoHeader);

			long bfOffBits = ByteUtil.lowByteToLong(bmpHeader.getBfOffBits());
			long biClrUsed = ByteUtil.lowByteToLong(bmpInfoHeader.getBiClrUsed());
			System.out.println(ByteUtil.byteToHex(ByteUtil.longToLowByte(biClrUsed, 4), 0, ByteUtil.longToLowByte(biClrUsed, 4).length));
			System.out.println(ByteUtil.byteToHex(ByteUtil.longToLowByte(bfOffBits, 4), 0, ByteUtil.longToLowByte(bfOffBits, 4).length));
			System.out.println(bfOffBits + "," + biClrUsed);
			//重新定位到调色板
			in.reset();
			in.skip(bfOffBits-biClrUsed*4);
			if(biClrUsed != 0) {
				//有调色板
				int index = 54;
				byte[][] palettes = new byte[(int) biClrUsed][4];
				for(int i = 0; i < palettes.length && index < bfOffBits; i++) {
					in.read(palettes[i], 0, palettes[i].length);
					index += palettes[i].length;
				}

				BmpPalette bmpPalette = new BmpPalette();
				bmpPalette.setPalettes(palettes);
				bmp.setBmpPalette(bmpPalette);
			} else {
				System.out.println(ByteUtil.lowByteToLong(bmpInfoHeader.getBiBitCount()) + "位色无调色板");
			}

			byte[] buf = new byte[1024];
			StringBuilder data = new StringBuilder();
			while((len = in.read(buf, 0, buf.length)) > 0) {
				data.append(ByteUtil.byteToHex(buf,0, len));
			}
			bmp.setDatas(ByteUtil.hexToByte(data.toString()));


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
	 * 写入要隐藏文件的内容和原Bmp文件信息到指定数据文件中
	 * @param bmp			 	原Bmp文件信息
	 * @param inputFileName		要隐藏的文件
	 * @param outFileName		输出的文件
	 * @throws IOException
	 */
	public static void writeFileToBmp(Bmp bmp, String inputFileName, String outFileName) throws IOException {
		File inputFile = new File(inputFileName);
		File outFile = new File(outFileName);
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		long bfOffBits = inputFile.length() + ByteUtil.lowByteToLong(bmp.getBmpHeader().getBfOffBits());
		bmp.getBmpHeader().setBfOffBits(ByteUtil.longToLowByte(bfOffBits, 4));

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(inputFile);
			out = new FileOutputStream(outFile);
			writeBmpHeader(out, bmp);
			//写入要隐藏的文件内容
			int len = -1;
			byte[] buf = new byte[1024];
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			//写入原有位图数据
			out.write(bmp.getDatas());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void readFileFromBmpFile(String bmpFile, String outFileName) throws IOException {
		File file = new File(bmpFile);
		File outFile = new File(outFileName);
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		int len = -1;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			out = new FileOutputStream(outFile);
			in.mark(54);
			BmpHeader bmpHeader = new BmpHeader();
			len = in.read(bmpHeader.getBfType(), 0, bmpHeader.getBfType().length);
			len = in.read(bmpHeader.getBfSize(), 0, bmpHeader.getBfSize().length);
			len = in.read(bmpHeader.getBfReserved1(), 0, bmpHeader.getBfReserved1().length);
			len = in.read(bmpHeader.getBfReserved2(), 0, bmpHeader.getBfReserved2().length);
			len = in.read(bmpHeader.getBfOffBits(), 0, bmpHeader.getBfOffBits().length);

			BmpInfoHeader bmpInfoHeader = new BmpInfoHeader();
			len = in.read(bmpInfoHeader.getBiSize(), 0, bmpInfoHeader.getBiSize().length);
			len = in.read(bmpInfoHeader.getBiWidth(), 0, bmpInfoHeader.getBiWidth().length);
			len = in.read(bmpInfoHeader.getBiHeight(), 0, bmpInfoHeader.getBiHeight().length);
			len = in.read(bmpInfoHeader.getBiPlans(), 0, bmpInfoHeader.getBiPlans().length);
			len = in.read(bmpInfoHeader.getBiBitCount(), 0, bmpInfoHeader.getBiBitCount().length);
			len = in.read(bmpInfoHeader.getBiCompression(), 0, bmpInfoHeader.getBiCompression().length);
			len = in.read(bmpInfoHeader.getBiSizeImage(), 0, bmpInfoHeader.getBiSizeImage().length);
			len = in.read(bmpInfoHeader.getBiXPelsPerMeter(), 0, bmpInfoHeader.getBiXPelsPerMeter().length);
			len = in.read(bmpInfoHeader.getBiYPelsPerMeter(), 0, bmpInfoHeader.getBiYPelsPerMeter().length);
			len = in.read(bmpInfoHeader.getBiClrUsed(), 0, bmpInfoHeader.getBiClrUsed().length);
			len = in.read(bmpInfoHeader.getBiClrImportant(), 0, bmpInfoHeader.getBiClrImportant().length);


			long bfOffBits = ByteUtil.lowByteToLong(bmpHeader.getBfOffBits());
			long biClrUsed = ByteUtil.lowByteToLong(bmpInfoHeader.getBiClrUsed());
			//读取范围从54 + biClrUsed 到 bfOffBits
			in.reset();
			in.skip(54 + biClrUsed * 4);
			long sumLen = 54 + biClrUsed * 4;
			long oldsumLen = 54 + biClrUsed * 4;
			byte[] buf = new byte[1024];
			StringBuilder builder = new StringBuilder();
			while((len = in.read(buf)) > 0) {
				sumLen += len;
				if(sumLen > bfOffBits) {
					builder.append(ByteUtil.byteToHex(buf, 0, (int) (bfOffBits - oldsumLen)));
					break;
				} else {
					builder.append(ByteUtil.byteToHex(buf, 0, len));
				}
				oldsumLen += len;
			}
			System.out.println(builder.toString());
			out.write(ByteUtil.hexToByte(builder.toString()));
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
	private static void writeBmpHeader(OutputStream out, Bmp bmp) throws IOException {
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

	public static void main(String[] args) throws IOException {
//		Bmp bmp = BmpUtil.readBmp(BmpUtil.class.getClassLoader().getResource("resource/background.bmp").getPath());
//		Bmp bmp = BmpUtil.readBmp(BmpUtil.class.getClassLoader().getResource("resource/SmallConfetti.bmp").getPath());
//		System.out.println(byteToHex(bmp.getBmpHeader().getBfType()));
//		System.out.println(byteToLong(bmp.getBmpHeader().getBfSize()));
//		for(int i = 0; i <bmp.getBmpPalette().getPalettes().length; i++) {
//			System.out.println(ByteUtil.byteToHex(bmp.getBmpPalette().getPalettes()[i], 0, bmp.getBmpPalette().getPalettes()[i].length));
//		}
//		System.out.println(ByteUtil.byt eToHex(bmp.getDatas(),0 ,bmp.getDatas().length));

		/*测试隐藏文件内容到bmp文件中*/
		Bmp bmp = BmpUtil.readBmp(BmpUtil.class.getClassLoader().getResource("resource/SmallConfetti.bmp").getPath());
		writeFileToBmp(bmp, BmpUtil.class.getClassLoader().getResource("resource/screct.txt").getPath(),
				BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "out.bmp");
		/*测试bmp文件中恢复文件内容*/
		readFileFromBmpFile(BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "out.bmp",
				BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "out.txt");

		/*测试隐藏文件内容到bmp文件中*/
		Bmp bmp2 = BmpUtil.readBmp(BmpUtil.class.getClassLoader().getResource("resource/background.bmp").getPath());
		writeFileToBmp(bmp2, BmpUtil.class.getClassLoader().getResource("resource/backgroundscrect.txt").getPath(),
				BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "backgroundout.bmp");
		/*测试bmp文件中恢复文件内容*/
		readFileFromBmpFile(BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "backgroundout.bmp",
				BmpUtil.class.getClassLoader().getResource("resource/").getPath() + "backgroundout.txt");

	}

}
