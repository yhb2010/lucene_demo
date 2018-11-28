package com.lucene.index.update;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import com.lucene.index.methd.Indexer;

public class UpdateIndexer {
	/**
	 * 更新/添加索引
	 *
	 * @param dir
	 * @throws IOException
	 */
	public void updateOrAdd(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);
		System.out.println("共" + writer.maxDoc() + "个文档！");
		String str = "我是新更新进来的文档，明天周末去外国看看山水太棒了，国内太差了！";
		Document doc = new Document();
		doc.add(new StringField("ID", "1_2", Field.Store.YES));
		doc.add(new TextField("text", str, Field.Store.YES));
		// 更新不存在的索引，则直接插入，性能不好，尽量不要使用
		writer.updateDocument(new Term("ID", "序号：0"), doc);
		// writer.addDocument(doc);

		System.out.println("共" + writer.maxDoc() + "个文档！");
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	// 先找到，然后修改field，再更新
	public void updateOrAdd2(Directory dir, IndexSearcher isearcher, Term t)
			throws IOException {
		TermQuery query = new TermQuery(t);
		TopDocs hits = isearcher.search(query, 10);
		Document doc = null;
		if (hits.scoreDocs.length == 0) {
			throw new IllegalArgumentException("索引中没有匹配的结果");
		} else if (hits.scoreDocs.length > 1) {
			throw new IllegalArgumentException("索引中匹配的结果大于1");
		} else {
			int docID = hits.scoreDocs[0].doc;
			doc = isearcher.doc(docID);
		}

		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);
		System.out.println("共" + writer.maxDoc() + "个文档！");
		String str = "我是新更新进来的文档，明天周末去外国看看山水太棒了，国内太差了！";
		Field textFiled = (Field) doc.getField("text");
		textFiled.setStringValue(str);
		doc.removeField("text");
		doc.add(textFiled);
		writer.updateDocument(new Term("ID", "序号：0"), doc);

		System.out.println("共" + writer.maxDoc() + "个文档！");
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}
}
