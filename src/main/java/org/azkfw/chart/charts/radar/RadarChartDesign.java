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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.radar.RadarChartDesign.RadarChartStyle;
import org.azkfw.chart.charts.radar.RadarSeries.RadarSeriesPoint;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、レーダーチャートのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author Kawakicchi
 */
public class RadarChartDesign extends AbstractSeriesChartDesign<RadarChartStyle, RadarSeries, RadarSeriesPoint> {

	/** デフォルトデザイン */
	public static RadarChartDesign DefalutDesign = new RadarChartDesign();

	/**
	 * コンストラクタ
	 */
	protected RadarChartDesign() {
		setChartStyle(new RadarChartStyle());
		setTitleStyle(new CustomTitleStyle());
		setLegendStyle(new CustomLegendStyle());
	}

	/**
	 * このクラスは、レーダーチャートのスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class RadarChartStyle extends AbstractSeriesChartStyle<RadarSeries, RadarSeriesPoint> {

		/**
		 * コンストラクタ
		 */
		public RadarChartStyle() {
		}

		/**
		 * 軸のフォントを取得する。
		 * 
		 * @return フォント
		 */
		public Font getAxisFont() {
			return new Font("Arial", Font.BOLD, 16);
		}

		/**
		 * 軸のフォントカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisFontColor() {
			return Color.BLACK;
		}

		/**
		 * 軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisLineColor() {
			return Color.BLACK;
		}

		/**
		 * 軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getAxisLineStroke() {
			return new BasicStroke(1.f);
		}

		/**
		 * 副軸のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAssistAxisLineColor() {
			return Color.LIGHT_GRAY;
		}

		/**
		 * 副軸のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getAssistAxisLineStroke() {
			return new BasicStroke(1.f);
		}

		/**
		 * 円のカラーを取得する。
		 * 
		 * @return カラー
		 */
		public Color getAxisCircleColor() {
			return Color.LIGHT_GRAY;
		}

		/**
		 * 円のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getAxisCircleStroke() {
			float dash[] = { 4.0f, 4.0f };
			BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
			return dsahStroke;
		}
	}
}
