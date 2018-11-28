package com.lucene.search;

import org.apache.lucene.store.Directory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer4Stop;

/**
 * 停用词
 *
 * @author dell
 *
 */
public class StringSearch4Stop {

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4Stop();
		Directory dic = indexer.initDic();
		indexer.addDocuments(dic);

	}

}
