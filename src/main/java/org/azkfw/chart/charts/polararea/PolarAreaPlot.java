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
package org.azkfw.chart.charts.polararea;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.List;

import org.azkfw.chart.charts.polararea.PolarAreaChartDesign.PolarAreaChartStyle;
import org.azkfw.chart.charts.polararea.PolarAreaSeries.PolarAreaSeriesPoint;
import org.azkfw.chart.plot.AbstractSeriesPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、鶏頭図グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarAreaPlot extends AbstractSeriesPlot<PolarAreaDataset, PolarAreaChartDesign> {

	/** 軸情報 */
	private PolarAreaAxis axis;

	/**
	 * コンストラクタ
	 */
	public PolarAreaPlot() {
		super(PolarAreaPlot.class);

		axis = new PolarAreaAxis();

		setChartDesign(PolarAreaChartDesign.DefalutDesign);
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public PolarAreaAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		PolarAreaDataset dataset = getDataset();
		PolarAreaChartDesign design = getChartDesign();
		PolarAreaChartStyle style = design.getChartStyle();

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

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillArc(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight(), 0, 360);
		}

		// Draw assist axis
		if (axis.isAssistAxis()) {
			g.setColor(style.getAssistAxisLineColor());
			g.setStroke(style.getAssistAxisLineStroke());
			for (double angle = 0.0; angle < 360.f; angle += axis.getAssistAxisAngle()) {
				if (0.0 == angle || 90.0 == angle || 180.0 == angle || 270.0 == angle) {
					// TODO: 主軸がある時のみ描画しない処理を追加
					continue;
				}
				float x = (float) (ptChartMiddle.getX() + (pixPerValue * scaleValue.getMax() * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixPerValue * scaleValue.getMax() * Math.sin(RADIANS(angle))));
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
			float rangeX = (float) (pixPerValue * (value - scaleValue.getMin()));
			float rangeY = (float) (pixPerValue * (value - scaleValue.getMin()));

			g.drawArc(ptChartMiddle.getX() - rangeX, ptChartMiddle.getY() - rangeY, rangeX * 2.f, rangeY * 2.f, 0, 360);
		}

		// Draw axis scale
		g.setStroke(style.getAxisLineStroke(), style.getAxisLineColor());
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float range = (float) (pixPerValue * (value - scaleValue.getMin()));

			g.drawLine(ptChartMiddle.getX() + range, ptChartMiddle.getY(), ptChartMiddle.getX() + range, ptChartMiddle.getY() + fontMargin);
		}

		// Draw dataset
		drawDataset(g, dataset, scaleValue, style, rtChart);

		// Draw axis scale
		FontMetrics fm = g.getFontMetrics(style.getAxisFont());
		g.setColor(style.getAxisFontColor());
		g.setFont(style.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float range = (float) (pixPerValue * (value - scaleValue.getMin()));

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawStringA(str, ptChartMiddle.getX() + range - (strWidth / 2), ptChartMiddle.getY() + fontMargin);
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

	private void drawDataset(final Graphics g, final PolarAreaDataset aDataset, final ScaleValue aScaleValue, final PolarAreaChartStyle aStyle,
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
				g.setClip(ellipse);
			}

			float maxRange = (float) ((aScaleValue.getMax() - aScaleValue.getMin()) * pixPerValue);

			// Draw series
			List<PolarAreaSeries> seriesList = aDataset.getSeriesList();
			for (int index = 0; index < seriesList.size(); index++) {
				PolarAreaSeries series = seriesList.get(index);

				Color fillColorSeries = aStyle.getSeriesFillColor(index, series);
				Stroke strokeSeries = aStyle.getSeriesStroke(index, series);
				Color strokeColorSeries = aStyle.getSeriesStrokeColor(index, series);

				List<PolarAreaSeriesPoint> points = series.getPoints();
				int angle = 360 / points.size();
				for (int i = 0; i < points.size(); i++) {
					PolarAreaSeriesPoint point = points.get(i);

					double value = point.getRange();
					if (0 == value) {
						continue;
					}

					float range = (float) (pixPerValue * value);
					range = pixelLimit(range);

					// Draw series fill
					Color fillColor = (Color) getNotNullObject(aStyle.getSeriesFillColor(index, series, i, point), fillColorSeries);
					if (ObjectUtility.isAllNotNull(fillColor)) {
						float[] dist = { 0.0f, 1.0f };
						Color[] colors = { new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), 0), fillColor };
						RadialGradientPaint gradient = new RadialGradientPaint(ptMiddle.getX(), ptMiddle.getY(), maxRange, dist, colors,
								MultipleGradientPaint.CycleMethod.NO_CYCLE);
						g.setPaint(gradient);

						// g.setColor(fillColor);
						g.fillArc(ptMiddle.getX() - range + 1, ptMiddle.getY() - range + 1, range * 2.f, range * 2, i * angle, angle);
					}
					// Draw series line
					Color strokeColor = (Color) getNotNullObject(aStyle.getSeriesStrokeColor(index, series, i, point), strokeColorSeries);
					Stroke stroke = (Stroke) getNotNullObject(aStyle.getSeriesStroke(index, series, i, point), strokeSeries);
					if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
						g.setStroke(stroke, strokeColor);
						g.drawArc(ptMiddle.getX() - range + 1, ptMiddle.getY() - range + 1, range * 2.f, range * 2, i * angle, angle);
					}
				}
			}

			if (!aStyle.isOverflow()) {
				g.clearClip();
			}
		}
	}

	private ScaleValue getScaleValue() {
		PolarAreaDataset dataset = getDataset();

		// データ最小値・最大値取得
		Double dataMaxValue = null;
		Double dataMinValue = null;
		if (null != dataset) {
			for (PolarAreaSeries series : dataset.getSeriesList()) {
				for (PolarAreaSeriesPoint point : series.getPoints()) {
					if (null == dataMaxValue) {
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
		debug(String.format("Axis minimum value : %f", minValue));
		debug(String.format("Axis maximum value : %f", maxValue));
		debug(String.format("Axis scale value : %f", scale));

		return scaleValue;
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		PolarAreaChartDesign design = getChartDesign();
		PolarAreaChartStyle style = design.getChartStyle();

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
