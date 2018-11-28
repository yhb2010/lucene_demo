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
import com.lucene.index.methd.Indexer4Synonym;

public class StringIndexer4Synonym implements IndexerInterface {
	private String[] str = new String[] { "精彩 cat" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer4Synonym indexer = new Indexer4Synonym();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file：" + str[i]);
			Document doc = new Document();
			// StringField只能精确匹配
			// doc.add(new StringField("ID", "1_" + i, Field.Store.YES));
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			int d = i + 3;
			doc.add(new StringField("time", "2009年01月0" + d + "日",
					Field.Store.YES));
			doc.add(new IntField("count", d, Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file " + str[i]);
			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			int d = i + 5;
			doc.add(new StringField("time", "2009年01月0" + d + "日",
					Field.Store.YES));
			doc.add(new IntField("count", d, Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

}
