package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.TxtFileIndexer;
import com.lucene.index.TxtFileIndexer2;

/**
 * 从.txt文件读取
 * 
 * @author dell
 * 
 */
public class TxtFileSearch {
	private static String keyword = "休息";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new TxtFileIndexer();
		Directory dir = indexer.initDic2();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "text", analyzera);
		qp.setDefaultOperator(QueryParser.Operator.AND);
		Query query = qp.parse(keyword);

		System.out.println("QueryParser:" + query.toString());

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}

	}

}
