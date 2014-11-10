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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.bar.BarChartDesign.BarChartStyle;
import org.azkfw.chart.charts.bar.BarSeries.BarSeriesPoint;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、棒グラフのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class BarChartDesign extends AbstractSeriesChartDesign<BarChartStyle, BarSeries, BarSeriesPoint> {

	/** デフォルトデザイン */
	public static BarChartDesign DefalutDesign = new BarChartDesign();

	/**
	 * コンストラクタ
	 */
	protected BarChartDesign() {
		setChartStyle(new BarChartStyle());
		setTitleStyle(new BarTitleStyle());
		setLegendStyle(new BarLegendStyle());
	}

	/**
	 * このクラスは、棒グラフのタイトルスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/03
	 * @author Kawakicchi
	 */
	public static class BarTitleStyle extends CustomTitleStyle {
		/**
		 * コンストラクタ
		 */
		public BarTitleStyle() {

		}
	}

	/**
	 * このクラスは、棒グラフの凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/03
	 * @author Kawakicchi
	 */
	public static class BarLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public BarLegendStyle() {
			setPosition(LegendDisplayPosition.Bottom);
		}
	}

	/**
	 * このクラスは、棒グラフのグラフスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class BarChartStyle extends AbstractSeriesChartStyle<BarSeries, BarSeriesPoint> {

		/**
		 * コンストラクタ
		 */
		public BarChartStyle() {

		}

		/**
		 * 水平軸線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getHorizontalAxisLineColor() {
			return getDefaultAxisLineColor();
		}

		/**
		 * 水平軸線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getHorizontalAxisLineStroke() {
			return getDefaultAxisLineStroke();
		}

		/**
		 * 水平軸ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getHorizontalAxisLabelFont() {
			return getDefaultAxisLabelFont();
		}

		/**
		 * 水平軸ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getHorizontalAxisLabelColor() {
			return getDefaultAxisLabelColor();
		}

		/**
		 * 水平軸目盛ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getHorizontalAxisScaleLabelFont() {
			return getDefaultAxisScaleLabelFont();
		}

		/**
		 * 水平軸目盛ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getHorizontalAxisScaleLabelColor() {
			return getDefaultAxisScaleLabelColor();
		}

		/**
		 * 水平軸目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getHorizontalAxisScaleLineColor() {
			return null;
		}

		/**
		 * 水平軸目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getHorizontalAxisScaleLineStroke() {
			return null;
		}

		/**
		 * 垂直軸線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getVerticalAxisLineColor() {
			return null;
		}

		/**
		 * 垂直軸線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getVerticalAxisLineStroke() {
			return null;
		}

		/**
		 * 垂直軸ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getVerticalAxisLabelFont() {
			return getDefaultAxisLabelFont();
		}

		/**
		 * 垂直軸ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getVerticalAxisLabelColor() {
			return getDefaultAxisLabelColor();
		}

		/**
		 * 垂直軸目盛ラベルのフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getVerticalAxisScaleLabelFont() {
			return getDefaultAxisScaleLabelFont();
		}

		/**
		 * 垂直軸目盛ラベルのカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getVerticalAxisScaleLabelColor() {
			return getDefaultAxisScaleLabelColor();
		}

		/**
		 * 垂直軸目盛線のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getVerticalAxisScaleLineColor() {
			return getDefaultAxisScaleLineColor();
		}

		/**
		 * 垂直軸目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getVerticalAxisScaleLineStroke() {
			return getDefaultAxisScaleLineStroke();
		}

		@Override
		public Marker getSeriesMarker(final int aIndex, final BarSeries aSeries) {
			return null;
		}

		@Override
		public Marker getSeriesPointMarker(final int aIndex, final BarSeries aSeries, final int aNo, final BarSeriesPoint aPoint) {
			return null;
		}

		@Override
		public Stroke getSeriesStroke(final int aIndex, final BarSeries aSeries) {
			return new BasicStroke(1.f);
		}
	}

}
