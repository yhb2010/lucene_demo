package com.lucene.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4Boost;

/**
 * 加权
 * 
 * @author dell
 * 
 */
public class StringSearch4Boost {
	private static String keyword = "李明";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4Boost();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 按词条搜索—TermQuery
		Query query1 = null;
		Query query2 = null;
		BooleanQuery query = null;
		query1 = new TermQuery(new Term("name", keyword));
		query2 = new TermQuery(new Term("name", "陈黎"));
		// 搜索加权
		// query2.setBoost(1.5f);
		query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.SHOULD);
		query.add(query2, BooleanClause.Occur.SHOULD);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("email"));
		}

	}

}
