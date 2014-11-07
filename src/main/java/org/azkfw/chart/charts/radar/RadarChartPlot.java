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
package org.azkfw.chart.charts.radar;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.MultipleGradientPaint;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;
import java.util.List;

import org.azkfw.chart.charts.radar.RadarChartDesign.RadarChartStyle;
import org.azkfw.chart.charts.radar.RadarSeries.RadarSeriesPoint;
import org.azkfw.chart.core.element.TitleElement;
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
 * このクラスは、レーダーチャートのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarChartPlot extends AbstractSeriesChartPlot<RadarDataset, RadarChartDesign> {

	/** 軸情報 */
	private RadarAxis axis;

	/**
	 * コンストラクタ
	 */
	public RadarChartPlot() {
		super(RadarChartPlot.class);

		axis = new RadarAxis();

		setChartDesign(RadarChartDesign.DefalutDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public RadarChartPlot(final RadarDataset aDataset) {
		super(RadarChartPlot.class, aDataset);

		axis = new RadarAxis();

		setChartDesign(RadarChartDesign.DefalutDesign);
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public RadarAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDrawChart(final Graphics g, final Rect aRect) {
		RadarDataset dataset = getDataset();
		RadarChartDesign design = getChartDesign();
		RadarChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// エレメント作成 ////////////////////////////////
		TitleElement elementTitle = null;
		if (ObjectUtility.isAllNotNull(dataset, design)) {
			elementTitle = createTitleElement(dataset.getTitle(), design.getTitleStyle());
		}
		/////////////////////////////////////////////

		// エレメント配備 ////////////////////////////////
		// タイトル配備
		Rect rtTitle = null;
		if (ObjectUtility.isNotNull(elementTitle)) {
			rtTitle = elementTitle.deploy(g, rtChartPre);
		}
		// 凡例適用
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);
		/////////////////////////////////////////////

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

		// 正多角形調整
		float minRange = Math.min(rtChart.getWidth(), rtChartPre.getHeight());
		rtChart.setX(ptChartMiddle.getX() - (minRange / 2));
		rtChart.setY(ptChartMiddle.getY() - (minRange / 2));
		rtChart.setWidth(minRange);
		rtChart.setHeight(minRange);

		// スケール計算(正多角形のためX,Yどちらでもいい)
		double difValue = scaleValue.getDiff();
		double pixPerValue = (rtChart.getWidth() / 2.f) / difValue;

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}
		debug(String.format("Data point size : %d", dataPointSize));

		Polygon polygon = null;
		{
			int[] pxs = new int[dataPointSize + 1];
			int[] pys = new int[dataPointSize + 1];
			for (int i = 0; i < dataPointSize; i++) {
				double angle = -1 * (360.f / dataPointSize) * i + 90;
				double value = scaleValue.getMax();

				float x = (float) (ptChartMiddle.getX() + (pixPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
				pxs[i] = (int) pixelLimit(x);
				pys[i] = (int) pixelLimit(y);
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];

			polygon = new Polygon(pxs, pys, dataPointSize + 1);
		}

		// 背景描画
		if (ObjectUtility.isNotNull(style.getBackgroundColor())) {
			g.setColor(style.getBackgroundColor());
			g.fillPolygon(polygon);
		}

		{ // Y軸
			// 目盛線描画
			Stroke scaleLineStroke = style.getAxisScaleLineStroke();
			Color scaleLineColor = style.getAxisScaleLineColor();
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor)) {
				g.setStroke(scaleLineStroke, scaleLineColor);
				for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
					float[] pxs = new float[dataPointSize + 1];
					float[] pys = new float[dataPointSize + 1];
					for (int i = 0; i < dataPointSize; i++) {
						double angle = (360.f / dataPointSize) * i + 90;
						float x = (float) (ptChartMiddle.getX() + (pixPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
						float y = (float) (ptChartMiddle.getY() - (pixPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
						pxs[i] = x;
						pys[i] = y;
					}
					pxs[dataPointSize] = pxs[0];
					pys[dataPointSize] = pys[0];

					g.drawPolyline(pxs, pys, dataPointSize + 1);
				}
			}
			// 軸描画
			Stroke lineStroke = style.getAxisLineStroke();
			Color lineColor = style.getAxisLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				float y = (float) (ptChartMiddle.getY() - (pixPerValue * scaleValue.getDiff()));

				g.setStroke(lineStroke, lineColor);
				g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX(), y);
			}
		}
		{ // X軸
			// 目盛線描画
			Stroke scaleLineStroke = style.getCircleScaleLineStroke();
			Color scaleLineColor = style.getCircleScaleLineColor();
			if (ObjectUtility.isAllNotNull(scaleLineStroke, scaleLineColor)) {
				g.setStroke(scaleLineStroke, scaleLineColor);
				for (int i = 0; i < dataPointSize; i++) {
					double angle = (360.f / dataPointSize) * i + 90;
					float x = (float) (ptChartMiddle.getX() + (pixPerValue * scaleValue.getDiff() * Math.cos(RADIANS(angle))));
					float y = (float) (ptChartMiddle.getY() - (pixPerValue * scaleValue.getDiff() * Math.sin(RADIANS(angle))));

					g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), x, y);
				}
			}
			// 軸描画
			Stroke lineStroke = style.getCircleLineStroke();
			Color lineColor = style.getCircleLineColor();
			if (ObjectUtility.isAllNotNull(lineStroke, lineColor)) {
				float[] pxs = new float[dataPointSize + 1];
				float[] pys = new float[dataPointSize + 1];
				for (int i = 0; i < dataPointSize; i++) {
					double angle = (360.f / dataPointSize) * i + 90;
					float x = (float) (ptChartMiddle.getX() + (pixPerValue * scaleValue.getDiff() * Math.cos(RADIANS(angle))));
					float y = (float) (ptChartMiddle.getY() - (pixPerValue * scaleValue.getDiff() * Math.sin(RADIANS(angle))));
					pxs[i] = x;
					pys[i] = y;
				}
				pxs[dataPointSize] = pxs[0];
				pys[dataPointSize] = pys[0];

				g.setStroke(lineStroke, lineColor);
				g.drawPolyline(pxs, pys, dataPointSize + 1);
			}
		}

		// Draw dataset
		drawDataset(g, dataset, dataPointSize, scaleValue, style, rtChart, polygon);

		// 目盛ラベル描画(Y軸)
		Font scaleLabelFont = style.getAxisScaleLabelFont();
		Color scaleLabelColor = style.getAxisScaleLabelColor();
		if (ObjectUtility.isAllNotNull(scaleLabelFont, scaleLabelColor)) {
			int fontSize = style.getAxisScaleLabelFont().getSize();
			FontMetrics fm = g.getFontMetrics(style.getAxisScaleLabelFont());
			DisplayFormat df = axis.getDisplayFormat();

			g.setFont(scaleLabelFont, scaleLabelColor);
			for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
				String str = df.toString(value);
				if (StringUtility.isNotEmpty(str)) {
					int strWidth = fm.stringWidth(str);
					double rangeY = pixPerValue * (value - scaleValue.getMin());
					float x = (float) (ptChartMiddle.getX() - fontMargin - (strWidth));
					float y = (float) (ptChartMiddle.getY() - rangeY - (fontSize / 2));

					g.drawStringA(str, x, y);
				}
			}
		}

		// エレメント描画 ////////////////////////////////
		// Draw Legend
		if (ObjectUtility.isNotNull(rtLegend)) {
			drawLegend(g, design.getLegendStyle(), rtLegend);
		}
		// Draw title
		if (ObjectUtility.isNotNull(elementTitle)) {
			elementTitle.draw(g, rtTitle);
		}
		/////////////////////////////////////////////

		return true;
	}

	private void drawDataset(final Graphics g, final RadarDataset aDataset, final int aDataPointSize, final ScaleValue aScaleValue,
			final RadarChartStyle aStyle, final Rect aRect, final Polygon aPolygon) {
		if (ObjectUtility.isNotNull(aDataset)) {
			// 中心座標取得
			Point ptMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));
			// スケール計算(正多角形のためX,Yどちらでもいい)
			double difValue = aScaleValue.getDiff();
			double pixPerValue = (aRect.getWidth() / 2.f) / difValue;

			float maxRange = (float) ((aScaleValue.getMax() - aScaleValue.getMin()) * pixPerValue);

			// Draw series
			List<RadarSeries> seriesList = aDataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				RadarSeries series = seriesList.get(index);

				List<RadarSeriesPoint> points = series.getPoints();

				int[] pxs = new int[aDataPointSize + 1];
				int[] pys = new int[aDataPointSize + 1];
				for (int i = 0; i < points.size(); i++) {
					RadarSeriesPoint point = points.get(i);

					double angle = -1 * (360.f / aDataPointSize) * i + 90;
					double value = point.getValue();

					float x = (float) (ptMiddle.getX() + (pixPerValue * (value - aScaleValue.getMin()) * Math.cos(RADIANS(angle))));
					float y = (float) (ptMiddle.getY() - (pixPerValue * (value - aScaleValue.getMin()) * Math.sin(RADIANS(angle))));
					pxs[i] = (int) pixelLimit(x);
					pys[i] = (int) pixelLimit(y);
				}
				pxs[aDataPointSize] = pxs[0];
				pys[aDataPointSize] = pys[0];

				if (!aStyle.isOverflow()) {
					g.setClip(aPolygon);
				}

				// Draw series fill
				Color fillColor = aStyle.getSeriesFillColor(index, series);
				if (null != fillColor) {
					float[] dist = { 0.0f, 1.0f };
					Color[] colors = { new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 0), fillColor };
					RadialGradientPaint gradient = new RadialGradientPaint(ptMiddle.getX(), ptMiddle.getY(), maxRange, dist, colors,
							MultipleGradientPaint.CycleMethod.NO_CYCLE);
					g.setPaint(gradient);

					// g.setColor(fillColor);
					g.fillPolygon(pxs, pys, aDataPointSize + 1);
				}
				// Draw series line
				Stroke stroke = aStyle.getSeriesStroke(index, series);
				Color strokeColor = aStyle.getSeriesStrokeColor(index, series);
				if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
					g.setStroke(stroke, strokeColor);
					g.drawPolyline(pxs, pys, aDataPointSize + 1);
				}

				if (!aStyle.isOverflow()) {
					g.clearClip();
				}

				// Draw series marker
				{
					Marker seriesMarker = aStyle.getSeriesMarker(index, series);
					for (int j = 0; j < points.size(); j++) {
						RadarSeriesPoint point = points.get(j);

						if (!aStyle.isOverflow()) {
							if (point.getValue() < aScaleValue.getMin() || point.getValue() > aScaleValue.getMax()) {
								continue;
							}
						}

						Marker pointMarker = aStyle.getSeriesPointMarker(index, series, j, point);
						Marker marker = (Marker) ObjectUtility.getNotNullObject(pointMarker, seriesMarker);
						if (ObjectUtility.isNotNull(marker)) {
							double angle = -1 * (360.f / aDataPointSize) * j + 90;
							double value = point.getValue();

							float x = (float) (ptMiddle.getX() + (pixPerValue * (value - aScaleValue.getMin()) * Math.cos(RADIANS(angle))));
							float y = (float) (ptMiddle.getY() - (pixPerValue * (value - aScaleValue.getMin()) * Math.sin(RADIANS(angle))));
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
		RadarDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMaxValue = null;
		Double dataMinValue = null;
		if (null != dataset) {
			for (RadarSeries series : dataset.getSeriesList()) {
				for (RadarSeriesPoint point : series.getPoints()) {
					if (null == dataMaxValue) {
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
		RadarChartDesign design = getChartDesign();
		RadarChartStyle style = design.getChartStyle();

		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		Point ptChartMiddle = new Point(aRtChart.getX() + (aRtChart.getWidth() / 2.f), aRtChart.getY() + (aRtChart.getHeight() / 2.f));

		double difValue = aScaleValue.getDiff();

		// スケール計算(プレ)
		double pixPerValue = ((aRtChart.getHeight() - margin.getVerticalSize()) / 2.f) / difValue;

		float minY = aRtChart.getY();

		// Draw axis scale
		DisplayFormat df = axis.getDisplayFormat();
		int fontSize = style.getAxisScaleLabelFont().getSize();
		g.setFont(style.getAxisScaleLabelFont());
		for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
			String str = df.toString(value);
			if (StringUtility.isNotEmpty(str)) {
				double rangeY = pixPerValue * (value - aScaleValue.getMin());

				float y = (float) (ptChartMiddle.getY() - rangeY - (fontSize / 2));

				minY = Math.min(minY, y);
			}
		}

		if (minY < aRtChart.getY()) {
			margin.addTop(aRtChart.getY() - minY);
		}

		return margin;
	}

	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
