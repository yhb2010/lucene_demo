package search_engine.htmlparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;

public class SimpleReadHtml2 {

	public static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(szFileName)), "UTF-8"));
			String szContent = "";
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp;
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {

		try {
			Lexer l = new Lexer(
					openFile("WebRoot/WEB-INF/classes/search_engine/data/test.html"));
			Node node;
			while (null != (node = l.nextNode())) {
				System.out.println(node.toHtml());
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

}
