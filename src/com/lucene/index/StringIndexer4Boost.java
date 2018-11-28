package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.lucene.Contants;
import com.lucene.index.methd.Indexer;

/**
 * 测试加权
 * 
 * @author dell
 * 
 */
public class StringIndexer4Boost implements IndexerInterface {

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		String[] names = new String[] { "张苏磊", "李明", "刘东", "陈黎", "赵雪" };
		String[] emails = new String[] { "youtong82@163.com", "lm@163.com",
				"ld@163.com", "waterchenli@126.com", "zhaoxue@yahoo.com" };
		Integer[] isManagers = new Integer[] { 1, 0, 0, 1, 0 };
		for (int i = 0; i < names.length; i++) {
			Document doc = new Document();
			// StringField只能精确匹配
			// doc.add(new StringField("ID", "1_" + i, Field.Store.YES));
			doc.add(new StringField("ID", "序号：" + (i + 1), Field.Store.YES));
			FieldType fieldType = new FieldType();
			fieldType.setIndexed(true);// set 是否索引
			fieldType.setStored(true);// set 是否存储
			fieldType.setTokenized(false);// set 是否分类
			Field f3 = new Field("name", names[i], fieldType);
			if (isManagers[i] == 1) {
				f3.setBoost(8f);
			} else {
				f3.setBoost(0f);
			}
			doc.add(f3);
			doc.add(new StringField("email", emails[i], Field.Store.YES));
			doc.add(new IntField("isManager", isManagers[i], Field.Store.YES));
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
