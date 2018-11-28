package com.lucene.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.DocIdSetIterator;
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
import org.apache.lucene.search.TimeLimitingCollector;
import org.apache.lucene.search.TimeLimitingCollector.TimeExceededException;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Counter;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.StringIndexer4MoreLikeThisQuery;
import com.lucene.index.StringIndexer4TermVector;

/**
 * 针对时间较长的搜索进行处理
 * 
 * @author dell
 * 
 */
public class StringSearch4TimLimitingCollector {
	private static String keyword = "开发";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4MoreLikeThisQuery();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 根据评分来保留最靠前的若干条结果
		TopScoreDocCollector topDocs = TopScoreDocCollector.create(2, false);
		Counter clock = Counter.newCounter(true);
		Collector collector = new TimeLimitingCollector(topDocs, clock, 1);

		Query query1 = null;
		Query query2 = null;
		Query query3 = null;
		BooleanQuery query = null;
		query1 = new TermQuery(new Term("info", "开发"));
		query2 = new TermQuery(new Term("info", "php"));
		query3 = new TermQuery(new Term("info", "经验"));
		query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.MUST);
		query.add(query2, BooleanClause.Occur.MUST_NOT);
		query.add(query3, BooleanClause.Occur.MUST);

		try {
			isearcher.search(query, collector);
		} catch (TimeExceededException e) {
			System.out.println("to long！");
		}

		System.out.println("匹配了：" + topDocs.getTotalHits() + "次！");
	}
}
