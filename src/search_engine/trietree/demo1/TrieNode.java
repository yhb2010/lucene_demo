package search_engine.trietree.demo1;

/**
 * Trie树的节点类定义
 *
 * @author Administrator
 *
 */
public class TrieNode {

	protected TrieNode[] children;// 孩子节点
	protected char splitChar;// 分隔字符
	protected String area;// 电话所属地区信息

	public TrieNode(char splitChar) {
		children = new TrieNode[10];
		area = null;
		this.splitChar = splitChar;
	}

}
