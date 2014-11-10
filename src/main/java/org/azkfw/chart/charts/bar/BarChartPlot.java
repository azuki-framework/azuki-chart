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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.charts.bar.BarAxis.BarHorizontalAxis;
import org.azkfw.chart.charts.bar.BarAxis.BarVerticalAxis;
import org.azkfw.chart.charts.bar.BarChartDesign.BarChartStyle;
import org.azkfw.chart.charts.bar.BarSeries.BarSeriesPoint;
import org.azkfw.chart.core.element.BarLegendElement;
import org.azkfw.chart.core.element.LegendElement;
import org.azkfw.chart.core.plot.AbstractSeriesChartPlot;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、棒グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class BarChartPlot extends AbstractSeriesChartPlot<BarDataset, BarChartDesign> {

	/** 水平軸情報 */
	private BarHorizontalAxis axisHorizontal;
	/** 垂直軸情報 */
	private BarVerticalAxis axisVertical;

	/**
	 * コンストラクタ
	 */
	public BarChartPlot() {
		super(BarChartPlot.class);

		axisHorizontal = new BarHorizontalAxis();
		axisVertical = new BarVerticalAxis();

		setChartDesign(BarChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public BarChartPlot(final BarDataset aDataset) {
		super(BarChartPlot.class, aDataset);

		axisHorizontal = new BarHorizontalAxis();
		axisVertical = new BarVerticalAxis();

		setChartDesign(BarChartDesign.DefalutDesign);
	}

	/**
	 * 水平軸情報を設定する。
	 * 
	 * @return 水平軸情報
	 */
	public BarHorizontalAxis getHorizontalAxis() {
		return axisHorizontal;
	}

	/**
	 * 垂直軸情報を設定する。
	 * 
	 * @return 垂直軸情報
	 */
	public BarVerticalAxis getVerticalAxis() {
		return axisVertical;
	}

	@Override
	protected LegendElement createLegendElement() {
		LegendElement element = new BarLegendElement(getDataset(), getDesign(), isDebugMode());
		return element;
	}

	@Override
	protected boolean doDrawChart(final Graphics g, final Rect aRect) {
		BarDataset dataset = getDataset();
		BarChartDesign design = getDesign();
		BarChartStyle style = design.getChartStyle();

		// スケール調整
		ScaleValue scaleValue = getScaleValue(getDataset());

		float fontMargin = 8.0f;

		Margin margin = fitChart(g, aRect, scaleValue, fontMargin);
		debug(String.format("Margin : { Left : %f, Right : %f, Top : %f, Bottom : %f, }", margin.getLeft(), margin.getRight(), margin.getTop(),
				margin.getBottom()));

		Rect rtChart = new Rect(aRect);
		rtChart.addPosition(margin.getLeft(), margin.getTop());
		rtChart.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixPerValue = rtChart.getHeight() / difValue;

		// データポイント数取得(ポイント数が最大のシリーズを採用する）
		int dataSize = 3;
		int dataPointSize = 5;
		if (ObjectUtility.isNotNull(dataset)) {
			if (ObjectUtility.isNotNull(dataset.getSeriesList())) {
				dataSize = dataset.getSeriesList().size();
				if (0 < dataSize) {
					dataPointSize = 0;
					for (BarSeries series : dataset.getSeriesList()) {
						dataPointSize = Math.max(dataPointSize, series.getPoints().size());
					}
				}
			}
		}
		debug(String.format("Data point size : %d", dataPointSize));

		// 背景描画
		if (ObjectUtility.isNotNull(style.getBackgroundColor())) {
			g.setColor(style.getBackgroundColor());
			g.fillRect(rtChart);
		}

		// 垂直軸描画
		{
			// ラベル描画
			Font labelFont = style.getVerticalAxisLabelFont();
			Color labelColor = style.getVerticalAxisLabelColor();
			String labelTitle = getVerticalAxis().getLabelTitle();
			if (ObjectUtility.isAllNotNull(labelFont, labelColor) && StringUtility.isNotEmpty(labelTitle)) {
				FontMetrics fm = g.getFontMetrics(labelFont);
				int strWidth = fm.stringWidth(labelTitle);
				int fontHeight = fm.getAscent() - fm.getDescent();

				float x = aRect.getX() + (fontHeight / 2);
				float y = (rtChart.getY() + rtChart.getHeight()) - (rtChart.getHeight() / 2);

				AffineTransform save = g.getTransform();
				AffineTransform at = new AffineTransform();
				at.setToRotation(Math.toRadians(-90), x, y);
				g.setTransform(at);

				g.setFont(labelFont, labelColor);
				g.drawStringA(labelTitle, x - (strWidth / 2), y - (fontHeight / 2));

				g.setTransform(save);
			}
			// 目盛ラベル描画
			Font scaleLabelFont = style.getVerticalAxisScaleLabelFont();
			Color scaleLabelColor = style.getVerticalAxisScaleLabelColor();
			if (ObjectUtility.isAllNotNull(scaleLabelFont, scaleLabelColor)) {
				int fontSize = scaleLabelFont.getSize();
				FontMetrics fm = g.getFontMetrics(scaleLabelFont);
				DisplayFormat df = axisVertical.getDisplayFormat();

				g.setFont(scaleLabelFont, scaleLabelColor);
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					String str = df.toString(value);
					if (StringUtility.isNotEmpty(str)) {
						float strWidth = fm.stringWidth(str);

						float x = (float) (rtChart.getX() - (strWidth + fontMargin));
						float y = (float) ((rtChart.getY() + rtChart.getHeight()) - ((value - scaleValue.getMin()) * pixPerValue) - (fontSize / 2));
						g.drawStringA(str, x, y);
					}
				}
			}
			// 目盛線描画
			Stroke scaleLineStroke = style.getVerticalAxisScaleLineStroke();
			Color scaleLineColor = style.getVerticalAxisScaleLineColor();
			g.setStroke(scaleLineStroke, scaleLineColor);
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor))
				;
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				float x = (float) (rtChart.getX());
				float y = (float) ((rtChart.getY() + rtChart.getHeight()) - ((value - scaleValue.getMin()) * pixPerValue));
				g.drawLine(x, y, x + rtChart.getWidth(), y);
			}
			// 軸線描画
			Stroke lineStroke = style.getVerticalAxisLineStroke();
			Color lineColor = style.getVerticalAxisLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				g.setStroke(lineStroke, lineColor);
				g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX(), rtChart.getY() + rtChart.getHeight());
				// 目盛
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float x = (float) (rtChart.getX());
					float y = (float) ((rtChart.getY() + rtChart.getHeight()) - ((value - scaleValue.getMin()) * pixPerValue));
					g.drawLine(x, y, x + 6, y);
				}
			}
		}
		// 水平軸描画
		{
			float dataWidth = rtChart.getWidth() / dataPointSize;

			// ラベル描画
			Font labelFont = style.getHorizontalAxisLabelFont();
			Color labelColor = style.getHorizontalAxisLabelColor();
			String labelTitle = getHorizontalAxis().getLabelTitle();
			if (ObjectUtility.isAllNotNull(labelFont, labelColor) && StringUtility.isNotEmpty(labelTitle)) {
				FontMetrics fm = g.getFontMetrics(labelFont);
				int strWidth = fm.stringWidth(labelTitle);
				int fontHeight = fm.getAscent() - fm.getDescent();

				float x = rtChart.getX() + (rtChart.getWidth() - strWidth) / 2;
				float y = (aRect.getY() + aRect.getHeight() - fontHeight);

				g.setFont(labelFont, labelColor);
				g.drawStringA(labelTitle, x, y);

			}
			// 目盛ラベル描画
			Font scaleLabelFont = style.getHorizontalAxisScaleLabelFont();
			Color scaleLabelColor = style.getHorizontalAxisScaleLabelColor();
			if (ObjectUtility.isAllNotNull(scaleLabelFont, scaleLabelColor)) {
				FontMetrics fm = g.getFontMetrics(scaleLabelFont);
				DisplayFormat df = axisHorizontal.getDisplayFormat();

				g.setFont(scaleLabelFont, scaleLabelColor);
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i + 1);
					if (StringUtility.isNotEmpty(str)) {
						float strWidth = fm.stringWidth(str);

						float y = (rtChart.getY() + rtChart.getHeight()) + fontMargin;
						float x = rtChart.getX() + (i * dataWidth) + (dataWidth / 2) - (strWidth / 2);
						g.drawStringA(str, x, y);
					}
				}
			}
			// 軸線描画
			Stroke lineStroke = style.getHorizontalAxisLineStroke();
			Color lineColor = style.getHorizontalAxisLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				g.setStroke(lineStroke, lineColor);
				g.drawLine(rtChart.getX(), rtChart.getY() + rtChart.getHeight(), rtChart.getX() + rtChart.getWidth(),
						rtChart.getY() + rtChart.getHeight());
			}
		}

		// Draw dataset
		drawDataset(g, dataset, dataSize, dataPointSize, scaleValue, style, rtChart);

		if (isDebugMode()) {
			g.setStroke(new BasicStroke(1.f));
			g.setColor(Color.blue);
			g.drawRect(rtChart);
		}

		return true;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		BarDataset dataset = getDataset();
		BarChartDesign design = getDesign();
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
			{ // 軸ラベル
				Font labelFont = style.getVerticalAxisLabelFont();
				Color labelColor = style.getVerticalAxisLabelColor();
				String labelTitle = getVerticalAxis().getLabelTitle();
				if (ObjectUtility.isAllNotNull(labelFont, labelColor) && StringUtility.isNotEmpty(labelTitle)) {
					FontMetrics fm = g.getFontMetrics(labelFont);
					margin.addLeft((fm.getAscent() - fm.getDescent()) + aFontMargin);
				}
			}
			{ // 軸目盛ラベル
				float maxYLabelWidth = 0.0f;
				FontMetrics fm = g.getFontMetrics(style.getVerticalAxisScaleLabelFont());
				DisplayFormat df = axisVertical.getDisplayFormat();
				for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
					String str = df.toString(value);
					if (StringUtility.isNotEmpty(str)) {
						int width = fm.stringWidth(str);
						maxYLabelWidth = Math.max(width, maxYLabelWidth);
					}
				}
				if (0 < maxYLabelWidth) {
					maxYLabelWidth += aFontMargin;
				}
				debug(String.format("Max y axis label width : %f", maxYLabelWidth));
				margin.addLeft(maxYLabelWidth);
			}

			// スケール計算(プレ)
			pixPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / difValue;

			{
				{ // 軸ラベル
					Font labelFont = style.getHorizontalAxisLabelFont();
					Color labelColor = style.getHorizontalAxisLabelColor();
					String labelTitle = getHorizontalAxis().getLabelTitle();
					if (ObjectUtility.isAllNotNull(labelFont, labelColor) && StringUtility.isNotEmpty(labelTitle)) {
						FontMetrics fm = g.getFontMetrics(labelFont);
						margin.addBottom((fm.getAscent() - fm.getDescent()) + aFontMargin);
					}
				}

				float chartWidth = aRtChart.getWidth() - margin.getLeft();
				float dataWidth = chartWidth / (float) dataPointSize;
				FontMetrics fm = g.getFontMetrics(style.getHorizontalAxisScaleLabelFont());
				DisplayFormat df = axisHorizontal.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = aRtChart.getWidth();
				int fontHeight = 0;
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i);
					if (StringUtility.isNotEmpty(str)) {
						int width = fm.stringWidth(str);
						fontHeight = Math.max((fm.getAscent() - fm.getDescent()), fontHeight);

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
				if (0 < fontHeight) {
					margin.addBottom(fontHeight + aFontMargin);
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
				int fontHeight = style.getVerticalAxisScaleLabelFont().getSize();
				DisplayFormat df = axisVertical.getDisplayFormat();
				float minTop = 0.f;
				for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
					float y = (float) (aRtChart.getHeight() - margin.getBottom() - pixPerValue * (value - aScaleValue.getMin()));
					String str = df.toString(value);
					if (StringUtility.isNotEmpty(str)) {
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

	private void drawDataset(final Graphics g, final BarDataset aDataset, final int aDataSize, final int aDataPointSize,
			final ScaleValue aScaleValue, final BarChartStyle aStyle, final Rect aRect) {
		if (ObjectUtility.isNotNull(aDataset)) {
			// スケール計算
			double difValue = aScaleValue.getDiff();
			double pixPerValue = aRect.getHeight() / difValue;

			float width = aRect.getWidth() / aDataPointSize;
			float barInterval = 8.f;
			float barMargin = 4.f;

			float barWidth = (1 == aDataSize) ? width - (barInterval * 2) : (width - (barInterval * 2) - (barMargin * (aDataSize - 1))) / aDataSize;

			if (!aStyle.isOverflow()) {
				g.setClip(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());
			}

			List<BarSeries> seriesList = aDataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				BarSeries series = seriesList.get(index);
				List<BarSeriesPoint> points = series.getPoints();
				for (int i = 0; i < points.size(); i++) {
					if (aDataPointSize <= i) {
						break;
					}
					BarSeriesPoint point = points.get(i);

					float barHeight = (float) ((point.getValue() - aScaleValue.getMin()) * pixPerValue);
					Rect rtBar = new Rect();
					rtBar.setX(aRect.getX() + (width * i) + barInterval + (index * (barWidth + ((0 == index) ? 0 : barMargin))));
					rtBar.setY((aRect.getY() + aRect.getHeight()) - barHeight);
					rtBar.setWidth(barWidth);
					rtBar.setHeight(barHeight);

					Color fillColor = aStyle.getSeriesFillColor(index, series);
					if (ObjectUtility.isNotNull(fillColor)) {
						GradientPaint paint = new GradientPaint(0f, aRect.getY(), fillColor, 0f, aRect.getY() + aRect.getHeight(), new Color(
								fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 0));
						g.setPaint(paint);
						// g.setColor(style.getSeriesFillColor(index, series));
						g.fillRect(rtBar);
					}

					Stroke stroke = aStyle.getSeriesStroke(index, series);
					Color strokeColor = aStyle.getSeriesStrokeColor(index, series);
					if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
						List<Point> ps = new ArrayList<Point>();
						ps.add(new Point(rtBar.getX(), rtBar.getY() + rtBar.getHeight()));
						ps.add(new Point(rtBar.getX(), rtBar.getY()));
						ps.add(new Point(rtBar.getX() + rtBar.getWidth(), rtBar.getY()));
						ps.add(new Point(rtBar.getX() + rtBar.getWidth(), rtBar.getY() + rtBar.getHeight()));

						g.setStroke(stroke, strokeColor);
						// g.drawRect(rtBar);
						g.drawPolyline(ps);
					}
				}
			}

			if (!aStyle.isOverflow()) {
				g.clearClip();
			}
		}
	}

	private ScaleValue getScaleValue(final BarDataset aDataset) {
		// データ最小値・最大値取得 //////////////////////////
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (null != aDataset) {
			for (BarSeries series : aDataset.getSeriesList()) {
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
		////////////////////////////////////////////////

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double minValue = axisVertical.getMinimumValue();
		double maxValue = axisVertical.getMaximumValue();
		double scale = axisVertical.getScale();
		if (axisVertical.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axisVertical.isMinimumValueAutoFit()) {
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
		if (axisVertical.isScaleAutoFit()) {
			double dif = maxValue - minValue;
			int logDif = (int) (Math.log10(dif));
			double scaleDif = Math.pow(10, logDif);
			if (dif <= scaleDif * 1) {
				scale = scaleDif / 5;
			} else if (dif <= scaleDif * 2.5) {
				scale = scaleDif / 2;
			} else if (dif <= scaleDif * 5) {
				scale = scaleDif;
			} else {
				scale = scaleDif * 2;
			}
		}

		// TODO: 全て同じ数値の場合の対応
		if (0 == scale) {
			if (0 == minValue) {
				minValue = 0;
				maxValue = 1;
				scale = 1;
			} else {
				int logMin = (int) Math.log10(minValue);
				double scaleMin = Math.pow(10, logMin);
				scale = scaleMin;
				maxValue = minValue + scale;
				minValue = minValue - scale;
			}
		}

		ScaleValue scaleValue = new ScaleValue(minValue, maxValue, scale);
		debug(String.format("Vertical axis scale minimum  value : %f", minValue));
		debug(String.format("Vertical axis scale maximum  value : %f", maxValue));
		debug(String.format("Vertical axis scale interval value : %f", scale));

		return scaleValue;
	}
}
