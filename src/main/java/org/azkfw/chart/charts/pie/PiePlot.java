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
import org.azkfw.chart.design.legend.LegendStyle.LegendDisplayPosition;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ListUtility;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

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
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public PiePlot(final PieDataset aDataset) {
		super(PiePlot.class, aDataset);

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
		Rect rtLegend = fitLegend(g, design.getLegendStyle(), rtChartPre);

		float pieSize = Math.min(rtChartPre.getWidth(), rtChartPre.getHeight());
		Point ptChartMiddle = new Point(rtChartPre.getX() + (rtChartPre.getWidth() / 2.f), rtChartPre.getY() + (rtChartPre.getHeight() / 2.f));

		Rect rtChart = new Rect();
		rtChart.setX(ptChartMiddle.getX() - (pieSize / 2));
		rtChart.setY(ptChartMiddle.getY() - (pieSize / 2));
		rtChart.setWidth(pieSize);
		rtChart.setHeight(pieSize);

		// fill background
		if (null != style.getBackgroundColor()) {
			g.setColor(style.getBackgroundColor());
			g.fillArc(rtChart.getX(), rtChart.getY(), rtChart.getWidth(), rtChart.getHeight(), 0, 360);
		}

		// Draw dataset
		drawDataset(g, dataset, style, rtChart);

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

	private void drawDataset(final Graphics g, final PieDataset aDataset, final PieChartStyle aStyle, final Rect aRect) {
		Point ptMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));

		if (ObjectUtility.isNotNull(aDataset)) {
			List<PieData> dataList = aDataset.getDataList();

			double totalValue = 0.f;
			for (PieData data : dataList) {
				totalValue += data.getValue();
			}

			int angle = 90;
			for (int index = 0; index < dataList.size(); index++) {
				PieData data = dataList.get(index);
				float acrAngle = (float) (-1 * 360.f * data.getValue() / totalValue);

				Color fillColor = aStyle.getDataFillColor(index);
				if (ObjectUtility.isNotNull(fillColor)) {
					g.setColor(fillColor);
					g.fillArc(ptMiddle.getX() - (aRect.getWidth() / 2.f), ptMiddle.getY() - (aRect.getHeight() / 2.f), aRect.getWidth(),
							aRect.getHeight(), angle, acrAngle);
				}

				angle += acrAngle;
			}

			angle = 90;
			for (int index = 0; index < dataList.size(); index++) {
				PieData data = dataList.get(index);
				float acrAngle = (float) (-1 * 360.f * data.getValue() / totalValue);

				Color strokeColor = aStyle.getDataStrokeColor(index);
				if (ObjectUtility.isNotNull(strokeColor)) {
					g.setColor(strokeColor);
					g.drawArc(ptMiddle.getX() - (aRect.getWidth() / 2.f), ptMiddle.getY() - (aRect.getHeight() / 2.f), aRect.getWidth(),
							aRect.getHeight(), angle, acrAngle);
				}

				angle += acrAngle;
			}
		}
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
	 * @return 凡例Rect
	 */
	private Rect fitLegend(final Graphics g, final LegendStyle aStyle, Rect rtChart) {
		Rect rtLegend = null;

		PieDataset dataset = getDataset();

		if (!ObjectUtility.isAllNotNull(aStyle, dataset)) {
			return rtLegend;
		}
		if (ListUtility.isEmpty(dataset.getDataList())) {
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
				fontHeight = font.getSize();
			}

			LegendDisplayPosition pos = aStyle.getPosition();

			// get size
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < dataset.getDataList().size(); i++) {
					PieData data = dataset.getDataList().get(i);

					String title = data.getTitle();
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
				for (int i = 0; i < dataset.getDataList().size(); i++) {
					PieData data = dataset.getDataList().get(i);

					String title = data.getTitle();
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

	/**
	 * タイトルの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aStyle 凡例スタイル
	 * @param aRect 描画範囲
	 */
	private void drawLegend(final Graphics g, final LegendStyle aStyle, final Rect aRect) {
		PieDataset dataset = getDataset();
		PieChartDesign design = getChartDesign();
		PieChartStyle style = design.getChartStyle();

		if (null != design) {
			Margin margin = (Margin) getNotNullObject(aStyle.getMargin(), new Margin());
			Padding padding = (Padding) getNotNullObject(aStyle.getPadding(), new Padding());

			{ // Draw legend frame
				Rect rtFrame = new Rect();
				rtFrame.setX(aRect.getX() + margin.getLeft());
				rtFrame.setY(aRect.getY() + margin.getTop());
				rtFrame.setWidth(aRect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(aRect.getHeight() - margin.getVerticalSize());
				// fill background
				if (null != aStyle.getBackgroundColor()) {
					g.setColor(aStyle.getBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw stroke
				if (null != aStyle.getStroke() && null != aStyle.getStrokeColor()) {
					g.setStroke(aStyle.getStroke(), aStyle.getStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			int fontHeight = 16;
			FontMetrics fm = null;
			Font font = aStyle.getFont();
			if (ObjectUtility.isNotNull(font)) {
				fm = g.getFontMetrics(font);
				fontHeight = font.getSize();
			}

			List<PieData> dataList = dataset.getDataList();

			float x = aRect.getX() + margin.getLeft() + padding.getLeft();
			float y = aRect.getY() + margin.getTop() + padding.getTop();
			LegendDisplayPosition pos = aStyle.getPosition();
			if (isHorizontalLegend(pos)) {
				for (int i = 0; i < dataList.size(); i++) {
					PieData data = dataList.get(i);
					// draw color
					Color fillColor = style.getDataFillColor(i);
					if (ObjectUtility.isNotNull(fillColor)) {
						g.setColor(fillColor);
						g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}
					Color strokeColor = style.getDataStrokeColor(i);
					if (ObjectUtility.isNotNull(strokeColor)) {
						g.setColor(strokeColor);
						g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}

					// draw title
					String title = data.getTitle();
					Color fontColor = aStyle.getFontColor();
					int strWidth = 16; // XXX: タイトルがない場合とりあえず16pixelあける
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						strWidth = fm.stringWidth(title);
						g.setFont(font, fontColor);
						g.drawStringA(title, (fontHeight * 2) + x, y);
					}

					x += aStyle.getSpace() + (fontHeight * 2) + strWidth;
				}

			} else if (isVerticalLegend(pos)) {
				for (int i = 0; i < dataList.size(); i++) {
					PieData data = dataList.get(i);
					// draw color
					Color fillColor = style.getDataFillColor(i);
					if (ObjectUtility.isNotNull(fillColor)) {
						g.setColor(fillColor);
						g.fillRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}
					Color strokeColor = style.getDataStrokeColor(i);
					if (ObjectUtility.isNotNull(strokeColor)) {
						g.setColor(strokeColor);
						g.drawRect(x + (fontHeight / 2), y, fontHeight, fontHeight);
					}

					// draw title
					String title = data.getTitle();
					Color fontColor = aStyle.getFontColor();
					if (StringUtility.isNotEmpty(title) && ObjectUtility.isAllNotNull(font, fontColor)) {
						g.setFont(font, fontColor);
						g.drawStringA(title, (fontHeight * 2) + x, y);
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
