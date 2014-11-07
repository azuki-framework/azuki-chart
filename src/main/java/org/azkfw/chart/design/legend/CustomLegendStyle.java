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
package org.azkfw.chart.design.legend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;

/**
 * このクラスは、凡例デザインをカスタマイズするクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public class CustomLegendStyle implements LegendStyle {

	private boolean display;

	private LegendDisplayPosition position;

	private Margin margin;

	private Padding padding;

	private Font font;

	private Color fontColor;

	private Stroke stroke;

	private Color strokeColor;

	private Color backgroundColor;

	private float space;

	/**
	 * コンストラクタ
	 */
	public CustomLegendStyle() {
		display = true;
		position = LegendDisplayPosition.Right;

		margin = new Margin(4.f, 4.f, 4.f, 4.f);
		padding = new Padding(6.f, 6.f, 6.f, 6.f);

		// font = new Font("Arial", Font.BOLD, 16);
		font = new Font("MS ゴシック", Font.BOLD, 16);
		fontColor = new Color(64, 64, 64, 255);

		stroke = null;
		strokeColor = null;
		backgroundColor = null;

		space = 4.f;
	}

	/**
	 * 表示を行うか設定する。
	 * 
	 * @param aDisplay 有無
	 */
	public void setDisplay(final boolean aDisplay) {
		display = aDisplay;
	}

	/**
	 * 凡例表示位置を設定する。
	 * 
	 * @param aPosition 表示位置
	 */
	public void setPosition(final LegendDisplayPosition aPosition) {
		position = aPosition;
	}

	/**
	 * マージンを設定する。
	 * 
	 * @param aMargin マージン
	 */
	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	/**
	 * パディングを設定する。
	 * 
	 * @param aPadding パディング
	 */
	public void setPadding(final Padding aPadding) {
		padding = aPadding;
	}

	/**
	 * フォントを設定する。
	 * 
	 * @param aFont フォント
	 */
	public void setFont(final Font aFont) {
		font = aFont;
	}

	/**
	 * フォントカラーを設定する。
	 * 
	 * @param aColor カラー
	 */
	public void setFontColor(final Color aColor) {
		fontColor = aColor;
	}

	/**
	 * フォントを設定する。
	 * 
	 * @param aFont フォント
	 * @param aColor カラー
	 */
	public void setFont(final Font aFont, final Color aColor) {
		font = aFont;
		fontColor = aColor;
	}

	/**
	 * 枠のストロークを設定する。
	 * 
	 * @param aStroke ストローク
	 */
	public void setFrameStroke(final Stroke aStroke) {
		stroke = aStroke;
	}

	/**
	 * 枠のストロークカラーを設定する。
	 * 
	 * @param aColor カラー
	 */
	public void setFrameStrokeColor(final Color aColor) {
		strokeColor = aColor;
	}

	/**
	 * 枠のストロークを設定する。
	 * 
	 * @param aStroke ストローク
	 * @param aColor カラー
	 */
	public void setFrameStroke(final Stroke aStroke, final Color aColor) {
		stroke = aStroke;
		strokeColor = aColor;
	}

	/**
	 * 枠の背景色を設定する。
	 * 
	 * @param aColor 背景色
	 */
	public void setBackgroundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	/**
	 * スペースを設定する。
	 * 
	 * @param aSpace スペース
	 */
	public void setSpace(final float aSpace) {
		space = aSpace;
	}

	@Override
	public boolean isDisplay() {
		return display;
	}

	@Override
	public LegendDisplayPosition getPosition() {
		return position;
	}

	@Override
	public Margin getMargin() {
		return margin;
	}

	@Override
	public Padding getPadding() {
		return padding;
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public Color getFontColor() {
		return fontColor;
	}

	@Override
	public Stroke getFrameStroke() {
		return stroke;
	}

	@Override
	public Color getFrameStrokeColor() {
		return strokeColor;
	}

	@Override
	public Color getFrameBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public float getSpace() {
		return space;
	}

}
