package search_engine.chineseWordSegmentation.maxLenMatch;

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

		MaxLenMatch m = new MaxLenMatch();
		// 正向最大长度匹配
		m.wordSegment(root, "联合大学大学生活动中心");
	}

}
