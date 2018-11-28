package search_engine.chineseWordSegmentation.getAllPrefix;

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

}
