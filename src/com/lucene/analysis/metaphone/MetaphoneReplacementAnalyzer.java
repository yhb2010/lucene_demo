package com.lucene.analysis.metaphone;

import java.io.Reader;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import com.lucene.util.FileReadUtil;

/**
 * 同音词分析器（同时可以自定义停用词）
 * 
 * @author dell
 * 
 */
public class MetaphoneReplacementAnalyzer extends Analyzer {

	private CharArraySet stopWords;

	public MetaphoneReplacementAnalyzer() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 同音词分析器（同时可以自定义停用词）
	 * 
	 * @param fileName
	 */
	public MetaphoneReplacementAnalyzer(String fileName) {
		super();

		List<String> list = FileReadUtil.readFileByLines(fileName);
		stopWords = new CharArraySet(Version.LUCENE_45, list, true);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		// LowerCaseTokenizer接收Reader，根据Character.isLetter(char)来进行分词，并转换为字符小写
		final Tokenizer source = new LowerCaseTokenizer(Version.LUCENE_45,
				reader);
		if (stopWords == null || stopWords.size() == 0) {
			return new TokenStreamComponents(source,
					new MetaphoneReplacementFilter(source));
		}
		return new TokenStreamComponents(source,
				new MetaphoneReplacementFilter(new StopFilter(
						Version.LUCENE_45, source, stopWords)));
	}

}
