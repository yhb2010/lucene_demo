package com.lucene.index.selfscore;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * 在某些场景需要做自定义排序（非单值字段排序、非文本相关度排序），除了自己重写collect、weight，可以借助CustomScoreQuery。
 * 
 * 场景：根据tag字段中标签的数量进行排序（tag字段中，标签的数量越多得分越高）
 * 
 * @author Administrator
 * 
 */
public class CustomScoreTest {

	public static void main(String[] args) throws IOException {
		Directory dir = new RAMDirectory();
		Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_45);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		IndexWriter writer = new IndexWriter(dir, conf);
		Document doc1 = new Document();
		FieldType type1 = new FieldType();
		type1.setIndexed(true);
		type1.setStored(true);
		type1.setStoreTermVectors(true);
		Field field1 = new Field("f1", "fox", type1);
		doc1.add(field1);
		Field field2 = new Field("tag", "fox1 fox2 fox3 ", type1);
		doc1.add(field2);
		writer.addDocument(doc1);
		//
		field1.setStringValue("fox");
		field2.setStringValue("fox1");
		doc1 = new Document();
		doc1.add(field1);
		doc1.add(field2);
		writer.addDocument(doc1);
		//
		field1.setStringValue("fox");
		field2.setStringValue("fox1 fox2 fox3 fox4");
		doc1 = new Document();
		doc1.add(field1);
		doc1.add(field2);
		writer.addDocument(doc1);
		//
		writer.commit();
		//
		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dir));
		//匹配的所有文件
		Query query = new MatchAllDocsQuery();
		Query selfquery = new CountingQuery(query);
		int n = 10;
		TopDocs tds = searcher.search(selfquery, n);
		ScoreDoc[] sds = tds.scoreDocs;
		for (ScoreDoc sd : sds) {
			System.out.println(searcher.doc(sd.doc));
		}
	}

}
