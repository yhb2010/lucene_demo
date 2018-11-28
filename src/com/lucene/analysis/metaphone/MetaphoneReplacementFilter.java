package com.lucene.analysis.metaphone;

import java.io.IOException;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 根据等效音素替换语汇单元
 * 
 * @author Administrator
 * 
 */
public class MetaphoneReplacementFilter extends TokenFilter {
	public static final String METAPHONE = "metaphone";

	private DoubleMetaphone metaphoner = new DoubleMetaphone();
	private CharTermAttribute cta;
	private TypeAttribute typeAttr;

	public MetaphoneReplacementFilter(TokenStream input) {
		super(input);
		cta = addAttribute(CharTermAttribute.class);
		typeAttr = addAttribute(TypeAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (!input.incrementToken())
			return false;

		String encoded;
		encoded = metaphoner.encode(cta.toString());
		cta.setEmpty();
		cta.append(encoded);
		typeAttr.setType(METAPHONE);
		return true;
	}

}
