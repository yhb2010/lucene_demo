package search_engine.chineseWordSegmentation.segmentationMap;

import java.util.ArrayList;

import search_engine.ternarySearchTrie.TSTNode;

public class GetMatch {

	/**
	 * 以某个字符串的前缀开始的词集合
	 *
	 * @param rootNode
	 * @param sentence
	 * @param offset
	 * @param prefix
	 * @return
	 */
	// 如果匹配上则返回true，否则返回false
	public boolean getMatch(TSTNode rootNode, String sentence, int offset,
			PrefixRet prefix) {
		if (sentence == null || rootNode == null || sentence.equals("")) {
			return false;
		}

		boolean match = matchEnglish(offset, sentence, prefix);
		if (match) {
			return true;
		}

		match = matchNum(offset, sentence, prefix);
		if (match) {
			return true;
		}

		prefix.end = offset + 1;
		ArrayList<String> ret = new ArrayList<String>();
		TSTNode currentNode = rootNode;
		int charIndex = offset;

		while (true) {
			if (currentNode == null) {
				prefix.values = ret;
				if (ret.size() > 0) {
					return true;
				}
				return false;
			}

			int charComp = sentence.charAt(charIndex) - currentNode.splitchar;
			if (charComp == 0) {
				charIndex++;
				if (currentNode.data != null) {
					ret.add(currentNode.data.getS());
				}
				if (charIndex == sentence.length()) {
					prefix.values = ret;
					if (ret.size() > 0) {
						return true;
					}
					return false;
				}
				currentNode = currentNode.eqNode;
			} else if (charComp < 0) {
				currentNode = currentNode.loNode;
			} else {
				currentNode = currentNode.hiNode;
			}
		}
	}

	private boolean matchNum(int offset, String sentence, PrefixRet prefix) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean matchEnglish(int offset, String sentence, PrefixRet prefix) {
		// TODO Auto-generated method stub
		return false;
	}

	// 通过查词典形成切分词图的主体过程
	public void create(TSTNode rootNode, String sentence, int len,
			PrefixRet wordMatch, AdjList g) {
		int j = 0;
		for (int i = 0; i < len;) {
			boolean match = getMatch(rootNode, sentence, i, wordMatch);// 到词典中查询
			if (match) {// 已经匹配上
				for (String word : wordMatch.values) {// 把查询到的词作为边加入切分词图中
					j = i + word.length();
					g.addEdge(new CnToken(i, j, word));
				}
				i = wordMatch.end;
			} else {// 把单字作为边加入切分词图中
				j = i + 1;
				g.addEdge(new CnToken(i, j, sentence.substring(i, j)));
				i = j;
			}
		}
	}

}
