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

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.polar.PolarSeries.PolarSeriesPoint;
import org.azkfw.chart.charts.polararea.PolarAreaChartDesign.PolarAreaChartStyle;
import org.azkfw.chart.charts.polararea.PolarAreaSeries.PolarAreaSeriesPoint;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、鶏頭図のデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author Kawakicchi
 */
public class PolarAreaChartDesign extends AbstractSeriesChartDesign<PolarAreaChartStyle, PolarAreaSeries, PolarSeriesPoint> {

	/** デフォルトデザイン */
	public static PolarAreaChartDesign DefalutDesign = new PolarAreaChartDesign();

	/**
	 * コンストラクタ
	 */
	protected PolarAreaChartDesign() {
		setChartStyle(new PolarAreaChartStyle());
		setTitleStyle(new PolarAreaTitleStyle());
		setLegendStyle(new PolarAreaLegendStyle());
	}

	/**
	 * このクラスは、鶏頭図グラフのタイトルスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PolarAreaTitleStyle extends CustomTitleStyle {

		/**
		 * コンストラクタ
		 */
		public PolarAreaTitleStyle() {

		}
	}

	/**
	 * このクラスは、鶏頭図グラフの凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PolarAreaLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public PolarAreaLegendStyle() {
			setPosition(LegendDisplayPosition.InnerTopLeft);
		}
	}

	/**
	 * このクラスは、鶏頭図グラフのグラフスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PolarAreaChartStyle extends AbstractSeriesChartStyle<PolarAreaSeries, PolarAreaSeriesPoint> {

		/**
		 * コンストラクタ
		 */
		public PolarAreaChartStyle() {

		}

		/**
		 * 軸線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisLineColor() {
			return null;
		}

		/**
		 * 軸線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getAxisLineStroke() {
			return null;
		}

		/**
		 * 軸目盛ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getAxisScaleLabelFont() {
			return getDefaultAxisScaleLabelFont();
		}

		/**
		 * 軸目盛ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisScaleLabelColor() {
			return getDefaultAxisScaleLabelColor();
		}

		/**
		 * 軸目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisScaleLineColor() {
			return getDefaultAxisScaleLineColor();
		}

		/**
		 * 軸目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getAxisScaleLineStroke() {
			return getDefaultAxisScaleLineStroke();
		}

		/**
		 * 円線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getCircleLineColor() {
			return null;
		}

		/**
		 * 円線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getCircleLineStroke() {
			return null;
		}

		/**
		 * 円目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getCircleScaleLineColor() {
			return getDefaultAxisScaleLineColor();
		}

		/**
		 * 円目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getCircleScaleLineStroke() {
			return getDefaultAxisScaleLineStroke();
		}

		/**
		 * 円目盛の角度を取得する。
		 * 
		 * @return 角度
		 */
		public double getCircleScaleAngle() {
			return 30.f;
		}

		@Override
		public Marker getSeriesMarker(final int aIndex, final PolarAreaSeries aSeries) {
			return null;
		}

		@Override
		public Marker getSeriesPointMarker(final int aIndex, final PolarAreaSeries aSeries, final int aNo, final PolarAreaSeriesPoint aPoint) {
			return null;
		}

		/**
		 * データのストロークを取得する。
		 * 
		 * @param aIndex データインデックス
		 * @param aSeries シリーズデータ
		 * @return ストローク
		 */
		public Stroke getSeriesStroke(final int aIndex, final PolarAreaSeries aSeries, final int aNo, final PolarAreaSeriesPoint aPoint) {
			return getSeriesStroke(aIndex, aSeries);
		}

		/**
		 * データのストロークカラーを取得する。
		 * 
		 * @param aIndex データインデックス
		 * @param aSeries シリーズデータ
		 * @return カラー
		 */
		public Color getSeriesStrokeColor(final int aIndex, final PolarAreaSeries aSeries, final int aNo, final PolarAreaSeriesPoint aPoint) {
			return getSeriesStrokeColor(aIndex, aSeries);
		}

		/**
		 * データの塗りつぶしカラーを取得する。
		 * 
		 * @param aIndex データインデックス
		 * @param aSeries シリーズデータ
		 * @return カラー
		 */
		public Color getSeriesFillColor(final int aIndex, final PolarAreaSeries aSeries, final int aNo, final PolarAreaSeriesPoint aPoint) {
			return getSeriesFillColor(aIndex, aSeries);
		}
	}

}
