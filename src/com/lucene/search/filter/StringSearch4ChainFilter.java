package com.lucene.search.filter;

import java.util.Calendar;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.ChainedFilter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4ChainFilter;

/**
 * 链式过滤
 * 
 * @author Administrator
 * 
 */
public class StringSearch4ChainFilter {
	private static String keyword = "兄弟 女人";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4ChainFilter();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 匹配所有文档
		BooleanQuery bq = new BooleanQuery();
		bq.add(new TermQuery(new Term("owner", "张苏磊")),
				BooleanClause.Occur.SHOULD);
		bq.add(new TermQuery(new Term("owner", "陈黎")),
				BooleanClause.Occur.SHOULD);

		// 根据日期匹配所有文档
		Calendar cal = Calendar.getInstance();
		cal.set(2099, 1, 1, 0, 0);
		Filter dateFilter = TermRangeFilter.Less(
				"date",
				new BytesRef(DateTools.timeToString(cal.getTimeInMillis(),
						DateTools.Resolution.DAY)));

		// 只匹配张苏磊的文档
		Filter zslFilter = new CachingWrapperFilter(new QueryWrapperFilter(
				new TermQuery(new Term("owner", "张苏磊"))));

		// 只匹配陈黎的文档
		Filter clFilter = new CachingWrapperFilter(new QueryWrapperFilter(
				new TermQuery(new Term("owner", "陈黎"))));

		// ANDNOT匹配第一个不能匹配第二个
		ChainedFilter cf = new ChainedFilter(new Filter[] { dateFilter,
				clFilter }, ChainedFilter.AND);

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(bq, cf, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！最大评分："
				+ topDocs.getMaxScore());

		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("date"));
		}

	}

}
