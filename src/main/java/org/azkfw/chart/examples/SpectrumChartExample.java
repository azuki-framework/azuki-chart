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
import org.azkfw.chart.charts.spectrum.SpectrumAxis;
import org.azkfw.chart.charts.spectrum.SpectrumDataset;
import org.azkfw.chart.charts.spectrum.SpectrumMatrixData;
import org.azkfw.chart.charts.spectrum.SpectrumPlot;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、スペクトログラムの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/06
 * @author Kawakicchi
 */
public class SpectrumChartExample {

	public static void main(final String[] args) {
		AzukiChartUtility.showChartAsFrame(new AzukiChart(createPlot(createDataset())), "Spectrum Chart");
	}

	public static SpectrumDataset createDataset() {
		SpectrumDataset dataset = new SpectrumDataset("Spectrum Chart", 200, 200);
		for (int row = 0; row < 200; row++) {
			for (int col = 0; col < 200; col++) {
				dataset.put(row, col, new SpectrumMatrixData(Math.random()));
			}
		}
		return dataset;
	}

	public static SpectrumPlot createPlot(final SpectrumDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createSpectrumChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		SpectrumPlot plot = (SpectrumPlot) chart.getPlot();

		SpectrumAxis axis = plot.getAxis();
		axis.setMinimumValueAutoFit(false);
		axis.setMinimumValue(0.0);
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(1.0);
		axis.setDisplayFormat(new NumericDisplayFormat(0));

		return plot;
	}
}
