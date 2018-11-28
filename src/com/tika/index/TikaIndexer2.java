package com.tika.index;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class TikaIndexer2 implements IndexerInterface {

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		/* 指明要索引的文件所在文件夹的位置 */
		File dataDir = new File(Contants.sourcePath2);
		File[] dataFiles = dataDir.listFiles();
		for (int i = 0; i < dataFiles.length; i++) {
			File f = dataFiles[i];
			System.out.println("Indexing file：" + f.getCanonicalPath());
			Tika tika = new Tika();
			Reader txtReader = tika.parse(f);
			try {
				System.out.println("all text：" + tika.parseToString(f));
			} catch (TikaException e) {
				e.printStackTrace();
			}

			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new StringField("ID", "10000", Field.Store.YES));
			doc.add(new TextField("text", txtReader));
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

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new TikaIndexer2();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);
	}

}
