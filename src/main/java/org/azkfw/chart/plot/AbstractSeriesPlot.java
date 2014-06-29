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
package org.azkfw.chart.plot;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;

import org.azkfw.chart.dataset.SeriesDataset;
import org.azkfw.chart.looks.legend.LegendStyle;
import org.azkfw.chart.looks.legend.LegendStyle.LegendPosition;
import org.azkfw.chart.looks.marker.Marker;
import org.azkfw.chart.series.Series;
import org.azkfw.chart.style.SeriesChartStyle;
import org.azkfw.core.util.ListUtility;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、グラフプロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractSeriesPlot<D extends SeriesDataset, S extends SeriesChartStyle> extends AbstractPlot<D, S> implements Plot {

	/**
	 * コンストラクタ
	 */
	public AbstractSeriesPlot() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractSeriesPlot(final Class<?> aClass) {
		super(aClass);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractSeriesPlot(final String aName) {
		super(aName);
	}

	protected Rect fitLegend(final Graphics g, final LegendStyle aStyle, Rect rtChart) {
		Rect rtLegend = null;

		if (null == aStyle) {
			return rtLegend;
		}
		if (null == getDataset()) {
			return rtLegend;
		}
		if (ListUtility.isEmpty(getDataset().getSeriesList())) {
			return rtLegend;
		}

		if (aStyle.isDisplay()) {
			rtLegend = new Rect();

			Margin margin = aStyle.getMargin();
			Padding padding = aStyle.getPadding();

			Font font = aStyle.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			LegendPosition pos = aStyle.getPosition();

			List<? extends Series> seriesList = getDataset().getSeriesList();

			// get size
			if (LegendPosition.Top == pos || LegendPosition.Bottom == pos) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					int strWidth = fm.stringWidth(series.getTitle());
					rtLegend.setHeight(font.getSize());
					rtLegend.addWidth((font.getSize() * 2) + strWidth);
					if (0 < i) {
						rtLegend.addWidth(aStyle.getSpace());
					}
				}
			} else if (LegendPosition.Left == pos || LegendPosition.Right == pos) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					int strWidth = fm.stringWidth(series.getTitle());
					rtLegend.addHeight(font.getSize());
					rtLegend.setWidth(Math.max(rtLegend.getWidth(), (font.getSize() * 2) + strWidth));
					if (0 < i) {
						rtLegend.addHeight(aStyle.getSpace());
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
				rtLegend.setPosition(rtChart.getX() + (rtChart.getWidth() - rtLegend.getWidth()) / 2,
						rtChart.getY() + rtChart.getHeight() - rtLegend.getHeight());

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

		return rtLegend;
	}

	protected void drawLegend(final Graphics g, final LegendStyle aStyle, final Rect aRect) {
		S style = getChartStyle();

		Margin margin = (Margin) getNotNullObject(aStyle.getMargin(), new Margin());

		Rect rtBox = new Rect(aRect.getX() + margin.getLeft(), aRect.getY() + margin.getTop(), aRect.getWidth() - margin.getHorizontalSize(),
				aRect.getHeight() - margin.getVerticalSize());
		// fill background
		if (null != aStyle.getBackgroundColor()) {
			g.setColor(aStyle.getBackgroundColor());
			g.fillRect(rtBox);
		}
		// draw stroke
		if (null != aStyle.getStroke() && null != aStyle.getStrokeColor()) {
			g.setStroke(aStyle.getStroke(), aStyle.getStrokeColor());
			g.drawRect(rtBox);
		}

		Padding padding = (Padding) getNotNullObject(aStyle.getPadding(), new Padding());

		Font font = aStyle.getFont();
		FontMetrics fm = g.getFontMetrics(font);
		int fontHeight = font.getSize();

		List<? extends Series> seriesList = getDataset().getSeriesList();

		if (aStyle.getPosition() == LegendPosition.Top || aStyle.getPosition() == LegendPosition.Bottom) {
			float x = aRect.getX() + margin.getLeft() + padding.getLeft();
			float y = aRect.getY() + margin.getTop() + padding.getTop();

			for (int i = 0; i < seriesList.size(); i++) {
				Series series = seriesList.get(i);
				// draw line
				g.setStroke(style.getSeriesStroke(i, series), style.getSeriesStrokeColor(i, series));
				g.drawLine(x + 3, y + (fontHeight / 2), x + (fontHeight * 2) - 3, y + (fontHeight / 2));
				// draw marker
				Marker marker = style.getSeriesMarker(i, series);
				if (null != marker) {
					marker.draw(g, x + (fontHeight * 2 - marker.getSize().getWidth()) / 2, y + (fontHeight - marker.getSize().getHeight()) / 2);
				}
				// draw title
				int strWidth = fm.stringWidth(series.getTitle());
				g.setFont(font, aStyle.getFontColor());
				g.drawStringA(series.getTitle(), (fontHeight * 2) + x, y);

				x += aStyle.getSpace() + (fontHeight * 2) + strWidth;
			}
		} else if (aStyle.getPosition() == LegendPosition.Left || aStyle.getPosition() == LegendPosition.Right) {
			float x = aRect.getX() + margin.getLeft() + padding.getLeft();
			float y = aRect.getY() + margin.getTop() + padding.getTop();

			for (int i = 0; i < seriesList.size(); i++) {
				Series series = seriesList.get(i);
				// draw line
				g.setStroke(style.getSeriesStroke(i, series), style.getSeriesStrokeColor(i, series));
				g.drawLine(x + 3, y + (fontHeight / 2), x + (fontHeight * 2) - 3, y + (fontHeight / 2));
				// draw marker
				Marker marker = style.getSeriesMarker(i, series);
				if (null != marker) {
					marker.draw(g, x + (fontHeight * 2 - marker.getSize().getWidth()) / 2, y + (fontHeight - marker.getSize().getHeight()) / 2);
				}
				// draw title
				g.setFont(font, aStyle.getFontColor());
				g.drawStringA(series.getTitle(), (fontHeight * 2) + x, y);

				y += aStyle.getSpace() + fontHeight;
			}
		}
	}

	public static Object getNotNullObject(Object... objs) {
		for (Object obj : objs) {
			if (null != obj) {
				return obj;
			}
		}
		return null;
	}
}
