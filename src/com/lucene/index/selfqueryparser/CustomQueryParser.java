package com.lucene.index.selfqueryparser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.Query;

public class CustomQueryParser extends QueryParser {

	public CustomQueryParser(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
		// TODO Auto-generated constructor stub
	}

	protected final Query getWildcardQuery(String field, String termStr)
			throws ParseException {
		throw new ParseException("Wildcard not allowed");
	}

	protected final Query getFuzzyQuery(String field, String termStr,
			float minSimilarity) throws ParseException {
		throw new ParseException("Fuzzy");
	}

}
