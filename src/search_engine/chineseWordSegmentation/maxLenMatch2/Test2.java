package search_engine.chineseWordSegmentation.maxLenMatch2;

import java.util.Collections;
import java.util.List;

import search_engine.chineseWordSegmentation.maxLenMatch.MaxLenMatch;
import search_engine.ternarySearchTrie.SearchTrie;
import search_engine.ternarySearchTrie.TSTNode;

public class Test2 {

	public static void main(String[] args) {
		SearchTrie searchTrie = new SearchTrie();
		TSTNode root = new TSTNode('前');
		searchTrie.addWord(root, "北京");
		searchTrie.addWord(root, "北京大学");
		searchTrie.addWord(root, "大学");
		searchTrie.addWord(root, "大学生");
		searchTrie.addWord(root, "生前");
		searchTrie.addWord(root, "前来");
		searchTrie.addWord(root, "应聘");

		MaxLenMatch m = new MaxLenMatch();
		// 正向最大长度匹配
		m.wordSegment(root, "北京大学生前来应聘");

		System.out.println();
		System.out.println("========================================");

		Test2 t = new Test2();
		searchTrie = new SearchTrie();
		searchTrie.addWord(root, t.handleStr("北京"));
		searchTrie.addWord(root, t.handleStr("北京大学"));
		searchTrie.addWord(root, t.handleStr("大学"));
		searchTrie.addWord(root, t.handleStr("大学生"));
		searchTrie.addWord(root, t.handleStr("生前"));
		searchTrie.addWord(root, t.handleStr("前来"));
		searchTrie.addWord(root, t.handleStr("应聘"));

		MaxLenMatch2 m2 = new MaxLenMatch2();
		// 逆向最大长度匹配
		List<String> l = m2.wordSegment(root, t.handleStr("北京大学生前来应聘"));
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
