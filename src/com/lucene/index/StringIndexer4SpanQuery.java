package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.methd.Indexer;

public class StringIndexer4SpanQuery implements IndexerInterface {
	private String[] bookNames = new String[] { "钢铁是怎样炼成钢铁的", "钢铁战士", "篱笆女人和狗",
			"女人是水做的", "英雄儿女", "白毛女", "我的兄弟和女儿", "张三丰钢铁大侠",
			"会计考试是很难的，中华网校可以学习" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new IntField("bookNumber", i, Field.Store.YES));
			doc.add(new TextField("bookName", bookNames[i], Field.Store.YES));
			writer.addDocument(doc);
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), bookNames[i]);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
