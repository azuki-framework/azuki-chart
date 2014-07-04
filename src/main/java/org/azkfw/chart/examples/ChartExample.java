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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.azkfw.chart.AzukiChart;
import org.azkfw.chart.AzukiChartFactory;
import org.azkfw.chart.AzukiChartGraphics;
import org.azkfw.chart.charts.bar.BarAxis.BarHorizontalAxis;
import org.azkfw.chart.charts.bar.BarAxis.BarVerticalAxis;
import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.bar.BarSeries;
import org.azkfw.chart.charts.line.LineAxis.LineHorizontalAxis;
import org.azkfw.chart.charts.line.LineAxis.LineVerticalAxis;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.line.LineSeries;
import org.azkfw.chart.charts.pie.PieData;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarAxis;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polar.PolarSeries;
import org.azkfw.chart.charts.polararea.PolarAreaAxis;
import org.azkfw.chart.charts.polararea.PolarAreaDataset;
import org.azkfw.chart.charts.polararea.PolarAreaPlot;
import org.azkfw.chart.charts.polararea.PolarAreaSeries;
import org.azkfw.chart.charts.radar.RadarAxis;
import org.azkfw.chart.charts.radar.RadarDataset;
import org.azkfw.chart.charts.radar.RadarPlot;
import org.azkfw.chart.charts.radar.RadarSeries;
import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterXAxis;
import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterYAxis;
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.charts.scatter.ScatterSeries;
import org.azkfw.chart.displayformat.MonthDisplayFormat;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、折れ線グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author Kawakicchi
 */
public class ChartExample {

	public static void main(final String[] args) {
		if (1 != args.length) {
			System.out.println("argument error.");
			return;
		}

		// createBarChart(new File(args[0]));
		// createScatterChart(new File(args[0]));
		// createPieChart(new File(args[0]));

		// createRadarChart(new File(args[0]));
		// createPolarChart(new File(args[0]));
		//createPolarAreaChart(new File(args[0]));

		//createAllChartNoData(new File(args[0]));
		createAllChartData(new File(args[0]));
		//createAllChart(new File(args[0]));
	}

	public static void createAllChartNoData(final File aFile) {
		BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1400, 1200);

		AzukiChartGraphics gra = new AzukiChartGraphics(g);

		LineDataset dsLine = null;
		BarDataset dsBar = null;
		ScatterDataset dsScatter = null;
		PolarDataset dsPolar = null;
		PolarAreaDataset dsPolarArea = null;
		PieDataset dsPie = null;
		RadarDataset dsRadar = null;
		gra.drawLineChart((LinePlot) AzukiChartFactory.createLineChart(dsLine).getPlot(), new Rect(0, 0, 600, 400));
		gra.drawBarChart((BarPlot) AzukiChartFactory.createBarChart(dsBar).getPlot(), new Rect(0, 400, 600, 400));
		gra.drawScatterChart((ScatterPlot) AzukiChartFactory.createScatterChart(dsScatter).getPlot(), new Rect(0, 800, 600, 400));
		gra.drawPolarChart((PolarPlot) AzukiChartFactory.createPolarChart(dsPolar).getPlot(), new Rect(600, 0, 400, 400));
		gra.drawPolarAreaChart((PolarAreaPlot) AzukiChartFactory.createPolarAreaChart(dsPolarArea).getPlot(), new Rect(600, 400, 400, 400));
		gra.drawPieChart((PiePlot) AzukiChartFactory.createPieChart(dsPie).getPlot(), new Rect(600, 800, 400, 400));
		gra.drawRadarChart((RadarPlot) AzukiChartFactory.createRadarChart(dsRadar).getPlot(), new Rect(1000, 0, 400, 400));

		try {
			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createAllChartData(final File aFile) {
		BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1400, 1200);

		AzukiChartGraphics gra = new AzukiChartGraphics(g);

		LineDataset dsLine = createLineDataset();
		BarDataset dsBar = createBarDataset();
		ScatterDataset dsScatter = createScatterDataset();
		PolarDataset dsPolar = createPolarDataset();
		PolarAreaDataset dsPolarArea = createPolarAreaDataset();
		PieDataset dsPie = createPieDataset();
		RadarDataset dsRadar = createRadarDataset();
		gra.drawLineChart((LinePlot) AzukiChartFactory.createLineChart(dsLine).getPlot(), new Rect(0, 0, 600, 400));
		gra.drawBarChart((BarPlot) AzukiChartFactory.createBarChart(dsBar).getPlot(), new Rect(0, 400, 600, 400));
		gra.drawScatterChart((ScatterPlot) AzukiChartFactory.createScatterChart(dsScatter).getPlot(), new Rect(0, 800, 600, 400));
		gra.drawPolarChart((PolarPlot) AzukiChartFactory.createPolarChart(dsPolar).getPlot(), new Rect(600, 0, 400, 400));
		gra.drawPolarAreaChart((PolarAreaPlot) AzukiChartFactory.createPolarAreaChart(dsPolarArea).getPlot(), new Rect(600, 400, 400, 400));
		gra.drawPieChart((PiePlot) AzukiChartFactory.createPieChart(dsPie).getPlot(), new Rect(600, 800, 400, 400));
		gra.drawRadarChart((RadarPlot) AzukiChartFactory.createRadarChart(dsRadar).getPlot(), new Rect(1000, 0, 400, 400));

		try {
			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createAllChart(final File aFile) {
		BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1400, 1200);

		AzukiChartGraphics gra = new AzukiChartGraphics(g);

		LineDataset dsLine = createLineDataset();
		BarDataset dsBar = createBarDataset();
		ScatterDataset dsScatter = createScatterDataset();
		PolarDataset dsPolar = createPolarDataset();
		PolarAreaDataset dsPolarArea = createPolarAreaDataset();
		PieDataset dsPie = createPieDataset();
		RadarDataset dsRadar = createRadarDataset();
		gra.drawLineChart(createLinePlot(dsLine), new Rect(0, 0, 600, 400));
		gra.drawBarChart(createBarPlot(dsBar), new Rect(0, 400, 600, 400));
		gra.drawScatterChart(createScatterPlot(dsScatter), new Rect(0, 800, 600, 400));
		gra.drawPolarChart(createPolarPlot(dsPolar), new Rect(600, 0, 400, 400));
		gra.drawPolarAreaChart(createPolarAreaPlot(dsPolarArea), new Rect(600, 400, 400, 400));
		gra.drawPieChart(createPiePlot(dsPie), new Rect(600, 800, 400, 400));
		gra.drawRadarChart(createRadarPlot(dsRadar), new Rect(1000, 0, 400, 400));

		try {
			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static LinePlot createLinePlot(final LineDataset aDataset) {
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

	private static BarPlot createBarPlot(final BarDataset aDataset) {
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

	private static ScatterPlot createScatterPlot(final ScatterDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createScatterChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		ScatterPlot plot = (ScatterPlot) chart.getPlot();
		//plot.setChartDesign(ScatterChartDesign.DarkDesign);

		ScatterXAxis xAxis = plot.getXAxis();
		xAxis.setDisplayFormat(new NumericDisplayFormat(0));
		xAxis.setScaleAutoFit(false);
		xAxis.setScale(45);

		ScatterYAxis yAxis = plot.getYAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(-1.5);
		yAxis.setMaximumValueAutoFit(false);
		yAxis.setMaximumValue(1.5);

		return plot;
	}

	private static PiePlot createPiePlot(final PieDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPieChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PiePlot plot = (PiePlot) chart.getPlot();

		return plot;
	}

	private static RadarPlot createRadarPlot(final RadarDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createRadarChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		RadarPlot plot = (RadarPlot) chart.getPlot();

		RadarAxis axis = plot.getAxis();
		axis.setMinimumValueAutoFit(false);
		axis.setMinimumValue(0.0);
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(100.0);
		axis.setDisplayFormat(new NumericDisplayFormat(0));

		return plot;
	}

	private static PolarPlot createPolarPlot(final PolarDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPolarChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PolarPlot plot = (PolarPlot) chart.getPlot();

		PolarAxis axis = plot.getAxis();
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(1.2);
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		return plot;
	}

	private static PolarAreaPlot createPolarAreaPlot(final PolarAreaDataset aDataset) {
		AzukiChart chart = AzukiChartFactory.createPolarAreaChart(aDataset);
		chart.setBackgoundColor(Color.WHITE);

		PolarAreaPlot plot = (PolarAreaPlot) chart.getPlot();

		PolarAreaAxis axis = plot.getAxis();
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(1.5);
		axis.setDisplayFormat(new NumericDisplayFormat(1));

		return plot;
	}

	private static LineDataset createLineDataset() {
		LineDataset dataset = new LineDataset("Smpale Line Chart");

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

	private static BarDataset createBarDataset() {
		BarDataset dataset = new BarDataset("Smpale Bar Chart");

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

	private static ScatterDataset createScatterDataset() {
		ScatterDataset dataset = new ScatterDataset("Smpale Scatter Chart");

		ScatterSeries seriesSin = new ScatterSeries("Sine");
		ScatterSeries seriesCos = new ScatterSeries("Cosine");
		for (int i = 0; i <= 360; i += 10) {
			seriesSin.add(i, Math.sin(RADIANS(i)));
			seriesCos.add(i, Math.cos(RADIANS(i)));
		}
		dataset.addSeries(seriesSin);
		dataset.addSeries(seriesCos);

		return dataset;
	}

	private static PieDataset createPieDataset() {
		PieDataset dataset = new PieDataset("Smpale Pie Chart");

		dataset.addData(new PieData("Japan", 50.f));
		dataset.addData(new PieData("America", 30.f));
		dataset.addData(new PieData("England", 15.f));
		dataset.addData(new PieData("Germany", 5.f));

		return dataset;
	}

	private static RadarDataset createRadarDataset() {
		RadarDataset dataset = new RadarDataset("Smpale Radar Chart");

		RadarSeries series1 = new RadarSeries("Border");
		series1.add(75);
		series1.add(75);
		series1.add(75);
		series1.add(75);
		series1.add(75);
		dataset.addSeries(series1);

		RadarSeries series2 = new RadarSeries("Average");
		series2.add(80);
		series2.add(60);
		series2.add(75);
		series2.add(50);
		series2.add(95);
		dataset.addSeries(series2);

		RadarSeries series3 = new RadarSeries("Data 1");
		series3.add(100);
		series3.add(30);
		series3.add(75);
		series3.add(80);
		series3.add(90);
		dataset.addSeries(series3);

		return dataset;
	}

	private static PolarDataset createPolarDataset() {
		PolarDataset dataset = new PolarDataset("Smpale Polar Chart");

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

	private static PolarAreaDataset createPolarAreaDataset() {
		PolarAreaDataset dataset = new PolarAreaDataset("Smpale PolarArea Chart");

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

	private static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}

	public static void createBarChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createBarChart();
		chart.setBackgoundColor(Color.WHITE);

		BarPlot plot = (BarPlot) chart.getPlot();

		BarVerticalAxis yAxis = plot.getVerticalAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(0.f);
		yAxis.setMaximumValueAutoFit(false);
		yAxis.setMaximumValue(150.f);
		BarHorizontalAxis xAxis = plot.getHorizontalAxis();
		xAxis.setDisplayFormat(new MonthDisplayFormat());

		plot.setDataset(createBarDataset());

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 1200, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createScatterChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createScatterChart();
		chart.setBackgoundColor(Color.WHITE);

		ScatterPlot plot = (ScatterPlot) chart.getPlot();
		//plot.setChartDesign(ScatterChartDesign.DarkDesign);

		ScatterXAxis xAxis = plot.getXAxis();
		xAxis.setDisplayFormat(new NumericDisplayFormat(0));
		xAxis.setScaleAutoFit(false);
		xAxis.setScale(45);

		ScatterYAxis yAxis = plot.getYAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));
		yAxis.setMinimumValueAutoFit(false);
		yAxis.setMinimumValue(-1.5);
		yAxis.setMaximumValueAutoFit(false);
		yAxis.setMaximumValue(1.5);

		plot.setDataset(createScatterDataset());

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 1200, 800);
			// AzukiChartUtility.saveChartAsPNG(aFile, chart, 480, 320);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPieChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPieChart();
		chart.setBackgoundColor(Color.WHITE);

		PiePlot plot = (PiePlot) chart.getPlot();

		plot.setDataset(createPieDataset());

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createRadarChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createRadarChart();
		chart.setBackgoundColor(Color.WHITE);

		RadarPlot plot = (RadarPlot) chart.getPlot();

		RadarAxis axis = plot.getAxis();
		axis.setMinimumValueAutoFit(false);
		axis.setMinimumValue(0.0);
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(100.0);
		axis.setDisplayFormat(new NumericDisplayFormat(0));

		plot.setDataset(createRadarDataset());

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
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(0.6);
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		plot.setDataset(createPolarDataset());

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 600, 600);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPolarAreaChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPolarAreaChart();
		chart.setBackgoundColor(Color.WHITE);

		PolarAreaPlot plot = (PolarAreaPlot) chart.getPlot();

		PolarAreaAxis axis = plot.getAxis();
		//axis.setMaximumValueAutoFit(false);
		//axis.setMaximumValue(1.5);
		axis.setDisplayFormat(new NumericDisplayFormat(1));

		plot.setDataset(createPolarAreaDataset());

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 600, 600);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
