package search_engine.chineseWordSegmentation.maxLenMatch2;

import java.util.Collections;
import java.util.List;

import search_engine.chineseWordSegmentation.maxLenMatch.MaxLenMatch;
import search_engine.ternarySearchTrie.SearchTrie;
import search_engine.ternarySearchTrie.TSTNode;

public class Test {

	public static void main(String[] args) {
		SearchTrie searchTrie = new SearchTrie();
		TSTNode root = new TSTNode('意');
		searchTrie.addWord(root, "意见");
		searchTrie.addWord(root, "有意");
		searchTrie.addWord(root, "分歧");

		MaxLenMatch m = new MaxLenMatch();
		// 正向最大长度匹配
		m.wordSegment(root, "有意见分歧");

		System.out.println();
		System.out.println("========================================");

		Test t = new Test();
		searchTrie = new SearchTrie();
		searchTrie.addWord(root, t.handleStr("意见"));
		searchTrie.addWord(root, t.handleStr("有意"));
		searchTrie.addWord(root, t.handleStr("分歧"));

		MaxLenMatch2 m2 = new MaxLenMatch2();
		// 逆向最大长度匹配
		List<String> l = m2.wordSegment(root, t.handleStr("有意见分歧"));
		Collections.reverse(l);
		for (String s : l) {
			System.out.print(s + "/");
		}
	}

	public String handleStr(String input) {
		if (input != null && !input.trim().equals("")) {
			int len = input.length();
			char[] output = new char[len];
			for (int i = 0, j = len - 1; i < len; i++, j--) {
				output[i] = input.charAt(j);
			}
			return new String(output);
		}
		return "";
	}

}
