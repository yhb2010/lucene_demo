package search_engine.chineseWordSegmentation.segmentationMap;

import java.util.ArrayList;

public class PrefixRet {

	public ArrayList<String> values;// 以某个字符串的前缀开始的词集合
	public int end;// 记录下次匹配的开始位置

}
