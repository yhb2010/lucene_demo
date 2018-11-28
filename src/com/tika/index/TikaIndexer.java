package com.tika.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class TikaIndexer implements IndexerInterface {

	static Set<Property> textualMetadataFields = new HashSet<Property>();
	static {
		textualMetadataFields.add(TikaCoreProperties.TITLE);
		textualMetadataFields.add(TikaCoreProperties.COMMENTS);
		textualMetadataFields.add(TikaCoreProperties.DESCRIPTION);
	}

	@Override
	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	@Override
	public Directory initDic2() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		/* 指明要索引的文件所在文件夹的位置 */
		File dataDir = new File(Contants.sourcePath2);
		File[] dataFiles = dataDir.listFiles();
		for (int i = 0; i < dataFiles.length; i++) {
			File f = dataFiles[i];
			System.out.println("Indexing file：" + f.getCanonicalPath());
			Metadata metadata = new Metadata();
			// 文件名
			metadata.add(Metadata.RESOURCE_NAME_KEY, f.getName());
			InputStream iStream = new FileInputStream(f);
			Parser parser = new AutoDetectParser();
			ContentHandler iHandler = new BodyContentHandler();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);
			try {
				parser.parse(iStream, iHandler, metadata, context);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				iStream.close();
			}

			Document doc = new Document();
			// StringField等只能精确匹配
			doc.add(new TextField("text", iHandler.toString(), Store.YES));
			System.out.println("all text:" + iHandler.toString());

			for (String name : metadata.names()) {
				String value = metadata.get(name);
				doc.add(new StringField(name, value, Store.YES));
			}

			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub

	}

}
