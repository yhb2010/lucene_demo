package com.lucene;

import java.io.File;

public class Contants {

	// 索引文件的保存目录
	public static String indexPath = "f:" + File.separator + "zsl"
			+ File.separator + "luceneIndex";
	// 指明要索引的文件所在文件夹的位置
	public static String sourcePath = "f:" + File.separator + "zsl"
			+ File.separator + "luceneSource";
	// 指明要索引的文件所在文件夹的位置
	public static String sourcePath2 = "f:" + File.separator + "zsl"
			+ File.separator + "luceneSource2";
	// 停用词保存位置
	public static String stopWordPath = "f:" + File.separator + "zsl"
			+ File.separator + "txt" + File.separator + "stop-word.txt";

}
