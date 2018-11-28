package search_engine.juniversalchardet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * 在java的项目中,为了对付多个平台,有时候需要判断要出来的文本文件时什么编码. 如果不知道编码的话有可能就产生乱码了.
 * 当然有一中方法就是事先约定是什么编码.比如只能是UTF-8等,这样就稍微有一点不方便. 最近在发现了一个判断的小工具.比较好用.随便推广一下.
 * juniversalchardet:http://code.google.com/p/juniversalchardet/ 能够识别的编码如下:
 * Chinese ISO-2022-CN BIG5 EUC-TW GB18030 HZ-GB-23121 Cyrillic ISO-8859-5
 * KOI8-R WINDOWS-1251 MACCYRILLIC IBM866 IBM855 Greek ISO-8859-7 WINDOWS-1253
 * Hebrew ISO-8859-8 WINDOWS-1255 Japanese ISO-2022-JP SHIFT_JIS EUC-JP Korean
 * ISO-2022-KR EUC-KR Unicode UTF-8 UTF-16BE / UTF-16LE UTF-32BE / UTF-32LE /
 * X-ISO-10646-UCS-4-34121 / X-ISO-10646-UCS-4-21431 Others WINDOWS-1252
 *
 * @author Administrator
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		byte[] buf = new byte[4096];
		// String fileName = args[0];
		String fileName = "WebRoot/WEB-INF/classes/search_engine/data/juniversal.txt";
		java.io.FileInputStream fis = new java.io.FileInputStream(fileName);

		// (1)
		UniversalDetector detector = new UniversalDetector(null);

		// (2)
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);// 给编码检测器提供数据
		}
		// 通知编码检测器数据已经结束
		detector.dataEnd();

		// 得出检测出的编码名
		String encoding = detector.getDetectedCharset();
		if (encoding != null) {
			System.out.println("Detected encoding = " + encoding);
		} else {
			System.out.println("No encoding detected.");
		}

		// 在再次使用编码检测器之前，先调用reset()
		detector.reset();

		String cread;
		StringBuffer content = new StringBuffer();

		InputStreamReader r = new InputStreamReader(new FileInputStream(
				fileName), encoding);
		BufferedReader in = new BufferedReader(r);
		while ((cread = in.readLine()) != null) {
			content.append(cread);
		}
		System.out.print(content.toString());

	}

}
