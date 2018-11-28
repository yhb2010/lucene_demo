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
import org.apache.lucene.queryparser.xml.builders.DuplicateFilterBuilder;
import org.apache.lucene.sandbox.queries.DuplicateFilter;
import org.apache.lucene.sandbox.queries.DuplicateFilter.KeepMode;
import org.apache.lucene.sandbox.queries.DuplicateFilter.ProcessingMode;
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
import com.lucene.index.StringIndexer4DuplicateFilter;

/**
 * DuplicateFilter:文档去重
 * 
 * @author dell
 * 
 */
public class StringSearch4DuplicateFilter {
	private static String keyword = "文档";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4DuplicateFilter();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 正常查询
		TermQuery tq = new TermQuery(new Term("content", "你好"));

		// 去重
		DuplicateFilter filter = new DuplicateFilter("id");
		// int
		// keepMode：KM_USE_FIRST_OCCURRENCE表示重复的文档取第一篇，KM_USE_LAST_OCCURRENCE表示重复的文档取最后一篇。
		KeepMode keepMode = KeepMode.KM_USE_FIRST_OCCURRENCE;
		filter.setKeepMode(keepMode);
		// int processingMode：
		// PM_FULL_VALIDATION是首先将bitset中所有文档都设为false，当出现同组重复文章的第一篇的时候，将其设为1
		// PM_FAST_INVALIDATION是首先将bitset中所有文档都设为true，除了同组重复文章的第一篇，其他的的全部设为0
		// 两者在所有的文档都包含指定域的情况下，功能一样，只不过后者不用处理docFreq=1的文档，速度加快。
		// 然而当有的文档不包含指定域的时候，后者由于都设为true，则没有机会将其清零，因而会被允许返回，当然工程中应避免这种情况。
		ProcessingMode processingMode = ProcessingMode.PM_FULL_VALIDATION;
		filter.setProcessingMode(processingMode);
		FilteredQuery query = new FilteredQuery(tq, filter);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："
				+ topDocs.getMaxScore());
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("id：" + targetDoc.get("id"));
			System.out.println("内容：" + targetDoc.get("content"));
		}

	}

}
