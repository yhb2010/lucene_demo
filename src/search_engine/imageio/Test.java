package search_engine.imageio;

import java.io.IOException;

/**
 * 以上为个人对java
 * ImageIO处理图形的一些常用的方法进行的封装。全部测试通过，main方法中的均为测试代码，如果想用这个图像处理类，可以参考mian方法的例子。
 * 
 * 另外代码中有些地方需要改进，效率也需要进一步提高。
 * 
 * @author Administrator
 * 
 */
public class Test {

	public static void main(String[] args) throws IOException {
		String srcImagePath = "WebRoot/WEB-INF/classes/search_engine/data/1.JPG";
		CropImage.cropImage(srcImagePath, "src/search_engine/data/1_bak.JPG",
				1000, 800, 500, 500, "JPG", "JPG");

		ImageByRatio.reduceImageByRatio(srcImagePath,
				"src/search_engine/data/1_bak2.JPG", 2, 2);
		ImageByRatio.enlargementImageByRatio(srcImagePath,
				"src/search_engine/data/1_bak3.JPG", 2, 2);
	}

}
