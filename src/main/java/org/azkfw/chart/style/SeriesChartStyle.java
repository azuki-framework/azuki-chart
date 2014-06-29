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
package org.azkfw.chart.style;

import java.awt.Color;
import java.awt.Stroke;

import org.azkfw.chart.looks.marker.Marker;
import org.azkfw.chart.series.Series;
import org.azkfw.chart.series.SeriesPoint;

/**
 * このインターフェースは、シリーズグラフスタイルを定義する為のインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/28
 * @author Kawakicchi
 */
public interface SeriesChartStyle<S extends Series, P extends SeriesPoint> extends ChartStyle {

	/**
	 * データのストロークを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return ストローク
	 */
	public Stroke getSeriesStroke(final int aIndex, final S aSeries);

	/**
	 * データのストロークカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesStrokeColor(final int aIndex, final S aSeries);

	/**
	 * データの塗りつぶしカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesFillColor(final int aIndex, final S aSeries);

	/**
	 * シリーズマーカー情報を取得する。
	 * 
	 * @param aIndex シリーズインデックス
	 * @param aSeries シリーズ情報
	 * @return シリーズマーカー情報
	 */
	public Marker getSeriesMarker(final int aIndex, final S aSeries);

	/**
	 * シリーズポイントマーカー情報を取得する。
	 * 
	 * @param aIndex シリーズインデックス
	 * @param aSeries シリーズ情報
	 * @param aNo シリーズポイント番号
	 * @param aPoint シリーズポイント情報
	 * @return シリーズマーカー情報
	 */
	public Marker getSeriesPointMarker(final int aIndex, final S aSeries, final int aNo, final P aPoint);
}
