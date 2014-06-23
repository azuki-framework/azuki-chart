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

/**
 * このクラスは、レーダーチャートのシリーズクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarSeries {

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * ポイント
	 */
	private List<RadarSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public RadarSeries() {
		title = null;
		points = new ArrayList<RadarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public RadarSeries(final String aTitle) {
		title = aTitle;
		points = new ArrayList<RadarSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイント
	 */
	public RadarSeries(final String aTitle, final List<RadarSeriesPoint> aPoints) {
		title = aTitle;
		points = new ArrayList<>(aPoints);
	}

	/**
	 * タイトルを設定する。
	 * 
	 * @param aTitle タイトル
	 */
	public void setTitle(final String aTitle) {
		title = aTitle;
	}

	/**
	 * タイトルを取得する。
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
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
}
