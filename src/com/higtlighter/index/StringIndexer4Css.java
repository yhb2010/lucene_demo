package com.higtlighter.index;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import com.lucene.Contants;
import com.lucene.index.IndexerInterface;
import com.lucene.index.methd.Indexer;

public class StringIndexer4Css implements IndexerInterface {
	private String[] str = new String[] { "记者盘点31省份今年高考加分照顾政策发现，与往年相比，此次调整涉及奥数、科技类、体育项目、"
			+ "少数民族等传统加分领域，不少省份既减项又缩水分值。例如今年四川高考加分项目大瘦身，和去年相比共删除29个加分项目，另外还有17个"
			+ "项目缩减了加分分值。而北京市此前公布的高考加分调整方案也提出，在2014年高考中，体育特长生加分项目有所减少，由原本的15项缩减到10项。"
			+ "而少数民族考生由增加10分投档调整为增加5分投档。市优秀学生干部由增加20分投档调整为增加10分投档。"
			+ "按照规定，今年体育特长生加分项目限定为田径、篮球、足球、排球、乒乓球、武术、游泳、羽毛球等8项，各省还可根据本地情况，"
			+ "增加一般不超过2个强身健体项目。内蒙古甚至提出更加严苛的方案，即今年开始，对高水平运动员、高考体育加分者需要统一实施全区体育测试，"
			+ "不合格则不予加分。然而，记者统计发现，虽然竞赛、体育接连缩水，但有13个省份的高考加分项目中，提到了思想品德及见义勇为。"
			+ "其中，10地提出见义勇为者加10分奖励，而北京、浙江、四川三省份奖励加20分,山东省甚至在自选项目里仅保留了“见义勇为”这一项加分。" };

	public Directory initDic() throws Exception {
		return new RAMDirectory();
	}

	public Directory initDic2() throws Exception {
		return FSDirectory.open(new File(Contants.indexPath));
	}

	public void addDocuments(Directory dir) throws IOException {
		Indexer indexer = new Indexer();
		IndexWriter writer = indexer.getIndexer(dir);

		for (int i = 0; i < str.length; i++) {
			System.out.println("Indexing file：" + str[i]);
			Document doc = new Document();
			doc.add(new StringField("ID", "序号：" + i, Field.Store.YES));
			doc.add(new TextField("text", str[i], Field.Store.YES));
			writer.addDocument(doc);
			writer.commit();
		}
		System.out.println("共" + writer.numDocs() + "个文档！");
		writer.close();
	}

	@Override
	public void addDocuments(IndexWriter writer) throws IOException {

	}

}
