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
package org.azkfw.chart.core.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;

import org.azkfw.chart.charts.pie.PieChartDesign;
import org.azkfw.chart.charts.pie.PieChartDesign.PieChartStyle;
import org.azkfw.chart.charts.pie.PieData;
import org.azkfw.chart.charts.pie.PieDataset;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.legend.LegendStyle.LegendDisplayPosition;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ListUtility;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、Pie用の凡例エレメント機能を実装したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public class PieLegendElement extends LegendElement {

	/** データセット */
	private PieDataset dataset;
	/** デザイン */
	private PieChartDesign design;

	/** 凡例描画範囲 */
	private Rect rtLegend;

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public PieLegendElement(final PieDataset aDataset, final PieChartDesign aDesign) {
		dataset = aDataset;
		design = aDesign;

		rtLegend = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public PieLegendElement(final PieDataset aDataset, final PieChartDesign aDesign, final boolean aDebugMode) {
		super(aDebugMode);

		dataset = aDataset;
		design = aDesign;

		rtLegend = null;
	}

	private boolean isDraw() {
		if (!ObjectUtility.isAllNotNull(dataset, design)) {
			return false;
		}
		if (ObjectUtility.isNull(design.getLegendStyle())) {
			return false;
		}
		if (ListUtility.isEmpty(dataset.getDataList())) {
			return false;
		}

		if (!design.getLegendStyle().isDisplay()) {
			return false;
		}
		return true;
	}

	@Override
	public final Rect deploy(final Graphics g, final Rect rect) {
		Rect rtArea = new Rect(rect);

		if (isDraw()) {
			LegendStyle style = design.getLegendStyle();

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = style.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = fm.getAscent() - fm.getDescent();
			}

			// get size
			rtLegend = new Rect();
			LegendDisplayPosition pos = style.getPosition();
			if (isHorizontalLegend(pos)) {
				rtLegend.setHeight(fontHeight);
				for (int i = 0; i < dataset.getDataList().size(); i++) {
					PieData data = dataset.getDataList().get(i);

					String title = data.getTitle();
					Color fontColor = style.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
					}

					rtLegend.addWidth((fontHeight * 2) + strWidth);
					if (0 < i) {
						rtLegend.addWidth(style.getSpace());
					}
				}
			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < dataset.getDataList().size(); i++) {
					PieData data = dataset.getDataList().get(i);

					String title = data.getTitle();
					Color fontColor = style.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
					}

					rtLegend.addHeight(fontHeight);
					rtLegend.setWidth(Math.max(rtLegend.getWidth(), (fontHeight * 2) + strWidth));
					if (0 < i) {
						rtLegend.addHeight(style.getSpace());
					}
				}
			}

			Margin margin = style.getMargin();
			if (ObjectUtility.isNotNull(margin)) { // Add margin
				rtLegend.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}
			Padding padding = style.getPadding();
			if (ObjectUtility.isNotNull(padding)) { // Add padding
				rtLegend.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}

			// get point and resize chart
			if (LegendDisplayPosition.Top == pos) {
				rtLegend.setPosition(rtArea.getX() + (rtArea.getWidth() - rtLegend.getWidth()) / 2, rtArea.getY());

				rtArea.addY(rtLegend.getHeight());
				rtArea.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Bottom == pos) {
				rtLegend.setPosition(rtArea.getX() + (rtArea.getWidth() - rtLegend.getWidth()) / 2,
						rtArea.getY() + rtArea.getHeight() - rtLegend.getHeight());

				rtArea.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Left == pos) {
				rtLegend.setPosition(rtArea.getX(), rtArea.getY() + ((rtArea.getHeight() - rtLegend.getHeight()) / 2));

				rtArea.addX(rtLegend.getWidth());
				rtArea.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.Right == pos) {
				rtLegend.setPosition(rtArea.getX() + rtArea.getWidth() - rtLegend.getWidth(),
						rtArea.getY() + ((rtArea.getHeight() - rtLegend.getHeight()) / 2));

				rtArea.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.InnerTopLeft == pos) {
				rtLegend.setPosition(rtArea.getX(), rtArea.getY());
			} else if (LegendDisplayPosition.InnerTop == pos) {
				rtLegend.setPosition(rtArea.getX() + (rtArea.getWidth() - rtLegend.getWidth()) / 2, rtArea.getY());
			} else if (LegendDisplayPosition.InnerTopRight == pos) {
				rtLegend.setPosition(rtArea.getX() + rtArea.getWidth() - rtLegend.getWidth(), rtArea.getY());
			} else if (LegendDisplayPosition.InnerLeft == pos) {
				rtLegend.setPosition(rtArea.getX(), rtArea.getY() + (rtArea.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerRight == pos) {
				rtLegend.setPosition(rtArea.getX() + rtArea.getWidth() - rtLegend.getWidth(),
						rtArea.getY() + (rtArea.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerBottomLeft == pos) {
				rtLegend.setPosition(rtArea.getX(), rtArea.getY() + rtArea.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottom == pos) {
				rtLegend.setPosition(rtArea.getX() + (rtArea.getWidth() - rtLegend.getWidth()) / 2,
						rtArea.getY() + rtArea.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottomRight == pos) {
				rtLegend.setPosition(rtArea.getX() + rtArea.getWidth() - rtLegend.getWidth(),
						rtArea.getY() + rtArea.getHeight() - rtLegend.getHeight());
			}
		}

		return rtArea;
	}

	@Override
	public final void draw(final Graphics g) {
		if (isDraw()) {
			LegendStyle style = design.getLegendStyle();
			Margin margin = ObjectUtility.getNotNullObject(style.getMargin(), new Margin());
			Padding padding = ObjectUtility.getNotNullObject(style.getPadding(), new Padding());

			{ // Draw legend frame
				Rect rtFrame = new Rect(rtLegend);
				rtFrame.addPosition(margin.getLeft(), margin.getTop());
				rtFrame.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());

				// fill background
				if (ObjectUtility.isNotNull(style.getFrameBackgroundColor())) {
					g.setColor(style.getFrameBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw stroke
				if (ObjectUtility.isAllNotNull(style.getFrameStroke(), style.getFrameStrokeColor())) {
					g.setStroke(style.getFrameStroke(), style.getFrameStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = style.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = fm.getAscent() - fm.getDescent();
			}

			PieChartStyle styleChart = design.getChartStyle();
			List<PieData> dataList = dataset.getDataList();

			float x = rtLegend.getX() + margin.getLeft() + padding.getLeft();
			float y = rtLegend.getY() + margin.getTop() + padding.getTop();
			// float width = aRect.getWidth() - (margin.getHorizontalSize() + padding.getHorizontalSize());
			float height = rtLegend.getHeight() - (margin.getVerticalSize() + padding.getVerticalSize());
			LegendDisplayPosition pos = style.getPosition();
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < dataList.size(); i++) {
					PieData data = dataList.get(i);
					// draw color
					Color fillColor = styleChart.getDataFillColor(i);
					if (ObjectUtility.isNotNull(fillColor)) {
						g.setColor(fillColor);
						g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}
					Color strokeColor = styleChart.getDataStrokeColor(i);
					if (ObjectUtility.isNotNull(strokeColor)) {
						g.setColor(strokeColor);
						g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}

					// draw title
					String title = data.getTitle();
					Color fontColor = style.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
						g.setFont(font, fontColor);
						g.drawString(title, (fontHeight * 2) + x, y + fontHeight + (height - fontHeight) / 2);
					}

					x += style.getSpace() + (fontHeight * 2) + strWidth;
				}

			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < dataList.size(); i++) {
					PieData data = dataList.get(i);
					// draw color
					Color fillColor = styleChart.getDataFillColor(i);
					if (ObjectUtility.isNotNull(fillColor)) {
						g.setColor(fillColor);
						g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}
					Color strokeColor = styleChart.getDataStrokeColor(i);
					if (ObjectUtility.isNotNull(strokeColor)) {
						g.setColor(strokeColor);
						g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}

					// draw title
					String title = data.getTitle();
					Color fontColor = style.getFontColor();
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						g.setFont(font, fontColor);
						g.drawString(title, (fontHeight * 2) + x, y + fontHeight);
					}

					y += style.getSpace() + fontHeight;
				}

			}

			if (isDebugMode()) {
				g.setStroke(new BasicStroke(1.f));
				g.setColor(Color.green);

				Rect rect = new Rect(rtLegend);
				g.drawRect(rect); // margin

				rect.addPosition(margin.getLeft(), margin.getTop());
				rect.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());
				g.drawRect(rect); // frame

				rect.addPosition(padding.getLeft(), padding.getTop());
				rect.subtractSize(padding.getHorizontalSize(), padding.getVerticalSize());
				g.drawRect(rect); // padding
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
