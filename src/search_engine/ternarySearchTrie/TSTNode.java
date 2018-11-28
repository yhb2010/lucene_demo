package search_engine.ternarySearchTrie;

public class TSTNode {

	/** 节点的值 */
	public Data data = null;// data属性可以存储词原文和词性、词频等相关的信息

	public TSTNode loNode; // 左边节点，用斜线表示：不能形成关键字
	public TSTNode eqNode; // 中间节点，用垂直的虚线表示：一个父节点下面的直接后继节点。只有父节点和它的直接后继节点才能形成一个数据单元的关键字
	public TSTNode hiNode; // 右边节点，用斜线表示：不能形成关键字

	public char splitchar; // 本节点表示的字符

	/**
	 * 构造方法
	 *
	 * @param splitchar
	 *            该节点表示的字符
	 */
	public TSTNode(char splitchar) {
		this.splitchar = splitchar;
	}

	public String toString() {
		return "splitchar:" + splitchar;
	}

}
