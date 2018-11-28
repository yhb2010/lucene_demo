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

/**
 * 对搜索结果进行过滤
 * 
 * @author Administrator
 * 
 */
public class StringSearch4Filter {
	private static String keyword = "兄弟 女人";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4Filter();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "bookName",
				analyzera);
		Query query = qp.parse(keyword);
		// 对特定范围内的文档进行查询匹配，同TermRangeQuery，但TermRangeQuery没有评分，传null就是开放的
		// Filter filter = new TermRangeFilter("bookName", new BytesRef("r"),
		// new BytesRef("t"), true, true);
		// 对数字范围内进行过滤，同NumericRangeQuery
		// Filter filter = NumericRangeFilter.newIntRange("month", 5, 7, true,
		// true);
		// 在过滤结果中继续过滤
		/**
		List<Filter> filterList = new ArrayList<Filter>();
		Filter filter = new QueryWrapperFilter(qp.parse("女儿"));
		filterList.add(filter);
		filter = new QueryWrapperFilter(qp.parse("我的"));
		filterList.add(filter);
		System.out.println("QueryParser:" + query.toString());
		for (Filter f : filterList) {
			query = new FilteredQuery(query, f); // 这里面不断的构造query，传递过滤器封装最终的Query查询对象
			System.out.println("QueryParser:" + query.toString());
		}
		*/
		// 安全性过滤：只搜索日期是"1999-01-01"的书名里有"兄弟"的
		//Filter filter = new QueryWrapperFilter(new TermQuery(new Term("publishDate", "1999-01-01")));
		//Filter filterTmp = new QueryWrapperFilter(new TermQuery(new Term( "publishDate", "1999-01-01")));
		// 针对同一个isearcher可以应用缓存
		//CachingWrapperFilter filter = new CachingWrapperFilter(filterTmp);
		// 自定义过滤器
		Filter filter = new MyCustomFilter("bookNumber","0000007","0000001","2");

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, filter, 5);
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
