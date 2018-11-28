package search_engine.chineseWordSegmentation.segmentationMap;

/**
 * 切分词图中的边都是词典中的词，边的起点和终点分别是词的开始和结束位置。
 *
 * 为了消除分词中的歧异，提高切分准确度，需要找出一句话所有可能的词，生成全切分词图。
 *
 * 如果待切分的字符串有m个字符，考虑每个字符左边和右边的位置，则有m+1个点对应，点的编号从0到m。把候选词看成边，可以根据词典生成一个切分词图。
 * 切分词图是一个有向正权重的图。
 *
 * 在"有意见分歧"的切分词图中："有"这条边的起点是0，终点是1；"有意"这条边的起点是0，终点是2，以此类推。切分方案就是从源点0到终点5之间的路径，
 * 共存在两条切分路径。
 *
 * 路径1：0－1－3－5 对应切分方案S1：有/ 意见/ 分歧/ 路径2：0－2－3－5 对应切分方案S2：有意/ 见/ 分歧/
 *
 * @author Administrator
 *
 */
public class CnToken {

	public String termText;// 词
	public int start;// 词的开始位置
	public int end;// 词的结束位置
	public int freq;// 词在语料库中出现的频率

	public CnToken(int vertexFrom, int vertexTo, String word) {
		start = vertexFrom;
		end = vertexTo;
		termText = word;
	}

	@Override
	public String toString() {
		return "CnToken [termText=" + termText + ", start=" + start + ", end="
				+ end + ", freq=" + freq + "]";
	}

}
