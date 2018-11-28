package com.lucene.index.delete;

import java.io.IOException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import com.lucene.index.methd.Indexer;

public class DeleteIndexer {
	/**
	 * 删除索引
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public void delete(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);
		System.out.println("del共" + writer.numDocs() + "个文档！");
		writer.deleteDocuments(new Term("ID", "序号：0"));
		// writer.commit();
		System.out.println(writer.hasDeletions());
		System.out.println("del共" + writer.maxDoc() + "个文档！");
		System.out.println("del共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

	public void delete(IndexWriter writer) throws IOException {
		System.out.println("del共" + writer.numDocs() + "个文档！");
		writer.deleteDocuments(new Term("ID", "序号：0"));
		// writer.commit();
		System.out.println(writer.hasDeletions());
		System.out.println("del共" + writer.maxDoc() + "个文档！");
		System.out.println("del共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

}
