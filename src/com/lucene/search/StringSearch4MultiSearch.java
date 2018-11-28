package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4MultiSearch;
import com.lucene.index.StringIndexer4Sort;

/**
 * 单线程多索引搜索
 * 
 * @author dell
 * 
 */
public class StringSearch4MultiSearch {
	private static String keyword = "钢铁是怎样炼成的 钢铁 女人 白毛女 女儿 张三丰";

	public static void main(String[] args) throws Exception {
		StringIndexer4MultiSearch indexer = new StringIndexer4MultiSearch();
		Directory dic = indexer.initDic();
		Directory dic2 = indexer.initDic2();
		indexer.addDocuments(dic, dic2);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexReader ireader2 = DirectoryReader.open(dic2);// 尽量少打开
		//单线程
		MultiReader multiReader = new MultiReader(ireader, ireader2);
		IndexSearcher isearcher = new IndexSearcher(multiReader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "bookName",
				analyzera);
		Query query = qp.parse(keyword);

		System.out.println("QueryParser:" + query.toString());

		SortField[] sortfield = new SortField[] { SortField.FIELD_SCORE,
				new SortField("publishDate", SortField.Type.STRING, true) };
		Sort sort = new Sort(sortfield);
		TopDocs topDocs = isearcher.search(query, null, 10, sort);

		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("序号：" + targetDoc.get("bookNumber"));
		}

	}
}
