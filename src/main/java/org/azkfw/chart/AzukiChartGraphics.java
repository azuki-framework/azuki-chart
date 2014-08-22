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

import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polararea.PolarAreaPlot;
import org.azkfw.chart.charts.radar.RadarPlot;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.charts.spectrum.SpectrumPlot;
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
	public void drawBarChart(final BarPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 折れ線グラフを描画する。
	 * 
	 * @param aPlot 折れ線グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawLineChart(final LinePlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 円グラフを描画する。
	 * 
	 * @param aPlot 円グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPieChart(final PiePlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 極座標グラフを描画する。
	 * 
	 * @param aPlot 極座標グラフプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPolarChart(final PolarPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 鶏頭図を描画する。
	 * 
	 * @param aPlot 鶏頭図プロット情報
	 * @param aRect 描画範囲
	 */
	public void drawPolarAreaChart(final PolarAreaPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * レーダーチャートを描画する。
	 * 
	 * @param aPlot レーダーチャートプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawRadarChart(final RadarPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * 散布図を描画する。
	 * 
	 * @param aPlot 散布図プロット情報
	 * @param aRect 描画範囲
	 */
	public void drawScatterChart(final ScatterPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}

	/**
	 * スペクトログラムを描画する。
	 * 
	 * @param aPlot スペクトログラムプロット情報
	 * @param aRect 描画範囲
	 */
	public void drawSpectrumChart(final SpectrumPlot aPlot, final Rect aRect) {
		aPlot.draw(this, aRect);
	}
}
