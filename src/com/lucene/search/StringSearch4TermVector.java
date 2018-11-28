package com.lucene.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.index.IndexerInterface;
import com.lucene.index.StringIndexer;
import com.lucene.index.StringIndexer4TermVector;

/**
 * 使用项向量
 * 
 * @author dell
 * 
 */
public class StringSearch4TermVector {
	private static String keyword = "序号：1";

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new StringIndexer4TermVector();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		// 开始搜索
		// 实例化搜索器
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		int count = ireader.maxDoc();
		for (int i = 0; i < count; i++) {
			Terms terms = ireader.getTermVector(i, "bookName");
			if (terms != null && terms.size() > 0) {
				TermsEnum termsEnum = terms.iterator(null);
				BytesRef term = null;
				int j = 0;
				while ((term = termsEnum.next()) != null) {
					int freq = (int) termsEnum.totalTermFreq();
					if (j == 0)
						System.out.print("文档" + i + "有term：");
					System.out.print("\"" + termsEnum.term().utf8ToString()
							+ "\"，出现" + freq + "次；");
					j++;
				}
				System.out.println();
				termsEnum = terms.iterator(null);
				while ((term = termsEnum.next()) != null) {
					DocsEnum docsEnum = termsEnum.docs(null, null);
					int docIdEnum;
					while ((docIdEnum = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
						System.out.print(term.utf8ToString() + " " + docIdEnum
								+ " " + docsEnum.freq() + "；");

					}
				}
				System.out.println();
			}

		}

	}
}
