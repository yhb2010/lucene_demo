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
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Explanation;
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
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.StringIndexer4MoreLikeThisQuery;
import com.lucene.index.StringIndexer4TermVector;

/**
 * MoreLikeThis的使用，相似查询
 * 
 * @author dell
 * 
 */
public class StringSearch4MoreLikeThis {

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4MoreLikeThisQuery();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher search = new IndexSearcher(ireader);

		System.out.println("文档数:" + ireader.numDocs());

		String[] moreLikeFields = { "name", "info" }; // { "Name" ,"Info"};

		MoreLikeThis mlt = new MoreLikeThis(ireader);
		// 以下所有设置都是针对作为搜索条件的文档
		mlt.setFieldNames(moreLikeFields);
		// 最少的词频（各个搜索域的和）
		mlt.setMinTermFreq(2);
		// 最多的查询词数目
		mlt.setMaxQueryTerms(50);
		// 词至少在这么多篇文档中出现
		mlt.setMinDocFreq(2);
		Query query = mlt.like(0);

		System.out.println("搜索条件:" + query.toString());

		TopDocs tDocs = search.search(query, 10);

		ScoreDoc sDocs[] = tDocs.scoreDocs;

		int len = sDocs.length;

		for (int i = 0; i < len; i++) {
			ScoreDoc tScore = sDocs[i];
			// 从Lucene3.0开始已经不能通过 tScore.score 这样来得到些文档的得分了
			int docId = tScore.doc;
			Explanation exp = search.explain(query, docId);

			Document tDoc = search.doc(docId);
			String name = tDoc.get("name");
			String info = tDoc.get("info");

			float score = exp.getValue();
			// System.out.println(exp.toString()); //如果需要打印文档得分的详细信息则可以通过此方法

			System.out.println("DocId:" + docId + "\tScore:" + score
					+ "\tname:" + name + "\tinfo:" + info);
		}

	}
}
