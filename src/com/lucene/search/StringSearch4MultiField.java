package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4MultiField;

/**
 * 针对多域值的搜索
 * 
 * @author dell
 * 
 */
public class StringSearch4MultiField {
	private static String keyword = "篱笆";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4MultiField();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 多域值搜索
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_45,
				new String[] { "bookName", "content" }, analyzera);
		Query query = qp.parse(keyword);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("书名：" + targetDoc.get("bookName"));
			System.out.println("内容：" + targetDoc.get("content"));
		}

	}

}
