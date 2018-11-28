package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * （二）判断类Filter 2.1 TagNameFilter TabNameFilter是最容易理解的一个Filter，根据Tag的名字进行过滤。
 *
 * @author Administrator
 *
 */
public class TagNameFilterDemo {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			// 这里是控制测试的部分，后面的例子修改的就是这个地方。
			NodeFilter filter = new TagNameFilter("DIV");
			// 将html中所有满足条件的标签解析出来，放到NodeList里
			NodeList nodes = parser.extractAllNodesThatMatch(filter);

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
