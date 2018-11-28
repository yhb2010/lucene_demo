package com.spell.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class SpellCheckerIndexer implements IndexerInterface {

	private String[] bookNames = new String[] { "麻辣烫和麻辣火锅都起源自四川",
			"CSDN-全球最大中文IT社区" };

	@Override
	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	@Override
	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	@Override
	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < bookNames.length; i++) {
			Document doc = new Document();
			doc.add(new TextField("bookName", bookNames[i], Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
	}

}
