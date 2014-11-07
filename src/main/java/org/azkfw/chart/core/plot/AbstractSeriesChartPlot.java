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
package org.azkfw.chart.core.plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.util.List;

import org.azkfw.chart.core.dataset.SeriesDataset;
import org.azkfw.chart.core.dataset.series.Series;
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
 * このクラスは、シリーズデータのグラフプロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractSeriesChartPlot<DATASET extends SeriesDataset, DESIGN extends SeriesChartDesign> extends AbstractChartPlot<DATASET, DESIGN> {

	/**
	 * コンストラクタ
	 */
	public AbstractSeriesChartPlot() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractSeriesChartPlot(final Class<?> aClass) {
		super(aClass);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 * @param aDataset データセット
	 */
	public AbstractSeriesChartPlot(final Class<?> aClass, final DATASET aDataset) {
		super(aClass, aDataset);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractSeriesChartPlot(final String aName) {
		super(aName);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 * @param aDataset データセット
	 */
	public AbstractSeriesChartPlot(final String aName, final DATASET aDataset) {
		super(aName, aDataset);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public AbstractSeriesChartPlot(final DATASET aDataset) {
		super(aDataset);
	}

	/**
	 * 凡例のフィット処理を行なう。
	 * <p>
	 * チャートRectで適切な位置に凡例を配置するように設定する。
	 * </p>
	 * 
	 * @param g Graphics
	 * @param aStyle 凡例スタイル
	 * @param rtChart チャートRect（更新される）
	 * @return 凡例Rect。凡例を表示しない場合、<code>null</code>を返す。
	 */
	protected Rect fitLegend(final Graphics g, final LegendStyle aStyle, final Rect rtChart) {
		Rect rtLegend = null;

		DATASET dataset = getDataset();

		if (!ObjectUtility.isAllNotNull(aStyle, dataset)) {
			return rtLegend;
		}
		if (ListUtility.isEmpty(dataset.getSeriesList())) {
			return rtLegend;
		}

		if (aStyle.isDisplay()) {
			rtLegend = new Rect();

			Margin margin = aStyle.getMargin();
			Padding padding = aStyle.getPadding();

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = aStyle.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = fm.getAscent() - fm.getDescent();
			}

			LegendDisplayPosition pos = aStyle.getPosition();

			@SuppressWarnings("unchecked")
			List<Series> seriesList = dataset.getSeriesList();

			// get size
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					String title = series.getTitle();
					Color fontColor = aStyle.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
					}

					rtLegend.setHeight(fontHeight);
					rtLegend.addWidth((fontHeight * 2) + strWidth);
					if (0 < i) {
						rtLegend.addWidth(aStyle.getSpace());
					}
				}
			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					String title = series.getTitle();
					Color fontColor = aStyle.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
					}

					rtLegend.addHeight(fontHeight);
					rtLegend.setWidth(Math.max(rtLegend.getWidth(), (fontHeight * 2) + strWidth));
					if (0 < i) {
						rtLegend.addHeight(aStyle.getSpace());
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

	@SuppressWarnings("unchecked")
	protected void doDrawLegendMark(final Graphics g, final Rect aRect, final SeriesChartStyle aStyle, final int aIndex, final Series aSeries) {
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
	 * タイトルの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aStyle 凡例スタイル
	 * @param aRect 描画範囲
	 */
	@SuppressWarnings("unchecked")
	protected void drawLegend(final Graphics g, final LegendStyle aStyle, final Rect aRect) {
		DATASET dataset = getDataset();
		DESIGN design = getChartDesign();
		SeriesChartStyle style = (SeriesChartStyle) design.getChartStyle();

		if (null != design) {
			Margin margin = (Margin) ObjectUtility.getNotNullObject(aStyle.getMargin(), new Margin());
			Padding padding = (Padding) ObjectUtility.getNotNullObject(aStyle.getPadding(), new Padding());

			{ // Draw legend frame
				Rect rtFrame = new Rect();
				rtFrame.setX(aRect.getX() + margin.getLeft());
				rtFrame.setY(aRect.getY() + margin.getTop());
				rtFrame.setWidth(aRect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(aRect.getHeight() - margin.getVerticalSize());
				// fill background
				if (ObjectUtility.isNotNull(aStyle.getFrameBackgroundColor())) {
					g.setColor(aStyle.getFrameBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw stroke
				if (ObjectUtility.isAllNotNull(aStyle.getFrameStroke(), aStyle.getFrameStrokeColor())) {
					g.setStroke(aStyle.getFrameStroke(), aStyle.getFrameStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = aStyle.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = fm.getAscent() - fm.getDescent();
			}

			List<? extends Series> seriesList = dataset.getSeriesList();

			float x = aRect.getX() + margin.getLeft() + padding.getLeft();
			float y = aRect.getY() + margin.getTop() + padding.getTop();
			// float width = aRect.getWidth() - (margin.getHorizontalSize() + padding.getHorizontalSize());
			float height = aRect.getHeight() - (margin.getVerticalSize() + padding.getVerticalSize());
			LegendDisplayPosition pos = aStyle.getPosition();
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					Rect rtMark = new Rect(x, y, (fontHeight * 2), fontHeight);
					doDrawLegendMark(g, rtMark, style, i, series);

					// draw title
					String title = series.getTitle();
					Color fontColor = aStyle.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
						g.setFont(font, fontColor);
						g.drawString(title, (fontHeight * 2) + x, y + fontHeight + (height - fontHeight) / 2);
					}

					x += aStyle.getSpace() + (fontHeight * 2) + strWidth;
				}

			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < seriesList.size(); i++) {
					Series series = seriesList.get(i);

					Rect rtMark = new Rect(x, y, (fontHeight * 2), fontHeight);
					doDrawLegendMark(g, rtMark, style, i, series);

					// draw title
					String title = series.getTitle();
					Color fontColor = aStyle.getFontColor();
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						g.setFont(font, fontColor);
						g.drawString(title, (fontHeight * 2) + x, y + fontHeight);
					}

					y += aStyle.getSpace() + fontHeight;
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
