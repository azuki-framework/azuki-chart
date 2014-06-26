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

import org.azkfw.chart.charts.area.AreaPlot;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polararea.PolarAreaPlot;
import org.azkfw.chart.charts.radar.RadarPlot;
import org.azkfw.chart.charts.scatter.ScatterPlot;

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
	 * エリアグラフのインスタンスを生成する。
	 * 
	 * @return エリアグラフ
	 */
	public static AzukiChart createAreaChart() {
		AreaPlot plot = new AreaPlot();
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
	 * 鶏頭図のインスタンスを生成する。
	 * 
	 * @return 鶏頭図
	 */
	public static AzukiChart createPolarAreaChart() {
		PolarAreaPlot plot = new PolarAreaPlot();
		AzukiChart chart = new AzukiChart(plot);
		return chart;
	}
}
