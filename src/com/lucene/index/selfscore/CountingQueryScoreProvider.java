package com.lucene.index.selfscore;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.CustomScoreProvider;

public class CountingQueryScoreProvider extends CustomScoreProvider {

	String field;

	public CountingQueryScoreProvider(AtomicReaderContext context) {
		super(context);
	}

	public CountingQueryScoreProvider(AtomicReaderContext context, String field) {
		super(context);
		this.field = field;
	}

	// 根据tag字段中标签的数量进行排序（tag字段中，标签的数量越多得分越高）
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScores[])
			throws IOException {
		IndexReader r = context.reader();
		Terms tv = r.getTermVector(doc, field);
		TermsEnum termsEnum = null;
		int numTerms = 0;
		if (tv != null) {
			termsEnum = tv.iterator(termsEnum);
			while ((termsEnum.next()) != null) {
				numTerms++;
			}
		}
		return (float) (numTerms);
	}

}
