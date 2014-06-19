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

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

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

	public AzukiChart(final Plot aPlot) {
		plot = aPlot;
	}

	public Plot getPlot() {
		return plot;
	}

	public boolean draw(final Graphics2D g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		if (null != plot) {
			result = plot.draw(g, x, y, width, height);
		}
		return result;
	}

	public static void main(final String[] args) {

		AzukiChart chart = AzukiChartFactory.createPolarChart();

		try {
			AzukiChartUtility.saveChartAsPNG(new File("PolarChart.png"), chart, 800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
