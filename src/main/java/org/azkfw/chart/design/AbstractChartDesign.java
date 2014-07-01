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
package org.azkfw.chart.design;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.azkfw.chart.design.chart.ChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.title.TitleStyle;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;

/**
 * このクラスは、グラフデザインを定義する為の基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public abstract class AbstractChartDesign<STYLE extends ChartStyle> implements ChartDesign<STYLE> {

	/** グラフスタイル */
	private STYLE chart;

	/** タイトルスタイル */
	private TitleStyle title;

	/** 凡例スタイル */
	private LegendStyle legend;

	/** 背景色 */
	private Color backgroundColor;

	/** フレームストローク */
	private Stroke frameStroke;

	/** フレームストロークカラー */
	private Color frameStrokeColor;

	/** マージン情報 */
	private Margin margin;

	/** パディング情報 */
	private Padding padding;

	/**
	 * コンストラクタ
	 */
	public AbstractChartDesign() {
		chart = null;
		title = null;
		legend = null;

		// backgroundColor = null;
		backgroundColor = new Color(240, 240, 240, 255);
		// frameStroke = null;
		frameStroke = new BasicStroke(1.f);
		// frameStrokeColor = null;
		frameStrokeColor = Color.DARK_GRAY;
		// margin = null;
		margin = new Margin(4.f, 4.f, 4.f, 4.f);
		// padding = null;
		padding = new Padding(4.f, 4.f, 4.f, 4.f);
	}

	@Override
	public final void setChartStyle(final STYLE aStyle) {
		chart = aStyle;
	}

	@Override
	public final STYLE getChartStyle() {
		return chart;
	}

	@Override
	public final void setTitleStyle(final TitleStyle aStyle) {
		title = aStyle;
	}

	@Override
	public final TitleStyle getTitleStyle() {
		return title;
	}

	@Override
	public final void setLegendStyle(final LegendStyle aStyle) {
		legend = aStyle;
	}

	@Override
	public final LegendStyle getLegendStyle() {
		return legend;
	}

	@Override
	public final void setBackgroundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	@Override
	public final Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void setFrameStroke(final Stroke aStroke) {
		frameStroke = aStroke;
	}

	@Override
	public Stroke getFrameStroke() {
		return frameStroke;
	}

	@Override
	public void setFrameStrokeColor(final Color aColor) {
		frameStrokeColor = aColor;
	}

	@Override
	public Color getFrameStrokeColor() {
		return frameStrokeColor;
	}

	@Override
	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	public Margin getMargin() {
		return margin;
	}

	@Override
	public void setPadding(final Padding aPadding) {
		padding = aPadding;
	}

	@Override
	public Padding getPadding() {
		return padding;
	}
}
