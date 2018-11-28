package com.lucene.analysis.synonym;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.Version;

/**
 * 同义词分析器
 * 
 * @author dell
 * 
 */
public class SynonymAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader reader) {
		// TODO Auto-generated method stub
		final Tokenizer source = new LowerCaseTokenizer(Version.LUCENE_45,
				reader);

		String base1 = "精彩";
		String syn1 = "出色";
		String syn11 = "好看";
		String base2 = "slow";
		String syn2 = "sluggish";

		SynonymMap.Builder sb = new SynonymMap.Builder(true);
		sb.add(new CharsRef(base1), new CharsRef(syn1), true);
		sb.add(new CharsRef(base1), new CharsRef(syn11), true);
		sb.add(new CharsRef(base2), new CharsRef(syn2), true);
		SynonymMap smap = null;
		try {
			smap = sb.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new TokenStreamComponents(source, new SynonymFilter(source,
				smap, true));
	}
}
