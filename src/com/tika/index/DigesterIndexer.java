package com.tika.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;
import com.tika.domain.Contact;

public class DigesterIndexer implements IndexerInterface {

	private Digester dig;
	private Document doc;
	private static IndexWriter writer;
	private List<Document> l=new ArrayList<Document>();

	public DigesterIndexer() {
		dig = new Digester();
		dig.setValidating(false);
		// 当找到“address-book”时创建DigesterIndexer实例
		dig.addObjectCreate("address-book", DigesterIndexer.class);
		// 当找到“contact”时创建Contact实例
		dig.addObjectCreate("address-book/contact", Contact.class);

		dig.addSetProperties("address-book/contact", "type", "type");

		dig.addCallMethod("address-book/contact/name", "setName", 0);
		dig.addCallMethod("address-book/contact/address", "setAddress", 0);
		dig.addCallMethod("address-book/contact/city", "setCity", 0);
		dig.addCallMethod("address-book/contact/province", "setProvince", 0);
		dig.addCallMethod("address-book/contact/postalcode", "setPostalcode", 0);
		dig.addCallMethod("address-book/contact/country", "setCountry", 0);

		dig.addSetNext("address-book/contact", "populateDocument");
	}

	public synchronized void getDocument(InputStream is) {
		try {
			dig.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void populateDocument(Contact contact) {
		try {
			doc = new Document();
			doc.add(new StringField("type", contact.getType(), Store.YES));
			doc.add(new StringField("name", contact.getName(), Store.YES));
			doc.add(new StringField("address", contact.getAddress(), Store.YES));
			l.add(doc);
			System.out.println(doc.toString());
			writer.addDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		// TODO Auto-generated method stub
		Indexer indexer = new Indexer();
		writer = indexer.getIndexer(dir);

		/* 指明要索引的文件所在文件夹的位置 */
		File dataDir = new File(Contants.sourcePath2 + File.separator
				+ "bb.xml");
		DigesterIndexer a = new DigesterIndexer();
		a.getDocument(new FileInputStream(dataDir));
		writer.commit();
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		DigesterIndexer a=new DigesterIndexer();
		a.addDocuments(a.initDic());
	}

}
