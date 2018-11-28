package com.lucene.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.Spans;
import org.apache.lucene.util.DocIdBitSet;

public class AnalyzerUtils {
	public static void displayTokens(Analyzer analyzer, String text)
			throws IOException {
		displayTokens(analyzer.tokenStream("contents", new StringReader(text)));
	}

	public static void displayTokens(Analyzer analyzer, String fieldName,
			String text) throws IOException {
		displayTokens(analyzer.tokenStream(fieldName, new StringReader(text)));
	}

	/**
	 * 显示分词结果
	 *
	 * @param stream
	 * @throws IOException
	 */
	public static void displayTokens(TokenStream stream) throws IOException {
		// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
		CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
		stream.reset();// 不添加会显示空指针错误
		while (stream.incrementToken()) {
			System.out.println("[" + cta + "]");
		}
		stream.end();
	}

	/**
	 * 显示分词的所有信息
	 *
	 * @param stream
	 * @throws IOException
	 */
	public static void displayTokensWithFullDetail(TokenStream stream)
			throws IOException {
		// 位置增量的属性，存储语汇单元之间的距离
		PositionIncrementAttribute pis = stream
				.addAttribute(PositionIncrementAttribute.class);
		// 每个语汇单元的位置偏移量
		OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
		// 创建一个属性，这个属性会添加流中，随着这个TokenStream增加
		CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
		// 使用的分词器的类型信息
		TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
		stream.reset();// 不添加会显示空指针错误
		while (stream.incrementToken()) {
			System.out.print("增量:" + pis.getPositionIncrement() + ":");
			System.out.print("分词:" + cta + "->位置:[" + oa.startOffset() + "~"
					+ oa.endOffset() + "]->类型:" + ta.type() + "\n");
		}
		stream.end();
	}

	/**
	 * 跨度查询的帮助方法
	 *
	 * @param query
	 */
	public static void dumpSpans(SpanQuery query, IndexReader reader,
			BitSet bits) throws IOException {
		Map<Term, TermContext> termContexts = new HashMap<Term, TermContext>();
		int i = 0;
		int docID = 0;
		for (AtomicReaderContext atomic : reader.getContext().leaves()) {
			Spans spans = query.getSpans(atomic, new DocIdBitSet(bits),
					termContexts);
			if (bits.get(i)) {
				docID = i;
				Document targetDoc = reader.document(docID);
				// System.out.println("内容：" + targetDoc.get("bookName"));
			}
			while (spans.next()) {
				System.out.print(spans.start() + "-");
				System.out.println(spans.end());
			}
			i++;
		}
	}

}
