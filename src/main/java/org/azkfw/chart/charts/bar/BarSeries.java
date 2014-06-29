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
package org.azkfw.chart.charts.bar;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.series.AbstractSeries;

/**
 * このクラスは、棒グラフのシリーズ情報を保持したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class BarSeries extends AbstractSeries {

	/**
	 * ポイント
	 */
	private List<BarSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public BarSeries() {
		super();
		points = new ArrayList<BarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public BarSeries(final String aTitle) {
		super(aTitle);
		points = new ArrayList<BarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイント
	 */
	public BarSeries(final String aTitle, final List<BarSeriesPoint> aPoints) {
		super(aTitle);
		points = new ArrayList<>(aPoints);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aPoint ポイント
	 */
	public void add(final BarSeriesPoint aPoint) {
		points.add(aPoint);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aValue 値
	 */
	public void add(final double aValue) {
		BarSeriesPoint point = new BarSeriesPoint(aValue);
		points.add(point);
	}

	/**
	 * ポイント一覧を取得する。
	 * 
	 * @return ポイント一覧
	 */
	public List<BarSeriesPoint> getPoints() {
		return points;
	}
}
