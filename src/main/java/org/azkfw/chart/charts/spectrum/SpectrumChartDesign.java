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
package org.azkfw.chart.charts.spectrum;

import org.azkfw.chart.charts.spectrum.SpectrumChartDesign.SpectrumChartStyle;
import org.azkfw.chart.design.AbstractMatrixChartDesign;
import org.azkfw.chart.design.chart.AbstractMatrixChartStyle;
import org.azkfw.chart.design.legend.CustomLegendStyle;
import org.azkfw.chart.design.title.CustomTitleStyle;

/**
 * このクラスは、スペクトログラムのデザイン情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
public class SpectrumChartDesign extends AbstractMatrixChartDesign<SpectrumChartStyle, SpectrumMatrixData> {

	/** デフォルトデザイン */
	public static SpectrumChartDesign DefalutDesign = new SpectrumChartDesign();

	/**
	 * コンストラクタ
	 */
	protected SpectrumChartDesign() {
		setChartStyle(new SpectrumChartStyle());
		setTitleStyle(new SpectrumTitleStyle());
		setLegendStyle(new SpectrumLegendStyle());
	}

	/**
	 * このクラスは、スペクトログラムのタイトルスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/09
	 * @author Kawakicchi
	 */
	public static class SpectrumTitleStyle extends CustomTitleStyle {

		/**
		 * コンストラクタ
		 */
		public SpectrumTitleStyle() {

		}
	}

	/**
	 * このクラスは、スペクトログラムの凡例スタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/09
	 * @author Kawakicchi
	 */
	public static class SpectrumLegendStyle extends CustomLegendStyle {

		/**
		 * コンストラクタ
		 */
		public SpectrumLegendStyle() {
			setPosition(LegendDisplayPosition.InnerTopLeft);
		}
	}

	/**
	 * このクラスは、スペクトログラムのグラフスタイルを定義するクラスです。
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/07/09
	 * @author Kawakicchi
	 */
	public static class SpectrumChartStyle extends AbstractMatrixChartStyle<SpectrumMatrixData> {

		public SpectrumChartStyle() {
		}

	}
}
