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
package org.azkfw.chart.charts.scatter;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.util.List;

import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterXAxis;
import org.azkfw.chart.charts.scatter.ScatterAxis.ScatterYAxis;
import org.azkfw.chart.charts.scatter.ScatterChartDesign.ScatterChartStyle;
import org.azkfw.chart.charts.scatter.ScatterSeries.ScatterSeriesPoint;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、散布図のプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class ScatterPlot extends AbstractSeriesPlot<ScatterDataset, ScatterChartDesign> {

	/** X軸情報 */
	private ScatterXAxis axisX;
	/** Y軸情報 */
	private ScatterYAxis axisY;

	/**
	 * コンストラクタ
	 */
	public ScatterPlot() {
		super(ScatterPlot.class);

		axisX = new ScatterXAxis();
		axisY = new ScatterYAxis();

		setChartDesign(ScatterChartDesign.DefalutDesign);
	}

	/**
	 * X軸情報を取得する。
	 * 
	 * @return X軸情報
	 */
	public ScatterXAxis getXAxis() {
		return axisX;
	}

	/**
	 * Y軸情報を取得する。
	 * 
	 * @return Y軸情報
	 */
	public ScatterYAxis getYAxis() {
		return axisY;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		ScatterDataset dataset = getDataset();
		ScatterChartDesign design = getChartDesign();
		ScatterChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);

		// スケール調整
		ScaleValue[] svs = getXYScaleValue();
		ScaleValue xScaleValue = svs[0];
		ScaleValue yScaleValue = svs[1];

		float fontMargin = 8.0f;

		Margin margin = fitChart(g, rtChartPre, xScaleValue, yScaleValue, fontMargin);
		debug(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(), margin.getBottom()));

		Rect rtChart = new Rect();
		rtChart.setX(rtChartPre.getX() + margin.getLeft());
		rtChart.setY(rtChartPre.getY() + rtChartPre.getHeight() - margin.getBottom()); // ★注意：Yは原点から
		rtChart.setWidth(rtChartPre.getWidth() - margin.getHorizontalSize());
		rtChart.setHeight(rtChartPre.getHeight() - margin.getVerticalSize());

		// スケール計算
		double xDifValue = xScaleValue.getDiff();
		double yDifValue = yScaleValue.getDiff();
		double pixXPerValue = (rtChart.getWidth()) / xDifValue;
		double pixYPerValue = (rtChart.getHeight()) / yDifValue;

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillRect(rtChart.getX(), rtChart.getY() - rtChart.getHeight(), rtChart.getWidth(), rtChart.getHeight());
		}

		// Draw Y axis scale
		{
			int fontSize = style.getYAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(style.getYAxisFont());
			DisplayFormat df = axisY.getDisplayFormat();

			g.setFont(style.getYAxisFont(), style.getYAxisFontColor());
			for (double value = yScaleValue.getMin(); value <= yScaleValue.getMax(); value += yScaleValue.getScale()) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);

				float x = (float) (rtChart.getX() - strWidth - fontMargin);
				float y = (float) (rtChart.getY() - ((value - yScaleValue.getMin()) * pixYPerValue));
				g.drawStringA(str, x, y - (fontSize / 2));
			}

			g.setStroke(style.getYAxisScaleStroke(), style.getYAxisScaleColor());
			for (double value = yScaleValue.getMin(); value <= yScaleValue.getMax(); value += yScaleValue.getScale()) {
				float x = (float) (rtChart.getX());
				float y = (float) (rtChart.getY() - ((value - yScaleValue.getMin()) * pixYPerValue));
				g.drawLine(x, y, x + rtChart.getWidth(), y);
			}

			g.setStroke(style.getYAxisLineStroke(), style.getYAxisLineColor());
			for (double value = yScaleValue.getMin(); value <= yScaleValue.getMax(); value += yScaleValue.getScale()) {
				float x = (float) (rtChart.getX());
				float y = (float) (rtChart.getY() - ((value - yScaleValue.getMin()) * pixYPerValue));
				g.drawLine(x, y, x + 6, y);
			}
		}
		// Draw X axis scale
		{
			FontMetrics fm = g.getFontMetrics(style.getXAxisFont());
			DisplayFormat df = axisX.getDisplayFormat();

			g.setFont(style.getXAxisFont(), style.getXAxisFontColor());
			for (double value = xScaleValue.getMin(); value <= xScaleValue.getMax(); value += xScaleValue.getScale()) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);

				float x = (float) (rtChart.getX() + ((value - xScaleValue.getMin()) * pixXPerValue) - (strWidth / 2));
				float y = (float) (rtChart.getY() + fontMargin);
				g.drawStringA(str, x, y);
			}

			g.setStroke(style.getXAxisScaleStroke(), style.getXAxisScaleColor());
			for (double value = xScaleValue.getMin(); value <= xScaleValue.getMax(); value += xScaleValue.getScale()) {
				float y = (float) (rtChart.getY());
				float x = (float) (rtChart.getX() + ((value - xScaleValue.getMin()) * pixXPerValue));
				g.drawLine(x, y, x, y - rtChart.getHeight());
			}

			g.setStroke(style.getXAxisLineStroke(), style.getXAxisLineColor());
			for (double value = xScaleValue.getMin(); value <= xScaleValue.getMax(); value += xScaleValue.getScale()) {
				float y = (float) (rtChart.getY());
				float x = (float) (rtChart.getX() + ((value - xScaleValue.getMin()) * pixXPerValue));
				g.drawLine(x, y, x, y - 6);
			}
		}

		// Draw Y axis
		g.setStroke(style.getYAxisLineStroke(), style.getYAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX(), rtChart.getY() - rtChart.getHeight());

		// Draw X axis
		g.setStroke(style.getXAxisLineStroke(), style.getXAxisLineColor());
		g.drawLine(rtChart.getX(), rtChart.getY(), rtChart.getX() + rtChart.getWidth(), rtChart.getY());

		if (null != dataset) {
			List<ScatterSeries> seriesList = dataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				ScatterSeries series = seriesList.get(index);
				List<ScatterSeriesPoint> points = series.getPoints();

				g.setClip(rtChart.getX(), rtChart.getY() - rtChart.getHeight(), rtChart.getWidth(), rtChart.getHeight());

				// Draw series fill
				{
					int xps[] = new int[points.size() + 2];
					int yps[] = new int[points.size() + 2];
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);
						xps[j + 1] = (int) (rtChart.getX() + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
						yps[j + 1] = (int) (rtChart.getY() - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
					}
					xps[0] = (int) (rtChart.getX());
					yps[0] = (int) (rtChart.getY());
					xps[points.size() + 1] = (int) (rtChart.getX() + rtChart.getWidth());
					yps[points.size() + 1] = (int) (rtChart.getY());

					Color color = style.getSeriesFillColor(index, series);
					if (null != color) {
						GradientPaint paint = new GradientPaint(0f, rtChart.getY() - rtChart.getHeight(), color, 0f, rtChart.getY(), new Color(
								color.getRed(), color.getGreen(), color.getBlue(), 0));
						g.setPaint(paint);
						g.fillPolygon(new Polygon(xps, yps, points.size() + 2));
					}
				}

				// Draw series line
				{
					float xps[] = new float[points.size()];
					float yps[] = new float[points.size()];
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);
						xps[j] = (int) (rtChart.getX() + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
						yps[j] = (int) (rtChart.getY() - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
					}
					g.setStroke(style.getSeriesStroke(index, series), style.getSeriesStrokeColor(index, series));
					g.drawPolyline(xps, yps, points.size());
				}

				g.clearClip();

				// Draw series marker
				{
					Marker seriesMarker = style.getSeriesMarker(index, series);
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);

						if (point.getX() < xScaleValue.getMin() || point.getX() > xScaleValue.getMax() || point.getY() < yScaleValue.getMin()
								|| point.getY() > yScaleValue.getMax()) {
							continue;
						}

						Marker pointMarker = style.getSeriesPointMarker(index, series, j, point);
						Marker marker = (null != pointMarker) ? pointMarker : seriesMarker;
						if (null != marker) {
							float xMarker = (float) (rtChart.getX() + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
							float yMarker = (float) (rtChart.getY() - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
							Size size = marker.getSize();

							int mx = (0 == (int) size.getWidth() % 2) ? 0 : 1;
							int my = (0 == (int) size.getHeight() % 2) ? 0 : 1;
							marker.draw(g, xMarker - (size.getWidth() / 2) + mx, yMarker - (size.getHeight() / 2) + my);
						}

					}

				}

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

	private ScaleValue[] getXYScaleValue() {
		ScatterDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double xDataMinValue = null;
		Double xDataMaxValue = null;
		Double yDataMinValue = null;
		Double yDataMaxValue = null;
		if (null != dataset) {
			for (ScatterSeries series : dataset.getSeriesList()) {
				for (ScatterSeriesPoint point : series.getPoints()) {
					if (null == xDataMinValue) {
						xDataMinValue = point.getX();
						xDataMaxValue = point.getX();
						yDataMinValue = point.getY();
						yDataMaxValue = point.getY();
					} else {
						xDataMinValue = Math.min(xDataMinValue, point.getX());
						xDataMaxValue = Math.max(xDataMaxValue, point.getX());
						yDataMinValue = Math.min(yDataMinValue, point.getY());
						yDataMaxValue = Math.max(yDataMaxValue, point.getY());
					}
				}
			}
		}
		debug(String.format("X data minimum value : %f", xDataMinValue));
		debug(String.format("X data maximum value : %f", xDataMaxValue));
		debug(String.format("Y data minimum value : %f", yDataMinValue));
		debug(String.format("Y data maximum value : %f", yDataMaxValue));

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double xMinValue = axisX.getMinimumValue();
		double xMaxValue = axisX.getMaximumValue();
		double xScale = axisX.getScale();
		if (axisX.isMinimumValueAutoFit()) {
			if (null != xDataMinValue) {
				xMinValue = xDataMinValue;
			}
		}
		if (axisX.isMaximumValueAutoFit()) {
			if (null != xDataMaxValue) {
				xMaxValue = xDataMaxValue;
			}
		}
		if (axisX.isScaleAutoFit()) {
			double dif = xMaxValue - xMinValue;
			int logDif = (int) (Math.log10(dif));
			double scaleDif = Math.pow(10, logDif);
			if (dif <= scaleDif * 1) {
				xScale = scaleDif / 5;
			} else if (dif <= scaleDif * 2.5) {
				xScale = scaleDif / 2;
			} else if (dif <= scaleDif * 5) {
				xScale = scaleDif;
			} else {
				xScale = scaleDif * 2;
			}
		}
		ScaleValue xScaleValue = new ScaleValue(xMinValue, xMaxValue, xScale);
		debug(String.format("X axis minimum value : %f", xMinValue));
		debug(String.format("X axis maximum value : %f", xMaxValue));
		debug(String.format("X axis scale value : %f", xScale));

		double yMinValue = axisY.getMinimumValue();
		double yMaxValue = axisY.getMaximumValue();
		double yScale = axisY.getScale();
		if (axisY.isMinimumValueAutoFit()) {
			if (null != yDataMinValue) {
				yMinValue = yDataMinValue;
			}
		}
		if (axisY.isMaximumValueAutoFit()) {
			if (null != yDataMaxValue) {
				yMaxValue = yDataMaxValue;
			}
		}
		if (axisY.isScaleAutoFit()) {
			double dif = yMaxValue - yMinValue;
			int logDif = (int) (Math.log10(dif));
			double scaleDif = Math.pow(10, logDif);
			if (dif <= scaleDif * 1) {
				yScale = scaleDif / 5;
			} else if (dif <= scaleDif * 2.5) {
				yScale = scaleDif / 2;
			} else if (dif <= scaleDif * 5) {
				yScale = scaleDif;
			} else {
				yScale = scaleDif * 2;
			}
		}
		ScaleValue yScaleValue = new ScaleValue(yMinValue, yMaxValue, yScale);
		debug(String.format("Y axis minimum value : %f", yMinValue));
		debug(String.format("Y axis maximum value : %f", yMaxValue));
		debug(String.format("Y axis scale value : %f", yScale));

		ScaleValue[] result = new ScaleValue[2];
		result[0] = xScaleValue;
		result[1] = yScaleValue;
		return result;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aXScaleValue, final ScaleValue aYScaleValue,
			final float aFontMargin) {
		ScatterChartDesign design = getChartDesign();
		ScatterChartStyle style = design.getChartStyle();

		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		double xDifValue = aXScaleValue.getDiff();
		double yDifValue = aYScaleValue.getDiff();

		// スケール計算(プレ)
		double pixXPerValue = (aRtChart.getWidth() - margin.getHorizontalSize()) / xDifValue;
		double pixYPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / yDifValue;
		{
			{
				float maxYLabelWidth = 0.0f;
				FontMetrics fm = g.getFontMetrics(style.getYAxisFont());
				DisplayFormat df = axisY.getDisplayFormat();
				for (double value = aYScaleValue.getMin(); value <= aYScaleValue.getMax(); value += aYScaleValue.getScale()) {
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
			pixXPerValue = (aRtChart.getWidth() - margin.getHorizontalSize()) / xDifValue;
			pixYPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / yDifValue;

			{
				FontMetrics fm = g.getFontMetrics(style.getXAxisFont());
				DisplayFormat df = axisX.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = aRtChart.getWidth();
				for (double value = aXScaleValue.getMin(); value <= aXScaleValue.getMax(); value += aXScaleValue.getScale()) {
					String str = df.toString(value);
					int width = fm.stringWidth(str);
					if (0 < width) {
						margin.setBottom(style.getXAxisFont().getSize() + aFontMargin);

						float x = (float) (margin.getLeft() + ((value - aXScaleValue.getMin()) * pixXPerValue));
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
			pixXPerValue = (aRtChart.getWidth() - margin.getHorizontalSize()) / xDifValue;
			pixYPerValue = (aRtChart.getHeight() - margin.getVerticalSize()) / yDifValue;

			{
				int fontHeight = style.getYAxisFont().getSize();
				DisplayFormat df = axisY.getDisplayFormat();
				float minTop = 0.f;
				for (double value = aYScaleValue.getMin(); value <= aYScaleValue.getMax(); value += aYScaleValue.getScale()) {
					float y = (float) (aRtChart.getHeight() - margin.getBottom() - pixYPerValue * (value - aYScaleValue.getMin()));
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

		}

		return margin;
	}
}
