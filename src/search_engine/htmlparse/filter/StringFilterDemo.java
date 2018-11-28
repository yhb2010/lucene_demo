package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * 这个Filter用于过滤显示字符串中包含制定内容的Tag。注意是可显示的字符串，不可显示的字符串中的内容（例如注释，链接等等）不会被显示。
 *
 * @author Administrator
 *
 */
public class StringFilterDemo {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			NodeFilter filter = new StringFilter("www.baizeju.com");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);

			// 可以看到包含title，两个内容字符串和链接的文本字符串的Tag都被输出了，但是注释和链接Tag本身没有输出。
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node textnode = (Node) nodes.elementAt(i);

					SimpleReadHtml.message("getText:" + textnode.getText());
					SimpleReadHtml
							.message("=================================================");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
