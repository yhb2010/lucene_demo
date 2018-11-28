package search_engine.trietree.demo2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		TrieNode trie = new TrieNode();
		SearchTrie strie = new SearchTrie();

		File file = new File(
				"WebRoot/WEB-INF/classes/search_engine/data/trietree2.txt");
		BufferedReader bis = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "UTF-8"));
		String szTemp;
		while ((szTemp = bis.readLine()) != null) {
			String[] sp = szTemp.split(" ");
			strie.addTrieNode(trie, sp[1], Integer.parseInt(sp[0]));
		}

		// 检索go开头的字符串
		HashSet<Integer> hashSet = strie.searchTrie(trie, "go");

		for (Integer item : hashSet) {
			System.out.println("当前字符串的编号ID为:" + item);
		}

		System.out.println("go出现的次数为:" + strie.wordCount(trie, "go"));
		strie.deleteTrieNode(trie, "go", 3);
		strie.deleteTrieNode(trie, "go", 7);
		System.out.println("go出现的次数为:" + strie.wordCount(trie, "go"));
	}
}
