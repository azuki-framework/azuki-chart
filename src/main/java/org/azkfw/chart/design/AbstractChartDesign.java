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

import java.awt.Color;

import org.azkfw.chart.design.chart.ChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.title.TitleStyle;

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

	/**
	 * コンストラクタ
	 */
	public AbstractChartDesign() {
		chart = null;
		title = null;
		legend = null;
		backgroundColor = null;
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
}
