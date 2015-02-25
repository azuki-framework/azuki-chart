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
import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterXAxis;
import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterYAxis;
import org.azkfw.chart.charts.scatter.ScatterChartPlot;
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterSeries;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、散布図の実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class ScatterChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createChartPlot(createDataset())), "Scatter Chart");
	}

	public static ScatterDataset createDataset() {

		ScatterDataset dataset = new ScatterDataset();

		ScatterSeries seriesSin = new ScatterSeries("sin(x)");
		ScatterSeries seriesCos = new ScatterSeries("cos(x)");
		for (int i = 0; i <= 360; i += 10) {
			seriesSin.add(i, Math.sin(RADIANS(i)));
			seriesCos.add(i, Math.cos(RADIANS(i)));
		}
		dataset.addSeries(seriesSin);
		dataset.addSeries(seriesCos);

		return dataset;
	}

	public static ScatterChartPlot createChartPlot(final ScatterDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createScatterChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		ScatterChartPlot plot = (ScatterChartPlot) chart.getPlot();
		//plot.setChartDesign(ScatterChartDesign.DarkDesign);

		ScatterXAxis xAxis = plot.getXAxis();
		xAxis.setLabelTitle("X");
		xAxis.setDisplayFormat(new NumericDisplayFormat(0));
		xAxis.setScaleAutoFit(false);
		xAxis.setScale(45);

		ScatterYAxis yAxis = plot.getYAxis();
		yAxis.setLabelTitle("Y");
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(-1.5);
		yAxis.setMaximumValueAutoFit(false);
		yAxis.setMaximumValue(1.5);
		yAxis.setScaleAutoFit(false);
		yAxis.setScale(0.5);

		return plot;
	}

	private static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
