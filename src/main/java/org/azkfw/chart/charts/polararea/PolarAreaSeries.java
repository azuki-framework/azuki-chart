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
package org.azkfw.chart.charts.polararea;

import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.series.AbstractSeries;

/**
 * このクラスは、鶏頭図のシリーズクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarAreaSeries extends AbstractSeries {

	/**
	 * ポイント
	 */
	private List<PolarAreaSeriesPoint> points;

	/**
	 * コンストラクタ
	 */
	public PolarAreaSeries() {
		super();
		points = new ArrayList<PolarAreaSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 */
	public PolarAreaSeries(final String aTitle) {
		super(aTitle);
		points = new ArrayList<PolarAreaSeriesPoint>();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aPoints ポイント
	 */
	public PolarAreaSeries(final String aTitle, final List<PolarAreaSeriesPoint> aPoints) {
		super(aTitle);
		points = new ArrayList<>(aPoints);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aPoint ポイント
	 */
	public void add(final PolarAreaSeriesPoint aPoint) {
		points.add(aPoint);
	}

	/**
	 * ポイントを追加する。
	 * 
	 * @param aRange 値
	 */
	public void add(final double aRange) {
		PolarAreaSeriesPoint point = new PolarAreaSeriesPoint(aRange);
		points.add(point);
	}

	/**
	 * ポイント一覧を取得する。
	 * 
	 * @return ポイント一覧
	 */
	public List<PolarAreaSeriesPoint> getPoints() {
		return points;
	}
}
