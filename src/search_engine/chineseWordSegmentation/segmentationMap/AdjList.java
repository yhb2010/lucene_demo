package search_engine.chineseWordSegmentation.segmentationMap;

import java.util.Iterator;

/**
 * 为了方便用动态规划的方法计算最佳前驱词，在此单向链表的基础上形成逆向邻接表
 *
 * @author Administrator
 *
 */
public class AdjList {

	private TokenLinkedList list[];// AdjList的图结构

	/**
	 * 构造方法：分配空间
	 */
	public AdjList(int verticesNum) {
		list = new TokenLinkedList[verticesNum];

		// 初始化数组中所有的链表
		for (int index = 0; index < verticesNum; index++) {
			list[index] = new TokenLinkedList();
		}
	}

	public int getVerticesNum() {
		return list.length;
	}

	/**
	 * 增加一个边到图中
	 */
	public void addEdge(CnToken newEdge) {
		list[newEdge.end].put(newEdge);
	}

	/**
	 * 返回一个迭代器，包含以指定点结尾的所有的边
	 */
	public Iterator<CnToken> getAdjacencies(int vertex) {
		TokenLinkedList ll = list[vertex];
		if (ll == null)
			return null;
		return ll.iterator();
	}

}
