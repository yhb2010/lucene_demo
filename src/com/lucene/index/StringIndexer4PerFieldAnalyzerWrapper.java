package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
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
import com.lucene.index.methd.Indexer4PerFieldAnalyzerWrapper;

public class StringIndexer4PerFieldAnalyzerWrapper implements IndexerInterface {
	private String[] str = new String[] { "地貌,自然风光,奇特景观,河流,土地,寓意风景,美景,水,国内山水",
			"地貌，风光", "蓝天,自然风光,云,国内山水,依山傍水,山岩,奇特景观,天空,森林,山,水", "亲亲宝宝网" };
	private String[] bookNames = new String[] { "钢铁是怎样炼成的", "钢铁战士", "篱笆女人和狗",
			"女人是水做的" };
	private Double[] d2 = new Double[] { 52.2, 15.6, 20.5, 34.0, 65.3, 12.2 };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer4PerFieldAnalyzerWrapper indexer = new Indexer4PerFieldAnalyzerWrapper();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file：" + str[i]);
			Document doc = new Document();
			// StringField只能精确匹配
			// doc.add(new StringField("ID", "1_" + i, Field.Store.YES));
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new StringField("name", "文档", Field.Store.YES));
			doc.add(new TextField("bookName", bookNames[i], Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			int d = i + 3;
			doc.add(new StringField("time", "2009年01月0" + d + "日",
					Field.Store.YES));
			doc.add(new IntField("time2", 20090100 + d, Field.Store.YES));
			doc.add(new IntField("count", d, Field.Store.YES));
			doc.add(new DoubleField("price", d2[i], Field.Store.YES));
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), "text", str[i]);
			System.out.println("=================================");
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), "bookName",
					bookNames[i]);
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
