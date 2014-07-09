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
package org.azkfw.chart.charts.spectrum;

import java.awt.Color;

import org.azkfw.chart.charts.spectrum.SpectrumChartDesign.SpectrumChartStyle;
import org.azkfw.chart.plot.AbstractMatrixPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、スペクトログラムのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
public class SpectrumPlot extends AbstractMatrixPlot<SpectrumDataset, SpectrumChartDesign> {

	/** 軸情報 */
	private SpectrumAxis axis;

	/**
	 * コンストラクタ
	 */
	public SpectrumPlot() {
		super(SpectrumPlot.class);

		axis = new SpectrumAxis();

		setChartDesign(SpectrumChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public SpectrumPlot(final SpectrumDataset aDataset) {
		super(SpectrumPlot.class, aDataset);

		axis = new SpectrumAxis();

		setChartDesign(SpectrumChartDesign.DefalutDesign);
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public SpectrumAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		SpectrumDataset dataset = getDataset();
		SpectrumChartDesign design = getChartDesign();
		SpectrumChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);

		// スケール調整
		ScaleValue scaleValue = getScaleValue();

		float pieSize = Math.min(rtChartPre.getWidth(), rtChartPre.getHeight());
		Point ptChartMiddle = new Point(rtChartPre.getX() + (rtChartPre.getWidth() / 2.f), rtChartPre.getY() + (rtChartPre.getHeight() / 2.f));

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
		drawDataset(g, dataset, scaleValue, style, rtChart);

		// Draw Legend
		if (ObjectUtility.isNotNull(rtLegend)) {
			drawLegend(g, design.getLegendStyle(), rtLegend);
		}
		// Draw title
		if (ObjectUtility.isNotNull(rtTitle)) {
			drawTitle(g, rtTitle);
		}

		return true;
	}

	private void drawDataset(final Graphics g, final SpectrumDataset aDataset, final ScaleValue aScaleValue, final SpectrumChartStyle aStyle,
			final Rect aRect) {
		if (ObjectUtility.isNotNull(aDataset)) {

			double pixXPerValue = (aRect.getWidth()) / aDataset.getColSize();
			double pixYPerValue = (aRect.getHeight()) / aDataset.getRowSize();
			int width = (int) Math.ceil(pixXPerValue);
			int height = (int) Math.ceil(pixYPerValue);

			for (int row = 0; row < aDataset.getRowSize(); row++) {
				for (int col = 0; col < aDataset.getColSize(); col++) {

					float x = (float) (aRect.getX() + (col * pixXPerValue));
					float y = (float) (aRect.getY() + (row * pixYPerValue));

					SpectrumMatrixData data = aDataset.get(row, col);

					g.setColor(getColor(data.getValue(), aScaleValue));

					g.fillRect(x, y, width, height);
				}
			}
		}
	}

	private ScaleValue getScaleValue() {
		SpectrumDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (null != dataset) {
			for (int row = 0; row < dataset.getRowSize(); row++) {
				for (int col = 0; col < dataset.getColSize(); col++) {
					SpectrumMatrixData data = dataset.get(row, col);
					if (null == dataMinValue) {
						dataMinValue = data.getValue();
						dataMaxValue = data.getValue();
					} else {
						dataMinValue = Math.min(dataMinValue, data.getValue());
						dataMaxValue = Math.max(dataMaxValue, data.getValue());
					}

				}
			}
		}
		debug(String.format("Data minimum value : %f", dataMinValue));
		debug(String.format("Data maximum value : %f", dataMaxValue));

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double minValue = axis.getMinimumValue();
		double maxValue = axis.getMaximumValue();
		if (axis.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axis.isMinimumValueAutoFit()) {
			if (null != dataMinValue) {
				minValue = dataMinValue;
			}
			// TODO: ゼロに近づける
			if (minValue > 0) {
				if (minValue <= (maxValue - minValue) / 2) {
					minValue = 0.f;
				}
			}
		}
		ScaleValue scaleValue = new ScaleValue(minValue, maxValue, 0);
		debug(String.format("Axis minimum value : %f", minValue));
		debug(String.format("Axis maximum value : %f", maxValue));

		return scaleValue;
	}

	private Color getColor(final double aValue, final ScaleValue aScaleValue) {
		double index = ((aValue - aScaleValue.getMin()) / aScaleValue.getDiff()) * 511.f;
		if (index < 0)
			index = 0;
		if (index > 511)
			index = 511;
		index = 511 - index;

		Color color = null;
		if (index <= 255) {
			color = new Color((int) (255 - index), (int) index, 0);
		} else {
			color = new Color(0, (int) (255 - (index - 256)), (int) (index - 256));
		}
		return color;
	}
}
