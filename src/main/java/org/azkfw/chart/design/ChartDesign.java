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
 * このインターフェースは、グラフデザインを定義する為のインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/28
 * @author Kawakicchi
 */
public interface ChartDesign<STYLE extends ChartStyle> {

	/**
	 * グラフスタイルを設定する。
	 * 
	 * @param aStyle スタイル
	 */
	public void setChartStyle(final STYLE aStyle);

	/**
	 * グラフスタイルを取得する。
	 * 
	 * @return スタイル
	 */
	public STYLE getChartStyle();

	/**
	 * タイトルスタイルを設定する。
	 * 
	 * @param aStyle スタイル
	 */
	public void setTitleStyle(final TitleStyle aStyle);

	/**
	 * タイトルスタイルを取得する。
	 * 
	 * @return スタイル
	 */
	public TitleStyle getTitleStyle();

	/**
	 * 凡例スタイルを設定する。
	 * 
	 * @param aStyle スタイル
	 */
	public void setLegendStyle(final LegendStyle aStyle);

	/**
	 * 凡例スタイルを取得する。
	 * 
	 * @return スタイル
	 */
	public LegendStyle getLegendStyle();

	/**
	 * 背景色を設定する。
	 * 
	 * @param aColor 背景色
	 */
	public void setBackgroundColor(final Color aColor);

	/**
	 * 背景色を取得する。
	 * 
	 * @return 背景色
	 */
	public Color getBackgroundColor();
}
