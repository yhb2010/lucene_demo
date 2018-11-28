package com.lucene.search;

import java.util.BitSet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanFirstQuery;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanNotQuery;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4SpanQuery;

/**
 * 跨度查询
 * 
 * @author dell
 * 
 */
public class StringSearch4SpanQuery {
	private static String keyword = "钢铁";
	//private static String keyword = "考试";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4SpanQuery();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		/**
		 * SpanTermQuery类是一种携带了位置信息的Term对象查询类，单独使用时输出的结果与TermQuery相同。
		 * 但是他携带的位置信息可以为其他复杂的SpanQuery提供支持，是跨度检索的基础类。
		 * 也可以为后续自定义排序规则提供位置信息，或者用来特殊显示相关结果。
		 */
		SpanQuery query = new SpanTermQuery(new Term("bookName", keyword));
		/**
		 * 指定查询域中前面指定数量索引项的范围内进行检索，提高查询检索效率。 如果匹配的检索项在指定跨度范围之外，查询中不会返回该文档作为结果
		 * 数值表示位置跨度范围；实现在Content域中前几个索引项中找到searchWords的索引项
		 */
		//SpanQuery queryOther = new SpanFirstQuery(query, 1);
		/**
		 * 用来指定不同查询检索项在文本中出现的间隔距离，如果间隔太远，以致超出了参数指定的距离。 即使所有检索项都存在，也不能作为结果输出
		 * 
		 */
		/**
		SpanQuery query2 = new SpanTermQuery(new Term("bookName", "炼成"));
		SpanQuery[] queryarrary = new SpanQuery[] { query, query2 };
		SpanQuery queryOther = new SpanNearQuery(queryarrary, 2, true);// 数值代表跨度范围
		*/
		/**
		SpanQuery query2 = new SpanTermQuery(new Term("bookName", "网校"));
		SpanQuery[] queryarrary = new SpanQuery[] { query, query2 };
		SpanQuery queryOtherTmp = new SpanNearQuery(queryarrary, 3, true);// 数值代表跨度范围
		SpanQuery query3 = new SpanTermQuery(new Term("bookName", "学习"));
		queryarrary = new SpanQuery[] { queryOtherTmp, query3 };
		SpanQuery queryOther = new SpanNearQuery(queryarrary, 1, true);// 数值代表跨度范围
		*/
		/**
		 * 指定查询中，如果特定索引项落入了某两个查询项的跨度范围内，就把该文档从结果集中排除。
		 * 在SpanNearQuery对象的基础上
		 * ，增加对特定SpanTermQuery对象的检查，避免对象内容处于SpanNearQuery对象描述的区间范围内。
		 */
		/**
		SpanQuery query2 = new SpanTermQuery(new Term("bookName", "炼成"));
		SpanQuery query3 = new SpanTermQuery(new Term("bookName", "是"));
        SpanQuery[] queryarrary = new SpanQuery[]{query, query2};
        SpanQuery nearquery = new SpanNearQuery(queryarrary, 5, true);//数值代表跨度范围
        SpanQuery queryOther = new SpanNotQuery(nearquery, query3);
        */
		/**
		 * 用来对SpanQuery对象进行封装，用来组合其他SpanQuery对象，得到满足任一个跨度的查询结果合并后作为整体输出。
		 * 是两个SpanNearQuery对象的基础上
		 * ，增加对特定SpanTermQuery对象的检查，避免对象内容处于SpanNearQuery对象描述的区间范围内。
		 * 得到的结果表明所有满足其中任意一个区间跨度需求的文档都作为结果页最后生成。完成的功能相当于全局跨度区间的合并汇总
		 */
		
		SpanQuery query2 = new SpanTermQuery(new Term("bookName", "炼成"));
		SpanQuery query3 = new SpanTermQuery(new Term("bookName", "会计"));
		SpanQuery query4 = new SpanTermQuery(new Term("bookName", "可以"));
		SpanQuery query5 = new SpanTermQuery(new Term("bookName", "兄弟"));
		SpanQuery query6 = new SpanTermQuery(new Term("bookName", "女儿"));
        SpanQuery[] queryarrary1 = new SpanQuery[]{query, query2};
        SpanQuery[] queryarrary2 = new SpanQuery[]{query4, query3};
        SpanQuery[] queryarrary3 = new SpanQuery[]{query5, query6};
        SpanQuery nearquery1 = new SpanNearQuery(queryarrary1, 5, true);//数值代表跨度范围
        SpanQuery nearquery2 = new SpanNearQuery(queryarrary2, 5, false);//为false表示不需要匹配顺序
        SpanQuery nearquery3 = new SpanNearQuery(queryarrary3, 5, true);
        SpanQuery queryOther = new SpanOrQuery(new SpanQuery[]{nearquery1, nearquery2, nearquery3});		 
		
		TopDocs topDocs = isearcher.search(queryOther, 10);

		System.out.println("匹配了：" + topDocs.totalHits + "次！");
		BitSet bits = new BitSet(ireader.maxDoc());

		// 输出结果
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			bits.set(scoreDocs[i].doc);
			System.out.println("内容：" + targetDoc.get("bookName"));
		}
		AnalyzerUtils.dumpSpans(query, ireader, bits);

	}

}
