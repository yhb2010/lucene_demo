package search_engine.ternarySearchTrie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

	/**
	 * 树是否平衡取决于单词的读入顺序。如果按排序后的顺序插入，则生成方式最不平衡。单词的读入顺序对于创建平衡的三叉搜索树很重要，
	 * 但对于二叉搜索树就不太重要
	 * 。通过选择一个排序后数据单元集合的中间值，并把它作为开始节点，我们可以创建一个平衡的三叉树。可以写一个专门的过程来生成平衡的三叉树词典。
	 * 在调用此方法前，先把词典数组k排好序
	 *
	 * @param fp
	 *            写入的平衡序的词典
	 * @param k
	 *            排好序的词典数组
	 * @param offset
	 *            偏移量
	 * @param n
	 *            长度
	 * @throws Exception
	 */
	public static void outputBalanced(ArrayList<String> fp, List<String> k,
			int offset, int n) throws IOException {
		int m;
		if (n < 1) {
			return;
		}
		m = n >> 1; // m=n/2

		String item = k.get(m + offset);

		fp.add(item);// 把词条写入到文件

		outputBalanced(fp, k, offset, m); // 输出左半部分
		outputBalanced(fp, k, offset + m + 1, n - m - 1); // 输出右半部分
	}

	public static void main(String[] args) throws IOException {
		List<String> k = new ArrayList<String>();
		File file = new File("WebRoot/WEB-INF/classes/data/words.dic");
		BufferedReader bis = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "UTF-8"));
		String szTemp;
		while ((szTemp = bis.readLine()) != null) {
			k.add(szTemp);
		}
		// Collections.sort(k);
		// Test.outputBalanced(new ArrayList<String>(), k, 2, k.size());

		SearchTrie searchTrie = new SearchTrie();
		TSTNode root = new TSTNode('日');
		for (String string : k) {
			searchTrie.addWord(root, string);
		}

		long l = System.currentTimeMillis();
		System.out.println(searchTrie.getNode("说咸道淡", root));
		long l2 = System.currentTimeMillis();
		System.out.println(l2 - l);

		l = System.currentTimeMillis();
		for (String string : k) {
			if (string.equals("说咸道淡")) {
				System.out.println("说咸道淡");
			}
		}
		l2 = System.currentTimeMillis();
		System.out.println(l2 - l);
	}
}
