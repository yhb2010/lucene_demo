package com.lucene.index.selfscore;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.Query;

//自定义打分
public class CountingQuery extends CustomScoreQuery {

	public CountingQuery(Query subQuery) {
		super(subQuery);
	}

	@Override
	protected CustomScoreProvider getCustomScoreProvider(
			AtomicReaderContext context) throws IOException {
		return new CountingQueryScoreProvider(context, "tag");
	}

}
