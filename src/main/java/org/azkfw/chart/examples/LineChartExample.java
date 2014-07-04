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

import java.io.File;
import java.io.IOException;

import org.azkfw.chart.AzukiChart;
import org.azkfw.chart.AzukiChartFactory;
import org.azkfw.chart.charts.line.LineAxis.LineHorizontalAxis;
import org.azkfw.chart.charts.line.LineAxis.LineVerticalAxis;
import org.azkfw.chart.charts.line.LineDataset;
import org.azkfw.chart.charts.line.LinePlot;
import org.azkfw.chart.charts.line.LineSeries;
import org.azkfw.chart.displayformat.DayDisplayFormat;
import org.azkfw.chart.displayformat.TimeDisplayFormat;
import org.azkfw.chart.util.AzukiChartUtility;
import org.azkfw.util.PathUtility;

/**
 * このクラスは、折れ線グラフの実例を行うクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/03
 * @author Kawakicchi
 */
public class LineChartExample {

	public static void main(final String[] args) {
		File file = new File(PathUtility.cat("./target", "LineChart.png"));
		test(file);
	}

	public static void test(final File aFile) {
		try {
			AzukiChart chart = AzukiChartFactory.createLineChart();

			LinePlot plot = (LinePlot) chart.getPlot();

			LineHorizontalAxis xAxis = plot.getHorizontalAxis();
			xAxis.setDisplayFormat(new DayDisplayFormat());
			LineVerticalAxis yAxis = plot.getVerticalAxis();
			//			yAxis.setDisplayFormat(new ByteDisplayFormat(2));
			yAxis.setDisplayFormat(new TimeDisplayFormat(2));

			LineDataset dataset = new LineDataset("Disk IO");
			LineSeries readSeries = new LineSeries("Read");
			LineSeries writeSeries = new LineSeries("Write");
			double readSize = (24 * 60 * 60) + (60 * 60 * 12) + (60 * 30) + 30.f;
			double writeSize = 0;
			for (int i = 0; i < 24; i++) {
				//				readSize += (Math.random() * 2048.f )- 1024.f;
				//				writeSize += (Math.random() * 2048.f )- 1024.f;

				readSeries.add(readSize);
				writeSeries.add(readSize);
			}
			dataset.addSeries(readSeries);
			dataset.addSeries(writeSeries);

			plot.setDataset(dataset);

			AzukiChartUtility.saveChartAsPNG(aFile, chart, 800, 600);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
