package search_engine.chineseWordSegmentation.maxLenMatch;

import search_engine.TwoTuple;
import search_engine.ternarySearchTrie.TSTNode;

/**
 * 中文分词：最大长度匹配
 *
 * @author Administrator
 *
 */
public class MaxLenMatch {

	/**
	 * @param key
	 *            ：输入的字符串
	 * @param offset
	 *            ：开始位置
	 * @return
	 */
	public TwoTuple<String, Integer> mathchLong(TSTNode rootNode, String key,
			int offset) {

		String ret = null;
		if (key == null || rootNode == null || key.equals("")) {
			return null;
		}

		TSTNode currentNode = rootNode;
		int charIndex = offset;
		while (true) {
			if (currentNode == null) {
				return new TwoTuple<String, Integer>(ret, charIndex);
			}
			int charComp = key.charAt(charIndex) - currentNode.splitchar;

			if (charComp == 0) {
				charIndex++;

				if (currentNode.data != null) {
					ret = currentNode.data.getS();// 候选最长匹配词
				}

				if (charIndex == key.length()) {
					return new TwoTuple<String, Integer>(ret, charIndex);// 已经匹配完
				}

				currentNode = currentNode.eqNode;
			} else if (charComp < 0) {
				currentNode = currentNode.loNode;
			} else {
				currentNode = currentNode.hiNode;
			}
		}
	}

	/**
	 * 正向最大长度分词
	 *
	 * @param sentence
	 *            ：要处理的字符串
	 */
	public void wordSegment(TSTNode rootNode, String sentence) {

		int senLen = sentence.length();// 首先计算出传入的字符串的字符长度
		int i = 0;// 控制匹配的起始位置

		MaxLenMatch m = new MaxLenMatch();
		while (i < senLen) {// 如果i小于此字符串的长度就继续匹配
			TwoTuple<String, Integer> twoTuple = m.mathchLong(rootNode,
					sentence, i);
			String word = twoTuple.first;
			if (word != null) {
				// 匹配上
				// 下次匹配点在这个词之后
				i += word.length();
			} else {
				// 如果没有找到匹配的词，就按单字切分
				word = sentence.substring(i, i + 1);
				++i;
			}
			System.out.print(word + "/");
		}

	}

}
