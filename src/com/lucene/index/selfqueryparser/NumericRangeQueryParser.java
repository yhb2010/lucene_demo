package com.lucene.index.selfqueryparser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

public class NumericRangeQueryParser extends QueryParser {

	public NumericRangeQueryParser(Version matchVersion, String f, Analyzer a) {
		super(matchVersion, f, a);
		// TODO Auto-generated constructor stub
	}

	public Query getRangeQuery(String field, String part1, String part2,
			boolean inclusive1, boolean inclusive2) throws ParseException {
		TermRangeQuery query = (TermRangeQuery) super.getRangeQuery(field,
				part1, part2, inclusive1, inclusive2);
		if ("price".equals(field)) {
			return NumericRangeQuery.newDoubleRange(field,
					Double.parseDouble(query.getLowerTerm().utf8ToString()),
					Double.parseDouble(query.getUpperTerm().utf8ToString()),
					query.includesLower(), query.includesUpper());
		} else {
			return query;
		}
	}
}
