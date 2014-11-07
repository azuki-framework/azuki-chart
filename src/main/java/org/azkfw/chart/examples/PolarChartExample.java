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
import org.azkfw.chart.charts.polar.PolarAxis;
import org.azkfw.chart.charts.polar.PolarChartPlot;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polar.PolarSeries;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、極座標グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class PolarChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createChartPlot(createDataset())), "Polar Chart");
	}

	public static PolarDataset createDataset() {
		PolarDataset dataset = new PolarDataset("Polar Chart");

		PolarSeries series1 = new PolarSeries("aaa");
		series1.add(0, 1.0);
		series1.add(30, 0.1);
		series1.add(90, 0.5);
		series1.add(180, 0.6);
		series1.add(270, 0.6);
		dataset.addSeries(series1);

		PolarSeries series2 = new PolarSeries("bbb");
		for (int i = 0; i < 360; i += 30) {
			series2.add(i, i * 0.002 + 0.2);
		}
		dataset.addSeries(series2);

		PolarSeries series3 = new PolarSeries("ccc");
		for (int i = 0; i < 360; i += 45) {
			series3.add(i, 0.85);
		}
		dataset.addSeries(series3);

		return dataset;
	}

	public static PolarChartPlot createChartPlot(final PolarDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPolarChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PolarChartPlot plot = (PolarChartPlot) chart.getPlot();

		PolarAxis axis = plot.getAxis();
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(1.2);
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		return plot;
	}

}
