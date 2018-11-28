package com.lucene.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.lucene.Contants;
import com.lucene.analysis.AnalyzerUtils;
import com.lucene.index.methd.Indexer;
import com.lucene.index.methd.Indexer4Stop;

public class StringIndexer4Key implements IndexerInterface {
	private String[] str = new String[] {
			"2015年4月8日，在边城瑞丽召开的云南开放工作会议上，时任昆明市委书记高劲松在媒体记者面前侃侃而谈。彼时，52岁的高劲松不会想到，第二天他去省委开会时仅用十几秒即被当场“控制”。4月10日下午，中纪委发布消息，称高劲松涉嫌严重违纪违法，目前正接受组织调查。这是继仇和、张田欣之后，连续第三任昆明市委书记被查。对此，年过九旬的云南省政协原副主席杨维骏向《法制晚报》记者表示，高劲松主政昆明时发生严重群体冲突，自己曾就此提一些建议，结果未被理睬。",
			"1996年，24岁的翔哥在广州一家小单位已经当了6年保安，省吃俭用积攒了5000元，想着回家或者在广州郊区买一套小房子，开始自己的家庭生活。股市那时候离翔哥还很远，直到他换了工作，在一家证券交易所当了保安。“1997年入市，第一只股票是宁天龙。”翔哥说，当时是交易所的经理喊他买的，他不懂股票，但很相信有知识的人，拿了3000元让别人帮忙买进。到现在他都不知道当时买入的价格是多少，结果他却记得很清楚，那只股票约赚了1000元。对于每个像翔哥一样的老股民来说，2007年有着无可替代的意义。那一年上证指数达到历史高峰6124点，成就了无数人的同时，也给每个股民建构了财富梦。“07的时候，你只要拿钱进去，就能挣钱，全部都涨，完全不需要什么技术。”" };
	private Double[] d2 = new Double[] { 52.2, 15.6, 20.5, 34.0, 65.3, 12.2 };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer4Stop indexer = new Indexer4Stop();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file：" + str[i]);
			Document doc = new Document();
			// StringField只能精确匹配
			// doc.add(new StringField("ID", "1_" + i, Field.Store.YES));
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new StringField("name", "文档", Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			int d = i + 3;
			doc.add(new StringField("time", "2009年01月0" + d + "日",
					Field.Store.YES));
			doc.add(new IntField("time2", 20090100 + d, Field.Store.YES));
			doc.add(new IntField("count", d, Field.Store.YES));
			doc.add(new DoubleField("price", d2[i], Field.Store.YES));
			AnalyzerUtils.displayTokens(indexer.getAnalyzer(), str[i]);
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {
		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file " + str[i]);
			Document doc = new Document();
			// StringField只能精确匹配
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			int d = i + 5;
			doc.add(new StringField("time", "2009年01月0" + d + "日",
					Field.Store.YES));
			doc.add(new IntField("count", d, Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		// writer.close();
	}

}
