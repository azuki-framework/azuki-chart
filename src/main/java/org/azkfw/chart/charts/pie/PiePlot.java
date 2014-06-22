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
package org.azkfw.chart.charts.pie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Size;

/**
 * このクラスは、円グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PiePlot extends AbstractPlot {

	/** データセット */
	private PieDataset dataset;

	/** Looks */
	private PieLooks looks;

	public PiePlot() {
		dataset = null;
		looks = new PieLooks();
	}

	public void setLooks(final PieLooks aLooks) {
		looks = aLooks;
	}

	public void setDataset(final PieDataset aDataset) {
		dataset = aDataset;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		Size szChart = new Size(aWidth, aHeight);
		Point ptChartMiddle = new Point(aX + (aWidth / 2.f), aY + (aHeight / 2.f));

		List<PieData> dataList = dataset.getDataList();

		double totalValue = 0.f;
		for (PieData data : dataList) {
			totalValue += data.getValue();
		}

		int angle = 90;
		for (int index = 0; index < dataList.size(); index++) {
			PieData data = dataList.get(index);
			Color fillColor = looks.getDataFillColor(index);
			int size = (int) (-1 * 360.f * data.getValue() / totalValue);
			g.setColor(fillColor);
			g.fillArc((int) (ptChartMiddle.getX() - (szChart.getWidth() / 2.f)), (int) (ptChartMiddle.getY() - (szChart.getHeight() / 2.f)),
					(int) (szChart.getWidth()), (int) (szChart.getHeight()), angle, size);
			angle += size;
		}
		angle = 90;
		for (int index = 0; index < dataList.size(); index++) {
			PieData data = dataList.get(index);
			Color strokeColor = looks.getDataStrokeColor(index);
			int size = (int) (-1 * 360.f * data.getValue() / totalValue);
			g.setColor(strokeColor);
			g.drawArc((int) (ptChartMiddle.getX() - (szChart.getWidth() / 2.f)), (int) (ptChartMiddle.getY() - (szChart.getHeight() / 2.f)),
					(int) (szChart.getWidth()), (int) (szChart.getHeight()), angle, size);
			angle += size;
		}

		return true;
	}

}
