package com.lucene.index.methd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileReader2 extends InputStreamReader {

	public FileReader2(String fileName, String charSetName)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(new FileInputStream(fileName), charSetName);
	}

}
