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

import java.awt.BasicStroke;
import java.awt.Color;

import org.azkfw.chart.core.dataset.Dataset;
import org.azkfw.chart.core.element.LegendElement;
import org.azkfw.chart.core.element.TitleElement;
import org.azkfw.chart.design.ChartDesign;
import org.azkfw.chart.design.chart.ChartStyle;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、グラフプロット機能を実装するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public abstract class AbstractChartPlot<DATASET extends Dataset, DESIGN extends ChartDesign<? extends ChartStyle>> extends AbstractPlot implements
		ChartPlot {

	/** データセット */
	private DATASET dataset;

	/** デザイン */
	private DESIGN design;

	/**
	 * コンストラクタ
	 */
	public AbstractChartPlot() {
		super(ChartPlot.class);
		dataset = null;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aClass クラス
	 */
	public AbstractChartPlot(final Class<?> aClass) {
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
	public AbstractChartPlot(final Class<?> aClass, final DATASET aDataset) {
		super(aClass);
		dataset = aDataset;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aName 名前
	 */
	public AbstractChartPlot(final String aName) {
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
	public AbstractChartPlot(final String aName, final DATASET aDataset) {
		super(aName);
		dataset = aDataset;
		design = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aDataset データセット
	 */
	public AbstractChartPlot(final DATASET aDataset) {
		super(ChartPlot.class);
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
	 * グラフデザインを設定する。
	 * 
	 * @param aDesign デザイン
	 */
	public final void setChartDesign(final DESIGN aDesign) {
		design = aDesign;
	}

	@Override
	protected final boolean doDraw(final Graphics g, final Rect aRect) {
		boolean result = false;

		Rect rect = new Rect(aRect);
		if (ObjectUtility.isNotNull(design)) {
			Margin margin = design.getMargin();
			if (null != margin) {
				rect.addPosition(margin.getLeft(), margin.getTop());
				rect.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}

			if (null != design.getBackgroundColor()) {
				g.setColor(design.getBackgroundColor());
				g.fillRect(rect);
			}
			if (null != design.getFrameStroke() && null != design.getFrameStrokeColor()) {
				g.setStroke(design.getFrameStroke(), design.getFrameStrokeColor());
				g.drawRect(rect);
			}

			Padding padding = design.getPadding();
			if (null != padding) {
				rect.addPosition(padding.getLeft(), padding.getTop());
				rect.subtractSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}
		}

		// エレメント作成 ////////////////////////////////
		TitleElement elementTitle = null;
		LegendElement elementLegend = null;
		if (ObjectUtility.isAllNotNull(dataset, design)) {
			elementTitle = createTitleElement();
			elementLegend = createLegendElement();
		}
		/////////////////////////////////////////////

		// エレメント配備 ////////////////////////////////
		// タイトル配備
		if (ObjectUtility.isNotNull(elementTitle)) {
			rect = elementTitle.deploy(g, rect);
		}
		// 凡例適用
		if (ObjectUtility.isNotNull(elementLegend)) {
			rect = elementLegend.deploy(g, rect);
		}
		/////////////////////////////////////////////

		if (isDebugMode()) {
			g.setStroke(new BasicStroke(1.f));
			g.setColor(Color.red);
			g.drawRect(rect);
		}
		result = doDrawChart(g, rect);

		// エレメント描画 ////////////////////////////////
		// Draw Legend
		if (ObjectUtility.isNotNull(elementLegend)) {
			elementLegend.draw(g);
		}
		// Draw title
		if (ObjectUtility.isNotNull(elementTitle)) {
			elementTitle.draw(g);
		}
		/////////////////////////////////////////////

		return result;
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
	 * グラフデザインを取得する。
	 * 
	 * @return デザイン
	 */
	protected final DESIGN getDesign() {
		return design;
	}

	/**
	 * グラフの描画を行う。
	 * 
	 * @param g Graphics
	 * @param aRect 描画範囲
	 * @return 結果
	 */
	protected abstract boolean doDrawChart(final Graphics g, final Rect aRect);

	/**
	 * タイトルエレメントを作成する。
	 * <p>
	 * タイトルエレメントを拡張する場合、このメソッドをオーバーライドし拡張したエレメントを返却する。
	 * </p>
	 * 
	 * @return エレメント
	 */
	protected TitleElement createTitleElement() {
		TitleElement element = new TitleElement(dataset.getTitle(), design.getTitleStyle(), isDebugMode());
		return element;
	}

	/**
	 * 凡例エレメントを作成する。
	 * <p>
	 * 凡例エレメントを拡張する場合、このメソッドをオーバーライドし拡張したエレメントを返却する。
	 * </p>
	 * 
	 * @return エレメント
	 */
	protected LegendElement createLegendElement() {
		return null;
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
