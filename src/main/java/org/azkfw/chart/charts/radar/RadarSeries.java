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
package org.azkfw.chart.charts.radar;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.series.AbstractSeries;
import org.azkfw.chart.series.SeriesPoint;

/**
 * このクラスは、レーダーチャートのシリーズクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarSeries extends AbstractSeries {

	/**
	 * ポイント
	 */
	private List<RadarSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public RadarSeries() {
		super();
		points = new ArrayList<RadarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public RadarSeries(final String aTitle) {
		super(aTitle);
		points = new ArrayList<RadarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイント
	 */
	public RadarSeries(final String aTitle, final List<RadarSeriesPoint> aPoints) {
		super(aTitle);
		points = new ArrayList<>(aPoints);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aValues 値配列
	 */
	public RadarSeries(final String aTitle, final double... aValues) {
		super(aTitle);
		points = new ArrayList<RadarSeriesPoint>();
		for (double value : aValues) {
			points.add(new RadarSeriesPoint(value));
		}
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aPoint ポイント
	 */
	public void add(final RadarSeriesPoint aPoint) {
		points.add(aPoint);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aValue 値
	 */
	public void add(final double aValue) {
		RadarSeriesPoint point = new RadarSeriesPoint(aValue);
		points.add(point);
	}

	/**
	 * ポイント一覧を取得する。
	 * 
	 * @return ポイント一覧
	 */
	public List<RadarSeriesPoint> getPoints() {
		return points;
	}

	/**
	 * このクラスは、レーダーチャートのポイントクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/19
	 * @author Kawakicchi
	 */
	public static class RadarSeriesPoint implements SeriesPoint {

		/**
		 * 値
		 */
		private double value;

		/**
		 * コンストラクタ
		 */
		public RadarSeriesPoint() {
			value = 1.0;
		}

		/**
		 * コンストラクタ
		 * 
		 * @param aValue 値
		 */
		public RadarSeriesPoint(final double aValue) {
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
