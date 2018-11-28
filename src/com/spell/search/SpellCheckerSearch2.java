package com.spell.search;

import java.io.IOException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;
import com.spell.index.SpellCheckerIndexer;

/**
 * 拼写检查（LuceneDictionary）
 * 
 * @author Administrator
 * 
 */
public class SpellCheckerSearch2 {

	private SpellChecker spellchecker;

	private static String keyword = "麻辣将";

	public String[] search(String word, int numSug, IndexReader ireader) {
		Directory directory = new RAMDirectory();
		try {
			spellchecker = new SpellChecker(directory);
			Indexer indexer = new Indexer();
			indexer.createAnalyzer();
			// 指定原始索引，索引字段（某个字段的字典）
			spellchecker.indexDictionary(new LuceneDictionary(ireader,
					"bookName"), indexer.getConfig(), true);
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

		IndexReader ireader = DirectoryReader.open(dir);

		SpellCheckerSearch2 spellCheckerSearch = new SpellCheckerSearch2();
		String[] suggest = spellCheckerSearch.search(keyword, 5, ireader);
		if (suggest != null && suggest.length >= 1) {
			for (String s : suggest) {
				System.out.println("您是不是要找：" + s);
			}
		} else {
			System.out.println("拼写正确");
		}

	}

}
