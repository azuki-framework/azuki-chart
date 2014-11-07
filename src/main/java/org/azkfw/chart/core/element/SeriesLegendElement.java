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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.util.List;

import org.azkfw.chart.core.dataset.SeriesDataset;
import org.azkfw.chart.core.dataset.series.Series;
import org.azkfw.chart.core.dataset.series.SeriesPoint;
import org.azkfw.chart.design.SeriesChartDesign;
import org.azkfw.chart.design.chart.SeriesChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.legend.LegendStyle.LegendDisplayPosition;
import org.azkfw.chart.design.marker.Marker;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ListUtility;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、シリーズ用の凡例エレメント機能を実装したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public class SeriesLegendElement extends LegendElement {

	private SeriesDataset<? extends Series> dataset;
	private SeriesChartDesign<?, ?, ?> design;
	private LegendStyle style;

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public SeriesLegendElement(final SeriesDataset<? extends Series> aDataset, final SeriesChartDesign<?, ?, ?> aDesign) {
		dataset = aDataset;
		design = aDesign;
		style = design.getLegendStyle();
	}

	@Override
	public Rect deploy(final Graphics g, final Rect rect) {
		Rect rtLegend = null;

		if (!ObjectUtility.isAllNotNull(style, dataset)) {
			return rtLegend;
		}
		if (ListUtility.isEmpty(dataset.getSeriesList())) {
			return rtLegend;
		}

		if (style.isDisplay()) {
			rtLegend = new Rect();

			Margin margin = style.getMargin();
			Padding padding = style.getPadding();

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = style.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = fm.getAscent() - fm.getDescent();
			}

			LegendDisplayPosition pos = style.getPosition();

			List<? extends Series> seriesList = dataset.getSeriesList();

			// get size
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					String title = series.getTitle();
					Color fontColor = style.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
					}

					rtLegend.setHeight(fontHeight);
					rtLegend.addWidth((fontHeight * 2) + strWidth);
					if (0 < i) {
						rtLegend.addWidth(style.getSpace());
					}
				}
			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					String title = series.getTitle();
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
			if (ObjectUtility.isNotNull(margin)) { // Add margin
				rtLegend.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}
			if (ObjectUtility.isNotNull(padding)) { // Add padding
				rtLegend.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}

			// get point and resize chart
			if (LegendDisplayPosition.Top == pos) {
				rtLegend.setPosition(rect.getX() + (rect.getWidth() - rtLegend.getWidth()) / 2, rect.getY());

				rect.addY(rtLegend.getHeight());
				rect.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Bottom == pos) {
				rtLegend.setPosition(rect.getX() + (rect.getWidth() - rtLegend.getWidth()) / 2, rect.getY() + rect.getHeight() - rtLegend.getHeight());

				rect.subtractHeight(rtLegend.getHeight());
			} else if (LegendDisplayPosition.Left == pos) {
				rtLegend.setPosition(rect.getX(), rect.getY() + ((rect.getHeight() - rtLegend.getHeight()) / 2));

				rect.addX(rtLegend.getWidth());
				rect.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.Right == pos) {
				rtLegend.setPosition(rect.getX() + rect.getWidth() - rtLegend.getWidth(), rect.getY()
						+ ((rect.getHeight() - rtLegend.getHeight()) / 2));

				rect.subtractWidth(rtLegend.getWidth());
			} else if (LegendDisplayPosition.InnerTopLeft == pos) {
				rtLegend.setPosition(rect.getX(), rect.getY());
			} else if (LegendDisplayPosition.InnerTop == pos) {
				rtLegend.setPosition(rect.getX() + (rect.getWidth() - rtLegend.getWidth()) / 2, rect.getY());
			} else if (LegendDisplayPosition.InnerTopRight == pos) {
				rtLegend.setPosition(rect.getX() + rect.getWidth() - rtLegend.getWidth(), rect.getY());
			} else if (LegendDisplayPosition.InnerLeft == pos) {
				rtLegend.setPosition(rect.getX(), rect.getY() + (rect.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerRight == pos) {
				rtLegend.setPosition(rect.getX() + rect.getWidth() - rtLegend.getWidth(), rect.getY() + (rect.getHeight() - rtLegend.getHeight()) / 2);
			} else if (LegendDisplayPosition.InnerBottomLeft == pos) {
				rtLegend.setPosition(rect.getX(), rect.getY() + rect.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottom == pos) {
				rtLegend.setPosition(rect.getX() + (rect.getWidth() - rtLegend.getWidth()) / 2, rect.getY() + rect.getHeight() - rtLegend.getHeight());
			} else if (LegendDisplayPosition.InnerBottomRight == pos) {
				rtLegend.setPosition(rect.getX() + rect.getWidth() - rtLegend.getWidth(), rect.getY() + rect.getHeight() - rtLegend.getHeight());
			}
		}

		return rtLegend;
	}

	@Override
	public void draw(final Graphics g, final Rect rect) {
		@SuppressWarnings("unchecked")
		SeriesChartStyle<Series, SeriesPoint> styleChart = (SeriesChartStyle<Series, SeriesPoint>) design.getChartStyle();

		if (null != design) {
			Margin margin = (Margin) ObjectUtility.getNotNullObject(style.getMargin(), new Margin());
			Padding padding = (Padding) ObjectUtility.getNotNullObject(style.getPadding(), new Padding());

			{ // Draw legend frame
				Rect rtFrame = new Rect();
				rtFrame.setX(rect.getX() + margin.getLeft());
				rtFrame.setY(rect.getY() + margin.getTop());
				rtFrame.setWidth(rect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(rect.getHeight() - margin.getVerticalSize());
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

			List<? extends Series> seriesList = dataset.getSeriesList();

			float x = rect.getX() + margin.getLeft() + padding.getLeft();
			float y = rect.getY() + margin.getTop() + padding.getTop();
			// float width = aRect.getWidth() - (margin.getHorizontalSize() + padding.getHorizontalSize());
			float height = rect.getHeight() - (margin.getVerticalSize() + padding.getVerticalSize());
			LegendDisplayPosition pos = style.getPosition();
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					Rect rtMark = new Rect(x, y, (fontHeight * 2), fontHeight);
					doDrawLegendMark(g, rtMark, styleChart, i, series);

					// draw title
					String title = series.getTitle();
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
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					Rect rtMark = new Rect(x, y, (fontHeight * 2), fontHeight);
					doDrawLegendMark(g, rtMark, styleChart, i, series);

					// draw title
					String title = series.getTitle();
					Color fontColor = style.getFontColor();
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						g.setFont(font, fontColor);
						g.drawString(title, (fontHeight * 2) + x, y + fontHeight);
					}

					y += style.getSpace() + fontHeight;
				}
			}
		}
	}

	protected void doDrawLegendMark(final Graphics g, final Rect aRect, final SeriesChartStyle<Series, SeriesPoint> aStyle, final int aIndex,
			final Series aSeries) {
		// draw line
		Stroke stroke = aStyle.getSeriesStroke(aIndex, aSeries);
		Color strokeColor = aStyle.getSeriesStrokeColor(aIndex, aSeries);
		if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
			g.setStroke(stroke, strokeColor);
			g.drawLine(aRect.getX() + 3, aRect.getY() + (aRect.getHeight() / 2), aRect.getX() + aRect.getWidth() - 3,
					aRect.getY() + (aRect.getHeight() / 2));
		}
		// draw marker
		Marker marker = aStyle.getSeriesMarker(aIndex, aSeries);
		if (ObjectUtility.isNotNull(marker)) {
			marker.draw(g, aRect.getX() + (aRect.getWidth() - marker.getSize().getWidth()) / 2, aRect.getY()
					+ (aRect.getHeight() - marker.getSize().getHeight()) / 2);
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
