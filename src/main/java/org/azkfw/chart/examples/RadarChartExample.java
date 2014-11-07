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
package org.azkfw.chart.examples;

import java.awt.Color;

import org.azkfw.chart.AzukiChart;
import org.azkfw.chart.AzukiChartFactory;
import org.azkfw.chart.charts.radar.RadarAxis;
import org.azkfw.chart.charts.radar.RadarChartPlot;
import org.azkfw.chart.charts.radar.RadarDataset;
import org.azkfw.chart.charts.radar.RadarSeries;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、レーダーチャートの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class RadarChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createChartPlot(createDataset())), "Radar Chart");
	}

	public static RadarDataset createDataset() {
		RadarDataset dataset = new RadarDataset("Radar Chart");

		RadarSeries series1 = new RadarSeries("Border");
		series1.add(75);
		series1.add(75);
		series1.add(75);
		series1.add(75);
		series1.add(75);
		dataset.addSeries(series1);

		RadarSeries series2 = new RadarSeries("Average");
		series2.add(80);
		series2.add(60);
		series2.add(75);
		series2.add(50);
		series2.add(95);
		dataset.addSeries(series2);

		RadarSeries series3 = new RadarSeries("Data 1");
		series3.add(100);
		series3.add(30);
		series3.add(75);
		series3.add(80);
		series3.add(90);
		dataset.addSeries(series3);

		return dataset;
	}

	public static RadarChartPlot createChartPlot(final RadarDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createRadarChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		RadarChartPlot plot = (RadarChartPlot) chart.getPlot();

		RadarAxis axis = plot.getAxis();
		axis.setMinimumValueAutoFit(false);
		axis.setMinimumValue(0.0);
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(100.0);
		axis.setDisplayFormat(new NumericDisplayFormat(0));

		return plot;
	}
}
