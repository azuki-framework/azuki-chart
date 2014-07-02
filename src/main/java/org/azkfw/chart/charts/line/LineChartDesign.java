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
package org.azkfw.chart.charts.line;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.line.LineChartDesign.LineChartStyle;
import org.azkfw.chart.charts.line.LineSeries.LineSeriesPoint;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、折れ線グラフのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/02
 * @author Kawakicchi
 */
public class LineChartDesign extends AbstractSeriesChartDesign<LineChartStyle, LineSeries, LineSeriesPoint> {

	/** デフォルトデザイン */
	public static LineChartDesign DefalutDesign = new LineChartDesign();

	/**
	 * コンストラクタ
	 */
	protected LineChartDesign() {
		setChartStyle(new LineChartStyle());
		setTitleStyle(new CustomTitleStyle());
		setLegendStyle(new CustomLegendStyle());
	}

	/**
	 * このクラスは、折れ線グラフのスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/02
	 * @author Kawakicchi
	 */
	public static class LineChartStyle extends AbstractSeriesChartStyle<LineSeries, LineSeriesPoint> {

		/**
		 * コンストラクタ
		 */
		public LineChartStyle() {

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
		 * X軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getXAxisScaleColor() {
			return Color.LIGHT_GRAY;
		}

		/**
		 * X軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getXAxisScaleStroke() {
			float dash[] = { 2.0f, 2.0f };
			BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
			return dsahStroke;
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

		/**
		 * Y軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getYAxisScaleColor() {
			return Color.LIGHT_GRAY;
		}

		/**
		 * Y軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getYAxisScaleStroke() {
			float dash[] = { 2.0f, 2.0f };
			BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
			return dsahStroke;
		}
	}

}
