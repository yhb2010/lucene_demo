package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * 这个Filter用于判断链接中是否包含某个特定的字符串，可以用来过滤出指向某个特定网站的链接。
 *
 * @author Administrator
 *
 */
public class LinkStringFilterDemo {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			NodeFilter filter = new LinkStringFilter("www.baizeju.com");
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
