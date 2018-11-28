package com.lucene.index.selfsort;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class StringIndexer implements IndexerInterface {

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		addPoint(writer, "El Charro", "restaurant", 6, 1);
		addPoint(writer, "Los Betos", "restaurant", 7, 5);
		addPoint(writer, "Cafe Poca Cosa", "restaurant", 1, 5);
		addPoint(writer, "Nico's Taco Shop", "restaurant", 8, 3);
		addPoint(writer, "UME", "movie", 2, 4);

		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

	private void addPoint(IndexWriter writer, String name, String type, int x,
			int y) throws IOException {
		Document doc = new Document();
		doc.add(new StringField("name", name, Field.Store.YES));
		doc.add(new StringField("type", type, Field.Store.YES));
		doc.add(new StringField("location", x + "," + y, Field.Store.YES));
		writer.addDocument(doc);

	}

}
