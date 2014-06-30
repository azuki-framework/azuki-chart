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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.geom.Ellipse2D;
import java.util.List;

import org.azkfw.chart.charts.polar.PolarChartDesign.PolarChartStyle;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、極座標グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarPlot extends AbstractSeriesPlot<PolarDataset, PolarChartDesign> {

	/** 軸情報 */
	private PolarAxis axis;

	/**
	 * コンストラクタ
	 */
	public PolarPlot() {
		super(PolarPlot.class);

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
	protected boolean doDraw(final Graphics g, final Rect aRect) {
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
		rtChart.setX(ptChartMiddle.getX() - (minRange/2));
		rtChart.setY(ptChartMiddle.getY() - (minRange/2));
		rtChart.setWidth(minRange);
		rtChart.setHeight(minRange);

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixXPerValue = (rtChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (rtChart.getHeight() / 2.f) / difValue;

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillArc(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight(), 0, 360);
		}

		// Draw assist axis
		if (axis.isAssistAxis()) {
			g.setStroke(style.getAssistAxisLineStroke(), style.getAssistAxisLineColor());
			for (double angle = 0.0; angle < 360.f; angle += axis.getAssistAxisAngle()) {
				if (0.0 == angle || 90.0 == angle || 180.0 == angle || 270.0 == angle) {
					// TODO: 主軸がある時のみ描画しない処理を追加
					continue;
				}
				float x = (float) (ptChartMiddle.getX() + (pixXPerValue * scaleValue.getMax() * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixYPerValue * scaleValue.getMax() * Math.sin(RADIANS(angle))));
				g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), x, y);
			}
		}

		// Draw axis
		g.setStroke(style.getAxisLineStroke(), style.getAxisLineColor());
		g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX() - (rtChart.getWidth() / 2.f), ptChartMiddle.getY());
		g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX() + (rtChart.getWidth() / 2.f), ptChartMiddle.getY());
		g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX(), ptChartMiddle.getY() - (rtChart.getHeight() / 2.f));
		g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), ptChartMiddle.getX(), ptChartMiddle.getY() + (rtChart.getHeight() / 2.f));

		// Draw circle
		g.setStroke(style.getAxisCircleStroke(), style.getAxisCircleColor());
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float rangeX = (float) (pixXPerValue * (value - scaleValue.getMin()));
			float rangeY = (float) (pixYPerValue * (value - scaleValue.getMin()));

			g.drawArc(ptChartMiddle.getX() - rangeX, ptChartMiddle.getY() - rangeY, rangeX * 2.f, rangeY * 2.f, 0, 360);
		}

		// Draw axis scale
		g.setStroke(style.getAxisLineStroke(), style.getAxisLineColor());
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float range = (float) (pixXPerValue * (value - scaleValue.getMin()));

			g.drawLine(ptChartMiddle.getX() + range, ptChartMiddle.getY(), ptChartMiddle.getX() + range, ptChartMiddle.getY() + fontMargin);
		}

		Ellipse2D ellipse = null;
		if (!style.isOverflow()) {
			ellipse = new Ellipse2D.Double(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight());
		}

		// Draw series
		List<PolarSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			PolarSeries series = seriesList.get(index);

			List<PolarSeriesPoint> points = series.getPoints();
			float[] pxs = new float[points.size() + 1];
			float[] pys = new float[points.size() + 1];
			for (int i = 0; i < points.size(); i++) {
				PolarSeriesPoint point = points.get(i);

				float x = (float) (ptChartMiddle.getX() + pixXPerValue * point.getRange() * Math.cos(RADIANS(point.getAngle())));
				float y = (float) (ptChartMiddle.getY() - pixYPerValue * point.getRange() * Math.sin(RADIANS(point.getAngle())));
				pxs[i] = (int) pixelLimit(x);
				pys[i] = (int) pixelLimit(y);
			}
			pxs[points.size()] = pxs[0];
			pys[points.size()] = pys[0];

			if (!style.isOverflow()) {
				g.setClip(ellipse);
			}

			// Draw series fill
			Color fillColor = style.getSeriesFillColor(index, series);
			if (null != fillColor) {
				g.setColor(fillColor);
				g.fillPolygon(pxs, pys, points.size() + 1);
			}
			// Draw series line
			Color strokeColor = style.getSeriesStrokeColor(index, series);
			if (null != strokeColor) {
				g.setStroke(style.getSeriesStroke(index, series), strokeColor);
				g.drawPolygon(pxs, pys, points.size() + 1);
			}

			if (!style.isOverflow()) {
				g.clearClip();
			}

			// Draw series marker
			{
				Marker seriesMarker = style.getSeriesMarker(index, series);
				for (int j = 0; j < points.size(); j++) {
					PolarSeriesPoint point = points.get(j);

					if (point.getRange() < scaleValue.getMin() || point.getRange() > scaleValue.getMax()) {
						continue;
					}

					Marker pointMarker = style.getSeriesPointMarker(index, series, j, point);
					Marker marker = (null != pointMarker) ? pointMarker : seriesMarker;
					if (null != marker) {
						float x = (float) (ptChartMiddle.getX() + pixXPerValue * point.getRange() * Math.cos(RADIANS(point.getAngle())));
						float y = (float) (ptChartMiddle.getY() - pixYPerValue * point.getRange() * Math.sin(RADIANS(point.getAngle())));
						x = (int) pixelLimit(x);
						y = (int) pixelLimit(y);

						Size size = marker.getSize();

						int mx = (0 == (int) size.getWidth() % 2) ? 0 : 1;
						int my = (0 == (int) size.getHeight() % 2) ? 0 : 1;
						marker.draw(g, x - (size.getWidth() / 2) + mx, y - (size.getHeight() / 2) + my);
					}

				}

			}
		}

		// Draw axis scale
		FontMetrics fm = g.getFontMetrics(style.getAxisFont());
		g.setColor(style.getAxisFontColor());
		g.setFont(style.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float range = (float) (pixXPerValue * (value - scaleValue.getMin()));

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawStringA(str, ptChartMiddle.getX() + range - (strWidth / 2), ptChartMiddle.getY() + fontMargin);
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
		if (axis.isMinimumValueAutoFit()) {
			if (null != dataMinValue) {
				minValue = dataMinValue;
			}
		}
		if (axis.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axis.isScaleAutoFit()) {
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
		FontMetrics fm = g.getFontMetrics(style.getAxisFont());
		for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
			float range = (float) (pixPerValue * (value - aScaleValue.getMin()));

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);

			float x = ptChartMiddle.getX() + range + (strWidth / 2);

			maxX = Math.max(maxX, x);
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
