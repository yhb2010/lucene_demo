package com.lucene.search.selfsort;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.selfsort.DistanceComparatorSource;
import com.lucene.index.selfsort.StringIndexer;

/**
 * 对查询结果进行自定义排序
 * 
 * @author dell
 * 
 */
public class StringSearch {
	private static Query query;

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		query = new TermQuery(new Term("type", "restaurant"));
		Sort sort = new Sort(new SortField("location",
				new DistanceComparatorSource(0, 0)));

		System.out.println(sort.toString());

		TopDocs topDocs = isearcher.search(query, null, 10, sort);

		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("名称：" + targetDoc.get("name"));
			System.out.println("坐标：" + targetDoc.get("location"));
		}

	}

}
