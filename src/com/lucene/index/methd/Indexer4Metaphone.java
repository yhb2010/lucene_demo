package com.lucene.index.methd;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.lucene.analysis.metaphone.MetaphoneReplacementAnalyzer;

public class Indexer4Metaphone {
	private Analyzer analyzer;

	/**
	 * 获取索引
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getIndexer(Directory dir) throws IOException {
		analyzer = new MetaphoneReplacementAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		return new IndexWriter(dir, config);
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

}
