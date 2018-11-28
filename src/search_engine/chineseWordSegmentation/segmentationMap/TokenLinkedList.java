package search_engine.chineseWordSegmentation.segmentationMap;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 邻接表表示的切分词图由一个链表表示的数组组成。首先实现一个单向链表：
 *
 * @author Administrator
 *
 */
public class TokenLinkedList implements Iterable<CnToken> {

	private Node head;

	public TokenLinkedList() {
		head = null;
	}

	public void put(CnToken item) {
		Node n = new Node(item);
		n.next = head;
		head = n;
	}

	public Node getHead() {
		return head;
	}

	public Iterator<CnToken> iterator() {// 迭代器
		return new LinkIterator(head);
	}

	private class LinkIterator implements Iterator<CnToken> {
		Node itr;

		public LinkIterator(Node begin) {
			itr = begin;
		}

		public boolean hasNext() {
			return itr != null;
		}

		public CnToken next() {
			if (itr == null) {
				throw new NoSuchElementException();
			}
			Node cur = itr;
			itr = itr.next;
			return cur.item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static class Node {
		public CnToken item;
		public Node next;

		Node(CnToken item) {
			this.item = item;
			next = null;
		}
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		Node cur = head;

		while (cur != null) {
			buf.append(cur.item.toString());
			buf.append('\t');
			cur = cur.next;
		}

		return buf.toString();
	}

}
