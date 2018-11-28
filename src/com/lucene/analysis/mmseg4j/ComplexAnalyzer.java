package com.lucene.analysis.mmseg4j;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;
import com.lucene.util.FileReadUtil;

/**
 * 重写mmseg4j复杂分析，增加停用词功能(注意：停用词文件必须用utf-8无bom保存)，同义词功能
 *
 * @author dell
 *
 */
public class ComplexAnalyzer extends
		com.chenlb.mmseg4j.analysis.ComplexAnalyzer {

	private CharArraySet stopWords;
	private SynonymMap smap = null;

	public ComplexAnalyzer() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * 重写mmseg4j复杂分析，增加停用词功能
	 *
	 * @param fileName
	 */
	public ComplexAnalyzer(String fileName) {
		super();

		List<String> list = FileReadUtil.readFileByLines(fileName);
		stopWords = new CharArraySet(Version.LUCENE_45, list, true);

		String base1 = "精彩";
		String syn1 = "出色";
		String syn11 = "好看";
		String base2 = "slow";
		String syn2 = "sluggish";

		SynonymMap.Builder sb = new SynonymMap.Builder(true);
		sb.add(new CharsRef(base1), new CharsRef(syn1), true);
		sb.add(new CharsRef(base1), new CharsRef(syn11), true);
		sb.add(new CharsRef(base2), new CharsRef(syn2), true);
		sb.add(new CharsRef(syn2), new CharsRef(base2), true);

		try {
			smap = sb.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		// 创建一个分词器
		final Tokenizer source = new MMSegTokenizer(newSeg(), reader);
		// 创建一系列的分词过滤器
		return new TokenStreamComponents(source, new SynonymFilter(
				new StopFilter(Version.LUCENE_45, source, stopWords), smap,
				true));
	}

}
