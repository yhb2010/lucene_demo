package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
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

public class StringSearch4Term {
	private static String keyword = "序号：1";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 按词条搜索—TermQuery某个域精确匹配
		/**
		Query query = null;
		query = new TermQuery(new Term("ID", keyword));
		*/
		// 按“与或”搜索—BooleanQuery，
		// 1、和： MUST与MUST；2、或： SHOULD与SHOULD；3、有A不能有B： MUST与MUST_NOT
		/**
		Query query1 = null;
		Query query2 = null;
		Query query3 = null;
		BooleanQuery query = null;
		query1 = new TermQuery(new Term("text", "地貌"));
		query2 = new TermQuery(new Term("text", "没有美景"));
		query3 = new TermQuery(new Term("text", "histort"));
		query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.MUST);
		query.add(query2, BooleanClause.Occur.MUST_NOT);
		query.add(query3, BooleanClause.Occur.MUST);
		*/
		// 在某一文本范围内搜索—TermRangeQuery
		/**
		Term beginTime = new Term("time", "2009年01月01日");
		Term endTime = new Term("time", "2009年01月05日");
		TermRangeQuery query = new TermRangeQuery("time", beginTime.bytes(),
				endTime.bytes(), true, true);// 包含边界值
		*/
		// 在某一数字范围内搜索—NumericRangeQuery
		/**
		NumericRangeQuery<Integer> query = NumericRangeQuery.newIntRange("count", 1, 6, true, false);
		*/
		// 前缀搜索，它不能过滤内容，只能全盘接受—PrefixQuery
		/**
		Term prefix=new Term("ID","序号：");
		PrefixQuery query=new PrefixQuery(prefix);
		*/
		//或者，以“序”开头，“1”结尾
		/**
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "ID", analyzera);
		Query query = qp.parse("序*1");
		*/
		// 短语搜索—PhraseQuery，设置坡度：“地貌”和“风光”之间隔一个词
		/**
		PhraseQuery query = new PhraseQuery();
		query.add(new Term("text","java"));
		query.add(new Term("text","new"));
		query.add(new Term("text","sa"));
		query.setSlop(5);
		*/
		/**
		MultiPhraseQuery query=new MultiPhraseQuery();
		//首先向其中加入要查找的短语的前缀
		query.add(new Term("text","地貌"));
		//构建2个Term，作为短语的后缀
		Term t1=new Term("text","自然");
		Term t2=new Term("text","风光");
		//再向query中加入所有的后缀，与前缀一起，它们将组成2个短语
		query.add(new Term[]{t1,t2});
		*/
		//模糊匹配：FuzzyQuery，前6个字符必须一样
		FuzzyQuery query=new FuzzyQuery(new Term("text","history"),2,6);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："+topDocs.getMaxScore());
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
		}

	}

}
