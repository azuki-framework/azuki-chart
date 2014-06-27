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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Polygon;
import java.util.List;

import org.azkfw.chart.looks.legend.LegendDesign;
import org.azkfw.chart.looks.legend.LegendDesign.LegendPosition;
import org.azkfw.chart.looks.marker.Marker;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、レーダーチャートのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarPlot extends AbstractPlot {

	/** 軸情報 */
	private RadarAxis axis;

	/** データセット */
	private RadarDataset dataset;

	/** Looks */
	private RadarLooks looks;

	/**
	 * コンストラクタ
	 */
	public RadarPlot() {
		axis = new RadarAxis();
		dataset = null;
		looks = new RadarLooks();
	}

	public void setLooks(final RadarLooks aLooks) {
		looks = aLooks;
	}

	/**
	 * データセットを設定する。
	 * 
	 * @param aDataset データセット
	 */
	public void setDataset(final RadarDataset aDataset) {
		dataset = aDataset;
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
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, dataset.getTitle(), looks.getTitleDesign(), rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, rtChartPre);

		// スケール調整
		ScaleValue scaleValue = getScaleValue();

		float fontMargin = 8.0f;

		Margin margin = fitChart(g, rtChartPre, scaleValue, fontMargin);
		debug(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(), margin.getBottom()));

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixXPerValue = ((rtChartPre.getWidth() - margin.getHorizontalSize()) / 2.f) / difValue;
		double pixYPerValue = ((rtChartPre.getHeight() - margin.getVerticalSize()) / 2.f) / difValue;

		Rect rtChart = new Rect();
		rtChart.setX(rtChartPre.getX() + margin.getLeft());
		rtChart.setY(rtChartPre.getY() + margin.getTop());
		rtChart.setWidth(rtChartPre.getWidth() - margin.getHorizontalSize());
		rtChart.setHeight(rtChartPre.getHeight() - margin.getVerticalSize());

		Point ptChartMiddle = new Point(rtChart.getX() + (rtChart.getWidth() / 2.f), rtChart.getY() + (rtChart.getHeight() / 2.f));

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}
		debug(String.format("Data point size : %d", dataPointSize));

		// Draw axis
		g.setStroke(looks.getAxisLineStroke(), looks.getAxisLineColor());
		for (int i = 0; i < dataPointSize; i++) {
			double angle = (360.f / dataPointSize) * i + 90;
			float x = (float) (ptChartMiddle.getX() + (pixXPerValue * scaleValue.getDiff() * Math.cos(RADIANS(angle))));
			float y = (float) (ptChartMiddle.getY() - (pixYPerValue * scaleValue.getDiff() * Math.sin(RADIANS(angle))));
			g.drawLine(ptChartMiddle.getX(), ptChartMiddle.getY(), x, y);
		}

		// Draw circle
		g.setStroke(looks.getAxisCircleStroke(), looks.getAxisCircleColor());
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float[] pxs = new float[dataPointSize + 1];
			float[] pys = new float[dataPointSize + 1];
			for (int i = 0; i < dataPointSize; i++) {
				double angle = (360.f / dataPointSize) * i + 90;
				float x = (float) (ptChartMiddle.getX() + (pixXPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixYPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
				pxs[i] = x;
				pys[i] = y;
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];
			g.drawPolyline(pxs, pys, dataPointSize + 1);
		}

		Polygon polygon = null;
		{
			int[] pxs = new int[dataPointSize + 1];
			int[] pys = new int[dataPointSize + 1];
			for (int i = 0; i < dataPointSize; i++) {
				double angle = -1 * (360.f / dataPointSize) * i + 90;
				double value = scaleValue.getMax();

				float x = (float) (ptChartMiddle.getX() + (pixXPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixYPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
				pxs[i] = (int) pixelLimit(x);
				pys[i] = (int) pixelLimit(y);
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];

			polygon = new Polygon(pxs, pys, dataPointSize + 1);
		}

		// Draw series
		List<RadarSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			RadarSeries series = seriesList.get(index);

			List<RadarSeriesPoint> points = series.getPoints();

			int[] pxs = new int[dataPointSize + 1];
			int[] pys = new int[dataPointSize + 1];
			for (int i = 0; i < points.size(); i++) {
				RadarSeriesPoint point = points.get(i);

				double angle = -1 * (360.f / dataPointSize) * i + 90;
				double value = point.getValue();

				float x = (float) (ptChartMiddle.getX() + (pixXPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixYPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
				pxs[i] = (int) pixelLimit(x);
				pys[i] = (int) pixelLimit(y);
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];

			g.setClip(polygon);

			// Draw series fill
			Color fillColor = looks.getSeriesFillColor(index, series);
			if (null != fillColor) {
				g.setColor(fillColor);
				g.fillPolygon(pxs, pys, dataPointSize + 1);
			}
			// Draw series line
			Color strokeColor = looks.getSeriesStrokeColor(index, series);
			if (null != strokeColor) {
				g.setStroke(looks.getSeriesStroke(index, series), strokeColor);
				g.drawPolyline(pxs, pys, dataPointSize + 1);
			}

			g.clearClip();

			// Draw series marker
			{
				Marker seriesMarker = looks.getSeriesMarker(index, series);
				for (int j = 0; j < points.size(); j++) {
					RadarSeriesPoint point = points.get(j);

					if (point.getValue() < scaleValue.getMin() || point.getValue() > scaleValue.getMax()) {
						continue;
					}

					Marker pointMarker = looks.getSeriesPointMarker(index, series, j, point);
					Marker marker = (null != pointMarker) ? pointMarker : seriesMarker;
					if (null != marker) {
						double angle = -1 * (360.f / dataPointSize) * j + 90;
						double value = point.getValue();

						float x = (float) (ptChartMiddle.getX() + (pixXPerValue * (value - scaleValue.getMin()) * Math.cos(RADIANS(angle))));
						float y = (float) (ptChartMiddle.getY() - (pixYPerValue * (value - scaleValue.getMin()) * Math.sin(RADIANS(angle))));
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

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		FontMetrics fm = g.getFontMetrics(looks.getAxisFont());
		g.setColor(looks.getAxisFontColor());
		g.setFont(looks.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			double rangeY = pixYPerValue * (value - scaleValue.getMin());

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			float x = (float) (ptChartMiddle.getX() - fontMargin - (strWidth));
			float y = (float) (ptChartMiddle.getY() - rangeY - (fontSize / 2));
			g.drawStringA(str, x, y);
		}

		// Draw Legend
		if (null != rtLegend) {
			drawLegend(g, rtLegend);
		}
		// Draw title
		if (null != rtTitle) {
			drawTitle(g, dataset.getTitle(), looks.getTitleDesign(), rtTitle);
		}

		return true;
	}

	private ScaleValue getScaleValue() {
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

	private Rect fitLegend(final Graphics g, Rect rtChart) {
		Rect rtLegend = null;
		if (null != looks.getLegendDesign() && null != dataset && null != dataset.getSeriesList() && 0 < dataset.getSeriesList().size()) {
			LegendDesign design = looks.getLegendDesign();
			if (design.isDisplay()) {
				rtLegend = new Rect();

				Margin margin = design.getMargin();
				Padding padding = design.getPadding();

				Font font = design.getFont();
				FontMetrics fm = g.getFontMetrics(font);
				LegendPosition pos = design.getPosition();

				// get size
				if (LegendPosition.Top == pos || LegendPosition.Bottom == pos) {
					for (int i = 0; i < dataset.getSeriesList().size(); i++) {
						RadarSeries series = dataset.getSeriesList().get(i);

						int strWidth = fm.stringWidth(series.getTitle());
						rtLegend.setHeight(font.getSize());
						rtLegend.addWidth((font.getSize() * 2) + strWidth);
						if (0 < i) {
							rtLegend.addWidth(design.getSpace());
						}
					}
				} else if (LegendPosition.Left == pos || LegendPosition.Right == pos) {
					for (int i = 0; i < dataset.getSeriesList().size(); i++) {
						RadarSeries series = dataset.getSeriesList().get(i);

						int strWidth = fm.stringWidth(series.getTitle());
						rtLegend.addHeight(font.getSize());
						rtLegend.setWidth(Math.max(rtLegend.getWidth(), (font.getSize() * 2) + strWidth));
						if (0 < i) {
							rtLegend.addHeight(design.getSpace());
						}
					}
				}
				if (null != margin) { // Add margin
					rtLegend.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
				}
				if (null != padding) { // Add padding
					rtLegend.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
				}

				// get point and resize chart
				if (LegendPosition.Top == pos) {
					rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2, rtChart.getY());

					rtChart.addY(rtLegend.getHeight());
					rtChart.subtractHeight(rtLegend.getHeight());
				} else if (LegendPosition.Bottom == pos) {
					rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2, rtChart.getY() + rtChart.getHeight()
							- rtLegend.getHeight());

					rtChart.subtractHeight(rtLegend.getHeight());
				} else if (LegendPosition.Left == pos) {
					rtLegend.setPosition(rtChart.getX(), rtChart.getY() + ((rtChart.getHeight() - rtLegend.getHeight()) / 2));

					rtChart.addX(rtLegend.getWidth());
					rtChart.subtractWidth(rtLegend.getWidth());
				} else if (LegendPosition.Right == pos) {
					rtLegend.setPosition(rtChart.getX() + rtChart.getWidth() - rtLegend.getWidth(),
							rtChart.getY() + ((rtChart.getHeight() - rtLegend.getHeight()) / 2));

					rtChart.subtractWidth(rtLegend.getWidth());
				}
			}
		}
		return rtLegend;
	}

	private void drawLegend(final Graphics g, final Rect aRect) {
		LegendDesign design = looks.getLegendDesign();

		Margin mgn = (null != design.getMargin()) ? design.getMargin() : new Margin();
		// fill background
		if (null != design.getBackgroundColor()) {
			g.setColor(design.getBackgroundColor());
			g.fillRect((int) (aRect.getX() + mgn.getLeft()), (int) (aRect.getY() + mgn.getTop()), (int) (aRect.getWidth() - mgn.getHorizontalSize()),
					(int) (aRect.getHeight() - mgn.getVerticalSize()));
		}
		// draw stroke
		if (null != design.getStroke() && null != design.getStrokeColor()) {
			g.setStroke(design.getStroke());
			g.setColor(design.getStrokeColor());
			g.drawRect((int) (aRect.getX() + mgn.getLeft()), (int) (aRect.getY() + mgn.getTop()), (int) (aRect.getWidth() - mgn.getHorizontalSize()),
					(int) (aRect.getHeight() - mgn.getVerticalSize()));
		}

		Padding padding = (null != design.getPadding()) ? design.getPadding() : new Padding();
		Font font = design.getFont();
		FontMetrics fm = g.getFontMetrics(font);
		int fontHeight = font.getSize();
		if (design.getPosition() == LegendPosition.Top || design.getPosition() == LegendPosition.Bottom) {
			List<RadarSeries> seriesList = dataset.getSeriesList();
			int xLegend = (int) (aRect.getX() + mgn.getLeft() + padding.getLeft());
			int yLegend = (int) (aRect.getY() + mgn.getTop() + padding.getTop());
			for (int i = 0; i < seriesList.size(); i++) {
				RadarSeries series = seriesList.get(i);
				// draw line
				g.setStroke(looks.getSeriesStroke(i, series), looks.getSeriesStrokeColor(i, series));
				g.drawLine(xLegend + 3, yLegend + (fontHeight / 2), xLegend + (fontHeight * 2) - 3, yLegend + (fontHeight / 2));
				// draw marker
				Marker marker = looks.getSeriesMarker(i, series);
				if (null != marker) {
					marker.draw(g, xLegend + (fontHeight * 2 - marker.getSize().getWidth()) / 2, yLegend
							+ (fontHeight - marker.getSize().getHeight()) / 2);
				}
				// draw title
				int strWidth = fm.stringWidth(series.getTitle());
				g.setFont(font, design.getFontColor());
				g.drawStringA(series.getTitle(), (fontHeight * 2) + xLegend, yLegend);

				xLegend += design.getSpace() + (fontHeight * 2) + strWidth;
			}
		} else if (design.getPosition() == LegendPosition.Left || design.getPosition() == LegendPosition.Right) {
			List<RadarSeries> seriesList = dataset.getSeriesList();
			int xLegend = (int) (aRect.getX() + mgn.getLeft() + padding.getLeft());
			int yLegend = (int) (aRect.getY() + mgn.getTop() + padding.getTop());
			for (int i = 0; i < seriesList.size(); i++) {
				RadarSeries series = seriesList.get(i);
				// draw line
				g.setStroke(looks.getSeriesStroke(i, series), looks.getSeriesStrokeColor(i, series));
				g.drawLine(xLegend + 3, yLegend + (fontHeight / 2), xLegend + (fontHeight * 2) - 3, yLegend + (fontHeight / 2));
				// draw marker
				Marker marker = looks.getSeriesMarker(i, series);
				if (null != marker) {
					marker.draw(g, xLegend + (fontHeight * 2 - marker.getSize().getWidth()) / 2, yLegend
							+ (fontHeight - marker.getSize().getHeight()) / 2);
				}
				// draw title
				g.setFont(font, design.getFontColor());
				g.drawStringA(series.getTitle(), (fontHeight * 2) + xLegend, yLegend);

				yLegend += design.getSpace() + fontHeight;
			}
		}
	}

	private Margin fitChart(final Graphics g, final Rect aRtChart, final ScaleValue aScaleValue, final float aFontMargin) {
		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		Point ptChartMiddle = new Point(aRtChart.getX() + (aRtChart.getWidth() / 2.f), aRtChart.getY() + (aRtChart.getHeight() / 2.f));

		double difValue = aScaleValue.getDiff();

		// スケール計算(プレ)
		double pixPerValue = ((aRtChart.getHeight() - margin.getVerticalSize()) / 2.f) / difValue;

		float minY = aRtChart.getY();

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		g.setFont(looks.getAxisFont());
		for (double value = aScaleValue.getMin(); value <= aScaleValue.getMax(); value += aScaleValue.getScale()) {
			double rangeY = pixPerValue * (value - aScaleValue.getMin());

			float y = (float) (ptChartMiddle.getY() - rangeY - (fontSize / 2));

			minY = Math.min(minY, y);
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
