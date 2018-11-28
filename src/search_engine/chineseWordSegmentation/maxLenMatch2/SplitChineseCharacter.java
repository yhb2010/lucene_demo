package search_engine.chineseWordSegmentation.maxLenMatch2;

//逆向最大长度匹配
public class SplitChineseCharacter {

	public static void main(String[] args) {
		String input = "太好了，今天是星期六啊"; // 要匹配的字符串
		new Split(input).start();
	}
}

class Split {
	private String[] dictionary = { "今天", "是", "星期", "星期六" }; // 词典
	private String input = null;

	public Split(String input) {
		this.input = input;
	}

	public void start() {
		String temp = null;
		for (int i = 0; i < this.input.length(); i++) {
			temp = this.input.substring(i); // 每次从字符串的首部截取一个字，并存到temp中
			// System.out.println("*****" + temp + "*********" +
			// this.input);
			// 如果该词在字典中， 则删除该词并在原始字符串中截取该词
			if (this.isInDictionary(temp)) {
				System.out.println(temp);
				this.input = this.input.replace(temp, "");
				i = -1; // i=-1是因为要重新查找， 而要先执行循环中的i++
			}
		}

		// 当前循环完毕，词的末尾截去一个字，继续循环， 直到词变为空
		if (null != this.input && !"".equals(this.input)) {
			this.input = this.input.substring(0, this.input.length() - 1);
			this.start();
		}
	}

	// 判断当前词是否在字典中
	public boolean isInDictionary(String temp) {
		for (int i = 0; i < this.dictionary.length; i++) {
			if (temp.equals(this.dictionary[i])) {
				return true;
			}
		}
		return false;
	}
}
