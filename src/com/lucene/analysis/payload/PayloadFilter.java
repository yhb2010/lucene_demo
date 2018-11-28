package com.lucene.analysis.payload;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.payloads.PayloadEncoder;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;

public class PayloadFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final PayloadAttribute payAtt = addAttribute(PayloadAttribute.class);
	private final PayloadEncoder encoder;

	public PayloadFilter(TokenStream input, PayloadEncoder encoder) {
		super(input);
		this.encoder = encoder;
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String term = termAtt.toString();
			if (term.equals("汽车")) {
				payAtt.setPayload(encoder.encode("5f".toCharArray()));
			} else if (term.equals("轮船")) {
				payAtt.setPayload(encoder.encode("20f".toCharArray()));
			} else if (term.equals("飞机")) {
				payAtt.setPayload(encoder.encode("100f".toCharArray()));
			} else {
				payAtt.setPayload(null);
			}
			return true;
		} else
			return false;
	}

}
