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
import org.azkfw.chart.charts.pie.PieChartPlot;
import org.azkfw.chart.charts.pie.PieData;
import org.azkfw.chart.charts.pie.PieDataset;
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
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createChartPlot(createDataset())), "Pie Chart");
	}

	public static PieDataset createDataset() {
		PieDataset dataset = new PieDataset("ウェブブラウザ - シェア率");

		dataset.addData(new PieData("Chrome", 27.25f));
		dataset.addData(new PieData("IE", 23.65f));
		dataset.addData(new PieData("FireFox", 18.87f));
		dataset.addData(new PieData("Safari", 15.87f));
		dataset.addData(new PieData("Android", 4.75f));
		dataset.addData(new PieData("Opera", 4.59f));
		dataset.addData(new PieData("Other", 5.02f));

		return dataset;
	}

	public static PieChartPlot createChartPlot(final PieDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPieChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PieChartPlot plot = (PieChartPlot) chart.getPlot();

		return plot;
	}

}
