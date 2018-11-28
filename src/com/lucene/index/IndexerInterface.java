package com.lucene.index;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

public interface IndexerInterface {

	public Directory initDic() throws Exception;

	public Directory initDic2() throws Exception;

	public void addDocuments(Directory dir) throws IOException;

	public void addDocuments(IndexWriter writer) throws IOException;

}
