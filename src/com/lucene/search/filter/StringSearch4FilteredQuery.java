package com.lucene.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.NumericRangeFilterBuilder;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.FixedBitSet;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.Contants;
import com.lucene.filter.MyCustomFilter;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4Filter;
import com.lucene.index.StringIndexer4FilteredQuery;

/**
 * 将过滤器转换为具体的查询，提供了一种可能性，即可以把过滤器添加到booleanQuery的查询子句中
 * 
 * @author Administrator
 * 
 */
public class StringSearch4FilteredQuery {
	private static String keyword = "education";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4FilteredQuery();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 自定义过滤器
		Filter filter = new MyCustomFilter("isbn", "0000001", "0000003");
		Query query = new TermQuery(new Term("category", keyword));
		FilteredQuery edBookOnSpecial = new FilteredQuery(query, filter);

		Query query2 = new TermQuery(new Term("category", "story"));

		BooleanQuery bquery = new BooleanQuery();
		bquery.add(edBookOnSpecial, BooleanClause.Occur.SHOULD);
		bquery.add(query2, BooleanClause.Occur.SHOULD);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(bquery, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："
				+ topDocs.getMaxScore());
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("bookName"));
		}

	}

}
