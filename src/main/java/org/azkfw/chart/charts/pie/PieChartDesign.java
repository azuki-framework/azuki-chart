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
package org.azkfw.chart.charts.pie;

import java.awt.Color;

import org.azkfw.chart.charts.pie.PieChartDesign.PieChartStyle;
import org.azkfw.chart.design.AbstractChartDesign;
import org.azkfw.chart.design.chart.AbstractChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、円グラフのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/22
 * @author Kawakicchi
 */
public class PieChartDesign extends AbstractChartDesign<PieChartStyle> {

	/** デフォルトデザイン */
	public static PieChartDesign DefalutDesign = new PieChartDesign();

	/**
	 * コンストラクタ
	 */
	protected PieChartDesign() {
		setChartStyle(new PieChartStyle());
		setTitleStyle(new PieTitleStyle());
		setLegendStyle(new PieLegendStyle());
	}

	/**
	 * このクラスは、円グラフのタイトルスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PieTitleStyle extends CustomTitleStyle {

		/**
		 * コンストラクタ
		 */
		public PieTitleStyle() {

		}
	}

	/**
	 * このクラスは、円グラフの凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PieLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public PieLegendStyle() {
			setPosition(LegendDisplayPosition.InnerTopLeft);
		}
	}

	/**
	 * このクラスは、円グラフのグラフスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/30
	 * @author Kawakicchi
	 */
	public static class PieChartStyle extends AbstractChartStyle {

		public PieChartStyle() {
		}

		/**
		 * データのストロークカラーを取得する。
		 * 
		 * @param aIndex データインデックス
		 * @return カラー
		 */
		public Color getDataStrokeColor(final int aIndex) {
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
		 * @return カラー
		 */
		public Color getDataFillColor(final int aIndex) {
			Color color = getDataStrokeColor(aIndex);
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
			return color;
		}
	}
}
