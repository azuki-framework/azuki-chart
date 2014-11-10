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

import java.awt.BasicStroke;
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

	/** タイトル描画範囲 */
	private Rect rtTitle;

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public TitleElement(final String aTitle, final TitleStyle aStyle) {
		super(false);

		title = aTitle;
		style = aStyle;

		rtTitle = null;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param aTitle タイトル
	 * @param aStyle スタイル
	 */
	public TitleElement(final String aTitle, final TitleStyle aStyle, final boolean aDebugMode) {
		super(aDebugMode);

		title = aTitle;
		style = aStyle;

		rtTitle = null;
	}

	private boolean isDraw() {
		if (ObjectUtility.isNull(style) || !style.isDisplay()) {
			return false;
		}
		if (StringUtility.isEmpty(title)) {
			return false;
		}
		return true;
	}

	@Override
	public Rect deploy(final Graphics g, final Rect rect) {
		Rect rtArea = new Rect(rect);

		if (isDraw()) {
			Font font = style.getFont();
			FontMetrics fm = g.getFontMetrics(font);
			TitleDisplayPosition pos = style.getPosition();

			// get size
			rtTitle = new Rect();
			rtTitle.setSize(fm.stringWidth(title), (fm.getAscent() - fm.getDescent()));

			Margin margin = style.getMargin();
			if (ObjectUtility.isNotNull(margin)) { // Add margin
				rtTitle.addSize(margin.getHorizontalSize(), margin.getVerticalSize());
			}
			Padding padding = style.getPadding();
			if (ObjectUtility.isNotNull(padding)) { // Add padding
				rtTitle.addSize(padding.getHorizontalSize(), padding.getVerticalSize());
			}

			// get point and resize chart
			if (TitleDisplayPosition.Top == pos) {
				rtTitle.setPosition(rtArea.getX() + ((rtArea.getWidth() - rtTitle.getWidth()) / 2), rtArea.getY());

				rtArea.addY(rtTitle.getHeight());
				rtArea.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Bottom == pos) {
				rtTitle.setPosition(rtArea.getX() + ((rtArea.getWidth() - rtTitle.getWidth()) / 2),
						rtArea.getY() + rtArea.getHeight() - rtTitle.getHeight());

				rtArea.subtractHeight(rtTitle.getHeight());
			} else if (TitleDisplayPosition.Left == pos) {
				rtTitle.setPosition(rtArea.getX(), rtArea.getY() + ((rtArea.getHeight() - rtTitle.getHeight()) / 2));

				rtArea.addX(rtTitle.getWidth());
				rtArea.subtractWidth(rtTitle.getWidth());
			} else if (TitleDisplayPosition.Right == pos) {
				rtTitle.setPosition(rtArea.getX() + rtArea.getWidth() - rtTitle.getWidth(),
						rtArea.getY() + ((rtArea.getHeight() - rtTitle.getHeight()) / 2));

				rtArea.subtractWidth(rtTitle.getWidth());
			}
		}

		return rtArea;
	}

	@Override
	public void draw(final Graphics g) {
		if (isDraw()) {
			Margin margin = ObjectUtility.getNotNullObject(style.getMargin(), new Margin());
			Padding padding = ObjectUtility.getNotNullObject(style.getPadding(), new Padding());

			{ // Draw title frame
				Rect rtFrame = new Rect(rtTitle);
				rtFrame.addPosition(margin.getLeft(), margin.getTop());
				rtFrame.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());

				// fill frame background
				if (ObjectUtility.isNotNull(style.getFrameBackgroundColor())) {
					g.setColor(style.getFrameBackgroundColor());
					g.fillRect(rtFrame);
				}
				// draw frame
				if (ObjectUtility.isAllNotNull(style.getFrameStroke(), style.getFrameStrokeColor())) {
					g.setStroke(style.getFrameStroke(), style.getFrameStrokeColor());
					g.drawRect(rtFrame);
				}
			}

			if (ObjectUtility.isNotNull(style.getFontColor())) { // Draw title				
				float x = rtTitle.getX() + margin.getLeft() + padding.getLeft();
				float y = rtTitle.getY() + margin.getTop() + padding.getTop();

				if (style.isFontShadow()) {
					// Draw shadow
					g.setFont(style.getFont());
					int max = 4;
					int s = max;
					while (s > 0) {
						float x2 = x + s;
						float y2 = y + s;
						g.setColor(new Color(0, 0, 0, 255 - (255 / (max + 1)) * s));
						g.drawStringA(title, x2, y2);
						s--;
					}
				}

				g.setFont(style.getFont(), style.getFontColor());
				g.drawStringA(title, x, y);
			}

			if (isDebugMode()) {
				g.setStroke(new BasicStroke(1.f));
				g.setColor(Color.green);

				Rect rect = new Rect(rtTitle);
				g.drawRect(rect); // margin

				rect.addPosition(margin.getLeft(), margin.getTop());
				rect.subtractSize(margin.getHorizontalSize(), margin.getVerticalSize());
				g.drawRect(rect); // frame

				rect.addPosition(padding.getLeft(), padding.getTop());
				rect.subtractSize(padding.getHorizontalSize(), padding.getVerticalSize());
				g.drawRect(rect); // padding
			}
		}
	}
}
