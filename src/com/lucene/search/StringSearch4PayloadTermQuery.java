package com.lucene.search;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.payloads.AveragePayloadFunction;
import org.apache.lucene.search.payloads.PayloadTermQuery;
import org.apache.lucene.store.Directory;

import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4PayloadTermQuery;
import com.lucene.similarity.PayloadSimilarity;

/**
 * 有效载荷，可以对某些项进行加权
 * 
 * @author dell
 * 
 */
public class StringSearch4PayloadTermQuery {

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4PayloadTermQuery();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		BooleanQuery bq = new BooleanQuery();

		PayloadTermQuery ptq1 = new PayloadTermQuery(new Term("tools", "轮船"),
				new AveragePayloadFunction());
		PayloadTermQuery ptq2 = new PayloadTermQuery(new Term("tools", "飞机"),
				new AveragePayloadFunction());
		PayloadTermQuery ptq3 = new PayloadTermQuery(
				new Term("tools", "汽车"), new AveragePayloadFunction());

		bq.add(ptq1, Occur.SHOULD);
		bq.add(ptq2, Occur.SHOULD);
		bq.add(ptq3, Occur.SHOULD);
		
		isearcher.setSimilarity(new PayloadSimilarity()); // 设置自定义的PayloadSimilarity
		TopDocs results = isearcher.search(bq, 10);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");

		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc; // 文档编号
			float lucene_score = hits[i].score;
			String tools = isearcher.doc(docId).get("tools");
			System.out.println("DocId:" + docId + "\tLucene Score:"
					+ lucene_score + "\tcontents:" + tools);
			Explanation explanation = isearcher.explain(bq, docId);
			System.out.println(explanation.toString());
		}
	}

}
