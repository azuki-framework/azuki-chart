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
package org.azkfw.chart.looks.legend;

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

	private LegendPosition position;

	private Color fontColor;

	private Font font;

	private Margin margin;

	private Padding padding;

	private Stroke stroke;

	private Color strokeColor;

	private Color backgroundColor;

	private float space;

	/**
	 * コンストラクタ
	 */
	public CustomLegendStyle() {
		display = true;
		position = LegendPosition.Right;
		fontColor = new Color(64, 64, 64, 255);
		font = new Font("Arial", Font.BOLD, 16);

		margin = new Margin(4.f, 4.f, 4.f, 4.f);
		padding = new Padding(6.f, 6.f, 6.f, 6.f);

		stroke = null;
		strokeColor = null;

		backgroundColor = null;

		space = 4.f;
	}

	public void setDisplay(final boolean aDisplay) {
		display = aDisplay;
	}

	public void setPosition(final LegendPosition aPosition) {
		position = aPosition;
	}

	public void setFontColor(final Color aColor) {
		fontColor = aColor;
	}

	public void setFont(final Font aFont) {
		font = aFont;
	}

	public void setFont(final Font aFont, final Color aColor) {
		font = aFont;
		fontColor = aColor;
	}

	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	public void setPadding(final Padding aPadding) {
		padding = aPadding;
	}

	public void setStroke(final Stroke aStroke) {
		stroke = aStroke;
	}

	public void setStrokeColor(final Color aColor) {
		strokeColor = aColor;
	}

	public void setBackgroundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	public boolean isDisplay() {
		return display;
	}

	public LegendPosition getPosition() {
		return position;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Font getFont() {
		return font;
	}

	public Margin getMargin() {
		return margin;
	}

	public Padding getPadding() {
		return padding;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public float getSpace() {
		return space;
	}
}
