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
package org.azkfw.chart.charts.line;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.List;

import org.azkfw.chart.charts.line.LineAxis.LineHorizontalAxis;
import org.azkfw.chart.charts.line.LineAxis.LineVerticalAxis;
import org.azkfw.chart.charts.line.LineChartDesign.LineChartStyle;
import org.azkfw.chart.charts.line.LineSeries.LineSeriesPoint;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、折れ線グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class LinePlot extends AbstractSeriesPlot<LineDataset, LineChartDesign> {

	/** 水平軸情報 */
	private LineHorizontalAxis axisHorizontal;
	/** 垂直軸情報 */
	private LineVerticalAxis axisVertical;

	/**
	 * コンストラクタ
	 */
	public LinePlot() {
		super(LinePlot.class);

		axisHorizontal = new LineHorizontalAxis();
		axisVertical = new LineVerticalAxis();

		setChartDesign(LineChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public LinePlot(final LineDataset aDataset) {
		super(LinePlot.class, aDataset);

		axisHorizontal = new LineHorizontalAxis();
		axisVertical = new LineVerticalAxis();

		setChartDesign(LineChartDesign.DefalutDesign);
	}

	/**
	 * 水平軸情報を取得する。
	 * 
	 * @return 水平軸情報
	 */
	public LineHorizontalAxis getHorizontalAxis() {
		return axisHorizontal;
	}

	/**
	 * 垂直軸情報を取得する。
	 * 
	 * @return 垂直軸情報
	 */
	public LineVerticalAxis getVerticalAxis() {
		return axisVertical;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		LineDataset dataset = getDataset();
		LineChartDesign design = getChartDesign();
		LineChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);

		// スケール調整
		ScaleValue scaleValue = getScaleValue();

		float fontMargin = 8.0f;

		Margin margin = fitChart(g, rtChartPre, scaleValue, fontMargin);
		debug(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(), margin.getBottom()));

		Rect rtChart = new Rect();
		rtChart.setX(rtChartPre.getX() + margin.getLeft());
		rtChart.setY(rtChartPre.getY() + rtChartPre.getHeight() - margin.getBottom()); // ★注意：Yは原点から
		rtChart.setWidth(rtChartPre.getWidth() - margin.getHorizontalSize());
		rtChart.setHeight(rtChartPre.getHeight() - margin.getVerticalSize());

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixPerValue = rtChart.getHeight() / difValue;

		// データポイント数取得(ポイント数が最大のシリーズを採用する）
		int dataSize = 1;
		int dataPointSize = 10;
		if (ObjectUtility.isNotNull(dataset)) {
			if (ObjectUtility.isNotNull(dataset.getSeriesList())) {
				dataSize = dataset.getSeriesList().size();
				if (0 < dataSize) {
					dataPointSize = 0;
					for (LineSeries series : dataset.getSeriesList()) {
						dataPointSize = Math.max(dataPointSize, series.getPoints().size());
					}
				}
			}
		}
		debug(String.format("Data point size : %d", dataPointSize));

		// 背景色描画
		if (ObjectUtility.isNotNull(style.getBackgroundColor())) {
			g.setColor(style.getBackgroundColor());
			g.fillRect(rtChart.getX(), rtChart.getY() - rtChart.getHeight(), rtChart.getWidth(), rtChart.getHeight());
		}

		// 垂直軸描画
		{
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
						float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue) - (fontSize / 2));
						g.drawStringA(str, x, y);
					}
				}
			}
			// 目盛線描画
			Stroke scaleLineStroke = style.getVerticalAxisScaleLineStroke();
			Color scaleLineColor = style.getVerticalAxisScaleLineColor();
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor)) {
				g.setStroke(scaleLineStroke, scaleLineColor);
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float x = (float) (rtChart.getX());
					float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue));
					g.drawLine(x, y, x + rtChart.getWidth(), y);
				}
			}
			// 軸線描画
			Stroke lineStroke = style.getVerticalAxisLineStroke();
			Color lineColor = style.getVerticalAxisLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				g.setStroke(lineStroke, lineColor);
				g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX(), rtChart.getY() - rtChart.getHeight());
				// 目盛
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float x = (float) (rtChart.getX());
					float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue));
					g.drawLine(x, y, x + 6, y);
				}
			}
		}
		// 水平軸描画
		{
			float dataWidth = rtChart.getWidth() / dataPointSize;

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

						float y = rtChart.getY() + fontMargin;
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
				g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX() + rtChart.getWidth(), rtChart.getY());
			}
		}

		// Draw dataset
		drawDataset(g, dataset, dataPointSize, scaleValue, style, rtChart);

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

	private void drawDataset(final Graphics g, final LineDataset aDataset, final int aDataPointSize, final ScaleValue aScaleValue,
			final LineChartStyle aStyle, final Rect aRect) {
		if (ObjectUtility.isNotNull(aDataset)) {
			// スケール計算
			double difValue = aScaleValue.getDiff();
			double pixPerValue = aRect.getHeight() / difValue;

			float width = aRect.getWidth() / aDataPointSize;

			float lineOffset = width / 2.f;

			List<LineSeries> seriesList = aDataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				LineSeries series = seriesList.get(index);
				List<LineSeriesPoint> points = series.getPoints();

				if (!aStyle.isOverflow()) {
					g.setClip(aRect.getX(), aRect.getY() - aRect.getHeight(), aRect.getWidth(), aRect.getHeight());
				}

				// Draw series fill
				{
					Color fillColor = aStyle.getSeriesFillColor(index, series);
					if (ObjectUtility.isNotNull(fillColor)) {
						int xps[] = new int[points.size() + 2];
						int yps[] = new int[points.size() + 2];
						for (int j = 0; j < points.size(); j++) {
							LineSeriesPoint point = points.get(j);
							xps[j + 1] = (int) (aRect.getX() + (j * width + lineOffset));
							yps[j + 1] = (int) (aRect.getY() - ((point.getValue() - aScaleValue.getMin()) * pixPerValue));
						}
						xps[0] = (int) (xps[1]);
						yps[0] = (int) (aRect.getY());
						xps[points.size() + 1] = (int) (xps[points.size()]);
						yps[points.size() + 1] = (int) (aRect.getY());

						GradientPaint paint = new GradientPaint(0f, aRect.getY() - aRect.getHeight(), fillColor, 0f, aRect.getY(), new Color(
								fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 0));
						g.setPaint(paint);
						g.fillPolygon(new Polygon(xps, yps, points.size() + 2));
					}
				}

				// Draw series line
				{
					Stroke stroke = aStyle.getSeriesStroke(index, series);
					Color strokeColor = aStyle.getSeriesStrokeColor(index, series);
					if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
						float xps[] = new float[points.size()];
						float yps[] = new float[points.size()];
						for (int j = 0; j < points.size(); j++) {
							LineSeriesPoint point = points.get(j);
							xps[j] = (int) (aRect.getX() + (j * width + lineOffset));
							yps[j] = (int) (aRect.getY() - ((point.getValue() - aScaleValue.getMin()) * pixPerValue));
						}
						g.setStroke(stroke, strokeColor);
						g.drawPolyline(xps, yps, points.size());
					}
				}

				if (!aStyle.isOverflow()) {
					g.clearClip();
				}

				// Draw series marker
				{
					Marker seriesMarker = aStyle.getSeriesMarker(index, series);
					for (int j = 0; j < points.size(); j++) {
						LineSeriesPoint point = points.get(j);

						if (!aStyle.isOverflow()) {
							if (point.getValue() < aScaleValue.getMin() || point.getValue() > aScaleValue.getMax()) {
								continue;
							}
						}

						Marker pointMarker = aStyle.getSeriesPointMarker(index, series, j, point);
						Marker marker = (Marker) getNotNullObject(pointMarker, seriesMarker);
						if (ObjectUtility.isNotNull(marker)) {
							float xMarker = (float) (aRect.getX() + (j * width + lineOffset));
							float yMarker = (float) (aRect.getY() - ((point.getValue() - aScaleValue.getMin()) * pixPerValue));
							Size size = marker.getSize();

							int mx = (0 == (int) size.getWidth() % 2) ? 0 : 1;
							int my = (0 == (int) size.getHeight() % 2) ? 0 : 1;
							marker.draw(g, xMarker - (size.getWidth() / 2) + mx, yMarker - (size.getHeight() / 2) + my);
						}

					}

				}

			}

		}
	}

	private ScaleValue getScaleValue() {
		LineDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (ObjectUtility.isNotNull(dataset)) {
			for (LineSeries series : dataset.getSeriesList()) {
				for (LineSeriesPoint point : series.getPoints()) {
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
		debug(String.format("Y axis minimum value : %f", minValue));
		debug(String.format("Y axis maximum value : %f", maxValue));
		debug(String.format("Y axis scale value : %f", scale));

		return scaleValue;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		LineDataset dataset = getDataset();
		LineChartDesign design = getChartDesign();
		LineChartStyle style = design.getChartStyle();

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
				FontMetrics fm = g.getFontMetrics(style.getVerticalAxisScaleLabelFont());
				DisplayFormat df = axisVertical.getDisplayFormat();
				for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
					String str = df.toString(value);
					if (StringUtility.isNotEmpty(str)) {
						int width = fm.stringWidth(str);
						if (maxYLabelWidth < width) {
							maxYLabelWidth = width;
						}
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
				FontMetrics fm = g.getFontMetrics(style.getHorizontalAxisScaleLabelFont());
				DisplayFormat df = axisHorizontal.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = aRtChart.getWidth();
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i);
					if (StringUtility.isNotEmpty(str)) {
						int width = fm.stringWidth(str);
						margin.setBottom(style.getHorizontalAxisScaleLabelFont().getSize() + aFontMargin);

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

}
