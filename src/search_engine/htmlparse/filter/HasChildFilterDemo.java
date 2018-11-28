package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * 返回有符合条件的子节点的节点，需要另外一个Filter作为过滤子节点的参数。缺省的构造函数虽然可以初始化，但是由于子节点的Filter是null，
 * 所以使用的时候发生了Exception。从这点来看，HTMLParser的代码还有很多可以优化的的地方。呵呵。
 * HasParentFilter和HasSiblingFilter的功能与HasChildFilter类似，大家自己试一下就应该了解了。
 *
 * @author Administrator
 *
 */
public class HasChildFilterDemo {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			NodeFilter innerFilter = new TagNameFilter("DIV");
			NodeFilter filter = new HasChildFilter(innerFilter);
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
