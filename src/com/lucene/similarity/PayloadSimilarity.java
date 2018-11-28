package com.lucene.similarity;

import org.apache.lucene.analysis.payloads.PayloadHelper;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.util.BytesRef;

public class PayloadSimilarity extends DefaultSimilarity {
	
	@Override
	public float scorePayload(int doc, int start, int end, BytesRef payload) {
		return PayloadHelper.decodeFloat(payload.bytes);
	}
	
}