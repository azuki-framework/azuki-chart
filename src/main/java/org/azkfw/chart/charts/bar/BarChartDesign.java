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
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、棒グラフのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class BarChartDesign extends AbstractSeriesChartDesign<BarChartStyle, BarSeries, BarSeriesPoint> {

	/**
	 * コンストラクタ
	 */
	public BarChartDesign() {
		setChartStyle(new BarChartStyle());
		setTitleStyle(new CustomTitleStyle());
		setLegendStyle(new CustomLegendStyle());
	}

	/**
	 * このクラスは、棒グラフのスタイルを定義するクラスです。
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
		 * X軸のフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getXAxisFont() {
			return new Font("Arial", Font.BOLD, 16);
		}

		/**
		 * X軸のフォントカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisFontColor() {
			return Color.BLACK;
		}

		/**
		 * X軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisLineColor() {
			return Color.BLACK;
		}

		/**
		 * X軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getXAxisLineStroke() {
			return new BasicStroke(1.f);
		}

		/**
		 * Y軸のフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getYAxisFont() {
			return new Font("Arial", Font.BOLD, 16);
		}

		/**
		 * Y軸のフォントカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisFontColor() {
			return Color.BLACK;
		}

		/**
		 * Y軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisLineColor() {
			return Color.BLACK;
		}

		/**
		 * Y軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getYAxisLineStroke() {
			return new BasicStroke(1.f);
		}
	}

}
