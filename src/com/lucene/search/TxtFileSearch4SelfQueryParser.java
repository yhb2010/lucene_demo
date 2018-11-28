package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.selfqueryparser.CustomQueryParser;
import com.lucene.index.selfqueryparser.NumericDateRangeQueryParser;
import com.lucene.index.selfqueryparser.NumericRangeQueryParser;

/**
 * 自定义QueryParser
 * 
 * @author dell
 * 
 */
public class TxtFileSearch4SelfQueryParser {
	private static String keyword = "地貌~";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new CustomQueryParser(Version.LUCENE_45, "text",
				analyzera);

		// 处理数字
		QueryParser qp2 = new NumericRangeQueryParser(Version.LUCENE_45,
				"price", analyzera);
		Query query = qp2.parse("price:[10 TO 20]");

		// 处理日期
		QueryParser qp3 = new NumericDateRangeQueryParser(Version.LUCENE_45,
				"time2", analyzera);
		qp3.setDateResolution("time2", DateTools.Resolution.DAY);
		query = qp3.parse("time2:[2009-01-01 TO 2009-01-04]");

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："
				+ topDocs.getMaxScore());
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}

	}

}
