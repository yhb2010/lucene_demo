package com.lucene.index.methd;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

public class Indexer {
	private Analyzer analyzer;

	/**
	 * 获取索引
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getIndexer(Directory dir) throws IOException {
		createAnalyzer();
		IndexWriterConfig config = getConfig();
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		return new IndexWriter(dir, config);
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void createAnalyzer() {
		analyzer = new ComplexAnalyzer();
	}

	public IndexWriterConfig getConfig() {
		return new IndexWriterConfig(Version.LUCENE_45, analyzer);
	}

}
