package search_engine.trietree.demo1;

public class Test {

	public static void main(String[] args) {
		SearchTrie searchTrie = new SearchTrie();
		TrieNode root = new TrieNode('0');
		searchTrie.addWord("0856", root, "贵州：铜仁");
		searchTrie.addWord("010", root, "北京：北京");
		System.out.println(searchTrie.search(root, "0856"));
		System.out.println(searchTrie.search(root, "010"));
		System.out.println(searchTrie.search(root, "021"));
	}
}
