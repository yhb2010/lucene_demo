package com.lucene.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.lucene.Contants;
import com.lucene.index.methd.Indexer;

public class TxtFileIndexer implements IndexerInterface {

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
				File file = dataFiles[i];
				// StringField等只能精确匹配
				doc.add(new StringField("filename", file.getName(), Store.YES));
				doc.add(new TextField("text", getFileContent(file), Store.YES));
				doc.add(new LongField("size", file.getTotalSpace(), Store.YES));
				System.out.println("filename==" + doc.get("filename"));
				writer.addDocument(doc);
				writer.commit();
			}
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String getFileContent(File file) throws IOException {
		String charsetName = "GBK";

		String result = "";
		if (file.isFile() && file.exists()) {
			InputStreamReader insReader = new InputStreamReader(
					new FileInputStream(file), charsetName);

			BufferedReader bufReader = new BufferedReader(insReader);

			String line = new String();
			while ((line = bufReader.readLine()) != null) {
				result = result + "\n" + line;
				System.out.println(line);
			}
			bufReader.close();
			insReader.close();
		}
		return result;
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
