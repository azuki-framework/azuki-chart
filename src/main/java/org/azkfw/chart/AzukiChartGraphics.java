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
package org.azkfw.chart;

import java.awt.Graphics2D;

import org.azkfw.chart.charts.bar.BarChartPlot;
import org.azkfw.chart.charts.line.LineChartPlot;
import org.azkfw.chart.charts.pie.PieChartPlot;
import org.azkfw.chart.charts.polar.PolarChartPlot;
import org.azkfw.chart.charts.polararea.PolarAreaChartPlot;
import org.azkfw.chart.charts.radar.RadarChartPlot;
import org.azkfw.chart.charts.scatter.ScatterChartPlot;
import org.azkfw.chart.charts.spectrum.SpectrumChartPlot;
import org.azkfw.graphics.AzukiGraphics2D;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、グラフ描画機能を実装したグラフィクスクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2013/06/13
 * @author Kawakicchi
 */
public class AzukiChartGraphics extends AzukiGraphics2D {

	/**
	 * コンストラクタ
	 * 
	 * @param aGraphics Graphics2D
	 */
	public AzukiChartGraphics(final Graphics2D aGraphics) {
		super(aGraphics);
	}

	/**
	 * 棒グラフを描画する。
	 * 
	 * @param aPlot 棒グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawBarChart(final BarChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 折れ線グラフを描画する。
	 * 
	 * @param aPlot 折れ線グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawLineChart(final LineChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 円グラフを描画する。
	 * 
	 * @param aPlot 円グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPieChart(final PieChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 極座標グラフを描画する。
	 * 
	 * @param aPlot 極座標グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPolarChart(final PolarChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 鶏頭図を描画する。
	 * 
	 * @param aPlot 鶏頭図プロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPolarAreaChart(final PolarAreaChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * レーダーチャートを描画する。
	 * 
	 * @param aPlot レーダーチャートプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawRadarChart(final RadarChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 散布図を描画する。
	 * 
	 * @param aPlot 散布図プロット情報
	 * @param aRect 描画範囲
	 */
	public void drawScatterChart(final ScatterChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * スペクトログラムを描画する。
	 * 
	 * @param aPlot スペクトログラムプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawSpectrumChart(final SpectrumChartPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}
}
