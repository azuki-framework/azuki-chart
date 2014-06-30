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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.charts.polar.PolarSeriesPoint;
import org.azkfw.chart.charts.polararea.PolarAreaChartDesign.PolarAreaChartStyle;
import org.azkfw.chart.design.AbstractSeriesChartDesign;
import org.azkfw.chart.design.chart.AbstractSeriesChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;
import org.azkfw.chart.looks.marker.Marker;

/**
 * このクラスは、鶏頭図のデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author Kawakicchi
 */
public class PolarAreaChartDesign extends AbstractSeriesChartDesign<PolarAreaChartStyle, PolarAreaSeries, PolarSeriesPoint> {

	/**
	 * コンストラクタ
	 */
	public PolarAreaChartDesign() {
		setChartStyle(new PolarAreaChartStyle());
		setTitleStyle(new CustomTitleStyle());
		setLegendStyle(new CustomLegendStyle());
	}

	/**
	 * このクラスは、鶏頭図グラフのスタイルを定義するクラスです。
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

		public Marker getSeriesMarker(final int aIndex, final PolarAreaSeries aSeries) {
			return null;
		}

		public Marker getSeriesPointMarker(final int aIndex, final PolarAreaSeries aSeries, final int aNo, final PolarSeriesPoint aPoint) {
			return null;
		}

	}

}
