package com.lucene.index.add;

import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import com.lucene.index.methd.Indexer;

public class AddIndexer {
	/**
	 * 添加索引
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public void add(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);
		System.out.println("add共" + writer.maxDoc() + "个文档！");
		String str = "我是新更新进来的文档，明天周末去外国看看山水太棒了，国内太差了！";
		Document doc = new Document();
		doc.add(new StringField("ID", "1_5", Field.Store.YES));
		doc.add(new TextField("text", str, Field.Store.YES));
		writer.addDocument(doc);

		System.out.println("add共" + writer.maxDoc() + "个文档！");
		System.out.println("add共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

	public void add(IndexWriter writer) throws IOException {
		System.out.println("add共" + writer.maxDoc() + "个文档！");
		String str = "我是新更新进来的文档，明天周末去外国看看山水太棒了，国内太差了！";
		Document doc = new Document();
		doc.add(new StringField("ID", "1_5", Field.Store.YES));
		doc.add(new TextField("text", str, Field.Store.YES));
		writer.addDocument(doc);

		System.out.println("add共" + writer.maxDoc() + "个文档！");
		System.out.println("add共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

}
