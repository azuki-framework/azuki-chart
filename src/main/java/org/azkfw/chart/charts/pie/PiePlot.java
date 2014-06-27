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

import org.azkfw.chart.looks.legend.LegendDesign;
import org.azkfw.chart.looks.legend.LegendDesign.LegendPosition;
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
public class PiePlot extends AbstractPlot {

	/** 軸情報 */
	private PieAxis axis;

	/** データセット */
	private PieDataset dataset;

	/** Looks */
	private PieLooks looks;

	public PiePlot() {
		axis = new PieAxis();
		dataset = null;
		looks = new PieLooks();
	}

	public void setLooks(final PieLooks aLooks) {
		looks = aLooks;
	}

	public void setDataset(final PieDataset aDataset) {
		dataset = aDataset;
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
		Rect rtChartPre = new Rect(aRect.getX(), aRect.getY(), aRect.getWidth(), aRect.getHeight());

		// タイトル適用
		Rect rtTitle = fitTitle(g, dataset.getTitle(), looks.getTitleDesign(), rtChartPre);
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
			Color fillColor = looks.getDataFillColor(index);
			int size = (int) (-1 * 360.f * data.getValue() / totalValue);
			g.setColor(fillColor);
			g.fillArc(ptChartMiddle.getX() - (szChart.getWidth() / 2.f), ptChartMiddle.getY() - (szChart.getHeight() / 2.f), szChart.getWidth(),
					szChart.getHeight(), angle, size);
			angle += size;
		}
		angle = 90;
		for (int index = 0; index < dataList.size(); index++) {
			PieData data = dataList.get(index);
			Color strokeColor = looks.getDataStrokeColor(index);
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
			drawTitle(g, dataset.getTitle(), looks.getTitleDesign(), rtTitle);
		}

		return true;
	}

	private Rect fitLegend(final Graphics g, Rect rtChart) {
		Rect rtLegend = null;
		if (null != looks.getLegendDesign() && null != dataset && null != dataset.getDataList() && 0 < dataset.getDataList().size()) {
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
					for (int i = 0; i < dataset.getDataList().size(); i++) {
						PieData data = dataset.getDataList().get(i);

						int strWidth = fm.stringWidth(data.getTitle());
						rtLegend.setHeight(font.getSize());
						rtLegend.addWidth((font.getSize() * 2) + strWidth);
						if (0 < i) {
							rtLegend.addWidth(design.getSpace());
						}
					}
				} else if (LegendPosition.Left == pos || LegendPosition.Right == pos) {
					for (int i = 0; i < dataset.getDataList().size(); i++) {
						PieData data = dataset.getDataList().get(i);

						int strWidth = fm.stringWidth(data.getTitle());
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
			List<PieData> dataList = dataset.getDataList();
			int xLegend = (int) (aRect.getX() + mgn.getLeft() + padding.getLeft());
			int yLegend = (int) (aRect.getY() + mgn.getTop() + padding.getTop());
			for (int i = 0; i < dataList.size(); i++) {
				PieData data = dataList.get(i);
				// draw color
				g.setColor(looks.getDataFillColor(i));
				g.fillRect(xLegend + (fontHeight / 2), yLegend, fontHeight, fontHeight);
				g.setColor(looks.getDataStrokeColor(i));
				g.drawRect(xLegend + (fontHeight / 2), yLegend, fontHeight, fontHeight);
				// draw title
				int strWidth = fm.stringWidth(data.getTitle());
				g.setFont(font);
				g.setColor(design.getFontColor());
				g.drawStringA(data.getTitle(), (fontHeight * 2) + xLegend, yLegend);

				xLegend += design.getSpace() + (fontHeight * 2) + strWidth;
			}
		} else if (design.getPosition() == LegendPosition.Left || design.getPosition() == LegendPosition.Right) {
			List<PieData> dataList = dataset.getDataList();
			int xLegend = (int) (aRect.getX() + mgn.getLeft() + padding.getLeft());
			int yLegend = (int) (aRect.getY() + mgn.getTop() + padding.getTop());
			for (int i = 0; i < dataList.size(); i++) {
				PieData data = dataList.get(i);
				// draw color
				g.setColor(looks.getDataFillColor(i));
				g.fillRect(xLegend + (fontHeight / 2), yLegend, fontHeight, fontHeight);
				g.setColor(looks.getDataStrokeColor(i));
				g.drawRect(xLegend + (fontHeight / 2), yLegend, fontHeight, fontHeight);
				// draw title
				g.setFont(font);
				g.setColor(design.getFontColor());
				g.drawStringA(data.getTitle(), (fontHeight * 2) + xLegend, yLegend);

				yLegend += design.getSpace() + fontHeight;
			}
		}
	}
}
