package com.higtlighter.search;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import com.higtlighter.index.StringIndexer4Css;
import com.lucene.Contants;
import com.lucene.analysis.mmseg4j.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;

/**
 * 添加样式
 * 
 * @author dell
 * 
 */
public class StringSearch4Css {
	private static String keyword = "记者";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4Css();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		// 使用QueryParser查询分析器构造Query对象
		Analyzer analyzera = new ComplexAnalyzer(Contants.stopWordPath);
		// Analyzer analyzera = new SynonymAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_45, "text", analyzera);
		qp.setDefaultOperator(QueryParser.Operator.AND);
		Query query = qp.parse(keyword);

		System.out.println("QueryParser:" + query.toString());

		// 搜索相似度最高的5条记录
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println("匹配了：" + topDocs.totalHits + "次！");

		// 高亮
		//自定义标签
		SimpleHTMLFormatter formatter= new SimpleHTMLFormatter("<span color=\"red\">", "</span>");
		QueryScorer scorer = new QueryScorer(query, "text");
		// 设置评分
		Highlighter highlighter = new Highlighter(formatter, scorer);
		// 设置文本片段，每个片段长度为10
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,10));

		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			String text = targetDoc.get("text");

			//以下两种方式均可
			// TokenStream tokenStream = TokenSources.getAnyTokenStream(ireader, scoreDocs[i].doc, "text", targetDoc, analyzera);
			TokenStream tokenStream = analyzera.tokenStream("text", new StringReader(text));
			// 高亮显示2个匹配最好的片断，“...”是片段间的分隔符
			String fragment = highlighter.getBestFragments(tokenStream, text,
					2, "...");
			System.out.println("高亮处理后的内容：" + fragment);
		}

	}

}
