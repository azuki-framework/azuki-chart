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
import org.azkfw.chart.charts.polararea.PolarAreaAxis;
import org.azkfw.chart.charts.polararea.PolarAreaChartPlot;
import org.azkfw.chart.charts.polararea.PolarAreaDataset;
import org.azkfw.chart.charts.polararea.PolarAreaSeries;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、鶏頭図の実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class PolarAreaChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createChartPlot(createDataset())), "PolarArea Chart");
	}

	public static PolarAreaDataset createDataset() {
		PolarAreaDataset dataset = new PolarAreaDataset("PolarArea Chart");

		PolarAreaSeries series1 = new PolarAreaSeries("=AAAAAAAA=");
		series1.add(1.2);
		series1.add(0.1);
		series1.add(0.5);
		series1.add(0.6);
		series1.add(0.6);
		series1.add(0.3);
		dataset.addSeries(series1);

		PolarAreaSeries series2 = new PolarAreaSeries("BBB");
		for (int i = 0; i < 6; i++) {
			series2.add(i * 0.1 + 0.1);
		}
		dataset.addSeries(series2);

		PolarAreaSeries series3 = new PolarAreaSeries("CCC");
		for (int i = 0; i < 6; i++) {
			series3.add(0.85);
		}
		dataset.addSeries(series3);

		return dataset;
	}

	public static PolarAreaChartPlot createChartPlot(final PolarAreaDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPolarAreaChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PolarAreaChartPlot plot = (PolarAreaChartPlot) chart.getPlot();

		PolarAreaAxis axis = plot.getAxis();
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(1.5);
		axis.setDisplayFormat(new NumericDisplayFormat(1));

		return plot;
	}

}
