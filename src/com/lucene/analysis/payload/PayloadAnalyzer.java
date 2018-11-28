package com.lucene.analysis.payload;

import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.payloads.PayloadEncoder;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class PayloadAnalyzer extends ComplexAnalyzer {

	private PayloadEncoder encoder;

	public PayloadAnalyzer() {
		// TODO Auto-generated constructor stub
		super();
	}

	public PayloadAnalyzer(PayloadEncoder encoder) {
		super();
		this.encoder = encoder;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader reader) {
		final Tokenizer source = new MMSegTokenizer(newSeg(), reader);
		// 自定义的Filter,用来获取字段的Payload值
		return new TokenStreamComponents(source, new PayloadFilter(source,
				encoder));
	}

}
