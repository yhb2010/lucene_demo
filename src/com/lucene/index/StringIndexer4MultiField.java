package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.methd.Indexer;

public class StringIndexer4MultiField implements IndexerInterface {
	private String[] bookNumbers = new String[] { "0000001", "0000002",
			"0000003", "0000004", "0000005", "0000006", "0000007", "0000008" };
	private String[] bookNames = new String[] { "钢铁是怎样炼成的", "钢铁战士", "篱笆女人和狗",
			"女人是水做的", "英雄儿女", "白毛女", "我的兄弟和女儿", "张三丰" };
	private String[] contents = new String[] { "钢铁", "篱笆", "篱笆", "女人是水做的",
			"英雄儿女", "白毛女", "我的兄弟和女儿", "张三丰" };
	private String[] publishDates = new String[] { "1997-01-01", "1992-01-01",
			"1992-01-01", "1999-01-01", "1999-01-01", "1999-01-01",
			"1999-01-01", "1998-01-01" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < bookNumbers.length; i++) {
			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new StringField("bookNumber", bookNumbers[i],
					Field.Store.YES));
			doc.add(new TextField("bookName", bookNames[i], Field.Store.YES));
			doc.add(new TextField("content", contents[i], Field.Store.YES));
			doc.add(new StringField("publishDate", publishDates[i],
					Field.Store.YES));
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
