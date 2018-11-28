package com.lucene.index;

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

import com.lucene.Contants;
import com.lucene.index.methd.FileReader2;
import com.lucene.index.methd.Indexer;

public class TxtFileIndexer2 implements IndexerInterface {

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
		File dataDir = new File(Contants.sourcePath);
		File[] dataFiles = dataDir.listFiles();
		for (int i = 0; i < dataFiles.length; i++) {
			if (dataFiles[i].isFile()
					&& dataFiles[i].getName().endsWith(".txt")) {
				System.out.println("Indexing file："
						+ dataFiles[i].getCanonicalPath());
				Document doc = new Document();
				Reader txtReader = new FileReader2(
						dataFiles[i].getCanonicalPath(), "GBK");
				// StringField只能精确匹配
				doc.add(new StringField("ID", "10000", Field.Store.YES));
				doc.add(new TextField("text", txtReader));
				writer.addDocument(doc);
				writer.commit();
			}
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
