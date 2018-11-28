package com.lucene.index.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.util.BytesRef;

/**
 * 自定义Collector collector的作用就是收集某些我们需要定制化的结果集，某些情况下使用collector可以可以极大的提升我们程序的性能，
 * 通过collector可以让我们对每一个匹配上的文档做一些特有的定制化操作
 * 
 * @author dell
 * 
 */
public class BookLinkCollector extends Collector {

	private String[] bookNumber;
	private String[] publishDate;
	private Scorer scorer;
	private Map<String, String> map = new HashMap<String, String>();
	private int docBase;// 全局相对段基数
	public List<ScoreDoc> docs = new ArrayList<ScoreDoc>();

	// collect()检索时，每匹配上一个文档，都会调用此方法
	// acceptsDocsOutOfOrder()测试本collector是否能处理无序到达的docid
	// setScorer(Scorer scorer)处理检索结果的评分
	// setNextReader(AtomicReaderContext context)检索时，在多个索引段结构之间切换的方法

	@Override
	public boolean acceptsDocsOutOfOrder() {
		// 返回true是允许无次序的ID
		// 返回false必须是有次序的
		return true;

	}

	@Override
	public void collect(int docID) throws IOException {
		// TODO Auto-generated method stub
		String key = bookNumber[docID];
		String value = publishDate[docID];
		map.put(key, value);
		docs.add(new ScoreDoc(docID + docBase, scorer.score()));
	}

	@Override
	public void setNextReader(AtomicReaderContext context) throws IOException {
		// TODO Auto-generated method stub
		BinaryDocValues bdv = FieldCache.DEFAULT.getTerms(context.reader(),
				"bookNumber", false);
		BinaryDocValues bdv2 = FieldCache.DEFAULT.getTerms(context.reader(),
				"publishDate", false);

		int length = context.reader().maxDoc();
		bookNumber = new String[length];
		publishDate = new String[length];
		for (int i = 0; i < length; i++) {
			BytesRef br = new BytesRef();
			bdv.get(i, br);
			bookNumber[i] = br.utf8ToString();
			bdv2.get(i, br);
			publishDate[i] = br.utf8ToString();
		}
		this.docBase = context.docBase;// 记录每个索引段结构的相对位置

	}

	@Override
	public void setScorer(Scorer scorer) throws IOException {
		// TODO Auto-generated method stub
		this.scorer = scorer;

	}

	public Map<String, String> getMap() {
		return Collections.unmodifiableMap(map);
	}

}
