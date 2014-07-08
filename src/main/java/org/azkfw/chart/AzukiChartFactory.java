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

import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polararea.PolarAreaDataset;
import org.azkfw.chart.charts.polararea.PolarAreaPlot;
import org.azkfw.chart.charts.radar.RadarDataset;
import org.azkfw.chart.charts.radar.RadarPlot;
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.charts.scatter.ScatterSeries;

/**
 * このクラスは、チャート生成を行うファクトリークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public class AzukiChartFactory {

	/**
	 * 棒グラフのインスタンスを生成する。
	 * 
	 * @return 棒グラフ
	 */
	public static AzukiChart createBarChart() {
		BarPlot plot = new BarPlot();
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
		BarPlot plot = new BarPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 折れ線グラフのインスタンスを生成する。
	 * 
	 * @return 折れ線グラフ
	 */
	public static AzukiChart createLineChart() {
		LinePlot plot = new LinePlot();
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
		LinePlot plot = new LinePlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 円グラフのインスタンスを生成する。
	 * 
	 * @return 円グラフ
	 */
	public static AzukiChart createPieChart() {
		PiePlot plot = new PiePlot();
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
		PiePlot plot = new PiePlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 極座標グラフのインスタンスを生成する。
	 * 
	 * @return 極座標グラフ
	 */
	public static AzukiChart createPolarChart() {
		PolarPlot plot = new PolarPlot();
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
		PolarPlot plot = new PolarPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 鶏頭図のインスタンスを生成する。
	 * 
	 * @return 鶏頭図
	 */
	public static AzukiChart createPolarAreaChart() {
		PolarAreaPlot plot = new PolarAreaPlot();
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
		PolarAreaPlot plot = new PolarAreaPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * レーダーチャートのインスタンスを生成する。
	 * 
	 * @return レーダーチャート
	 */
	public static AzukiChart createRadarChart() {
		RadarPlot plot = new RadarPlot();
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
		RadarPlot plot = new RadarPlot(aDataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}

	/**
	 * 散布図のインスタンスを生成する。
	 * 
	 * @return 散布図
	 */
	public static AzukiChart createScatterChart() {
		ScatterPlot plot = new ScatterPlot();
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
		ScatterPlot plot = new ScatterPlot(aDataset);
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

		ScatterPlot plot = new ScatterPlot(dataset);
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}
}
