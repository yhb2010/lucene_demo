package search_engine.htmlparse;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

public class SimpleReadHtml {

	private static String ENCODE = "UTF-8";

	public static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System
					.getProperty("file.encoding")));
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {

		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");
			// Parser parser = new Parser( (HttpURLConnection) (new
			// URL("http://127.0.0.1:8080/HTMLParserTester.html")).openConnection()
			// );

			for (NodeIterator i = parser.elements(); i.hasMoreNodes();) {
				Node node = i.nextNode();
				message("getText:" + node.getText());
				message("getPlainText:" + node.toPlainTextString());
				message("toHtml:" + node.toHtml());
				message("toHtml(true):" + node.toHtml(true));
				message("toHtml(false):" + node.toHtml(false));
				message("toString:" + node.toString());
				message("=================================================");
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

}
