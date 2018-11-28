package search_engine.imageio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageByRatio {

	/**
	 * 按倍率缩小图片
	 * 
	 * @param srcImagePath
	 *            读取图片路径
	 * @param toImagePath
	 *            写入图片路径
	 * @param widthRatio
	 *            宽度缩小比例
	 * @param heightRatio
	 *            高度缩小比例
	 * @throws IOException
	 */
	public static void reduceImageByRatio(String srcImagePath,
			String toImagePath, int widthRatio, int heightRatio)
			throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 缩小边长
			BufferedImage tag = new BufferedImage(width / widthRatio, height
					/ heightRatio, BufferedImage.TYPE_INT_RGB);
			// 绘制 缩小 后的图片
			tag.getGraphics().drawImage(src, 0, 0, width / widthRatio,
					height / heightRatio, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 按倍率放大图片
	 * 
	 * @param srcImagePath
	 *            读取图形路径
	 * @param toImagePath
	 *            写入入行路径
	 * @param widthRatio
	 *            宽度放大比例
	 * @param heightRatio
	 *            高度放大比例
	 * @throws IOException
	 */
	public static void enlargementImageByRatio(String srcImagePath,
			String toImagePath, int widthRatio, int heightRatio)
			throws IOException {
		FileOutputStream out = null;
		try {
			// 读入文件
			File file = new File(srcImagePath);
			// 构造Image对象
			BufferedImage src = javax.imageio.ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			// 放大边长
			BufferedImage tag = new BufferedImage(width * widthRatio, height
					* heightRatio, BufferedImage.TYPE_INT_RGB);
			// 绘制放大后的图片
			tag.getGraphics().drawImage(src, 0, 0, width * widthRatio,
					height * heightRatio, null);
			out = new FileOutputStream(toImagePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
