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
package org.azkfw.chart.charts.pie;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;

import org.azkfw.chart.charts.pie.PieChartDesign.PieChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.legend.LegendStyle.LegendPosition;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、円グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PiePlot extends AbstractPlot<PieDataset, PieChartDesign> {

	/** 軸情報 */
	private PieAxis axis;

	/**
	 * コンストラクタ
	 */
	public PiePlot() {
		axis = new PieAxis();

		setChartStyle(new PieChartDesign());
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public PieAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		PieChartStyle style = design.getChartStyle();

		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, rtChartPre);
		// 凡例適用
		Rect rtLegend = fitLegend(g, rtChartPre);

		float pieSize = Math.min(rtChartPre.getWidth(), rtChartPre.getHeight());
		Size szChart = new Size(pieSize, pieSize);
		Point ptChartMiddle = new Point(rtChartPre.getX() + (rtChartPre.getWidth() / 2.f), rtChartPre.getY() + (rtChartPre.getHeight() / 2.f));

		List<PieData> dataList = dataset.getDataList();

		double totalValue = 0.f;
		for (PieData data : dataList) {
			totalValue += data.getValue();
		}

		int angle = 90;
		for (int index = 0; index < dataList.size(); index++) {
			PieData data = dataList.get(index);
			Color fillColor = style.getDataFillColor(index);
			int size = (int) (-1 * 360.f * data.getValue() / totalValue);
			g.setColor(fillColor);
			g.fillArc(ptChartMiddle.getX() - (szChart.getWidth() / 2.f), ptChartMiddle.getY() - (szChart.getHeight() / 2.f), szChart.getWidth(),
					szChart.getHeight(), angle, size);
			angle += size;
		}
		angle = 90;
		for (int index = 0; index < dataList.size(); index++) {
			PieData data = dataList.get(index);
			Color strokeColor = style.getDataStrokeColor(index);
			int size = (int) (-1 * 360.f * data.getValue() / totalValue);
			g.setColor(strokeColor);
			g.drawArc((int) (ptChartMiddle.getX() - (szChart.getWidth() / 2.f)), (int) (ptChartMiddle.getY() - (szChart.getHeight() / 2.f)),
					(int) (szChart.getWidth()), (int) (szChart.getHeight()), angle, size);
			angle += size;
		}

		// Draw Legend
		if (null != rtLegend) {
			drawLegend(g, rtLegend);
		}
		// Draw title
		if (null != rtTitle) {
			drawTitle(g, rtTitle);
		}

		return true;
	}

	private Rect fitLegend(final Graphics g, Rect rtChart) {
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();

		Rect rtLegend = null;
		if (null != design.getLegendStyle() && null != dataset && null != dataset.getDataList() && 0 < dataset.getDataList().size()) {
			LegendStyle style = design.getLegendStyle();
			if (style.isDisplay()) {
				rtLegend = new Rect();

				Margin margin = style.getMargin();
				Padding padding = style.getPadding();

				Font font = style.getFont();
				FontMetrics fm = g.getFontMetrics(font);
				LegendPosition pos = style.getPosition();

				// get size
				if (LegendPosition.Top == pos || LegendPosition.Bottom == pos) {
					for (int i = 0; i < dataset.getDataList().size(); i++) {
						PieData data = dataset.getDataList().get(i);

						int strWidth = fm.stringWidth(data.getTitle());
						rtLegend.setHeight(font.getSize());
						rtLegend.addWidth((font.getSize() * 2) + strWidth);
						if (0 < i) {
							rtLegend.addWidth(style.getSpace());
						}
					}
				} else if (LegendPosition.Left == pos || LegendPosition.Right == pos) {
					for (int i = 0; i < dataset.getDataList().size(); i++) {
						PieData data = dataset.getDataList().get(i);

						int strWidth = fm.stringWidth(data.getTitle());
						rtLegend.addHeight(font.getSize());
						rtLegend.setWidth(Math.max(rtLegend.getWidth(), (font.getSize() * 2) + strWidth));
						if (0 < i) {
							rtLegend.addHeight(style.getSpace());
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
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		PieChartStyle style = design.getChartStyle();

		LegendStyle styleLegend = design.getLegendStyle();

		Margin mgn = (null != styleLegend.getMargin()) ? styleLegend.getMargin() : new Margin();
		// fill background
		if (null != styleLegend.getBackgroundColor()) {
			g.setColor(styleLegend.getBackgroundColor());
			g.fillRect(aRect.getX() + mgn.getLeft(), aRect.getY() + mgn.getTop(), aRect.getWidth() - mgn.getHorizontalSize(),
					aRect.getHeight() - mgn.getVerticalSize());
		}
		// draw stroke
		if (null != styleLegend.getStroke() && null != styleLegend.getStrokeColor()) {
			g.setStroke(styleLegend.getStroke(), styleLegend.getStrokeColor());
			g.drawRect(aRect.getX() + mgn.getLeft(), aRect.getY() + mgn.getTop(), aRect.getWidth() - mgn.getHorizontalSize(),
					aRect.getHeight() - mgn.getVerticalSize());
		}

		Padding padding = (null != styleLegend.getPadding()) ? styleLegend.getPadding() : new Padding();
		Font font = styleLegend.getFont();
		FontMetrics fm = g.getFontMetrics(font);
		int fontHeight = font.getSize();
		if (styleLegend.getPosition() == LegendPosition.Top || styleLegend.getPosition() == LegendPosition.Bottom) {
			List<PieData> dataList = dataset.getDataList();
			float x = aRect.getX() + mgn.getLeft() + padding.getLeft();
			float y = aRect.getY() + mgn.getTop() + padding.getTop();
			for (int i = 0; i < dataList.size(); i++) {
				PieData data = dataList.get(i);
				// draw color
				g.setColor(style.getDataFillColor(i));
				g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
				g.setColor(style.getDataStrokeColor(i));
				g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);

				// draw title
				int strWidth = fm.stringWidth(data.getTitle());
				g.setFont(font, styleLegend.getFontColor());
				g.drawStringA(data.getTitle(), (fontHeight * 2) + x, y);

				x += styleLegend.getSpace() + (fontHeight * 2) + strWidth;
			}
		} else if (styleLegend.getPosition() == LegendPosition.Left || styleLegend.getPosition() == LegendPosition.Right) {
			List<PieData> dataList = dataset.getDataList();
			float x = aRect.getX() + mgn.getLeft() + padding.getLeft();
			float y = aRect.getY() + mgn.getTop() + padding.getTop();
			for (int i = 0; i < dataList.size(); i++) {
				PieData data = dataList.get(i);
				// draw color
				g.setColor(style.getDataFillColor(i));
				g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
				g.setColor(style.getDataStrokeColor(i));
				g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);

				// draw title
				g.setFont(font, styleLegend.getFontColor());
				g.drawStringA(data.getTitle(), (fontHeight * 2) + x, y);

				y += styleLegend.getSpace() + fontHeight;
			}
		}
	}

}
