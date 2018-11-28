package com.higtlighter.index;

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
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class StringIndexer implements IndexerInterface {
	private String[] str = new String[] {
			"地貌,histort，自然风光出色,奇特景观,河流,土地,寓意风景,美景,水,国内山水好看", "地貌，风光",
			"java is my new slow language, we are cdel jumped 好看出色", "张三丰",
			"蓝天,自然风光,云,国内山水,依山傍水,山岩,奇特景观,天空,森林,山,水", "亲亲宝宝网" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file：" + str[i]);
			Document doc = new Document();
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
