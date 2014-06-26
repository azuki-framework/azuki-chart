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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.looks.legend.LegendDesign;
import org.azkfw.chart.looks.legend.LegendDesign.LegendPosition;
import org.azkfw.chart.looks.marker.Marker;
import org.azkfw.chart.looks.title.TitleDesign;
import org.azkfw.chart.looks.title.TitleDesign.TitlePosition;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.core.util.StringUtility;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、散布図のプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class ScatterPlot extends AbstractPlot {

	/** X軸情報 */
	private ScatterXAxis axisX;
	/** Y軸情報 */
	private ScatterYAxis axisY;

	/** データセット */
	private ScatterDataset dataset;

	/** Looks */
	private ScatterLooks looks;

	/** Margin */
	private Margin margin;

	/**
	 * コンストラクタ
	 */
	public ScatterPlot() {
		axisX = new ScatterXAxis();
		axisY = new ScatterYAxis();
		dataset = null;
		looks = new ScatterLooks();
		margin = null;
	}

	/**
	 * ルックス情報を設定する。
	 * 
	 * @param aLooks ルックス情報
	 */
	public void setLooks(final ScatterLooks aLooks) {
		looks = aLooks;
	}

	/**
	 * データセットを設定する。
	 * 
	 * @param aDataset データセット
	 */
	public void setDataset(final ScatterDataset aDataset) {
		dataset = aDataset;
	}

	/**
	 * X軸情報を設定する。
	 * 
	 * @return X軸情報
	 */
	public ScatterXAxis getXAxis() {
		return axisX;
	}

	/**
	 * Y軸情報を設定する。
	 * 
	 * @return Y軸情報
	 */
	public ScatterYAxis getYAxis() {
		return axisY;
	}

	/**
	 * マージンを設定する。
	 * 
	 * @param aMargin マージン
	 */
	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		float fontMargin = 8;

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
						xDataMinValue = (xDataMinValue > point.getX()) ? point.getX() : xDataMinValue;
						xDataMaxValue = (xDataMaxValue < point.getX()) ? point.getX() : xDataMaxValue;
						yDataMinValue = (yDataMinValue > point.getY()) ? point.getY() : yDataMinValue;
						yDataMaxValue = (yDataMaxValue < point.getY()) ? point.getY() : yDataMaxValue;
					}
				}
			}
		}
		System.out.println(String.format("X data minimum value : %f", xDataMinValue));
		System.out.println(String.format("X data maximum value : %f", xDataMaxValue));
		System.out.println(String.format("Y data minimum value : %f", yDataMinValue));
		System.out.println(String.format("Y data maximum value : %f", yDataMaxValue));

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
			if (dif >= scaleDif * 5) {
				xScale = scaleDif;
			} else if (dif >= scaleDif * 2) {
				xScale = scaleDif / 2;
			} else {
				xScale = scaleDif / 10;
			}
		}
		ScaleValue xScaleValue = new ScaleValue(xMinValue, xMaxValue, xScale);
		System.out.println(String.format("X axis minimum value : %f", xMinValue));
		System.out.println(String.format("X axis maximum value : %f", xMaxValue));
		System.out.println(String.format("X axis scale value : %f", xScale));

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
			if (dif >= scaleDif * 5) {
				yScale = scaleDif;
			} else if (dif >= scaleDif * 2) {
				yScale = scaleDif / 2;
			} else {
				yScale = scaleDif / 10;
			}
		}
		ScaleValue yScaleValue = new ScaleValue(yMinValue, yMaxValue, yScale);
		System.out.println(String.format("Y axis minimum value : %f", yMinValue));
		System.out.println(String.format("Y axis maximum value : %f", yMaxValue));
		System.out.println(String.format("Y axis scale value : %f", yScale));

		Rect rtPlotArea = null;
		// マージン適用
		if (null != margin) {
			rtPlotArea = new Rect(aX + margin.getLeft(), aY + margin.getTop(), aWidth - (margin.getLeft() + margin.getRight()), aHeight
					- (margin.getTop() + margin.getBottom()));
		} else {
			rtPlotArea = new Rect(aX, aY, aWidth, aHeight);
		}

		Point ptChartPre = new Point(rtPlotArea.getX(), rtPlotArea.getY());
		Size szChartPre = new Size(rtPlotArea.getWidth(), rtPlotArea.getHeight());

		// タイトル適用
		Point ptTitle = null;
		Size szTitle = null;
		if (null != looks.getTitleDesign() && null != dataset && StringUtility.isNotEmpty(dataset.getTitle())) {
			TitleDesign design = looks.getTitleDesign();
			if (design.isDisplay()) {
				Font font = design.getFont();
				Margin margin = design.getMargin();
				Padding padding = design.getPadding();
				FontMetrics fm = g.getFontMetrics(font);
				TitlePosition pos = design.getPosition();
				// get size
				szTitle = new Size(fm.stringWidth(dataset.getTitle()), font.getSize());
				if (null != margin) { // Add margin
					szTitle.add(margin.getHorizontalSize(), margin.getVerticalSize());
				}
				if (null != padding) { // Add padding
					szTitle.add(padding.getHorizontalSize(), padding.getVerticalSize());
				}
				// get point and resize chart
				if (TitlePosition.Top == pos) {
					ptTitle = new Point(ptChartPre.getX() + ((szChartPre.getWidth() - szTitle.getWidth()) / 2), ptChartPre.getY());

					ptChartPre.addY(szTitle.getHeight());
					szChartPre.addHeight(-szTitle.getHeight());
				} else if (TitlePosition.Bottom == pos) {
					ptTitle = new Point(ptChartPre.getX() + ((szChartPre.getWidth() - szTitle.getWidth()) / 2), ptChartPre.getY()
							+ szChartPre.getHeight() - szTitle.getHeight());

					szChartPre.addHeight(-szTitle.getHeight());
				} else if (TitlePosition.Left == pos) {
					ptTitle = new Point(ptChartPre.getX(), ptChartPre.getY() + ((szChartPre.getHeight() - szTitle.getHeight()) / 2));

					ptChartPre.addX(szTitle.getWidth());
					szChartPre.addWidth(-szTitle.getWidth());
				} else if (TitlePosition.Right == pos) {
					ptTitle = new Point(ptChartPre.getX() + szChartPre.getWidth() - szTitle.getWidth(), ptChartPre.getY()
							+ ((szChartPre.getHeight() - szTitle.getHeight()) / 2));

					szChartPre.addWidth(-szTitle.getWidth());
				}
			}
		}
		// 凡例適用
		Point ptLegend = null;
		Size szLegend = null;
		if (null != looks.getLegendDesign() && null != dataset && null != dataset.getSeriesList() && 0 < dataset.getSeriesList().size()) {
			LegendDesign design = looks.getLegendDesign();
			if (design.isDisplay()) {
				Font font = design.getFont();
				Margin margin = design.getMargin();
				Padding padding = design.getPadding();
				FontMetrics fm = g.getFontMetrics(font);
				LegendPosition pos = design.getPosition();
				// get size
				szLegend = new Size();
				if (LegendPosition.Top == pos || LegendPosition.Bottom == pos) {
					for (ScatterSeries series : dataset.getSeriesList()) {
						szLegend.setHeight(font.getSize());
						int strWidth = fm.stringWidth(series.getTitle());
						szLegend.addWidth((font.getSize() * 2) + strWidth);
					}
				} else if (LegendPosition.Left == pos || LegendPosition.Right == pos) {
					for (ScatterSeries series : dataset.getSeriesList()) {
						szLegend.addHeight(font.getSize());
						int strWidth = fm.stringWidth(series.getTitle());
						szLegend.setWidth(Math.max(szLegend.getWidth(), (font.getSize() * 2) + strWidth));
					}
				}
				if (null != margin) { // Add margin
					szLegend.add(margin.getHorizontalSize(), margin.getVerticalSize());
				}
				if (null != padding) { // Add padding
					szLegend.add(padding.getHorizontalSize(), padding.getVerticalSize());
				}
				// get point and resize chart
				if (LegendPosition.Top == pos) {
					ptLegend = new Point(ptChartPre.getX() + (szChartPre.getWidth() - szLegend.getWidth()) / 2, ptChartPre.getY());

					ptChartPre.addY(szLegend.getHeight());
					szChartPre.addHeight(-szLegend.getHeight());
				} else if (LegendPosition.Bottom == pos) {
					ptLegend = new Point(ptChartPre.getX() + (szChartPre.getWidth() - szLegend.getWidth()) / 2, ptChartPre.getY()
							+ szChartPre.getHeight() - szLegend.getHeight());

					szChartPre.addHeight(-szLegend.getHeight());
				} else if (LegendPosition.Left == pos) {
					ptLegend = new Point(ptChartPre.getX(), ptChartPre.getY() + ((szChartPre.getHeight() - szLegend.getHeight()) / 2));

					ptChartPre.addX(szLegend.getWidth());
					szChartPre.addWidth(-szLegend.getWidth());
				} else if (LegendPosition.Right == pos) {
					ptLegend = new Point(ptChartPre.getX() + szChartPre.getWidth() - szLegend.getWidth(), ptChartPre.getY()
							+ ((szChartPre.getHeight() - szLegend.getHeight()) / 2));

					szChartPre.addWidth(-szLegend.getWidth());
				}
			}
		}

		Margin margin = fit(g, szChartPre, xScaleValue, yScaleValue, fontMargin);

		// スケール計算
		double xDifValue = xMaxValue - xMinValue;
		double yDifValue = yMaxValue - yMinValue;
		double pixXPerValue = (szChartPre.getWidth() - margin.getHorizontalSize()) / xDifValue;
		double pixYPerValue = (szChartPre.getHeight() - margin.getVerticalSize()) / yDifValue;
		System.out.println(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(),
				margin.getBottom()));

		float x = ptChartPre.getX() + margin.getLeft();
		float y = ptChartPre.getY() + szChartPre.getHeight() - margin.getBottom();
		float width = szChartPre.getWidth() - margin.getHorizontalSize();
		float height = szChartPre.getHeight() - margin.getVerticalSize();

		// fill background
		if (null != looks.getBackgroundColor()) {
			g.setColor(looks.getBackgroundColor());
			g.fillRect((int) (x), (int) (y - height), (int) (width), (int) (height));
		}

		// Draw Y axis scale
		{
			int fontSize = looks.getYAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(looks.getYAxisFont());
			DisplayFormat df = axisY.getDisplayFormat();
			g.setColor(looks.getYAxisFontColor());
			g.setFont(looks.getYAxisFont());
			for (double value = yMinValue; value <= yMaxValue; value += yScale) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);
				int yLabel = (int) (y - ((value - yMinValue) * pixYPerValue) + (fontSize / 2));
				int xLabel = (int) (x - strWidth - fontMargin);
				g.drawString(str, xLabel, yLabel);
			}
			g.setColor(looks.getYAxisScaleColor());
			g.setStroke(looks.getYAxisScaleStroke());
			for (double value = yMinValue; value <= yMaxValue; value += yScale) {
				int yLine = (int) (y - ((value - yMinValue) * pixYPerValue));
				int xLine = (int) (x);
				g.drawLine(xLine, yLine, (int) (xLine + width), yLine);
			}
			g.setColor(looks.getYAxisLineColor());
			g.setStroke(looks.getYAxisLineStroke());
			for (double value = yMinValue; value <= yMaxValue; value += yScale) {
				int yLine = (int) (y - ((value - yMinValue) * pixYPerValue));
				int xLine = (int) (x);
				g.drawLine(xLine, yLine, (int) (xLine + 6), yLine);
			}
		}
		// Draw X axis scale
		{
			int fontSize = looks.getXAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(looks.getXAxisFont());
			DisplayFormat df = axisX.getDisplayFormat();
			g.setColor(looks.getXAxisFontColor());
			g.setFont(looks.getXAxisFont());
			for (double value = xMinValue; value <= xMaxValue; value += xScale) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);
				int yLabel = (int) (y + fontSize + fontMargin);
				int xLabel = (int) (x + ((value - xMinValue) * pixXPerValue) - (strWidth / 2));
				g.drawString(str, xLabel, yLabel);
			}
			g.setColor(looks.getXAxisScaleColor());
			g.setStroke(looks.getXAxisScaleStroke());
			for (double value = xMinValue; value <= xMaxValue; value += xScale) {
				int yLine = (int) (y);
				int xLine = (int) (x + ((value - xMinValue) * pixXPerValue));
				g.drawLine(xLine, yLine, xLine, (int) (yLine - height));
			}
			g.setColor(looks.getXAxisLineColor());
			g.setStroke(looks.getXAxisLineStroke());
			for (double value = xMinValue; value <= xMaxValue; value += xScale) {
				int yLine = (int) (y);
				int xLine = (int) (x + ((value - xMinValue) * pixXPerValue));
				g.drawLine(xLine, yLine, xLine, (int) (yLine - 6));
			}
		}

		// Draw Y axis
		g.setColor(looks.getYAxisLineColor());
		g.setStroke(looks.getYAxisLineStroke());
		g.drawLine((int) (x), (int) (y), (int) (x), (int) (y - height));
		// Draw X axis
		g.setColor(looks.getXAxisLineColor());
		g.setStroke(looks.getXAxisLineStroke());
		g.drawLine((int) (x), (int) (y), (int) (x + width), (int) (y));

		if (null != dataset) {
			List<ScatterSeries> seriesList = dataset.getSeriesList();
			for (int i = 0; i < seriesList.size(); i++) {
				ScatterSeries series = seriesList.get(i);
				List<ScatterSeriesPoint> points = series.getPoints();

				g.setClip((int) (x), (int) (y - height), (int) (width), (int) (height));

				// Draw series fill
				{
					int xps[] = new int[points.size() + 2];
					int yps[] = new int[points.size() + 2];
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);
						xps[j + 1] = (int) (x + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
						yps[j + 1] = (int) (y - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
					}
					xps[0] = (int) (x);
					yps[0] = (int) (y);
					xps[points.size() + 1] = (int) (x + width);
					yps[points.size() + 1] = (int) (y);

					Color color = looks.getSeriesFillColor(i, series);
					GradientPaint paint = new GradientPaint(0f, y - height, color, 0f, y, new Color(color.getRed(), color.getGreen(),
							color.getBlue(), 0));
					g.setPaint(paint);
					g.fillPolygon(new Polygon(xps, yps, points.size() + 2));
				}

				// Draw series line
				{
					int xps[] = new int[points.size()];
					int yps[] = new int[points.size()];
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);
						xps[j] = (int) (x + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
						yps[j] = (int) (y - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
					}
					g.setColor(looks.getSeriesStrokeColor(i, series));
					g.setStroke(looks.getSeriesStroke(i, series));
					g.drawPolyline(xps, yps, points.size());
				}

				g.setClip(null);

				// Draw series marker
				{
					Marker seriesMarker = looks.getSeriesMarker(i, series);
					for (int j = 0; j < points.size(); j++) {
						ScatterSeriesPoint point = points.get(j);

						if (point.getX() < xScaleValue.getMin() || point.getX() > xScaleValue.getMax() || point.getY() < yScaleValue.getMin()
								|| point.getY() > yScaleValue.getMax()) {
							continue;
						}

						Marker pointMarker = looks.getSeriesPointMarker(i, series, j, point);
						Marker marker = (null != pointMarker) ? pointMarker : seriesMarker;
						if (null != marker) {
							int xMarker = (int) (x + ((point.getX() - xScaleValue.getMin()) * pixXPerValue));
							int yMarker = (int) (y - ((point.getY() - yScaleValue.getMin()) * pixYPerValue));
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
		if (null != ptLegend) {
			LegendDesign design = looks.getLegendDesign();

			Margin mgn = (null != design.getMargin()) ? design.getMargin() : new Margin();
			// fill background
			if (null != design.getBackgroundColor()) {
				g.setColor(design.getBackgroundColor());
				g.fillRect((int) (ptLegend.getX() + mgn.getLeft()), (int) (ptLegend.getY() + mgn.getTop()),
						(int) (szLegend.getWidth() - mgn.getHorizontalSize()), (int) (szLegend.getHeight() - mgn.getVerticalSize()));
			}
			// draw stroke
			if (null != design.getStroke() && null != design.getStrokeColor()) {
				g.setStroke(design.getStroke());
				g.setColor(design.getStrokeColor());
				g.drawRect((int) (ptLegend.getX() + mgn.getLeft()), (int) (ptLegend.getY() + mgn.getTop()),
						(int) (szLegend.getWidth() - mgn.getHorizontalSize()), (int) (szLegend.getHeight() - mgn.getVerticalSize()));
			}

			Padding padding = (null != design.getPadding()) ? design.getPadding() : new Padding();
			Font font = design.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			int fontHeight = font.getSize();
			if (design.getPosition() == LegendPosition.Top || design.getPosition() == LegendPosition.Bottom) {
				List<ScatterSeries> seriesList = dataset.getSeriesList();
				int xLegend = (int) (ptLegend.getX() + mgn.getLeft() + padding.getLeft());
				int yLegend = (int) (ptLegend.getY() + mgn.getTop() + padding.getTop());
				for (int i = 0; i < seriesList.size(); i++) {
					ScatterSeries series = seriesList.get(i);
					// draw line
					g.setColor(looks.getSeriesStrokeColor(i, series));
					g.setStroke(looks.getSeriesStroke(i, series));
					g.drawLine(xLegend + 3, yLegend + (fontHeight / 2), xLegend + (fontHeight * 2) - 3, yLegend + (fontHeight / 2));
					// draw marker
					Marker marker = looks.getSeriesMarker(i, series);
					if (null != marker) {
						marker.draw(g, xLegend + (fontHeight * 2 - marker.getSize().getWidth()) / 2, yLegend
								+ (fontHeight - marker.getSize().getHeight()) / 2);
					}
					// draw title
					int strWidth = fm.stringWidth(series.getTitle());
					g.setFont(font);
					g.setColor(design.getFontColor());
					g.drawString(series.getTitle(), (fontHeight * 2) + xLegend, yLegend + fontHeight);
					xLegend += (fontHeight * 2) + strWidth;
				}
			} else if (design.getPosition() == LegendPosition.Left || design.getPosition() == LegendPosition.Right) {
				List<ScatterSeries> seriesList = dataset.getSeriesList();
				int xLegend = (int) (ptLegend.getX() + mgn.getLeft() + padding.getLeft());
				int yLegend = (int) (ptLegend.getY() + mgn.getTop() + padding.getTop());
				for (int i = 0; i < seriesList.size(); i++) {
					ScatterSeries series = seriesList.get(i);
					// draw line
					g.setColor(looks.getSeriesStrokeColor(i, series));
					g.setStroke(looks.getSeriesStroke(i, series));
					g.drawLine(xLegend + 3, yLegend + (fontHeight / 2), xLegend + (fontHeight * 2) - 3, yLegend + (fontHeight / 2));
					// draw marker
					Marker marker = looks.getSeriesMarker(i, series);
					if (null != marker) {
						marker.draw(g, xLegend + (fontHeight * 2 - marker.getSize().getWidth()) / 2, yLegend
								+ (fontHeight - marker.getSize().getHeight()) / 2);
					}
					// draw title
					g.setFont(font);
					g.setColor(design.getFontColor());
					g.drawString(series.getTitle(), (fontHeight * 2) + xLegend, yLegend + fontHeight);
					yLegend += fontHeight;
				}
			}
		}
		// Draw title
		if (null != ptTitle) {
			TitleDesign design = looks.getTitleDesign();

			Margin mgn = (null != design.getMargin()) ? design.getMargin() : new Margin();
			// fill background
			if (null != design.getBackgroundColor()) {
				g.setColor(design.getBackgroundColor());
				g.fillRect((int) (ptTitle.getX() + mgn.getLeft()), (int) (ptTitle.getY() + mgn.getTop()),
						(int) (szTitle.getWidth() - mgn.getHorizontalSize()), (int) (szTitle.getHeight() - mgn.getVerticalSize()));
			}
			// draw stroke
			if (null != design.getStroke() && null != design.getStrokeColor()) {
				g.setStroke(design.getStroke());
				g.setColor(design.getStrokeColor());
				g.drawRect((int) (ptTitle.getX() + mgn.getLeft()), (int) (ptTitle.getY() + mgn.getTop()),
						(int) (szTitle.getWidth() - mgn.getHorizontalSize()), (int) (szTitle.getHeight() - mgn.getVerticalSize()));
			}

			Padding padding = (null != design.getPadding()) ? design.getPadding() : new Padding();
			Font font = design.getFont();
			g.setFont(font);
			g.setColor(design.getFontColor());

			int xTitle = (int) (ptTitle.getX() + mgn.getLeft() + padding.getLeft());
			int yTitle = (int) (ptTitle.getY() + mgn.getTop() + padding.getTop() + font.getSize());
			g.drawString(dataset.getTitle(), xTitle, yTitle);
		}

		return true;
	}

	private Margin fit(final Graphics2D g, final Size aSize, final ScaleValue aXScaleValue, final ScaleValue aYScaleValue, final float aFontMargin) {
		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		double xDifValue = aXScaleValue.getMax() - aXScaleValue.getMin();
		double yDifValue = aYScaleValue.getMax() - aYScaleValue.getMin();
		// スケール計算(プレ)
		double pixXPerValue = (aSize.getWidth() - (margin.getLeft() + margin.getRight())) / xDifValue;
		double pixYPerValue = (aSize.getHeight() - (margin.getTop() + margin.getBottom())) / yDifValue;
		{
			{
				float maxYLabelWidth = 0.0f;
				FontMetrics fm = g.getFontMetrics(looks.getYAxisFont());
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
				System.out.println(String.format("Max y axis label width : %f", maxYLabelWidth));
				margin.setLeft(maxYLabelWidth);
			}

			// スケール計算(プレ)
			pixXPerValue = (aSize.getWidth() - (margin.getLeft() + margin.getRight())) / xDifValue;
			pixYPerValue = (aSize.getHeight() - (margin.getTop() + margin.getBottom())) / yDifValue;

			{
				FontMetrics fm = g.getFontMetrics(looks.getXAxisFont());
				DisplayFormat df = axisX.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = aSize.getWidth();
				for (double value = aXScaleValue.getMin(); value <= aXScaleValue.getMax(); value += aXScaleValue.getScale()) {
					String str = df.toString(value);
					int width = fm.stringWidth(str);
					if (0 < width) {
						margin.setBottom(looks.getXAxisFont().getSize() + aFontMargin);

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
				if (0 < maxRight - aSize.getWidth()) {
					margin.setRight(maxRight - aSize.getWidth());
				}
			}

			// スケール計算(プレ)
			pixXPerValue = (aSize.getWidth() - (margin.getLeft() + margin.getRight())) / xDifValue;
			pixYPerValue = (aSize.getHeight() - (margin.getTop() + margin.getBottom())) / yDifValue;

			{
				int fontHeight = looks.getYAxisFont().getSize();
				DisplayFormat df = axisY.getDisplayFormat();
				float minTop = 0.f;
				for (double value = aYScaleValue.getMin(); value <= aYScaleValue.getMax(); value += aYScaleValue.getScale()) {
					float y = (float) (aSize.getHeight() - margin.getBottom() - pixYPerValue * (value - aYScaleValue.getMin()));
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

	private class ScaleValue {
		private double min;
		private double max;
		private double scale;

		public ScaleValue(final double aMin, final double aMax, final double aScale) {
			min = aMin;
			max = aMax;
			scale = aScale;
		}

		public double getMin() {
			return min;
		}

		public double getMax() {
			return max;
		}

		public double getScale() {
			return scale;
		}
	}

}
