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
import org.azkfw.chart.charts.bar.BarAxis.BarHorizontalAxis;
import org.azkfw.chart.charts.bar.BarAxis.BarVerticalAxis;
import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.bar.BarSeries;
import org.azkfw.chart.displayformat.MonthDisplayFormat;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、棒グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class BarChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createPlot(createDataset())), "Bar Chart");
	}

	public static BarDataset createDataset() {
		BarDataset dataset = new BarDataset("Bar Chart");

		BarSeries seriesAve = new BarSeries("Average");
		BarSeries seriesMax = new BarSeries("Maximum");
		BarSeries seriesMin = new BarSeries("Mininum");
		seriesAve.add(50.f);
		seriesMax.add(100.f);
		seriesMin.add(10.f);
		seriesAve.add(70.f);
		seriesMax.add(70.f);
		seriesMin.add(70.f);
		seriesAve.add(100.f);
		seriesMax.add(200.f);
		seriesMin.add(50.f);
		seriesAve.add(100.f);
		seriesMax.add(200.f);
		seriesMin.add(50.f);
		seriesAve.add(100.f);
		seriesMax.add(200.f);
		seriesMin.add(50.f);
		dataset.addSeries(seriesAve);
		dataset.addSeries(seriesMax);
		dataset.addSeries(seriesMin);

		return dataset;
	}

	public static BarPlot createPlot(final BarDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createBarChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		BarPlot plot = (BarPlot) chart.getPlot();

		BarVerticalAxis yAxis = plot.getVerticalAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(0.f);
		// yAxis.setMaximumValueAutoFit(false);
		// yAxis.setMaximumValue(150.f);
		BarHorizontalAxis xAxis = plot.getHorizontalAxis();
		xAxis.setDisplayFormat(new MonthDisplayFormat());

		return plot;
	}
}
