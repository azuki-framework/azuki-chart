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
package org.azkfw.chart.charts.scatter;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.core.dataset.series.AbstractSeries;
import org.azkfw.chart.core.dataset.series.SeriesPoint;

/**
 * このクラスは、散布図のシリーズ情報を保持したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class ScatterSeries extends AbstractSeries {

	/**
	 * ポイント
	 */
	private List<ScatterSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public ScatterSeries() {
		super();
		points = new ArrayList<ScatterSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public ScatterSeries(final String aTitle) {
		super(aTitle);
		points = new ArrayList<ScatterSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイント
	 */
	public ScatterSeries(final String aTitle, final List<ScatterSeriesPoint> aPoints) {
		super(aTitle);
		points = new ArrayList<>(aPoints);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aPoint ポイント
	 */
	public void add(final ScatterSeriesPoint aPoint) {
		points.add(aPoint);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aX x値
	 * @param aY y値
	 */
	public void add(final double aX, final double aY) {
		ScatterSeriesPoint point = new ScatterSeriesPoint(aX, aY);
		points.add(point);
	}

	/**
	 * ポイント一覧を取得する。
	 * 
	 * @return ポイント一覧
	 */
	public List<ScatterSeriesPoint> getPoints() {
		return points;
	}

	/**
	 * このクラスは、散布図のポイント情報を保持したクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/25
	 * @author Kawakicchi
	 */
	public static class ScatterSeriesPoint implements SeriesPoint {

		/**
		 * x値
		 */
		private double x;
		/**
		 * y値
		 */
		private double y;

		/**
		 * コンストラクタ
		 */
		public ScatterSeriesPoint() {
			x = 1.0;
			y = 1.0;
		}

		/**
		 * コンストラクタ
		 * 
		 * @param aX x値
		 * @param aY y値
		 */
		public ScatterSeriesPoint(final double aX, final double aY) {
			x = aX;
			y = aY;
		}

		/**
		 * x値を取得する。
		 * 
		 * @return 値
		 */
		public double getX() {
			return x;
		}

		/**
		 * y値を取得する。
		 * 
		 * @return 値
		 */
		public double getY() {
			return y;
		}

	}
}
