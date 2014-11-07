/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.azkfw.chart.charts.line;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.core.dataset.series.AbstractSeries;
import org.azkfw.chart.core.dataset.series.SeriesPoint;

/**
 * このクラスは、折れ線グラフのシリーズ情報を保持したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/02
 * @author Kawakicchi
 */
public class LineSeries extends AbstractSeries {

	/**
	 * ポイント
	 */
	private List<LineSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public LineSeries() {
		super();
		points = new ArrayList<LineSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public LineSeries(final String aTitle) {
		super(aTitle);
		points = new ArrayList<LineSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイントリスト
	 */
	public LineSeries(final String aTitle, final List<LineSeriesPoint> aPoints) {
		super(aTitle);
		points = new ArrayList<LineSeriesPoint>(aPoints);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aValues 値配列
	 */
	public LineSeries(final String aTitle, final double... aValues) {
		super(aTitle);
		points = new ArrayList<LineSeriesPoint>();
		for (double value : aValues) {
			points.add(new LineSeriesPoint(value));
		}
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aPoint ポイント
	 */
	public void add(final LineSeriesPoint aPoint) {
		points.add(aPoint);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aValue 値
	 */
	public void add(final double aValue) {
		LineSeriesPoint point = new LineSeriesPoint(aValue);
		points.add(point);
	}

	/**
	 * ポイント一覧を取得する。
	 * 
	 * @return ポイント一覧
	 */
	public List<LineSeriesPoint> getPoints() {
		return points;
	}

	/**
	 * このクラスは、折れ線グラフのポイント情報を保持したクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/02
	 * @author Kawakicchi
	 */
	public static class LineSeriesPoint implements SeriesPoint {

		/**
		 * 値
		 */
		private double value;

		/**
		 * コンストラクタ
		 */
		public LineSeriesPoint() {
			value = 0.0;
		}

		/**
		 * コンストラクタ
		 * 
		 * @param aValue 値
		 */
		public LineSeriesPoint(final double aValue) {
			value = aValue;
		}

		/**
		 * 値を取得する。
		 * 
		 * @return 値
		 */
		public double getValue() {
			return value;
		}

	}
}
