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
import java.util.List;

import org.azkfw.chart.charts.pie.PieChartDesign.PieChartStyle;
import org.azkfw.chart.core.element.LegendElement;
import org.azkfw.chart.core.element.PieLegendElement;
import org.azkfw.chart.core.plot.AbstractChartPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、円グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PieChartPlot extends AbstractChartPlot<PieDataset, PieChartDesign> {

	/** 軸情報 */
	private PieAxis axis;

	/**
	 * コンストラクタ
	 */
	public PieChartPlot() {
		super(PieChartPlot.class);

		axis = new PieAxis();

		setChartDesign(PieChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public PieChartPlot(final PieDataset aDataset) {
		super(PieChartPlot.class, aDataset);

		axis = new PieAxis();

		setChartDesign(PieChartDesign.DefalutDesign);
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public PieAxis getAxis() {
		return axis;
	}

	@Override
	protected LegendElement createLegendElement() {
		LegendElement element = new PieLegendElement(getDataset(), getChartDesign());
		return element;
	}

	@Override
	protected boolean doDrawChart(final Graphics g, final Rect aRect) {
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		PieChartStyle style = design.getChartStyle();

		float pieSize = Math.min(aRect.getWidth(), aRect.getHeight());
		Point ptChartMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));

		Rect rtChart = new Rect();
		rtChart.setX(ptChartMiddle.getX() - (pieSize / 2));
		rtChart.setY(ptChartMiddle.getY() - (pieSize / 2));
		rtChart.setWidth(pieSize);
		rtChart.setHeight(pieSize);

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillArc(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight(), 0, 360);
		}

		// Draw dataset
		drawDataset(g, dataset, style, rtChart);

		return true;
	}

	private void drawDataset(final Graphics g, final PieDataset aDataset, final PieChartStyle aStyle, final Rect aRect) {
		Point ptMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));

		if (ObjectUtility.isNotNull(aDataset)) {
			List<PieData> dataList = aDataset.getDataList();

			double totalValue = 0.f;
			for (PieData data : dataList) {
				totalValue += data.getValue();
			}

			int angle = 90;
			for (int index = 0; index < dataList.size(); index++) {
				PieData data = dataList.get(index);
				float acrAngle = (float) (-1 * 360.f * data.getValue() / totalValue);

				Color fillColor = aStyle.getDataFillColor(index);
				if (ObjectUtility.isNotNull(fillColor)) {
					g.setColor(fillColor);
					g.fillArc(ptMiddle.getX() - (aRect.getWidth() / 2.f), ptMiddle.getY() - (aRect.getHeight() / 2.f), aRect.getWidth(),
							aRect.getHeight(), angle, acrAngle);
				}

				angle += acrAngle;
			}

			angle = 90;
			for (int index = 0; index < dataList.size(); index++) {
				PieData data = dataList.get(index);
				float acrAngle = (float) (-1 * 360.f * data.getValue() / totalValue);

				Color strokeColor = aStyle.getDataStrokeColor(index);
				if (ObjectUtility.isNotNull(strokeColor)) {
					g.setColor(strokeColor);
					g.drawArc(ptMiddle.getX() - (aRect.getWidth() / 2.f), ptMiddle.getY() - (aRect.getHeight() / 2.f), aRect.getWidth(),
							aRect.getHeight(), angle, acrAngle);
				}

				angle += acrAngle;
			}
		}
	}
}
