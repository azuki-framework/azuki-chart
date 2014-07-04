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

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.scatter.ScatterChartDesign.ScatterChartStyle;
import org.azkfw.chart.charts.scatter.ScatterSeries.ScatterSeriesPoint;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.design.title.CustomTitleStyle;
import org.azkfw.chart.design.title.TitleStyle;

/**
 * このクラスは、散布図のデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class ScatterChartDesign extends AbstractSeriesChartDesign<ScatterChartStyle, ScatterSeries, ScatterSeriesPoint> {

	/** デフォルトデザイン */
	public static ScatterChartDesign DefalutDesign = new ScatterChartDesign();

	public static ScatterChartDesign DarkDesign = new ScatterChartDesign(new ScatterChartDarkStyle(), new CustomTitleStyle(),
			new CustomLegendStyle(), new Color(32, 32, 32, 255));

	/**
	 * コンストラクタ
	 */
	protected ScatterChartDesign() {
		setChartStyle(new ScatterChartStyle());
		setTitleStyle(new ScatterTitleStyle());
		setLegendStyle(new ScatterLegendStyle());
	}

	/**
	 * コンストラクタ
	 */
	protected ScatterChartDesign(final ScatterChartStyle aChartStyle, final TitleStyle aTitleStyle, final LegendStyle aLegendStyle,
			final Color aBackgroundColor) {
		setChartStyle(aChartStyle);
		setTitleStyle(aTitleStyle);
		setLegendStyle(aLegendStyle);
		setBackgroundColor(aBackgroundColor);
	}

	/**
	 * このクラスは、散布図のタイトルスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/03
	 * @author Kawakicchi
	 */
	public static class ScatterTitleStyle extends CustomTitleStyle {

		/**
		 * コンストラクタ
		 */
		public ScatterTitleStyle() {

		}
	}

	/**
	 * このクラスは、散布図の凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/03
	 * @author Kawakicchi
	 */
	public static class ScatterLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public ScatterLegendStyle() {
			setPosition(LegendDisplayPosition.Bottom);
		}
	}

	/**
	 * このクラスは、散布図のグラフスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class ScatterChartStyle extends AbstractSeriesChartStyle<ScatterSeries, ScatterSeriesPoint> {

		/**
		 * コンストラクタ
		 */
		public ScatterChartStyle() {

		}

		/**
		 * X軸線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisLineColor() {
			return getDefaultAxisLineColor();
		}

		/**
		 * X軸線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getXAxisLineStroke() {
			return getDefaultAxisLineStroke();
		}

		/**
		 * X軸目盛ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getXAxisScaleLabelFont() {
			return getDefaultAxisScaleLabelFont();
		}

		/**
		 * X軸目盛ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisScaleLabelColor() {
			return getDefaultAxisScaleLabelColor();
		}

		/**
		 * X軸目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisScaleLineColor() {
			return getDefaultAxisScaleLineColor();
		}

		/**
		 * X軸目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getXAxisScaleLineStroke() {
			return getDefaultAxisScaleLineStroke();
		}

		/**
		 * Y軸線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisLineColor() {
			return getDefaultAxisLineColor();
		}

		/**
		 * Y軸線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getYAxisLineStroke() {
			return getDefaultAxisLineStroke();
		}

		/**
		 * Y軸目盛ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getYAxisScaleLabelFont() {
			return getDefaultAxisScaleLabelFont();
		}

		/**
		 * Y軸目盛ラベルのフォントカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisScaleLabelColor() {
			return getDefaultAxisScaleLabelColor();
		}

		/**
		 * Y軸目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisScaleLineColor() {
			return getDefaultAxisScaleLineColor();
		}

		/**
		 * Y軸目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getYAxisScaleLineStroke() {
			return getDefaultAxisScaleLineStroke();
		}

		@Override
		public Color getSeriesFillColor(final int aIndex, final ScatterSeries aSeries) {
			return null;
		}

		@Override
		public Marker getSeriesMarker(final int aIndex, final ScatterSeries aSeries) {
			return null;
		}

		@Override
		public Marker getSeriesPointMarker(final int aIndex, final ScatterSeries aSeries, final int aNo, final ScatterSeriesPoint aPoint) {
			return null;
		}
	}

	/**
	 * このクラスは、散布図のスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class ScatterChartDarkStyle extends ScatterChartStyle {

		/**
		 * コンストラクタ
		 */
		public ScatterChartDarkStyle() {
			setBackgroundColor(new Color(32, 32, 32, 255));
		}

		@Override
		public Color getXAxisScaleLabelColor() {
			return new Color(220, 220, 220, 255);
		}

		@Override
		public Color getXAxisLineColor() {
			return Color.LIGHT_GRAY;
		}

		@Override
		public Color getXAxisScaleLineColor() {
			return Color.LIGHT_GRAY;
		}

		@Override
		public Color getYAxisScaleLabelColor() {
			return new Color(220, 220, 220, 255);
		}

		@Override
		public Color getYAxisLineColor() {
			return Color.LIGHT_GRAY;
		}

		@Override
		public Color getYAxisScaleLineColor() {
			return Color.LIGHT_GRAY;
		}

		@Override
		public Color getSeriesFillColor(final int aIndex, final ScatterSeries aSeries) {
			return null;
		}
	}
}
