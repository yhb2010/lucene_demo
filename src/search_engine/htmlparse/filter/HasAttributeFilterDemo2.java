package search_engine.htmlparse.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * HasAttributeFilter有3个构造函数： public HasAttributeFilter (); public
 * HasAttributeFilter (String attribute); public HasAttributeFilter (String
 * attribute, String value); 这个Filter可以匹配出包含制定名字的属性，或者制定属性为指定值的节点。还是用例子说明比较容易。
 *
 * @author Administrator
 *
 */
public class HasAttributeFilterDemo2 {

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			NodeFilter filter = new HasAttributeFilter("id", "logoindex");
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
