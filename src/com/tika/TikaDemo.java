package com.tika;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaDemo {

	public static String PATH = "f:\\zsl\\aa.htm";

	public static String OUTPATH = "f:\\zsl\\aa.out";

	public static void main(String[] args) throws IOException, SAXException,
			TikaException {
		Parser parser = new HtmlParser();
		InputStream iStream = new BufferedInputStream(new FileInputStream(
				new File(PATH)));
		OutputStream oStream = new BufferedOutputStream(new FileOutputStream(
				new File(OUTPATH)));
		ContentHandler iHandler = new BodyContentHandler(oStream);
		Metadata meta = new Metadata();
		meta.add(Metadata.CONTENT_ENCODING, "UTF-8");
		parser.parse(iStream, iHandler, meta, new ParseContext());
	}

}
