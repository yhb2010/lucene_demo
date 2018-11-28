package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
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
import com.lucene.index.StringIndexer4Sort;

/**
 * 对查询结果进行排序
 * 
 * @author dell
 * 
 */
public class StringSearch4Sort {
	private static String keyword = "白毛女";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4Sort();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "bookName",
				analyzera);
		Query query = qp.parse("钢铁是怎样炼成的 钢铁 女人 白毛女 女儿 张三丰");

		System.out.println("QueryParser:" + query.toString());

		// 按默认方式排序（正常排序）
		// TopDocs topDocs = isearcher.search(query, null, 10, Sort.RELEVANCE);
		// 按文档索引顺序排序
		// TopDocs topDocs = isearcher.search(query, null, 10, Sort.INDEXORDER);
		// 按"publishDate"排序，第三个参数"true"表示倒序排列
		//SortField sortfield = new SortField("publishDate", SortField.Type.STRING, true);
		//多域值排序，先按score排序，再按"publishDate"排序
		SortField[] sortfield = new SortField[]{SortField.FIELD_SCORE, new SortField("publishDate", SortField.Type.STRING, true)};
		Sort sort = new Sort(sortfield);
		TopDocs topDocs = isearcher.search(query, null, 10, sort);

		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("序号：" + targetDoc.get("bookNumber"));
			System.out.println("内容：" + targetDoc.get("bookName"));
			System.out.println("出版日期：" + targetDoc.get("publishDate"));
			System.out.print("得分：");
			System.out.println(scoreDocs[i].score);
		}

	}

}
