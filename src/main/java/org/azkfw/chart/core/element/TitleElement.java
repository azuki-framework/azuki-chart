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
import java.awt.Font;
import java.awt.FontMetrics;

import org.azkfw.chart.design.title.TitleStyle;
import org.azkfw.chart.design.title.TitleStyle.TitleDisplayPosition;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Padding;
import org.azkfw.graphics.Rect;
import org.azkfw.util.ObjectUtility;
import org.azkfw.util.StringUtility;

/**
 * このクラスは、タイトルエレメント機能を実装したクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/11/07
 * @author Kawakicchi
 */
public class TitleElement extends AbstractElement {

	/** タイトル */
	private String title;
	/** スタイル */
	private TitleStyle style;

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public TitleElement(final String aTitle, final TitleStyle aStyle) {
		title = aTitle;
		style = aStyle;
	}

	@Override
	public Rect deploy(final Graphics g, final Rect rect) {
		Rect rtTitle = null;
		if (null != style && style.isDisplay() && StringUtility.isNotEmpty(title)) {
			rtTitle = new Rect();

			Margin margin = style.getMargin();
			Padding padding = style.getPadding();

			Font font = style.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			TitleDisplayPosition pos = style.getPosition();

			// get size
			rtTitle.setSize(fm.stringWidth(title), font.getSize());
			if (null != margin) { // Add margin
				rtTitle.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}
			if (null != padding) { // Add padding
				rtTitle.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}

			// get point and resize chart
			if (TitleDisplayPosition.Top == pos) {
				rtTitle.setPosition(rect.getX() + ((rect.getWidth() - rtTitle.getWidth()) / 2), rect.getY());

				rect.addY(rtTitle.getHeight());
				rect.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Bottom == pos) {
				rtTitle.setPosition(rect.getX() + ((rect.getWidth() - rtTitle.getWidth()) / 2), rect.getY() + rect.getHeight() - rtTitle.getHeight());

				rect.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Left == pos) {
				rtTitle.setPosition(rect.getX(), rect.getY() + ((rect.getHeight() - rtTitle.getHeight()) / 2));

				rect.addX(rtTitle.getWidth());
				rect.subtractWidth(rtTitle.getWidth());
			} else if (TitleDisplayPosition.Right == pos) {
				rtTitle.setPosition(rect.getX() + rect.getWidth() - rtTitle.getWidth(), rect.getY() + ((rect.getHeight() - rtTitle.getHeight()) / 2));

				rect.subtractWidth(rtTitle.getWidth());
			}
		}
		return rtTitle;
	}

	@Override
	public void draw(final Graphics g, final Rect rect) {
		if (null != style && style.isDisplay() && StringUtility.isNotEmpty(title)) {
			Margin margin = (Margin) ObjectUtility.getNotNullObject(style.getMargin(), new Margin());
			Padding padding = (Padding) ObjectUtility.getNotNullObject(style.getPadding(), new Padding());

			{ // Draw title frame
				Rect rtFrame = new Rect();
				rtFrame.setX(rect.getX() + margin.getLeft());
				rtFrame.setY(rect.getY() + margin.getTop());
				rtFrame.setWidth(rect.getWidth() - margin.getHorizontalSize());
				rtFrame.setHeight(rect.getHeight() - margin.getVerticalSize());
				// fill frame background
				if (null != style.getFrameBackgroundColor()) {
					g.setColor(style.getFrameBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw frame
				if (null != style.getFrameStroke() && null != style.getFrameStrokeColor()) {
					g.setStroke(style.getFrameStroke(), style.getFrameStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			if (null != style.getFontColor()) { // Draw title				
				if (style.isFontShadow()) {
					// Draw shadow
					g.setFont(style.getFont());
					int max = 4;
					int s = max;
					while (s > 0) {
						float x = rect.getX() + margin.getLeft() + padding.getLeft() + s;
						float y = rect.getY() + margin.getTop() + padding.getTop() + s;

						g.setColor(new Color(0, 0, 0, 255 - (255 / (max + 1)) * s));
						g.drawStringA(title, x, y);
						s--;
					}
				}

				float x = rect.getX() + margin.getLeft() + padding.getLeft();
				float y = rect.getY() + margin.getTop() + padding.getTop();
				g.setFont(style.getFont(), style.getFontColor());
				g.drawStringA(title, x, y);
			}
		}
	}
}
