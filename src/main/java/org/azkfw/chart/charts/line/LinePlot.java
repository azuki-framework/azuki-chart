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

import org.azkfw.chart.charts.line.LineAxis.LineXAxis;
import org.azkfw.chart.charts.line.LineAxis.LineYAxis;
import org.azkfw.chart.charts.line.LineChartDesign.LineChartStyle;
import org.azkfw.chart.charts.line.LineSeries.LineSeriesPoint;
import org.azkfw.chart.charts.scatter.ScatterPlot;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、折れ線グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class LinePlot extends AbstractSeriesPlot<LineDataset, LineChartDesign> {

	/** X軸情報 */
	private LineXAxis axisX;
	/** Y軸情報 */
	private LineYAxis axisY;

	/**
	 * コンストラクタ
	 */
	public LinePlot() {
		super(ScatterPlot.class);

		axisX = new LineXAxis();
		axisY = new LineYAxis();

		setChartDesign(LineChartDesign.DefalutDesign);
	}

	/**
	 * X軸情報を取得する。
	 * 
	 * @return X軸情報
	 */
	public LineXAxis getXAxis() {
		return axisX;
	}

	/**
	 * Y軸情報を取得する。
	 * 
	 * @return Y軸情報
	 */
	public LineYAxis getYAxis() {
		return axisY;
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
		int dataSize = 3;
		int dataPointSize = 5;
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

		// fill background
		if (ObjectUtility.isNotNull(style.getBackgroundColor())) {
			g.setColor(style.getBackgroundColor());
			g.fillRect(rtChart.getX(), rtChart.getY() - rtChart.getHeight(), rtChart.getWidth(), rtChart.getHeight());
		}

		// Draw Y axis scale
		{
			// Y軸目盛ラベル
			Font font = style.getYAxisFont();
			Color fontColor = style.getYAxisFontColor();
			if (ObjectUtility.isAllNotNull(font, fontColor)) {
				int fontSize = font.getSize();
				FontMetrics fm = g.getFontMetrics(font);
				DisplayFormat df = axisY.getDisplayFormat();

				g.setFont(font, fontColor);
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					String str = df.toString(value);
					float strWidth = fm.stringWidth(str);

					float x = (float) (rtChart.getX() - (strWidth + fontMargin));
					float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue) - (fontSize / 2));
					g.drawStringA(str, x, y);
				}
			}
			// Y軸補助線
			g.setStroke(style.getYAxisScaleStroke(), style.getYAxisScaleColor());
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				float x = (float) (rtChart.getX());
				float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue));
				g.drawLine(x, y, x + rtChart.getWidth(), y);
			}
			// Y軸目盛線
			g.setStroke(style.getYAxisLineStroke(), style.getYAxisLineColor());
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				float x = (float) (rtChart.getX());
				float y = (float) (rtChart.getY() - ((value - scaleValue.getMin()) * pixPerValue));
				g.drawLine(x, y, x + 6, y);
			}
		}
		// Draw X axis scale
		{
			float dataWidth = rtChart.getWidth() / dataPointSize;
			
			// X軸目盛ラベル
			Font font = style.getXAxisFont();
			Color fontColor = style.getXAxisFontColor();
			if (ObjectUtility.isAllNotNull(font, fontColor)) {
				FontMetrics fm = g.getFontMetrics(font);
				DisplayFormat df = axisX.getDisplayFormat();

				g.setFont(font, fontColor);
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i);
					float strWidth = fm.stringWidth(str);

					float y = rtChart.getY() + fontMargin;
					float x = rtChart.getX() + (i * dataWidth) + (dataWidth / 2) - (strWidth / 2);
					g.drawStringA(str, x, y);
				}
			}
			// X軸目盛線
			g.setStroke(style.getXAxisLineStroke(), style.getXAxisLineColor());
			for (int i = 0; i < dataPointSize; i++) {
				float y = (float) (rtChart.getY());
				float x = (float) (rtChart.getX() + (i * dataWidth) + (dataWidth / 2));
				g.drawLine(x, y, x, y - 6);
			}
		}

		// Y軸線
		g.setStroke(style.getYAxisLineStroke(), style.getYAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX(), rtChart.getY() - rtChart.getHeight());
		// X軸線
		g.setStroke(style.getXAxisLineStroke(), style.getXAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX() + rtChart.getWidth(), rtChart.getY());

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
		if (null != dataset) {
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
						margin.setBottom(style.getXAxisFont().getSize() + aFontMargin);

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
