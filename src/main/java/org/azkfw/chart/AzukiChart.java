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

import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.bar.BarYAxis;
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
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.charts.scatter.ScatterSeries;
import org.azkfw.chart.charts.scatter.ScatterXAxis;
import org.azkfw.chart.charts.scatter.ScatterYAxis;
import org.azkfw.chart.displayformat.NumericDisplayFormat;
import org.azkfw.chart.plot.Plot;
import org.azkfw.chart.util.AzukiChartUtility;
import org.azkfw.graphics.AzukiGraphics2D;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;

/**
 * このクラスは、チャートクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public final class AzukiChart {

	/**
	 * プロット情報
	 */
	private Plot plot;

	/**
	 * 背景色
	 */
	private Color backgroundColor;

	/**
	 * コンストラクタ
	 * 
	 * @param aPlot プロット情報
	 */
	public AzukiChart(final Plot aPlot) {
		plot = aPlot;
	}

	/**
	 * 背景色を設定する。
	 * 
	 * @param aColor 背景色
	 */
	public void setBackgoundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	/**
	 * プロット情報を取得する。
	 * 
	 * @return プロット情報
	 */
	public Plot getPlot() {
		return plot;
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param x グラフ描画X座標
	 * @param y グラフ描画Y座標
	 * @param width グラフ描画横幅
	 * @param height グラフ描画縦幅
	 * @return 結果
	 */
	public boolean draw(final Graphics2D g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		Graphics graphics = new AzukiGraphics2D(g);
		result = draw(graphics, x, y, width, height);
		return result;
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param x グラフ描画X座標
	 * @param y グラフ描画Y座標
	 * @param width グラフ描画横幅
	 * @param height グラフ描画縦幅
	 * @return 結果
	 */
	public boolean draw(final Graphics g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		if (null != plot) {
			if (null != backgroundColor) {
				g.setColor(backgroundColor);
				g.fillRect(x, y, width, height);
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

		// createScatterChart(new File(args[0]));
		// createPieChart(new File(args[0]));
		// createRadarChart(new File(args[0]));

		 createPolarChart(new File(args[0]));
		
		// createPolarAreaChart(new File(args[0]));
		// createBarChart(new File(args[0]));
	}

	public static void createScatterChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createScatterChart();
		chart.setBackgoundColor(Color.WHITE);

		ScatterPlot plot = (ScatterPlot) chart.getPlot();
		//plot.setMargin(new Margin(50.f, 5.f, 5.f, 5.f));

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

		ScatterDataset dataset = new ScatterDataset("SmpaleChart (Scatter)");
		ScatterSeries seriesSin = new ScatterSeries("Sine");
		ScatterSeries seriesCos = new ScatterSeries("Cosine");
		for (int i = 0; i <= 360; i += 10) {
			seriesSin.add(i, Math.sin(RADIANS(i)));
			seriesCos.add(i, Math.cos(RADIANS(i)));
		}
		dataset.addSeries(seriesSin);
		dataset.addSeries(seriesCos);
		plot.setDataset(dataset);

		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 1200, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPieChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPieChart();
		chart.setBackgoundColor(Color.WHITE);

		PiePlot plot = (PiePlot) chart.getPlot();
		//plot.setMargin(new Margin(50.f, 5.f, 5.f, 5.f));

		PieDataset dataset = new PieDataset("SmpaleChart (Pie)");
		dataset.addData(new PieData("Japan", 50.f));
		dataset.addData(new PieData("America", 30.f));
		dataset.addData(new PieData("England", 15.f));
		dataset.addData(new PieData("Germany", 5.f));
		plot.setDataset(dataset);

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
		//plot.setMargin(new Margin(50.f, 5.f, 5.f, 5.f));

		RadarAxis axis = plot.getAxis();
		axis.setMinumumValueAutoFit(false);
		axis.setMinimumValue(0.0);
		axis.setMaximumValueAutoFit(false);
		axis.setMaximumValue(100.0);
		axis.setDisplayFormat(new NumericDisplayFormat(0));

		RadarDataset dataset = new RadarDataset("SmpaleChart (Radar)");
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
		//plot.setMargin(new Margin(50.f, 5.f, 5.f, 5.f));

		PolarAxis axis = plot.getAxis();
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		PolarDataset dataset = new PolarDataset("SmpaleChart (Polar)");
		PolarSeries series1 = new PolarSeries("aaa");
		series1.add(0, 1.0);
		series1.add(30, 0.1);
		series1.add(90, 0.5);
		series1.add(180, 0.6);
		series1.add(270, 0.6);
		dataset.addSeries(series1);

		PolarSeries series2 = new PolarSeries("bbb");
		for (int i = 0; i < 36; i++) {
			series2.add(i * 10, i * 0.02);
		}
		dataset.addSeries(series2);

		PolarSeries series3 = new PolarSeries("ccc");
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

	public static void createBarChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createBarChart();
		chart.setBackgoundColor(Color.WHITE);

		BarPlot plot = (BarPlot) chart.getPlot();
		plot.setMargin(new Margin(50.f, 5.f, 5.f, 5.f));

		BarYAxis yAxis = plot.getYAxis();
		yAxis.setDisplayFormat(new NumericDisplayFormat(2));

		BarDataset dataset = new BarDataset();

		plot.setDataset(dataset);
		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 1200, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPolarAreaChart(final File aFile) {
		AzukiChart chart = AzukiChartFactory.createPolarAreaChart();
		chart.setBackgoundColor(Color.WHITE);

		PolarAreaPlot plot = (PolarAreaPlot) chart.getPlot();

		PolarAreaAxis axis = plot.getAxis();
		axis.setDisplayFormat(new NumericDisplayFormat(2));

		PolarAreaDataset dataset = new PolarAreaDataset();

		PolarAreaSeries series1 = new PolarAreaSeries();
		series1.add(1.6);
		series1.add(0.1);
		series1.add(0.5);
		series1.add(0.6);
		series1.add(0.6);
		series1.add(0.3);
		dataset.addSeries(series1);

		PolarAreaSeries series2 = new PolarAreaSeries();
		for (int i = 0; i < 6; i++) {
			series2.add(i * 0.1);
		}
		dataset.addSeries(series2);

		PolarAreaSeries series3 = new PolarAreaSeries();
		for (int i = 0; i < 6; i++) {
			series3.add(0.85);
		}
		dataset.addSeries(series3);

		plot.setDataset(dataset);
		try {
			AzukiChartUtility.saveChartAsPNG(aFile, chart, 800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
