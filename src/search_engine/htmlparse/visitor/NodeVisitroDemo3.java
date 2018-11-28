package search_engine.htmlparse.visitor;

import org.htmlparser.Parser;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;

import search_engine.htmlparse.SimpleReadHtml;

/**
 * 从简单方面的理解，Filter是根据某种条件过滤取出需要的Node再进行处理。Visitor则是遍历内容树的每一个节点，对于符合条件的节点进行处理。
 * 实际的结果异曲同工，两种不同的方法可以达到相同的结果。
 *
 * @author Administrator
 *
 */
public class NodeVisitroDemo3 {

	public static void main(String[] args) {
		try {
			Parser parser = new Parser(
					"WebRoot/WEB-INF/classes/search_engine/data/test.html");

			// 因为我设置的
			// 可以看到，所有的子节点都出现了
			NodeVisitor visitor = new NodeVisitor(true, false) {
				@Override
				public void visitTag(Tag tag) {
					SimpleReadHtml.message("This is Tag:" + tag.getText());
				}

				@Override
				public void visitStringNode(Text string) {
					SimpleReadHtml.message("This is Text:" + string);
				}

				@Override
				public void visitRemarkNode(Remark remark) {
					SimpleReadHtml
							.message("This is Remark:" + remark.getText());
				}

				// 开始遍历所以的节点以前，beginParsing先被调用
				@Override
				public void beginParsing() {
					SimpleReadHtml.message("beginParsing");
				}

				@Override
				public void visitEndTag(Tag tag) {
					SimpleReadHtml.message("visitEndTag:" + tag.getText());
				}

				// 最后在结束遍历以前，finishParsing被调用
				@Override
				public void finishedParsing() {
					SimpleReadHtml.message("finishedParsing");
				}
			};

			parser.visitAllNodesWith(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
