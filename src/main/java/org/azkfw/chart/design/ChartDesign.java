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
import java.awt.Stroke;

import org.azkfw.chart.design.chart.ChartStyle;
import org.azkfw.chart.design.legend.LegendStyle;
import org.azkfw.chart.design.title.TitleStyle;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;

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

	/**
	 * フレームのストロークを設定する。
	 * 
	 * @param aStroke ストローク
	 */
	public void setFrameStroke(final Stroke aStroke);

	/**
	 * フレームのストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getFrameStroke();

	/**
	 * フレームのストロークカラーを設定する。
	 * 
	 * @param aColor カラー
	 */
	public void setFrameStrokeColor(final Color aColor);

	/**
	 * フレームのカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getFrameStrokeColor();

	/**
	 * マージン情報を設定する。
	 * 
	 * @param aMargin マージン情報
	 */
	public void setMargin(final Margin aMargin);

	/**
	 * マージン情報を取得する。
	 * 
	 * @return マージン情報
	 */
	public Margin getMargin();

	/**
	 * パディング情報を設定する。
	 * 
	 * @param aPadding パディング情報
	 */
	public void setPadding(final Padding aPadding);

	/**
	 * パディング情報を取得する。
	 * 
	 * @return パディング情報
	 */
	public Padding getPadding();
}
