package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.update.UpdateIndexer;

/**
 * 从文本读取 增删改
 *
 * @author dell
 *
 */
public class StringSearch4Opt {
	private static String keyword = "山水国内";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 删除索引
		// DeleteIndexer dIndexer = new DeleteIndexer();
		// dIndexer.delete(dir);
		// 更新索引
		UpdateIndexer dIndexer = new UpdateIndexer();
		// 所谓更新就是把匹配到的文档删除，然后再增加新的文档，这样会有问题，就是在旧文档里的field，如果新文档里没有设置，更新后就变为空了。
		// 解决方法是先找到旧文档，把旧文档的field进行修改，再更新。
		// dIndexer.updateOrAdd(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		Term t = new Term("ID", "序号：0");
		dIndexer.updateOrAdd2(dir, isearcher, t);

		ireader = DirectoryReader.open(dir);
		isearcher = new IndexSearcher(ireader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "text", analyzera);
		qp.setDefaultOperator(QueryParser.Operator.AND);
		Query query = qp.parse(keyword);

		System.out.println("QueryParser:" + query.toString());

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("text"));
			System.out.println("time：" + targetDoc.get("time"));
		}

	}

}
