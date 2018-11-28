package com.lucene.index.methd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

/**
 * PerFieldAnalyzerWrapper功能主要用在针对不同的Field采用不同的Analyzer的场合。比如对于文件名，
 * 需要使用KeywordAnalyzer，而对于文件内容只使用StandardAnalyzer就可以了。
 *
 * @author Administrator
 *
 */
public class Indexer4PerFieldAnalyzerWrapper {
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
		Map<String, Analyzer> analyzerPerField = new HashMap<String, Analyzer>();
		analyzerPerField.put("bookName",
				new StandardAnalyzer(Version.LUCENE_45));
		analyzer = new PerFieldAnalyzerWrapper(new ComplexAnalyzer(),
				analyzerPerField);

		return new IndexWriterConfig(Version.LUCENE_45, analyzer);
	}

}
