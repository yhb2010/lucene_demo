package com.lucene.index.methd;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.payloads.FloatEncoder;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.lucene.analysis.payload.PayloadAnalyzer;
import com.lucene.similarity.PayloadSimilarity;

public class Indexer4PayloadTermQuery {
	private Analyzer analyzer;

	/**
	 * 获取索引
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getIndexer(Directory dir) throws IOException {
		analyzer = new PayloadAnalyzer(new FloatEncoder()); // 使用PayloadAnalyzer，并指定Encoder
		Similarity similarity = new PayloadSimilarity();
		
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_45,
				analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND)
				.setSimilarity(similarity);
		return new IndexWriter(dir, config);
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

}
