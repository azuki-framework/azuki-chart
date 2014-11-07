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
		LineDataset dataset = new LineDataset("支店別売上");

		LineSeries series1 = new LineSeries("東京");
		series1.add(50.f);
		series1.add(75.f);
		series1.add(135.f);
		series1.add(48.f);
		series1.add(185.f);
		dataset.addSeries(series1);

		LineSeries series2 = new LineSeries("大阪");
		series2.add(100.f);
		series2.add(0.f);
		series2.add(10.f);
		series2.add(200.f);
		series2.add(145.f);
		dataset.addSeries(series2);

		LineSeries series3 = new LineSeries("名古屋");
		series3.add(10.f);
		series3.add(35.f);
		series3.add(100.f);
		series3.add(75.f);
		series3.add(50.f);
		dataset.addSeries(series3);

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
