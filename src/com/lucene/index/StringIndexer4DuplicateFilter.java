package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.methd.Indexer;

public class StringIndexer4DuplicateFilter implements IndexerInterface {

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		Document doc = new Document();
		doc.add(new StringField("id", "1", Field.Store.YES));
		doc.add(new TextField("content", "你好", Field.Store.YES));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new StringField("id", "1", Field.Store.YES));
		doc.add(new TextField("content", "你好，日本", Field.Store.YES));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new StringField("id", "1", Field.Store.YES));
		doc.add(new TextField("content", "你好，美国", Field.Store.YES));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new StringField("id", "2", Field.Store.YES));
		doc.add(new TextField("content", "你好，法国", Field.Store.YES));
		writer.addDocument(doc);

		doc = new Document();
		doc.add(new StringField("id", "2", Field.Store.YES));
		doc.add(new TextField("content", "你好，韩国", Field.Store.YES));
		writer.addDocument(doc);

		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
