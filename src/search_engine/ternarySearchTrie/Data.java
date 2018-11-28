package search_engine.ternarySearchTrie;

public class Data {

	private String s;// 词原文
	private int freq;// 词频

	// 同一个词可以有不同的词性，例如"朝阳"既可能是一个"区"，也可能是一个"市"。可以把这些和某个词的词性相关的信息放在同一个链表中。这个链表可以存储在TSTNode
	// 的Data属性中。

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public Data(String s, int freq) {
		super();
		this.s = s;
		this.freq = freq;
	}

	@Override
	public String toString() {
		return "Data [s=" + s + ", freq=" + freq + "]";
	}

}
