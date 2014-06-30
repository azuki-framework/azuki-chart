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
package org.azkfw.chart.design.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;

/**
 * このクラスは、タイトルデザインをカスタマイズするクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public class CustomTitleStyle implements TitleStyle {

	private boolean display;

	private TitleDisplayPosition position;

	private Margin margin;

	private Padding padding;

	private Color fontColor;

	private Font font;

	private boolean fontShadow;

	private Stroke stroke;

	private Color strokeColor;

	private Color backgroundColor;

	/**
	 * コンストラクタ
	 */
	public CustomTitleStyle() {
		display = true;
		position = TitleDisplayPosition.Top;

		margin = new Margin(4.f, 4.f, 4.f, 4.f);
		padding = new Padding(8.f, 8.f, 8.f, 8.f);

		fontColor = new Color(0, 0, 0, 255);
		font = new Font("Arial", Font.BOLD, 26);
		fontShadow = false;

		stroke = null;
		strokeColor = null;

		backgroundColor = null;
	}

	public void setDisplay(final boolean aDisplay) {
		display = aDisplay;
	}

	public void setPosition(final TitleDisplayPosition aPosition) {
		position = aPosition;
	}

	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	public void setPadding(final Padding aPadding) {
		padding = aPadding;
	}

	public void setFontColor(final Color aColor) {
		fontColor = aColor;
	}

	public void setFont(final Font aFont) {
		font = aFont;
	}

	public void setFontShadow(final boolean aShadow) {
		fontShadow = aShadow;
	}

	public void setFont(final Font aFont, final Color aColor) {
		font = aFont;
		fontColor = aColor;
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

	public TitleDisplayPosition getPosition() {
		return position;
	}

	public Margin getMargin() {
		return margin;
	}

	public Padding getPadding() {
		return padding;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Font getFont() {
		return font;
	}

	public boolean isFontShadow() {
		return fontShadow;
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
}
