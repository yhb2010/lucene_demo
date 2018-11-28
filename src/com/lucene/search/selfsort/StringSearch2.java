package com.lucene.search.selfsort;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FieldDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.selfsort.DistanceComparatorSource;
import com.lucene.index.selfsort.StringIndexer;

/**
 * 对查询结果进行自定义排序，并访问自定义排序中的值
 * 
 * @author dell
 * 
 */
public class StringSearch2 {
	private static Query query;

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dic);// 尽量少打开
		IndexSearcher isearcher = new IndexSearcher(ireader);

		query = new TermQuery(new Term("type", "restaurant"));
		Sort sort = new Sort(new SortField("location",
				new DistanceComparatorSource(0, 0)));

		System.out.println(sort.toString());

		TopFieldDocs docs = isearcher.search(query, null, 10, sort);

		System.out.println("匹配了：" + docs.scoreDocs.length + "次！");

		for (int i = 0; i < docs.scoreDocs.length; i++) {
			FieldDoc fieldDoc = (FieldDoc) docs.scoreDocs[i];
			Document targetDoc = isearcher.doc(fieldDoc.doc);
			System.out.println("名称：" + targetDoc.get("name"));
			System.out.println("坐标：" + targetDoc.get("location"));
			System.out.println("距离：" + fieldDoc.fields[0]);
		}

	}

}
