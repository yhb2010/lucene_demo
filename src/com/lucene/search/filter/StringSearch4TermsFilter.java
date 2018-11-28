package com.lucene.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermsFilter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
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
import com.lucene.filter.MyCustomFilter;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;

/**
 * TermsFilter:逐个添加想要被过滤的项
 * 
 * @author dell
 * 
 */
public class StringSearch4TermsFilter {
	private static String keyword = "文档";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 自定义过滤器
		List<Term> termList = new ArrayList<Term>();
		termList.add(new Term("text", "地貌"));
		termList.add(new Term("text", "天空"));
		Query query = new TermQuery(new Term("name", keyword));
		Filter filter = new TermsFilter(termList);
		FilteredQuery query2 = new FilteredQuery(query, filter);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query2, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："
				+ topDocs.getMaxScore());
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}

	}

}
