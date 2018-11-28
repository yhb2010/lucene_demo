package com.lucene.search.collector;

import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.collector.BookLinkCollector;
import com.lucene.index.collector.StringIndexer;

/**
 * 自定义Collector collector的作用就是收集某些我们需要定制化的结果集，某些情况下使用collector可以可以极大的提升我们程序的性能，
 * 通过collector可以让我们对每一个匹配上的文档做一些特有的定制化操作
 * 
 * @author dell
 * 
 */
public class StringSearch {
	private static String keyword = "女人";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		BooleanQuery query = null;
		Query query1 = new TermQuery(new Term("bookName", keyword));
		Query query2 = new TermQuery(new Term("bookName", "钢铁是怎样炼成的"));
		query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.SHOULD);
		query.add(query2, BooleanClause.Occur.SHOULD);

		BookLinkCollector collector = new BookLinkCollector();
		isearcher.search(query, collector);

		// 输出结果
		Map<String, String> map = collector.getMap();
		System.out.println(map.toString());

		List<ScoreDoc> s = collector.docs;
		for (ScoreDoc sc : s) {
			System.out.println(sc.doc + "====" + sc.score);
		}

	}

}
