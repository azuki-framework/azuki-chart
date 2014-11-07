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
import java.awt.Font;
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

	/** デフォルト 軸線ストローク */
	private final static Stroke DEFAULT_STROKE_AXIS = new BasicStroke(1.0f);
	/** デフォルト 軸線カラー */
	private final static Color DEFAULT_STROKE_COLOR_AXIS = Color.LIGHT_GRAY;;
	/** デフォルト 軸ラベルフォント */
	// private final static Font DEFAULT_FONT_AXIS_LABEL = new Font("Arial", Font.BOLD, 14);
	private final static Font DEFAULT_FONT_AXIS_LABEL = new Font("MS ゴシック", Font.BOLD, 14);
	/** デフォルト 軸ラベルフォントカラー */
	private final static Color DEFAULT_FONT_COLOR_AXIS_LABEL = Color.DARK_GRAY;
	/** デフォルト 軸目盛ラベルフォント */
	// private final static Font DEFAULT_FONT_AXIS_SCALE_LABEL = new Font("Arial", Font.BOLD, 14);
	private final static Font DEFAULT_FONT_AXIS_SCALE_LABEL = new Font("MS ゴシック", Font.BOLD, 14);
	/** デフォルト 軸目盛ラベルカラー */
	private final static Color DEFAULT_FONT_COLOR_AXIS_SCALE_LABEL = Color.DARK_GRAY;
	/** デフォルト 軸目盛線ストローク */
	private final static Stroke DEFAULT_STROKE_SCALE = new BasicStroke(1.0f);
	/** デフォルト 軸目盛線カラー */
	private final static Color DEFAULT_STROKE_COLOR_SCALE = Color.LIGHT_GRAY;;

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

		backgroundColor = Color.WHITE;
		frameStroke = new BasicStroke(1.f);
		frameStrokeColor = Color.LIGHT_GRAY;

		margin = new Margin(4.f, 4.f, 4.f, 4.f);
		padding = new Padding(6.f, 6.f, 6.f, 6.f);
	}

	/**
	 * デフォルトの軸線のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public final static Stroke getDefaultAxisLineStroke() {
		return DEFAULT_STROKE_AXIS;
	}

	/**
	 * デフォルトの軸線のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public final static Color getDefaultAxisLineColor() {
		return DEFAULT_STROKE_COLOR_AXIS;
	}

	/**
	 * デフォルトの軸ラベルのフォントを取得する。
	 * 
	 * @return フォント
	 */
	public final static Font getDefaultAxisLabelFont() {
		return DEFAULT_FONT_AXIS_LABEL;
	}

	/**
	 * デフォルトの軸ラベルのカラーを取得する。
	 * 
	 * @return カラー
	 */
	public final static Color getDefaultAxisLabelColor() {
		return DEFAULT_FONT_COLOR_AXIS_LABEL;
	}

	/**
	 * デフォルトの軸目盛ラベルのフォントを取得する。
	 * 
	 * @return フォント
	 */
	public final static Font getDefaultAxisScaleLabelFont() {
		return DEFAULT_FONT_AXIS_SCALE_LABEL;
	}

	/**
	 * デフォルトの軸目盛ラベルのカラーを取得する。
	 * 
	 * @return カラー
	 */
	public final static Color getDefaultAxisScaleLabelColor() {
		return DEFAULT_FONT_COLOR_AXIS_SCALE_LABEL;
	}

	/**
	 * デフォルトの軸目盛線のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public final static Stroke getDefaultAxisScaleLineStroke() {
		return DEFAULT_STROKE_SCALE;
	}

	/**
	 * デフォルトの軸目盛線のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public final static Color getDefaultAxisScaleLineColor() {
		return DEFAULT_STROKE_COLOR_SCALE;
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
