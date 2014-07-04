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
		setTitleStyle(new RadarTitleStyle());
		setLegendStyle(new RadarLegendStyle());
	}

	/**
	 * このクラスは、レーダーチャートのタイトルタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class RadarTitleStyle extends CustomTitleStyle {

		/**
		 * コンストラクタ
		 */
		public RadarTitleStyle() {

		}
	}

	/**
	 * このクラスは、レーダーチャートの凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class RadarLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public RadarLegendStyle() {
			setPosition(LegendDisplayPosition.InnerTopLeft);
		}
	}

	/**
	 * このクラスは、レーダーチャートのグラフスタイルを定義するクラスです。
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
			return null;
		}

		/**
		 * 円目盛線のストロークを取得する。
		 * 
		 * @return ストローク
		 */
		public Stroke getCircleScaleLineStroke() {
			return null;
		}
	}
}
