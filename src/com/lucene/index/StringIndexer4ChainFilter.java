package com.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.index.methd.Indexer;

public class StringIndexer4ChainFilter implements IndexerInterface {

	public static final int MAX = 500;

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		Calendar cal = Calendar.getInstance();
		cal.set(2014, 1, 1, 0, 0);// 设置为2014年1月1日

		for (int i = 0; i < MAX; i++) {
			Document doc = new Document();
			// StringField只能精确匹配
			// doc.add(new StringField("ID", "1_" + i, Field.Store.YES));
			doc.add(new IntField("key", (i + 1), Field.Store.YES));
			doc.add(new StringField("owner", (i < MAX / 2) ? "张苏磊" : "陈黎",
					Field.Store.YES));
			doc.add(new StringField("date", DateTools.timeToString(
					cal.getTimeInMillis(), DateTools.Resolution.DAY),
					Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
			cal.add(Calendar.DATE, 1);
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
