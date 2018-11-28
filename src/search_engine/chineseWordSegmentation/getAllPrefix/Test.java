package search_engine.chineseWordSegmentation.getAllPrefix;

import search_engine.ternarySearchTrie.SearchTrie;
import search_engine.ternarySearchTrie.TSTNode;

public class Test {

	public static void main(String[] args) {
		SearchTrie searchTrie = new SearchTrie();
		TSTNode root = new TSTNode('大');
		searchTrie.addWord(root, "大学");
		searchTrie.addWord(root, "大学生");
		searchTrie.addWord(root, "中");
		searchTrie.addWord(root, "中心");
		searchTrie.addWord(root, "活动");
		searchTrie.addWord(root, "心");
		searchTrie.addWord(root, "生活");
		searchTrie.addWord(root, "联合国大使");
		searchTrie.addWord(root, "联合");
		searchTrie.addWord(root, "联合大学");

		GetMatch m = new GetMatch();
		PrefixRet prefix = new PrefixRet();
		m.getMatch(root, "大学生活动中心", 0, prefix);
		for (String s : prefix.values) {
			System.out.print(s + "/");
		}
	}

}
