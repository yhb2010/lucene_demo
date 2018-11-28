package search_engine.ternarySearchTrie;

public class SearchTrie {

	// //向词典树中加入一个单词的过程
	public TSTNode addWord(TSTNode root, String key) {
		TSTNode currentNode = root; // 从树的根节点开始查找
		int charIndex = 0; // 从词的开头匹配
		while (true) {
			// 比较词的当前字符与节点的当前字符
			int charComp = key.charAt(charIndex) - currentNode.splitchar;
			if (charComp == 0) {// 相等
				charIndex++;
				if (charIndex == key.length()) {
					if (currentNode.data == null) {
						currentNode.data = new Data(key, 1);
					} else {
						currentNode.data
								.setFreq(currentNode.data.getFreq() + 1);
					}
					return currentNode;
				}
				if (currentNode.eqNode == null) {
					currentNode.eqNode = new TSTNode(key.charAt(charIndex));
				}
				currentNode = currentNode.eqNode;
			} else if (charComp < 0) {// 小于
				if (currentNode.loNode == null) {
					currentNode.loNode = new TSTNode(key.charAt(charIndex));
				}
				currentNode = currentNode.loNode;
			} else {// 大于
				if (currentNode.hiNode == null) {
					currentNode.hiNode = new TSTNode(key.charAt(charIndex));
				}
				currentNode = currentNode.hiNode;
			}
		}
	}

	/**
	 * 查找词典的基本过程是：输入一个词，返回这个词对应的TSTNode对象，如果该词不在词典中则返回空。在查找词典的过程中，从树的根节点匹配Key，
	 * 按Char从前往后匹配Key。charIndex表示Key当前要比较的Char的位置。
	 *
	 * Ternary search tree
	 * 有三个只节点,在查找的时候,比较当前字符,如果查找的字符比较小,那么就跳到左节点.如果查找的字符比较大,那么就跳转到友节点
	 * .如果这个字符正好相等,那么就走向中间节点.这个时候比较下一个字符. 比如上面的例子,要查找”ax”, 先比较”a” 和 “i”, “a” <
	 * "i",跳转到"i"的左节点, 比较 "a" < "b", 跳转到"b"的左节点, "a" = "a", 跳转到
	 * "a"的中间节点,并且比较下一个字符"x". "x" > “s” , 跳转到”s” 的右节点, 比较 “x” > “t” 发现”t”
	 * 没有右节点了.找出结果,不存在”ax”这个字符
	 *
	 * @param key
	 * @param startNode
	 * @return
	 */
	protected Data getNode(String key, TSTNode startNode) {
		if (key == null) {
			return null;
		}
		int len = key.length();
		if (len == 0)
			return null;
		TSTNode currentNode = startNode; // 匹配过程中当前节点的位置
		int charIndex = 0;
		char cmpChar = key.charAt(charIndex);
		int charComp;
		while (true) {
			if (currentNode == null) {// 没找到
				return null;
			}
			charComp = cmpChar - currentNode.splitchar;
			if (charComp == 0) {// 相等
				charIndex++;
				if (charIndex == len) {// 找到了
					return currentNode.data;
				} else {
					cmpChar = key.charAt(charIndex);
				}
				currentNode = currentNode.eqNode;
			} else if (charComp < 0) {// 小于
				currentNode = currentNode.loNode;
			} else {// 大于
				currentNode = currentNode.hiNode;
			}
		}
	}

}
