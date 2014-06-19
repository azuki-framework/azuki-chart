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

import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.polar.PolarPlot;

/**
 * このクラスは、チャート生成を行うファクトリークラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public class AzukiChartFactory {

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
	 * 極座標グラフのインスタンスを生成する。
	 * 
	 * @return 極座標グラフ
	 */
	public static AzukiChart createPolarChart() {
		PolarPlot polar = new PolarPlot();
		AzukiChart chart = new AzukiChart(polar);
		return chart;
	}

}
