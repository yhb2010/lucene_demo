package search_engine.trietree.demo1;

public class SearchTrie {

	// 加载词，形成数字搜索树
	public void addWord(String string, TrieNode root, String area) {
		TrieNode tstNode = root;
		for (int i = 1; i < string.length(); i++) {
			char c0 = string.charAt(i);
			int ind = Integer.parseInt(string.substring(i, i + 1));
			TrieNode tmpNode = tstNode.children[ind];
			if (null == tmpNode) {
				tmpNode = new TrieNode(c0);
			}
			if (i == string.length() - 1) {
				tmpNode.area = area;
			}
			tstNode.children[ind] = tmpNode;
			tstNode = tmpNode;
		}
	}

	public String search(TrieNode root, String tel) {
		TrieNode tstNode = root;
		for (int i = 1; i < tel.length(); i++) {
			tstNode = tstNode.children[(tel.charAt(i) - '0')];
			if (tstNode == null) {
				return "没有";
			}
			if (null != tstNode.area) {
				return tstNode.area;
			}
		}
		return "没有";
	}

}
