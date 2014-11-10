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
import java.awt.Stroke;

import org.azkfw.chart.charts.bar.BarChartDesign;
import org.azkfw.chart.charts.bar.BarDataset;
import org.azkfw.chart.core.dataset.SeriesDataset;
import org.azkfw.chart.core.dataset.series.Series;
import org.azkfw.chart.core.dataset.series.SeriesPoint;
import org.azkfw.chart.design.SeriesChartDesign;
import org.azkfw.chart.design.chart.SeriesChartStyle;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;

/**
 * このクラスは、Bar用の凡例エレメント機能を実装したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public class BarLegendElement extends SeriesLegendElement {

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BarLegendElement(final BarDataset aDataset, final BarChartDesign aDesign) {
		super((SeriesDataset<? extends Series>) aDataset, (SeriesChartDesign) aDesign);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	@SuppressWarnings("unchecked")
	public BarLegendElement(final BarDataset aDataset, final BarChartDesign aDesign, final boolean aDebugMode) {
		super((SeriesDataset<? extends Series>) aDataset,
				(SeriesChartDesign<? extends SeriesChartStyle<Series, SeriesPoint>, ? extends Series, ? extends SeriesPoint>) aDesign, aDebugMode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doDrawLegendMark(final Graphics g, final Rect aRect, final SeriesChartStyle aStyle, final int aIndex, final Series aSeries) {
		// draw line
		Stroke stroke = aStyle.getSeriesStroke(aIndex, aSeries);
		Color strokeColor = aStyle.getSeriesStrokeColor(aIndex, aSeries);
		Color fillColor = aStyle.getSeriesFillColor(aIndex, aSeries);

		Rect rect = new Rect(aRect.getX() + (aRect.getWidth() - aRect.getHeight()) / 2, aRect.getY(), aRect.getHeight(), aRect.getHeight());
		if (ObjectUtility.isNotNull(fillColor)) {
			g.setColor(fillColor);
			g.fillRect(rect);
		}
		if (ObjectUtility.isAllNotNull(stroke, strokeColor)) {
			g.setStroke(stroke, strokeColor);
			g.drawRect(rect);
		}
	}
}
