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
import org.azkfw.chart.charts.line.LineAxis.LineHorizontalAxis;
import org.azkfw.chart.charts.line.LineAxis.LineVerticalAxis;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.line.LineSeries;
import org.azkfw.chart.displayformat.MonthDisplayFormat;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、折れ線グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class LineChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createPlot(createDataset())), "Line Chart");
	}

	public static LineDataset createDataset() {
		LineDataset dataset = new LineDataset("Line Chart");

		LineSeries seriesAve = new LineSeries("Average");
		LineSeries seriesMax = new LineSeries("Maximum");
		LineSeries seriesMin = new LineSeries("Mininum");
		seriesAve.add(50.f);
		seriesMax.add(100.f);
		seriesMin.add(10.f);

		seriesAve.add(70.f);
		seriesMax.add(100.f);
		seriesMin.add(70.f);

		seriesAve.add(100.f);
		seriesMax.add(45.f);
		seriesMin.add(50.f);

		seriesAve.add(100.f);
		seriesMax.add(60.f);
		seriesMin.add(50.f);

		seriesAve.add(100.f);
		seriesMax.add(70.f);
		seriesMin.add(50.f);

		dataset.addSeries(seriesAve);
		dataset.addSeries(seriesMax);
		dataset.addSeries(seriesMin);

		return dataset;
	}

	public static LinePlot createPlot(final LineDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createLineChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		LinePlot plot = (LinePlot) chart.getPlot();

		LineVerticalAxis yAxis = plot.getVerticalAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(0.f);
		// yAxis.setMaximumValueAutoFit(false);
		// yAxis.setMaximumValue(150.f);
		LineHorizontalAxis xAxis = plot.getHorizontalAxis();
		xAxis.setDisplayFormat(new MonthDisplayFormat());

		return plot;
	}
}
