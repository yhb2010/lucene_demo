package search_engine.chineseWordSegmentation.segmentationMap;

import java.util.Iterator;

import search_engine.ternarySearchTrie.SearchTrie;
import search_engine.ternarySearchTrie.TSTNode;

public class Test {

	public static void main(String[] args) {
		SearchTrie searchTrie = new SearchTrie();
		TSTNode root = new TSTNode('有');
		searchTrie.addWord(root, "有");
		searchTrie.addWord(root, "意见");
		searchTrie.addWord(root, "有意");
		searchTrie.addWord(root, "分歧");
		searchTrie.addWord(root, "联合");
		searchTrie.addWord(root, "联合大学");

		GetMatch m = new GetMatch();
		PrefixRet prefix = new PrefixRet();
		AdjList adjList = new AdjList(10);
		m.create(root, "有意见分歧", "有意见分歧".length(), prefix, adjList);

		Iterator<CnToken> it = adjList.getAdjacencies(1);
		while (it.hasNext()) {
			CnToken cn = it.next();
			System.out.print(cn + "||");
		}
		System.out.println();
		it = adjList.getAdjacencies(2);
		while (it.hasNext()) {
			CnToken cn = it.next();
			System.out.print(cn + "||");
		}
		System.out.println();
		it = adjList.getAdjacencies(3);
		while (it.hasNext()) {
			CnToken cn = it.next();
			System.out.print(cn + "||");
		}
		System.out.println();
	}

}
