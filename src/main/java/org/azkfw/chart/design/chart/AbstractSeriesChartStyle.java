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
package org.azkfw.chart.design.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.azkfw.chart.core.dataset.series.Series;
import org.azkfw.chart.core.dataset.series.SeriesPoint;
import org.azkfw.chart.design.marker.CircleMarker;
import org.azkfw.chart.design.marker.DiaMarker;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.design.marker.SquareMarker;
import org.azkfw.chart.design.marker.TriangleMarker;

/**
 * このクラスは、シリーズチャートスタイルを定義するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public abstract class AbstractSeriesChartStyle<SERIES extends Series, POINT extends SeriesPoint> extends AbstractChartStyle implements
		SeriesChartStyle<SERIES, POINT> {

	/**
	 * コンストラクタ
	 */
	public AbstractSeriesChartStyle() {
	}

	/**
	 * データのストロークを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return ストローク
	 */
	public Stroke getSeriesStroke(final int aIndex, final SERIES aSeries) {
		return new BasicStroke(2.f);
	}

	/**
	 * データのストロークカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesStrokeColor(final int aIndex, final SERIES aSeries) {
		Color color = getColorIndex(aIndex);
		if (null == color) {
			color = new Color(0, 0, 0, 255);
		}
		return color;
	}

	/**
	 * データの塗りつぶしカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesFillColor(final int aIndex, final SERIES aSeries) {
		Color color = getSeriesStrokeColor(aIndex, aSeries);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
		return color;
	}

	public Marker getSeriesMarker(final int aIndex, final SERIES aSeries) {
		Marker marker = null;
		switch (aIndex % 4) {
		case 0:
			marker = new CircleMarker(10, getSeriesStrokeColor(aIndex, aSeries));
			break;
		case 1:
			marker = new SquareMarker(10, getSeriesStrokeColor(aIndex, aSeries));
			break;
		case 2:
			marker = new TriangleMarker(10, getSeriesStrokeColor(aIndex, aSeries));
			break;
		case 3:
			marker = new DiaMarker(10, getSeriesStrokeColor(aIndex, aSeries));
			break;
		default:
			break;
		}

		return marker;
	}

	public Marker getSeriesPointMarker(final int aIndex, final SERIES aSeries, final int aNo, final POINT aPoint) {
		return null;
	}
}
