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
import java.awt.MultipleGradientPaint;
import java.awt.RadialGradientPaint;
import java.util.List;

import org.azkfw.chart.charts.pie.PieChartDesign.PieChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.legend.LegendStyle.LegendDisplayPosition;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.core.util.ListUtility;
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
		super(PiePlot.class);

		axis = new PieAxis();

		setChartDesign(PieChartDesign.DefalutDesign);
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
		Rect rtLegend = null;

		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		LegendStyle style = design.getLegendStyle();

		if (null == style) {
			return rtLegend;
		}
		if (null == dataset) {
			return rtLegend;
		}
		if (ListUtility.isEmpty(dataset.getDataList())) {
			return rtLegend;
		}

		if (style.isDisplay()) {
			rtLegend = new Rect();

			Margin margin = style.getMargin();
			Padding padding = style.getPadding();

			Font font = style.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			LegendDisplayPosition pos = style.getPosition();

			// get size
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < dataset.getDataList().size(); i++) {
					PieData data = dataset.getDataList().get(i);

					int strWidth = fm.stringWidth(data.getTitle());
					rtLegend.setHeight(font.getSize());
					rtLegend.addWidth((font.getSize() * 2) + strWidth);
					if (0 < i) {
						rtLegend.addWidth(style.getSpace());
					}
				}
			} else if (isVerticalLegend(pos)) {
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
			if (LegendDisplayPosition.Top == pos) {
				rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2, rtChart.getY());

				rtChart.addY(rtLegend.getHeight());
				rtChart.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Bottom == pos) {
				rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2,
						rtChart.getY() + rtChart.getHeight() - rtLegend.getHeight());

				rtChart.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Left == pos) {
				rtLegend.setPosition(rtChart.getX(), rtChart.getY() + ((rtChart.getHeight() - rtLegend.getHeight()) / 2));

				rtChart.addX(rtLegend.getWidth());
				rtChart.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.Right == pos) {
				rtLegend.setPosition(rtChart.getX() + rtChart.getWidth() - rtLegend.getWidth(),
						rtChart.getY() + ((rtChart.getHeight() - rtLegend.getHeight()) / 2));

				rtChart.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.InnerTopLeft == pos) {
				rtLegend.setPosition(rtChart.getX(), rtChart.getY());
			} else if (LegendDisplayPosition.InnerTop == pos) {
				rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2, rtChart.getY());
			} else if (LegendDisplayPosition.InnerTopRight == pos) {
				rtLegend.setPosition(rtChart.getX() + rtChart.getWidth() - rtLegend.getWidth(), rtChart.getY());
			} else if (LegendDisplayPosition.InnerLeft == pos) {
				rtLegend.setPosition(rtChart.getX(), rtChart.getY() + (rtChart.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerRight == pos) {
				rtLegend.setPosition(rtChart.getX() + rtChart.getWidth() - rtLegend.getWidth(),
						rtChart.getY() + (rtChart.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerBottomLeft == pos) {
				rtLegend.setPosition(rtChart.getX(), rtChart.getY() + rtChart.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottom == pos) {
				rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2,
						rtChart.getY() + rtChart.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottomRight == pos) {
				rtLegend.setPosition(rtChart.getX() + rtChart.getWidth() - rtLegend.getWidth(),
						rtChart.getY() + rtChart.getHeight() - rtLegend.getHeight());
			}
		}

		return rtLegend;
	}

	private void drawLegend(final Graphics g, final Rect aRect) {
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		PieChartStyle style = design.getChartStyle();
		LegendStyle styleLegend = design.getLegendStyle();

		if (null != design) {

			Margin margin = (Margin) getNotNullObject(styleLegend.getMargin(), new Margin());
			Padding padding = (Padding) getNotNullObject(styleLegend.getPadding(), new Padding());

			{ // Draw legend frame
				Rect rtFrame = new Rect();
				rtFrame.setX(aRect.getX() + margin.getLeft());
				rtFrame.setY(aRect.getY() + margin.getTop());
				rtFrame.setWidth(aRect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(aRect.getHeight() - margin.getVerticalSize());
				// fill background
				if (null != styleLegend.getBackgroundColor()) {
					g.setColor(styleLegend.getBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw stroke
				if (null != styleLegend.getStroke() && null != styleLegend.getStrokeColor()) {
					g.setStroke(styleLegend.getStroke(), styleLegend.getStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			Font font = styleLegend.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			LegendDisplayPosition pos = styleLegend.getPosition();
			int fontHeight = font.getSize();

			List<PieData> dataList = dataset.getDataList();
			
			if (isHorizontalLegend(pos)) {
				float x = aRect.getX() + margin.getLeft() + padding.getLeft();
				float y = aRect.getY() + margin.getTop() + padding.getTop();
				
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
			} else if (isVerticalLegend(pos)) {
				float x = aRect.getX() + margin.getLeft() + padding.getLeft();
				float y = aRect.getY() + margin.getTop() + padding.getTop();
				
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

	/**
	 * シリーズを横に並べる凡例か判断する。
	 * 
	 * @param aPosition
	 * @return
	 */
	private boolean isHorizontalLegend(final LegendDisplayPosition aPosition) {
		if (LegendDisplayPosition.Top == aPosition)
			return true;
		if (LegendDisplayPosition.Bottom == aPosition)
			return true;
		if (LegendDisplayPosition.InnerTop == aPosition)
			return true;
		if (LegendDisplayPosition.InnerBottom == aPosition)
			return true;
		return false;
	}

	/**
	 * シリーズを縦に並べる凡例か判断する。
	 * 
	 * @param aPosition
	 * @return
	 */
	private boolean isVerticalLegend(final LegendDisplayPosition aPosition) {
		if (LegendDisplayPosition.Left == aPosition)
			return true;
		if (LegendDisplayPosition.Right == aPosition)
			return true;
		if (LegendDisplayPosition.InnerTopLeft == aPosition)
			return true;
		if (LegendDisplayPosition.InnerTopRight == aPosition)
			return true;
		if (LegendDisplayPosition.InnerLeft == aPosition)
			return true;
		if (LegendDisplayPosition.InnerRight == aPosition)
			return true;
		if (LegendDisplayPosition.InnerBottomLeft == aPosition)
			return true;
		if (LegendDisplayPosition.InnerBottomRight == aPosition)
			return true;
		return false;
	}
}
