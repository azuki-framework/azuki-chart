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
package org.azkfw.chart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.pie.PieData;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarAxis;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polar.PolarSeries;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.plot.Plot;
import org.azkfw.chart.util.AzukiChartUtility;

/**
 * このクラスは、チャートクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public final class AzukiChart {

	private Plot plot;

	private Color backgroundColor;

	public AzukiChart(final Plot aPlot) {
		plot = aPlot;
	}

	public void setBackgoundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	public Plot getPlot() {
		return plot;
	}

	public boolean draw(final Graphics2D g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		if (null != plot) {
			if (null != backgroundColor) {
				g.setColor(backgroundColor);
				g.fillRect((int) (x), (int) (y), (int) (width), (int) (height));
			}
			result = plot.draw(g, x, y, width, height);
		}
		return result;
	}

	public static void main(final String[] args) {
		if (1 != args.length) {
			System.out.println("argument error.");
			return;
		}

		createPieChart(new File(args[0]));
		//createPolarChart(new File(args[0]));
	}

	public static void createPieChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPieChart();
		chart.setBackgoundColor(Color.WHITE);
		
		PiePlot plot = (PiePlot) chart.getPlot();

		PieDataset dataset = new PieDataset();
		
		dataset.addData(new PieData("", 50.f));
		dataset.addData(new PieData("", 30.f));
		dataset.addData(new PieData("", 15.f));
		dataset.addData(new PieData("", 5.f));

		plot.setDataset(dataset);
		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static void createPolarChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPolarChart();
		chart.setBackgoundColor(Color.WHITE);

		PolarPlot plot = (PolarPlot) chart.getPlot();

		PolarAxis axis = plot.getAxis();
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		PolarDataset dataset = new PolarDataset();

		PolarSeries series1 = new PolarSeries();
		series1.add(0, 1.0);
		series1.add(30, 0.1);
		series1.add(90, 0.5);
		series1.add(180, 0.6);
		series1.add(270, 0.6);
		dataset.addSeries(series1);

		PolarSeries series2 = new PolarSeries();
		for (int i = 0; i < 36; i++) {
			series2.add(i * 10, i * 0.02);
		}
		dataset.addSeries(series2);

		PolarSeries series3 = new PolarSeries();
		for (int i = 0; i < 360; i++) {
			series3.add(i, 0.85);
		}
		dataset.addSeries(series3);

		plot.setDataset(dataset);
		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
