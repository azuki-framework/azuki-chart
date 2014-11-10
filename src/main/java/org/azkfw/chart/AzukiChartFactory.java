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

import java.util.List;

import org.azkfw.chart.charts.bar.BarChartPlot;
import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.line.LineChartPlot;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.pie.PieChartPlot;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.polar.PolarChartPlot;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polararea.PolarAreaChartPlot;
import org.azkfw.chart.charts.polararea.PolarAreaDataset;
import org.azkfw.chart.charts.radar.RadarChartPlot;
import org.azkfw.chart.charts.radar.RadarDataset;
import org.azkfw.chart.charts.scatter.ScatterChartPlot;
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterSeries;
import org.azkfw.chart.charts.spectrum.SpectrumChartPlot;
import org.azkfw.chart.charts.spectrum.SpectrumDataset;

/**
 * このクラスは、チャート生成を行うファクトリークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public class AzukiChartFactory {

	/**
	 * コンストラクタ
	 * <p>
	 * インスタンス生成を禁止
	 * </p>
	 */
	private AzukiChartFactory() {

	}

	/**
	 * 棒グラフのインスタンスを生成する。
	 * 
	 * @return 棒グラフ
	 */
	public static AzukiChart createBarChart() {
		BarChartPlot plot = new BarChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 棒グラフのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 棒グラフ
	 */
	public static AzukiChart createBarChart(final BarDataset aDataset) {
		BarChartPlot plot = new BarChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 折れ線グラフのインスタンスを生成する。
	 * 
	 * @return 折れ線グラフ
	 */
	public static AzukiChart createLineChart() {
		LineChartPlot plot = new LineChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 折れ線グラフのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 折れ線グラフ
	 */
	public static AzukiChart createLineChart(final LineDataset aDataset) {
		LineChartPlot plot = new LineChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 円グラフのインスタンスを生成する。
	 * 
	 * @return 円グラフ
	 */
	public static AzukiChart createPieChart() {
		PieChartPlot plot = new PieChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 円グラフのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 円グラフ
	 */
	public static AzukiChart createPieChart(final PieDataset aDataset) {
		PieChartPlot plot = new PieChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 極座標グラフのインスタンスを生成する。
	 * 
	 * @return 極座標グラフ
	 */
	public static AzukiChart createPolarChart() {
		PolarChartPlot plot = new PolarChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 極座標グラフのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 極座標グラフ
	 */
	public static AzukiChart createPolarChart(final PolarDataset aDataset) {
		PolarChartPlot plot = new PolarChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 鶏頭図のインスタンスを生成する。
	 * 
	 * @return 鶏頭図
	 */
	public static AzukiChart createPolarAreaChart() {
		PolarAreaChartPlot plot = new PolarAreaChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 鶏頭図のインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 鶏頭図
	 */
	public static AzukiChart createPolarAreaChart(final PolarAreaDataset aDataset) {
		PolarAreaChartPlot plot = new PolarAreaChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * レーダーチャートのインスタンスを生成する。
	 * 
	 * @return レーダーチャート
	 */
	public static AzukiChart createRadarChart() {
		RadarChartPlot plot = new RadarChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * レーダーチャートのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return レーダーチャート
	 */
	public static AzukiChart createRadarChart(final RadarDataset aDataset) {
		RadarChartPlot plot = new RadarChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 散布図のインスタンスを生成する。
	 * 
	 * @return 散布図
	 */
	public static AzukiChart createScatterChart() {
		ScatterChartPlot plot = new ScatterChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 散布図のインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return 散布図
	 */
	public static AzukiChart createScatterChart(final ScatterDataset aDataset) {
		ScatterChartPlot plot = new ScatterChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 散布図のインスタンスを生成する。
	 * 
	 * @param aData データ
	 * @return 散布図
	 */
	public static AzukiChart createScatterChart(final List<Double> aData) {
		ScatterDataset dataset = new ScatterDataset();
		ScatterSeries series = new ScatterSeries();
		for (int i = 0; i < aData.size(); i++) {
			series.add(i, aData.get(i));
		}
		dataset.addSeries(series);

		ScatterChartPlot plot = new ScatterChartPlot(dataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * スペクトログラムのインスタンスを生成する。
	 * 
	 * @return スペクトログラム
	 */
	public static AzukiChart createSpectrumChart() {
		SpectrumChartPlot plot = new SpectrumChartPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * スペクトログラムのインスタンスを生成する。
	 * 
	 * @param aDataset データセット
	 * @return スペクトログラム
	 */
	public static AzukiChart createSpectrumChart(final SpectrumDataset aDataset) {
		SpectrumChartPlot plot = new SpectrumChartPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}
}
