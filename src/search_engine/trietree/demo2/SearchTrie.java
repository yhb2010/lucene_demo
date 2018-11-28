package search_engine.trietree.demo2;

import java.util.HashSet;

public class SearchTrie {

	// 插入操作
	// <param name="root"></param>
	// <param name="s"></param>
	public void addTrieNode(TrieNode root, String word, int id) {
		if (word.length() == 0)
			return;

		// 求字符地址，方便将该字符放入到26叉树中的哪一叉中
		int k = word.charAt(0) - 'a';

		// 如果该叉树为空，则初始化
		if (root.childNodes[k] == null) {
			root.childNodes[k] = new TrieNode();

			// 记录下字符
			root.childNodes[k].nodeChar = word.charAt(0);
		}

		// 该id途径的节点
		root.childNodes[k].hashSet.add(id);

		String nextWord = word.substring(1);

		// 说明是最后一个字符，统计该词出现的次数
		if (nextWord.length() == 0)
			root.childNodes[k].freq++;

		addTrieNode(root.childNodes[k], nextWord, id);
	}

	// 删除操作
	public void deleteTrieNode(TrieNode root, String word, int id) {
		if (word.length() == 0)
			return;

		// 求字符地址，方便将该字符放入到26叉树种的哪一颗树中
		int k = word.charAt(0) - 'a';

		// 如果该叉树为空,则说明没有找到要删除的点
		if (root.childNodes[k] == null)
			return;

		String nextWord = word.substring(1);

		// 如果是最后一个单词，则减去词频
		if (nextWord.length() == 0 && root.childNodes[k].freq > 0)
			root.childNodes[k].freq--;

		// 删除途经节点
		root.childNodes[k].hashSet.remove(id);

		deleteTrieNode(root.childNodes[k], nextWord, id);
	}

	// 检索单词的前缀,返回改前缀的Hash集合
	// <param name="root"></param>
	// <param name="s"></param>
	// <returns></returns>
	public HashSet<Integer> searchTrie(TrieNode trieNode, String word) {
		for (int i = 0; i < word.length(); i++) {
			int k = word.charAt(i) - 'a';
			trieNode = trieNode.childNodes[k];
			if (i == word.length() - 1) {
				return trieNode.hashSet;
			}
		}
		return null;
	}

	// 统计指定单词出现的次数
	public int wordCount(TrieNode trieNode, String word) {
		for (int i = 0; i < word.length(); i++) {
			int k = word.charAt(i) - 'a';
			trieNode = trieNode.childNodes[k];
			if (i == word.length() - 1) {
				return trieNode.freq;
			}
		}
		return 0;
	}
}
