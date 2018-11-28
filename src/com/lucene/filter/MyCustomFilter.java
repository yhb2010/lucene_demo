package com.lucene.filter;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.OpenBitSet;

/**
 * 自定义过滤器
 * 
 * @author dell
 * 
 */
public class MyCustomFilter extends Filter {
	public MyCustomFilter() {
		// TODO Auto-generated constructor stub
	}

	private String fieldName;
	private String[] terms;// 限制返回的数据字典

	public MyCustomFilter(String fieldName, String... terms) {
		// TODO Auto-generated constructor stub
		this.fieldName = fieldName;
		this.terms = terms;
	}

	@Override
	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits arg1)
			throws IOException {
		int length = context.reader().maxDoc();
		OpenBitSet openBitSet = new OpenBitSet(length);

		for (String string : terms) {
			TermQuery query = new TermQuery(new Term(fieldName, string));
			IndexSearcher indexSearcher = new IndexSearcher(context.reader());
			TopDocs docs = indexSearcher.search(query, length);
			ScoreDoc[] scoreDocs = docs.scoreDocs;
			if (scoreDocs.length == 1) {
				openBitSet.set(0, length);
			}
		}
		return openBitSet;

	}

}
