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

import org.azkfw.chart.core.dataset.Dataset;
import org.azkfw.chart.design.ChartDesign;
import org.azkfw.chart.design.title.TitleStyle;
import org.azkfw.chart.design.title.TitleStyle.TitleDisplayPosition;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;
import org.azkfw.lang.LoggingObject;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、グラフプロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPlot<DATASET extends Dataset, DESIGN extends ChartDesign> extends LoggingObject implements Plot {

	/** データセット */
	private DATASET dataset;

	/** デザイン */
	private DESIGN design;

	/**
	 * コンストラクタ
	 */
	public AbstractPlot() {
		super(Plot.class);
		dataset = null;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractPlot(final Class<?> aClass) {
		super(aClass);
		dataset = null;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 * @param aDataset データセット
	 */
	public AbstractPlot(final Class<?> aClass, final DATASET aDataset) {
		super(aClass);
		dataset = aDataset;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractPlot(final String aName) {
		super(aName);
		dataset = null;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 * @param aDataset データセット
	 */
	public AbstractPlot(final String aName, final DATASET aDataset) {
		super(aName);
		dataset = aDataset;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public AbstractPlot(final DATASET aDataset) {
		super(Plot.class);
		dataset = aDataset;
		design = null;
	}

	/**
	 * データセットを設定する。
	 * 
	 * @param aDataset データセット
	 */
	public final void setDataset(final DATASET aDataset) {
		dataset = aDataset;
	}

	/**
	 * データセットを取得する。
	 * 
	 * @return データセット
	 */
	protected final DATASET getDataset() {
		return dataset;
	}

	/**
	 * グラフデザインを設定する。
	 * 
	 * @param aDesign デザイン
	 */
	public final void setChartDesign(final DESIGN aDesign) {
		design = aDesign;
	}

	/**
	 * グラフデザインを取得する。
	 * 
	 * @return デザイン
	 */
	protected final DESIGN getChartDesign() {
		return design;
	}

	@Override
	public final boolean draw(final Graphics g, final int x, final int y, final int width, final int height) {
		return draw(g, new Rect(x, y, width, height));
	}

	@Override
	public final boolean draw(final Graphics g, final float x, final float y, final float width, final float height) {
		return draw(g, new Rect(x, y, width, height));
	}

	@Override
	public final boolean draw(final Graphics g, final Rect aRect) {
		boolean result = false;

		Rect rtGraph = new Rect(aRect);
		if (null != design) {
			Margin margin = design.getMargin();
			if (null != margin) {
				rtGraph.addPosition(margin.getLeft(), margin.getTop());
				rtGraph.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}

			if (null != design.getBackgroundColor()) {
				g.setColor(design.getBackgroundColor());
				g.fillRect(rtGraph);
			}
			if (null != design.getFrameStroke() && null != design.getFrameStrokeColor()) {
				g.setStroke(design.getFrameStroke(), design.getFrameStrokeColor());
				g.drawRect(rtGraph);
			}

			Padding padding = design.getPadding();
			if (null != padding) {
				rtGraph.addPosition(padding.getLeft(), padding.getTop());
				rtGraph.subtractSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}
		}

		result = doDraw(g, rtGraph);
		return result;
	}

	/**
	 * 描画を行う。
	 * 
	 * @param g Graphics
	 * @param aRect 描画範囲
	 * @return 結果
	 */
	protected abstract boolean doDraw(final Graphics g, final Rect aRect);

	/**
	 * タイトルのフィット処理を行なう。
	 * <p>
	 * チャートRectで適切な位置にタイトルを配置するように設定する。
	 * </p>
	 * 
	 * @param g Graphics
	 * @param rtChart チャートRect（更新される）
	 * @return タイトルRect。タイトルを表示しない場合は、<code>null</code>を返す。
	 */
	protected final Rect fitTitle(final Graphics g, Rect rtChart) {
		Rect rtTitle = null;

		if (null == dataset || null == design) {
			return rtTitle;
		}

		TitleStyle styleTitle = design.getTitleStyle();
		String title = dataset.getTitle();

		if (null != styleTitle && styleTitle.isDisplay() && StringUtility.isNotEmpty(title)) {
			rtTitle = new Rect();

			Margin margin = styleTitle.getMargin();
			Padding padding = styleTitle.getPadding();

			Font font = styleTitle.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			TitleDisplayPosition pos = styleTitle.getPosition();

			// get size
			rtTitle.setSize(fm.stringWidth(title), font.getSize());
			if (null != margin) { // Add margin
				rtTitle.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}
			if (null != padding) { // Add padding
				rtTitle.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}

			// get point and resize chart
			if (TitleDisplayPosition.Top == pos) {
				rtTitle.setPosition(rtChart.getX() + ((rtChart.getWidth() - rtTitle.getWidth()) / 2), rtChart.getY());

				rtChart.addY(rtTitle.getHeight());
				rtChart.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Bottom == pos) {
				rtTitle.setPosition(rtChart.getX() + ((rtChart.getWidth() - rtTitle.getWidth()) / 2),
						rtChart.getY() + rtChart.getHeight() - rtTitle.getHeight());

				rtChart.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Left == pos) {
				rtTitle.setPosition(rtChart.getX(), rtChart.getY() + ((rtChart.getHeight() - rtTitle.getHeight()) / 2));

				rtChart.addX(rtTitle.getWidth());
				rtChart.subtractWidth(rtTitle.getWidth());
			} else if (TitleDisplayPosition.Right == pos) {
				rtTitle.setPosition(rtChart.getX() + rtChart.getWidth() - rtTitle.getWidth(),
						rtChart.getY() + ((rtChart.getHeight() - rtTitle.getHeight()) / 2));

				rtChart.subtractWidth(rtTitle.getWidth());
			}
		}
		return rtTitle;
	}

	/**
	 * タイトルの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aRect 描画範囲
	 */
	protected final void drawTitle(final Graphics g, final Rect aRect) {

		if (null == dataset || null == design) {
			return;
		}

		TitleStyle styleTitle = design.getTitleStyle();
		String title = dataset.getTitle();

		if (null != styleTitle && StringUtility.isNotEmpty(title)) {
			Margin margin = (Margin) getNotNullObject(styleTitle.getMargin(), new Margin());
			Padding padding = (Padding) getNotNullObject(styleTitle.getPadding(), new Padding());

			{ // Draw title frame
				Rect rtFrame = new Rect();
				rtFrame.setX(aRect.getX() + margin.getLeft());
				rtFrame.setY(aRect.getY() + margin.getTop());
				rtFrame.setWidth(aRect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(aRect.getHeight() - margin.getVerticalSize());
				// fill frame background
				if (null != styleTitle.getBackgroundColor()) {
					g.setColor(styleTitle.getBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw frame
				if (null != styleTitle.getStroke() && null != styleTitle.getStrokeColor()) {
					g.setStroke(styleTitle.getStroke(), styleTitle.getStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			if (null != styleTitle.getFontColor()) { // Draw title				
				if (styleTitle.isFontShadow()) {
					// Draw shadow
					g.setFont(styleTitle.getFont());
					int max = 4;
					int s = max;
					while (s > 0) {
						float x = aRect.getX() + margin.getLeft() + padding.getLeft() + s;
						float y = aRect.getY() + margin.getTop() + padding.getTop() + s;

						g.setColor(new Color(0, 0, 0, 255 - (255 / (max + 1)) * s));
						g.drawStringA(title, x, y);
						s--;
					}
				}

				float x = aRect.getX() + margin.getLeft() + padding.getLeft();
				float y = aRect.getY() + margin.getTop() + padding.getTop();
				g.setFont(styleTitle.getFont(), styleTitle.getFontColor());
				g.drawStringA(title, x, y);
			}
		}
	}

	protected static float pixelLimit(final float aValue) {
		if (aValue > 100000) {
			return 100000;
		} else if (aValue < -100000) {
			return -100000;
		}
		return aValue;
	}

	protected static class ScaleValue {

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

		public double getDiff() {
			return max - min;
		}
	}

	protected static Object getNotNullObject(Object... objs) {
		for (Object obj : objs) {
			if (null != obj) {
				return obj;
			}
		}
		return null;
	}
}
