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
import org.azkfw.chart.charts.pie.PieData;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、円グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class PieChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createPlot(createDataset())), "Pie Chart");
	}

	public static PieDataset createDataset() {
		PieDataset dataset = new PieDataset("Pie Chart");

		dataset.addData(new PieData("日本", 50.f));
		dataset.addData(new PieData("アメリカ", 30.f));
		dataset.addData(new PieData("イギリス", 15.f));
		dataset.addData(new PieData("ドイツ", 5.f));

		return dataset;
	}

	public static PiePlot createPlot(final PieDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPieChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PiePlot plot = (PiePlot) chart.getPlot();

		return plot;
	}

}
