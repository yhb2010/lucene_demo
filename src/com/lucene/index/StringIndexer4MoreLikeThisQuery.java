package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.methd.Indexer;

public class StringIndexer4MoreLikeThisQuery implements IndexerInterface {
	private String[] bookNumbers = new String[] { "0000001", "0000002",
			"0000003", "0000004", "0000005", "0000006" };
	private String[] bookNames = new String[] { "java开发", "高级开发人员(java方向)",
			"php开发工程师", "linux管理员", "lucene开发工程师", "php软件工程师" };
	private String[] infos = new String[] { "招聘网站开发工程师,要求一年或一年以上开发工作经验",
			"需要有四年或者以上的工作经验,有大型项目实践,java基本扎实", "主要是维护公司的网站,能独立完成网站的功能",
			"管理及维护公司的linux服务器,职责包括完成mysql数据备份及日常管理,apache的性能调优等",
			"需要两年或者以上的从事lucene java 开发工作的经验,需要对算法,排序规则等有相关经验,java水平及基础要扎实",
			"具有大量的php开发工程师经验,如熟悉 java 开发,数据库管理则更佳" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// set 是否索引
		fieldType
				.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		fieldType.setStored(true);// set 是否存储
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);// set 是否分类

		for (int i = 0; i < bookNumbers.length; i++) {
			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new StringField("bookNumber", bookNumbers[i],
					Field.Store.YES));
			Field f = new Field("name", bookNames[i], fieldType);
			doc.add(f);
			f = new Field("info", infos[i], fieldType);
			doc.add(f);
			writer.addDocument(doc);
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), bookNames[i]);
			System.out.println("----------------------------------------");
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), infos[i]);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
