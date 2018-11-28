package com.lucene.search.nearrealtime;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.add.AddIndexer;
import com.lucene.index.delete.DeleteIndexer;
import com.lucene.index.methd.Indexer;

/**
 * 近实时搜索
 * 
 * @author dell
 * 
 */
public class NearRealTime {
	private static String keyword = "山水国内";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();

		Indexer ind = new Indexer();
		IndexWriter writer = ind.getIndexer(dir);

		indexer.addDocuments(writer);

		// 开始搜索
		// 实例化搜索器
		// IndexReader ireader = DirectoryReader.open(dir);
		DirectoryReader ireader = DirectoryReader.open(writer, true);
		// 以上两个方法均可
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "text", analyzera);
		qp.setDefaultOperator(QueryParser.Operator.AND);
		Query query = qp.parse(keyword);

		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}

		// 删除索引
		DeleteIndexer dIndexer = new DeleteIndexer();
		dIndexer.delete(writer);

		// 添加索引
		AddIndexer aIndexer = new AddIndexer();
		aIndexer.add(writer);

		IndexReader inewReader = DirectoryReader.openIfChanged(
				(DirectoryReader) ireader, writer, true); // 重启reader
		ireader.close();
		isearcher = new IndexSearcher(inewReader);
		topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}
	}

}
