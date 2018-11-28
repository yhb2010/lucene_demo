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
import com.lucene.index.methd.Indexer4PayloadTermQuery;

public class StringIndexer4PayloadTermQuery implements IndexerInterface {
	private String[] d2 = new String[] { "轮船是水上工具", "飞机是天上工具", "汽车是陆地工具" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer4PayloadTermQuery indexer = new Indexer4PayloadTermQuery();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < d2.length; i++) {
			Document doc = new Document();
			doc.add(new IntField("index", i, Field.Store.YES));
			doc.add(new TextField("tools", d2[i], Field.Store.YES));
			writer.addDocument(doc);
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), d2[i]);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
