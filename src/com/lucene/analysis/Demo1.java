package com.lucene.analysis;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.lucene.Contants;
import com.lucene.analysis.metaphone.MetaphoneReplacementAnalyzer;
import com.lucene.analysis.synonym.SynonymAnalyzer;

/**
 * 过TokenStream显示分词代码演示
 * 
 * @author Administrator
 * 
 */
public class Demo1 {
	private static final String[] examples = {
			"The quick brown fox jumped over the lazy dog"
			//"XYZ Corporation - xyz@example.com", 
		//"中华会计网校"
	};

	private static final Analyzer[] analyzers = new Analyzer[] {
			//new WhitespaceAnalyzer(Version.LUCENE_45),
			//new SimpleAnalyzer(Version.LUCENE_45),
			//new StopAnalyzer(Version.LUCENE_45),
			//new StandardAnalyzer(Version.LUCENE_45), 
		//new ComplexAnalyzer(),
		//new MetaphoneReplacementAnalyzer(Contants.stopWordPath),
		new SynonymAnalyzer()
	};

	public static void main(String[] args) throws IOException {
		String[] strings = examples;

		for (String text : strings) {
			analyze(text);
		}
	}

	private static void analyze(String text) throws IOException {
		System.out.println("Analyzing \"" + text + "\"");
		for (Analyzer analyzer : analyzers) {
			String name = analyzer.getClass().getSimpleName();
			System.out.println(" " + name + "： ");
			System.out.println("    ");
			AnalyzerUtils.displayTokens(analyzer, text);
			System.out.println("\n");
		}
	}

}
