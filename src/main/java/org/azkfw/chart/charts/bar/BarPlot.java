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
package org.azkfw.chart.charts.bar;

import java.awt.FontMetrics;

import org.azkfw.chart.charts.bar.BarChartDesign.BarChartStyle;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、棒グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class BarPlot extends AbstractSeriesPlot<BarDataset, BarChartDesign> {

	/** X軸情報 */
	private BarXAxis axisX;
	/** Y軸情報 */
	private BarYAxis axisY;

	/**
	 * コンストラクタ
	 */
	public BarPlot() {
		super(BarPlot.class);

		axisX = new BarXAxis();
		axisY = new BarYAxis();

		setChartDesign(BarChartDesign.DefalutDesign);
	}

	/**
	 * X軸情報を設定する。
	 * 
	 * @return X軸情報
	 */
	public BarXAxis getXAxis() {
		return axisX;
	}

	/**
	 * Y軸情報を設定する。
	 * 
	 * @return Y軸情報
	 */
	public BarYAxis getYAxis() {
		return axisY;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		BarDataset dataset = getDataset();
		BarChartDesign design = getChartDesign();
		BarChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);

		// スケール調整
		ScaleValue scaleValue = getScaleValue();

		float fontMargin = 8.0f;

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}
		debug(String.format("Data point size : %d", dataPointSize));

		Margin margin = fitChart(g, rtChartPre, scaleValue, fontMargin);
		debug(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(), margin.getBottom()));

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixPerValue = (rtChartPre.getHeight() - margin.getVerticalSize()) / difValue;

		Rect rtChart = new Rect();
		rtChart.setX(rtChartPre.getX() + margin.getLeft());
		rtChart.setY(rtChartPre.getY() + rtChartPre.getHeight() - margin.getBottom());
		rtChart.setWidth(rtChartPre.getWidth() - margin.getHorizontalSize());
		rtChart.setHeight(rtChartPre.getHeight() - margin.getVerticalSize());

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillRect(rtChart.getX(), rtChart.getY() - rtChart.getHeight(), rtChart.getWidth(), rtChart.getHeight());
		}

		// Draw Y axis
		g.setStroke(style.getYAxisLineStroke(), style.getYAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX(), rtChart.getY() - rtChart.getHeight());
		{
			int fontSize = style.getYAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(style.getYAxisFont());
			DisplayFormat df = axisY.getDisplayFormat();

			g.setFont(style.getYAxisFont(), style.getYAxisFontColor());
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);

				float x = (float) (rtChart.getX() - strWidth);
				float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue) - (fontSize / 2));
				g.drawStringA(str, x, y);
			}
		}

		// Draw X axis
		g.setStroke(style.getXAxisLineStroke(), style.getXAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX() + rtChart.getWidth(), rtChart.getY());
		{
			float dataWidth = rtChart.getWidth() / dataPointSize;
			FontMetrics fm = g.getFontMetrics(style.getXAxisFont());
			DisplayFormat df = axisX.getDisplayFormat();

			g.setFont(style.getXAxisFont(), style.getXAxisFontColor());
			for (int i = 0; i < dataPointSize; i++) {
				String str = df.toString(i);
				float strWidth = fm.stringWidth(str);

				float y = rtChart.getY();
				float x = rtChart.getX() + (i * dataWidth) + (dataWidth / 2) - (strWidth / 2);
				g.drawStringA(str, x, y);
			}
		}

		// Draw Legend
		if (null != rtLegend) {
			drawLegend(g, design.getLegendStyle(), rtLegend);
		}
		// Draw title
		if (null != rtTitle) {
			drawTitle(g, rtTitle);
		}

		return true;
	}

	private ScaleValue getScaleValue() {
		BarDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (null != dataset) {
			for (BarSeries series : dataset.getSeriesList()) {
				for (BarSeriesPoint point : series.getPoints()) {
					if (null == dataMinValue) {
						dataMinValue = point.getValue();
						dataMaxValue = point.getValue();
					} else {
						dataMinValue = Math.min(dataMinValue, point.getValue());
						dataMaxValue = Math.max(dataMaxValue, point.getValue());
					}
				}
			}
		}
		debug(String.format("Data minimum value : %f", dataMinValue));
		debug(String.format("Data maximum value : %f", dataMaxValue));

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double minValue = axisY.getMinimumValue();
		double maxValue = axisY.getMaximumValue();
		double scale = axisY.getScale();
		if (axisY.isMinimumValueAutoFit()) {
			if (null != dataMinValue) {
				minValue = dataMinValue;
			}
		}
		if (axisY.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axisY.isScaleAutoFit()) {
			double dif = maxValue - minValue;
			int logDif = (int) (Math.log10(dif));
			double scaleDif = Math.pow(10, logDif);
			if (dif >= scaleDif * 5) {
				scale = scaleDif;
			} else if (dif >= scaleDif * 2) {
				scale = scaleDif / 2;
			} else {
				scale = scaleDif / 10;
			}
		}
		ScaleValue scaleValue = new ScaleValue(minValue, maxValue, scale);
		debug(String.format("Y axis minimum value : %f", minValue));
		debug(String.format("Y axis maximum value : %f", maxValue));
		debug(String.format("Y axis scale value : %f", scale));

		return scaleValue;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		BarDataset dataset = getDataset();
		BarChartDesign design = getChartDesign();
		BarChartStyle style = design.getChartStyle();

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}

		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		double difValue = aScaleValue.getDiff();

		// スケール計算(プレ)
		double pixPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / difValue;
		{
			{
				float maxYLabelWidth = 0.0f;
				FontMetrics fm = g.getFontMetrics(style.getYAxisFont());
				DisplayFormat df = axisY.getDisplayFormat();
				for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
					String str = df.toString(value);
					int width = fm.stringWidth(str);
					if (maxYLabelWidth < width) {
						maxYLabelWidth = width;
					}
				}
				if (0 < maxYLabelWidth) {
					maxYLabelWidth += aFontMargin;
				}
				debug(String.format("Max y axis label width : %f", maxYLabelWidth));
				margin.setLeft(maxYLabelWidth);
			}

			// スケール計算(プレ)
			pixPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / difValue;

			{
				float chartWidth = aRtChart.getWidth() - margin.getLeft();
				float dataWidth = chartWidth / (float) dataPointSize;
				FontMetrics fm = g.getFontMetrics(style.getXAxisFont());
				DisplayFormat df = axisX.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = aRtChart.getWidth();
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i);
					int width = fm.stringWidth(str);
					if (0 < width) {
						margin.setBottom(style.getXAxisFont().getSize());

						float x = margin.getLeft() + (i * dataWidth) + (dataWidth / 2);
						float left = x - (width / 2);
						float right = x + (width / 2);
						if (minLeft > left) {
							minLeft = left;
						}
						if (maxRight < right) {
							maxRight = right;
						}
					}
				}
				if (0 > minLeft) {
					margin.setLeft(margin.getLeft() - minLeft);
				}
				if (0 < maxRight - aRtChart.getWidth()) {
					margin.setRight(maxRight - aRtChart.getWidth());
				}
			}

			// スケール計算(プレ)
			pixPerValue = (aRtChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;

			{
				int fontHeight = style.getYAxisFont().getSize();
				DisplayFormat df = axisY.getDisplayFormat();
				float minTop = 0.f;
				for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
					float y = (float) (aRtChart.getHeight() - margin.getBottom() - pixPerValue * (value - aScaleValue.getMin()));
					String str = df.toString(value);
					if (0 < str.length()) {
						y -= fontHeight / 2;
						if (minTop > y) {
							minTop = y;
						}
					}
				}
				if (0 > minTop) {
					margin.setTop(-1 * minTop);
				}
			}

			// スケール計算(Fix)
			pixPerValue = (aRtChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;

			debug(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(),
					margin.getBottom()));
		}

		return margin;
	}
}
