package com.spell.search;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;
import com.spell.index.SpellCheckerIndexer;

/**
 * 拼写检查（PlainTextDictionary）
 * 
 * @author Administrator
 * 
 */
public class SpellCheckerSearch {

	private SpellChecker spellchecker;

	private static String keyword = "麻辣将";

	public String[] search(String word, int numSug) {
		Directory directory = new RAMDirectory();
		try {
			spellchecker = new SpellChecker(directory);
			Indexer indexer = new Indexer();
			indexer.createAnalyzer();
			String path = SpellCheckerSearch.class.getClassLoader()
					.getResource("").toString();
			if (path.startsWith("file")) {
				path = path.substring(5);
			}
			path = path + "com" + File.separator + "spell" + File.separator
					+ "dic" + File.separator + "dic.txt";
			spellchecker.indexDictionary(
					new PlainTextDictionary(new File(path)),
					indexer.getConfig(), true);
			return getSuggestions(spellchecker, word, numSug);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String[] getSuggestions(SpellChecker spellchecker, String word,
			int numSug) throws IOException {
		return spellchecker.suggestSimilar(word, numSug);
	}

	public static void main(String[] args) throws Exception {
		IndexerInterface indexer = new SpellCheckerIndexer();
		Directory dir = indexer.initDic();
		indexer.addDocuments(dir);

		SpellCheckerSearch spellCheckerSearch = new SpellCheckerSearch();
		String[] suggest = spellCheckerSearch.search(keyword, 5);
		if (suggest != null && suggest.length >= 1) {
			for (String s : suggest) {
				System.out.println("您是不是要找：" + s);
			}
		} else {
			System.out.println("拼写正确");
		}

	}

}
