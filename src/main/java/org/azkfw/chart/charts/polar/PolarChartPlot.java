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
package org.azkfw.chart.charts.polar;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.List;

import org.azkfw.chart.charts.polar.PolarChartDesign.PolarChartStyle;
import org.azkfw.chart.charts.polar.PolarSeries.PolarSeriesPoint;
import org.azkfw.chart.core.plot.AbstractSeriesChartPlot;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、極座標グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarChartPlot extends AbstractSeriesChartPlot<PolarDataset, PolarChartDesign> {

	/** 軸情報 */
	private PolarAxis axis;

	/**
	 * コンストラクタ
	 */
	public PolarChartPlot() {
		super(PolarChartPlot.class);

		axis = new PolarAxis();

		setChartDesign(PolarChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public PolarChartPlot(final PolarDataset aDataset) {
		super(PolarChartPlot.class, aDataset);

		axis = new PolarAxis();

		setChartDesign(PolarChartDesign.DefalutDesign);
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public PolarAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDrawChart(final Graphics g, final Rect aRect) {
		PolarDataset dataset = getDataset();
		PolarChartDesign design = getChartDesign();
		PolarChartStyle style = design.getChartStyle();

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
		rtChart.setY(rtChartPre.getY() + margin.getTop());
		rtChart.setWidth(rtChartPre.getWidth() - margin.getHorizontalSize());
		rtChart.setHeight(rtChartPre.getHeight() - margin.getVerticalSize());

		Point ptChartMiddle = new Point(rtChart.getX() + (rtChart.getWidth() / 2.f), rtChart.getY() + (rtChart.getHeight() / 2.f));

		// 円に調整
		float minRange = Math.min(rtChart.getWidth(), rtChartPre.getHeight());
		rtChart.setX(ptChartMiddle.getX() - (minRange / 2));
		rtChart.setY(ptChartMiddle.getY() - (minRange / 2));
		rtChart.setWidth(minRange);
		rtChart.setHeight(minRange);

		// スケール計算(円のためX,Yどちらでもいい)
		double difValue = scaleValue.getDiff();
		double pixPerValue = (rtChart.getWidth() / 2.f) / difValue;

		// 背景描画
		if (ObjectUtility.isNotNull(style.getBackgroundColor())) {
			g.setColor(style.getBackgroundColor());
			g.fillArc(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight(), 0, 360);
		}

		{ // Y軸
			// 目盛線描画
			Stroke scaleLineStroke = style.getAxisScaleLineStroke();
			Color scaleLineColor = style.getAxisScaleLineColor();
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor)) {
				g.setStroke(scaleLineStroke, scaleLineColor);
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float range = (float) (pixPerValue * (value - scaleValue.getMin()));

					g.drawArc(ptChartMiddle.getX() - range, ptChartMiddle.getY() - range, range * 2.f, range * 2.f, 0, 360);
				}
			}
			// 軸線描画
			Stroke lineStroke = style.getAxisLineStroke();
			Color lineColor = style.getAxisLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				g.setStroke(lineStroke, lineColor);
				g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX() + (rtChart.getWidth() / 2.f), ptChartMiddle.getY());
				// 目盛
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float range = (float) (pixPerValue * (value - scaleValue.getMin()));

					g.drawLine(ptChartMiddle.getX() + range, ptChartMiddle.getY(), ptChartMiddle.getX() + range, ptChartMiddle.getY() + fontMargin);
				}
			}
		}
		{ // X軸
			// 目盛線描画
			Stroke scaleLineStroke = style.getCircleScaleLineStroke();
			Color scaleLineColor = style.getCircleScaleLineColor();
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor)) {
				g.setStroke(scaleLineStroke, scaleLineColor);
				for (double angle = 0.0; angle < 360.f; angle += style.getCircleScaleAngle()) {
					float x = (float) (ptChartMiddle.getX() + ((scaleValue.getDiff()) * pixPerValue * Math.cos(RADIANS(angle))));
					float y = (float) (ptChartMiddle.getY() - ((scaleValue.getDiff()) * pixPerValue * Math.sin(RADIANS(angle))));

					g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), x, y);
				}
			}
			// 軸線描画
			Stroke lineStroke = style.getCircleLineStroke();
			Color lineColor = style.getCircleLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				float range = (float) (pixPerValue * scaleValue.getDiff());

				g.setStroke(lineStroke, lineColor);
				g.drawArc(ptChartMiddle.getX() - range, ptChartMiddle.getY() - range, range * 2.f, range * 2.f, 0, 360);
			}
		}

		// Draw dataset
		drawDataset(g, dataset, scaleValue, style, rtChart);

		// 目盛描画(Y軸)
		Font scaleLabelFont = style.getAxisScaleLabelFont();
		Color scaleLabelColor = style.getAxisScaleLabelColor();
		if (ObjectUtility.isAllNotNull(scaleLabelFont, scaleLabelColor)) {
			FontMetrics fm = g.getFontMetrics(style.getAxisScaleLabelFont());
			DisplayFormat df = axis.getDisplayFormat();
			g.setFont(scaleLabelFont, scaleLabelColor);
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				String str = df.toString(value);
				if (StringUtility.isNotEmpty(str)) {
					int strWidth = fm.stringWidth(str);
					float range = (float) (pixPerValue * (value - scaleValue.getMin()));

					g.drawStringA(str, ptChartMiddle.getX() + range - (strWidth / 2), ptChartMiddle.getY() + fontMargin);
				}
			}
		}

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

	private void drawDataset(final Graphics g, final PolarDataset aDataset, final ScaleValue aScaleValue, final PolarChartStyle aStyle,
			final Rect aRect) {
		if (ObjectUtility.isNotNull(aDataset)) {
			// 中心座標取得
			Point ptMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));
			// スケール計算(円のためX,Yどちらでもいい)
			double difValue = aScaleValue.getDiff();
			double pixPerValue = (aRect.getWidth() / 2.f) / difValue;

			Ellipse2D ellipse = null;
			if (!aStyle.isOverflow()) {
				ellipse = new Ellipse2D.Double(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());
			}

			float maxRange = (float) ((aScaleValue.getMax() - aScaleValue.getMin()) * pixPerValue);

			// Draw series
			List<PolarSeries> seriesList = aDataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				PolarSeries series = seriesList.get(index);

				List<PolarSeriesPoint> points = series.getPoints();
				float[] pxs = new float[points.size() + 1];
				float[] pys = new float[points.size() + 1];
				for (int i = 0; i < points.size(); i++) {
					PolarSeriesPoint point = points.get(i);
					double value = point.getRange();

					float x = (float) (ptMiddle.getX() + pixPerValue * (value - aScaleValue.getMin()) * Math.cos(RADIANS(point.getAngle())));
					float y = (float) (ptMiddle.getY() - pixPerValue * (value - aScaleValue.getMin()) * Math.sin(RADIANS(point.getAngle())));
					pxs[i] = (int) pixelLimit(x);
					pys[i] = (int) pixelLimit(y);
				}
				pxs[points.size()] = pxs[0];
				pys[points.size()] = pys[0];

				if (!aStyle.isOverflow()) {
					g.setClip(ellipse);
				}

				// Draw series fill
				Color fillColor = aStyle.getSeriesFillColor(index, series);
				if (ObjectUtility.isNotNull(fillColor)) {
					float[] dist = { 0.0f, 1.0f };
					Color[] colors = { new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 0), fillColor };
					RadialGradientPaint gradient = new RadialGradientPaint(ptMiddle.getX(), ptMiddle.getY(), maxRange, dist, colors,
							MultipleGradientPaint.CycleMethod.NO_CYCLE);
					g.setPaint(gradient);

					// g.setColor(fillColor);
					g.fillPolygon(pxs, pys, points.size() + 1);
				}
				// Draw series line
				Stroke stroke = aStyle.getSeriesStroke(index, series);
				Color strokeColor = aStyle.getSeriesStrokeColor(index, series);
				if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
					g.setStroke(stroke, strokeColor);
					g.drawPolygon(pxs, pys, points.size() + 1);
				}

				if (!aStyle.isOverflow()) {
					g.clearClip();
				}

				// Draw series marker
				{
					Marker seriesMarker = aStyle.getSeriesMarker(index, series);
					for (int j = 0; j < points.size(); j++) {
						PolarSeriesPoint point = points.get(j);

						if (!aStyle.isOverflow()) {
							if (point.getRange() < aScaleValue.getMin() || point.getRange() > aScaleValue.getMax()) {
								continue;
							}
						}

						Marker pointMarker = aStyle.getSeriesPointMarker(index, series, j, point);
						Marker marker = (Marker) ObjectUtility.getNotNullObject(pointMarker, seriesMarker);
						if (ObjectUtility.isNotNull(marker)) {
							double value = point.getRange();
							float x = (float) (ptMiddle.getX() + pixPerValue * (value - aScaleValue.getMin()) * Math.cos(RADIANS(point.getAngle())));
							float y = (float) (ptMiddle.getY() - pixPerValue * (value - aScaleValue.getMin()) * Math.sin(RADIANS(point.getAngle())));
							x = pixelLimit(x);
							y = pixelLimit(y);

							Size size = marker.getSize();

							int mx = (0 == (int) size.getWidth() % 2) ? 0 : 1;
							int my = (0 == (int) size.getHeight() % 2) ? 0 : 1;
							marker.draw(g, x - (size.getWidth() / 2) + mx, y - (size.getHeight() / 2) + my);
						}

					}

				}
			}
		}

	}

	private ScaleValue getScaleValue() {
		PolarDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (null != dataset) {
			for (PolarSeries series : dataset.getSeriesList()) {
				for (PolarSeriesPoint point : series.getPoints()) {
					if (null == dataMinValue) {
						dataMinValue = point.getRange();
						dataMaxValue = point.getRange();
					} else {
						dataMinValue = Math.min(dataMinValue, point.getRange());
						dataMaxValue = Math.max(dataMaxValue, point.getRange());
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
		double scale = axis.getScale();
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
		if (axis.isScaleAutoFit()) {
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
		debug(String.format("Axis minimum value : %f", minValue));
		debug(String.format("Axis maximum value : %f", maxValue));
		debug(String.format("Axis scale value : %f", scale));

		return scaleValue;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		PolarChartDesign design = getChartDesign();
		PolarChartStyle style = design.getChartStyle();

		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		Point ptChartMiddle = new Point(aRtChart.getX() + (aRtChart.getWidth() / 2.f), aRtChart.getY() + (aRtChart.getHeight() / 2.f));

		double difValue = aScaleValue.getDiff();

		// スケール計算(プレ)
		double pixPerValue = ((aRtChart.getWidth() - margin.getHorizontalSize()) / 2.f) / difValue;

		float maxX = aRtChart.getX() + aRtChart.getWidth();

		// Draw axis scale
		FontMetrics fm = g.getFontMetrics(style.getAxisScaleLabelFont());
		DisplayFormat df = axis.getDisplayFormat();
		for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
			String str = df.toString(value);
			if (StringUtility.isNotEmpty(str)) {
				int strWidth = fm.stringWidth(str);

				float range = (float) (pixPerValue * (value - aScaleValue.getMin()));
				float x = ptChartMiddle.getX() + range + (strWidth / 2);

				maxX = Math.max(maxX, x);
			}
		}

		if (maxX > aRtChart.getX() + aRtChart.getWidth()) {
			margin.addRight(maxX - (aRtChart.getX() + aRtChart.getWidth()));
		}

		return margin;
	}

	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
