package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * 这个Filter用于判断节点类型是否是某个特定的Node类型
 *
 * @author Administrator
 *
 */
public class NodeClassFilterDemo {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			NodeFilter filter = new NodeClassFilter(RemarkNode.class);
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
