package search_engine.trietree.demo2;

import java.util.HashSet;

public class TrieNode {

	// 26个字符，也就是26叉树
	public TrieNode[] childNodes;

	// 词频统计
	public int freq;

	// 记录该节点的字符
	public char nodeChar;

	// 插入记录时的编码id
	public HashSet<Integer> hashSet = new HashSet<Integer>();

	// 初始化
	public TrieNode() {
		childNodes = new TrieNode[26];
		freq = 0;
	}

}
