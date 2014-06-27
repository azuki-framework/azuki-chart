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

import org.azkfw.chart.looks.title.TitleDesign;
import org.azkfw.chart.looks.title.TitleDesign.TitlePosition;
import org.azkfw.core.lang.LoggingObject;
import org.azkfw.core.util.StringUtility;
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
public abstract class AbstractPlot extends LoggingObject implements Plot {

	/** マージン情報 */
	private Margin margin;

	/**
	 * コンストラクタ
	 */
	public AbstractPlot() {
		super(Plot.class);
		margin = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractPlot(final Class<?> aClass) {
		super(aClass);
		margin = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractPlot(final String aName) {
		super(aName);
		margin = null;
	}

	/**
	 * マージン情報を設定する。
	 * 
	 * @param aMargin マージン情報
	 */
	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	public boolean draw(final Graphics g, final float x, final float y, final float width, final float height) {
		boolean result = false;

		Rect rtPlot = null;
		if (null != margin) {
			rtPlot = new Rect(x + margin.getLeft(), y + margin.getTop(), width - margin.getHorizontalSize(), height - margin.getVerticalSize());
		} else {
			rtPlot = new Rect(x, y, width, height);
		}
		result = doDraw(g, rtPlot);

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
	 * @param aTitle タイトル
	 * @param aDesign デザイン
	 * @param rtChart チャートRect（更新される）
	 * @return タイトルRect
	 */
	protected Rect fitTitle(final Graphics g, final String aTitle, final TitleDesign aDesign, Rect rtChart) {
		Rect rtTitle = null;
		if (null != aDesign && StringUtility.isNotEmpty(aTitle)) {
			if (aDesign.isDisplay()) {
				rtTitle = new Rect();

				Margin margin = aDesign.getMargin();
				Padding padding = aDesign.getPadding();

				Font font = aDesign.getFont();
				FontMetrics fm = g.getFontMetrics(font);
				TitlePosition pos = aDesign.getPosition();

				// get size
				rtTitle.setSize(fm.stringWidth(aTitle), font.getSize());
				if (null != margin) { // Add margin
					rtTitle.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
				}
				if (null != padding) { // Add padding
					rtTitle.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
				}

				// get point and resize chart
				if (TitlePosition.Top == pos) {
					rtTitle.setPosition(rtChart.getX() + ((rtChart.getWidth() - rtTitle.getWidth()) / 2), rtChart.getY());

					rtChart.addY(rtTitle.getHeight());
					rtChart.subtractHeight(rtTitle.getHeight());
				} else if (TitlePosition.Bottom == pos) {
					rtTitle.setPosition(rtChart.getX() + ((rtChart.getWidth() - rtTitle.getWidth()) / 2), rtChart.getY() + rtChart.getHeight()
							- rtTitle.getHeight());

					rtChart.subtractHeight(rtTitle.getHeight());
				} else if (TitlePosition.Left == pos) {
					rtTitle.setPosition(rtChart.getX(), rtChart.getY() + ((rtChart.getHeight() - rtTitle.getHeight()) / 2));

					rtChart.addX(rtTitle.getWidth());
					rtChart.subtractWidth(rtTitle.getWidth());
				} else if (TitlePosition.Right == pos) {
					rtTitle.setPosition(rtChart.getX() + rtChart.getWidth() - rtTitle.getWidth(),
							rtChart.getY() + ((rtChart.getHeight() - rtTitle.getHeight()) / 2));

					rtChart.subtractWidth(rtTitle.getWidth());
				}
			}
		}
		return rtTitle;
	}

	/**
	 * タイトルの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aTitle タイトル
	 * @param aDesign デザイン情報
	 * @param aRect 描画範囲
	 */
	protected void drawTitle(final Graphics g, final String aTitle, final TitleDesign aDesign, final Rect aRect) {
		Margin mgn = (null != aDesign.getMargin()) ? aDesign.getMargin() : new Margin();
		// fill background
		if (null != aDesign.getBackgroundColor()) {
			g.setColor(aDesign.getBackgroundColor());
			g.fillRect(aRect.getX() + mgn.getLeft(), aRect.getY() + mgn.getTop(), aRect.getWidth() - mgn.getHorizontalSize(),
					aRect.getHeight() - mgn.getVerticalSize());
		}
		// draw stroke
		if (null != aDesign.getStroke() && null != aDesign.getStrokeColor()) {
			g.setStroke(aDesign.getStroke(), aDesign.getStrokeColor());
			g.drawRect(aRect.getX() + mgn.getLeft(), aRect.getY() + mgn.getTop(), aRect.getWidth() - mgn.getHorizontalSize(),
					aRect.getHeight() - mgn.getVerticalSize());
		}

		Padding padding = (null != aDesign.getPadding()) ? aDesign.getPadding() : new Padding();
		float x = aRect.getX() + mgn.getLeft() + padding.getLeft();
		float y = aRect.getY() + mgn.getTop() + padding.getTop();

		g.setFont(aDesign.getFont(), aDesign.getFontColor());
		g.drawStringA(aTitle, x, y);
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

}
