package com.lucene.index.selfsort;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;
import org.apache.lucene.util.BytesRef;

public class DistanceComparatorSource extends FieldComparatorSource {
	private int x;
	private int y;

	public DistanceComparatorSource(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public FieldComparator<Float> newComparator(java.lang.String fieldName,
			int numHits, int sortPos, boolean reversed) throws IOException {
		return new DistanceScoreDocLookupComparator(fieldName, numHits);
	}

	private class DistanceScoreDocLookupComparator extends
			FieldComparator<Float> {
		private int[] xDoc, yDoc;
		private float[] values;
		private float bottom;
		private String fieldName;

		// setNextReader，当搜索的时候，首先会调用此方法，进行数据的初始化。
		// copy第一次调用的时候，将初始化两个值，后面再一个一个值初始化。
		// compare，实现对存储的值得对比，以便进行排序。
		// compareBottom，当元素超出初始化值的时候就会调用这个方法，最后一个与队列中末尾值进行比较，这样返回的元素始终是初始化的时候的元素。

		// 第一步调用此方法，进行数据的初始化
		public DistanceScoreDocLookupComparator(String fieldName, int numHits)
				throws IOException {
			this.values = new float[numHits];
			this.fieldName = fieldName;
		}

		@Override
		public FieldComparator<Float> setNextReader(AtomicReaderContext context)
				throws IOException {
			// 主要是此处做了变动，之前我们可以通过FieldCache.DEFAULT.getString()
			// 获得cache里的document value
			BinaryDocValues bdv = FieldCache.DEFAULT.getTerms(context.reader(),
					this.fieldName, false);

			int length = context.reader().maxDoc();
			xDoc = new int[length];
			yDoc = new int[length];
			for (int i = 0; i < length; i++) {
				BytesRef br = new BytesRef();
				bdv.get(i, br);
				String result = br.utf8ToString();
				String[] s = result.split(",");
				xDoc[i] = Integer.parseInt(s[0]);
				yDoc[i] = Integer.parseInt(s[1]);
			}

			return this;
		}

		@Override
		public void copy(int slot, int doc) throws IOException {
			values[slot] = getDistance(doc);
		}

		// 对values中计算好的值进行比较，以便进行排序
		@Override
		public int compare(int slot1, int slot2) {
			if (values[slot1] < values[slot2])
				return -1;
			if (values[slot1] > values[slot2])
				return 1;
			return 0;
		}

		// 当值超出 3时通过compareBottom对比后，将值设置到队列尾部
		@Override
		public void setBottom(int slot) {
			bottom = values[slot];
		}

		@Override
		public Float value(int slot) {
			return new Float(values[slot]);
		}

		// 当元素超出3时，对比队列中的最尾部的元素与获取的元素，当距离小于队列尾部的值时，将把这个值赋给队列尾部。
		@Override
		public int compareBottom(int doc) throws IOException {
			float docDistance = getDistance(doc);
			if (bottom < docDistance)
				return -1;
			if (bottom > docDistance)
				return 1;
			return 0;
		}

		@Override
		public int compareDocToValue(int doc, Float value) throws IOException {
			return 0;
		}

		private float getDistance(int doc) {
			int deltax = xDoc[doc] - x;
			int deltay = yDoc[doc] - y;
			return (float) Math.sqrt(deltax * deltax + deltay * deltay);
		}

	}

	public String toString() {
		return "Distance from (" + x + "," + y + ")";
	}

}
