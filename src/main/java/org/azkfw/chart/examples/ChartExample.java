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
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

import org.azkfw.chart.AzukiChartFactory;
import org.azkfw.chart.AzukiChartGraphics;
import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.charts.bar.BarPlot;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.charts.pie.PiePlot;
import org.azkfw.chart.charts.polar.PolarDataset;
import org.azkfw.chart.charts.polar.PolarPlot;
import org.azkfw.chart.charts.polararea.PolarAreaDataset;
import org.azkfw.chart.charts.polararea.PolarAreaPlot;
import org.azkfw.chart.charts.radar.RadarDataset;
import org.azkfw.chart.charts.radar.RadarPlot;
import org.azkfw.chart.charts.scatter.ScatterDataset;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.charts.spectrum.SpectrumDataset;
import org.azkfw.chart.charts.spectrum.SpectrumPlot;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、全てのグラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author Kawakicchi
 */
public class ChartExample {

	public static void main2(final String[] args) {
		ChartExample example = new ChartExample();
		example.gui();
	}

	public static void main(final String[] args) {
		File file = new File("sample.png");
		if (1 == args.length) {
			file = new File(args[0]);
		}

		//createAllChartNoData(file);
		//createAllChartData(file);
		createAllChart(file);
	}

	public ChartExample() {

	}

	public void gui() {
		ChartExampleFrame frame = new ChartExampleFrame();
		frame.setVisible(true);
	}

	private class ChartExampleFrame extends JFrame {

		/** serialVersionUID */
		private static final long serialVersionUID = 4377734213354917728L;

		private JSplitPane split;

		public ChartExampleFrame() {
			setTitle("Chart example");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setSize(800, 600);
			setLayout(null);

			split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			split.setLocation(0, 0);
			add(split);

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(final ComponentEvent event) {
					Insets insets = getInsets();
					int width = getWidth() - (insets.left + insets.right);
					int height = getHeight() - (insets.top + insets.bottom);

					split.setSize(width, height);
				}
			});
		}
	}

	public static void createAllChartNoData(final File aFile) {
		try {
			BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1400, 1200);

			AzukiChartGraphics gra = new AzukiChartGraphics(g);
			gra.drawLineChart((LinePlot) AzukiChartFactory.createLineChart().getPlot(), new Rect(0, 0, 600, 400));
			gra.drawBarChart((BarPlot) AzukiChartFactory.createBarChart().getPlot(), new Rect(0, 400, 600, 400));
			gra.drawScatterChart((ScatterPlot) AzukiChartFactory.createScatterChart().getPlot(), new Rect(0, 800, 600, 400));
			gra.drawPolarChart((PolarPlot) AzukiChartFactory.createPolarChart().getPlot(), new Rect(600, 0, 400, 400));
			gra.drawPolarAreaChart((PolarAreaPlot) AzukiChartFactory.createPolarAreaChart().getPlot(), new Rect(600, 400, 400, 400));
			gra.drawPieChart((PiePlot) AzukiChartFactory.createPieChart().getPlot(), new Rect(600, 800, 400, 400));
			gra.drawRadarChart((RadarPlot) AzukiChartFactory.createRadarChart().getPlot(), new Rect(1000, 0, 400, 400));
			gra.drawSpectrumChart((SpectrumPlot) AzukiChartFactory.createSpectrumChart().getPlot(), new Rect(1000, 400, 400, 400));

			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createAllChartData(final File aFile) {
		LineDataset dsLine = LineChartExample.createDataset();
		BarDataset dsBar = BarChartExample.createDataset();
		ScatterDataset dsScatter = ScatterChartExample.createDataset();
		PolarDataset dsPolar = PolarChartExample.createDataset();
		PolarAreaDataset dsPolarArea = PolarAreaChartExample.createDataset();
		PieDataset dsPie = PieChartExample.createDataset();
		RadarDataset dsRadar = RadarChartExample.createDataset();
		SpectrumDataset dsSpectrum = SpectrumChartExample.createDataset();

		try {
			BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1400, 1200);

			AzukiChartGraphics gra = new AzukiChartGraphics(g);
			gra.drawLineChart((LinePlot) AzukiChartFactory.createLineChart(dsLine).getPlot(), new Rect(0, 0, 600, 400));
			gra.drawBarChart((BarPlot) AzukiChartFactory.createBarChart(dsBar).getPlot(), new Rect(0, 400, 600, 400));
			gra.drawScatterChart((ScatterPlot) AzukiChartFactory.createScatterChart(dsScatter).getPlot(), new Rect(0, 800, 600, 400));
			gra.drawPolarChart((PolarPlot) AzukiChartFactory.createPolarChart(dsPolar).getPlot(), new Rect(600, 0, 400, 400));
			gra.drawPolarAreaChart((PolarAreaPlot) AzukiChartFactory.createPolarAreaChart(dsPolarArea).getPlot(), new Rect(600, 400, 400, 400));
			gra.drawPieChart((PiePlot) AzukiChartFactory.createPieChart(dsPie).getPlot(), new Rect(600, 800, 400, 400));
			gra.drawRadarChart((RadarPlot) AzukiChartFactory.createRadarChart(dsRadar).getPlot(), new Rect(1000, 0, 400, 400));
			gra.drawSpectrumChart((SpectrumPlot) AzukiChartFactory.createSpectrumChart(dsSpectrum).getPlot(), new Rect(1000, 400, 400, 400));

			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createAllChart(final File aFile) {
		LineDataset dsLine = LineChartExample.createDataset();
		BarDataset dsBar = BarChartExample.createDataset();
		ScatterDataset dsScatter = ScatterChartExample.createDataset();
		PolarDataset dsPolar = PolarChartExample.createDataset();
		PolarAreaDataset dsPolarArea = PolarAreaChartExample.createDataset();
		PieDataset dsPie = PieChartExample.createDataset();
		RadarDataset dsRadar = RadarChartExample.createDataset();
		SpectrumDataset dsSpectrum = SpectrumChartExample.createDataset();

		try {
			BufferedImage image = new BufferedImage(1400, 1200, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1400, 1200);

			AzukiChartGraphics gra = new AzukiChartGraphics(g);
			gra.drawLineChart(LineChartExample.createPlot(dsLine), new Rect(0, 0, 600, 400));
			gra.drawBarChart(BarChartExample.createPlot(dsBar), new Rect(0, 400, 600, 400));
			gra.drawScatterChart(ScatterChartExample.createPlot(dsScatter), new Rect(0, 800, 600, 400));
			gra.drawPolarChart(PolarChartExample.createPlot(dsPolar), new Rect(600, 0, 400, 400));
			gra.drawPolarAreaChart(PolarAreaChartExample.createPlot(dsPolarArea), new Rect(600, 400, 400, 400));
			gra.drawPieChart(PieChartExample.createPlot(dsPie), new Rect(600, 800, 400, 400));
			gra.drawRadarChart(RadarChartExample.createPlot(dsRadar), new Rect(1000, 0, 400, 400));
			gra.drawSpectrumChart(SpectrumChartExample.createPlot(dsSpectrum), new Rect(1000, 400, 400, 400));

			ImageIO.write(image, "png", aFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
