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
package org.azkfw.chart;

import java.awt.Color;
import java.awt.Graphics2D;

import org.azkfw.chart.plot.Plot;
import org.azkfw.graphics.AzukiGraphics2D;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;

/**
 * このクラスは、チャートクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/18
 * @author Kawakicchi
 */
public final class AzukiChart {

	/**
	 * プロット情報
	 */
	private Plot plot;

	/**
	 * 背景色
	 */
	private Color backgroundColor;

	/**
	 * コンストラクタ
	 * 
	 * @param aPlot プロット情報
	 */
	public AzukiChart(final Plot aPlot) {
		plot = aPlot;
	}

	/**
	 * 背景色を設定する。
	 * 
	 * @param aColor 背景色
	 */
	public void setBackgoundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	/**
	 * プロット情報を取得する。
	 * 
	 * @return プロット情報
	 */
	public Plot getPlot() {
		return plot;
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics2D g, final Rect rect) {
		return draw(g, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param x グラフ描画X座標
	 * @param y グラフ描画Y座標
	 * @param width グラフ描画横幅
	 * @param height グラフ描画縦幅
	 * @return 結果
	 */
	public boolean draw(final Graphics2D g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		Graphics graphics = new AzukiGraphics2D(g);
		result = draw(graphics, x, y, width, height);
		return result;
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics g, final Rect rect) {
		return draw(g, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param x グラフ描画X座標
	 * @param y グラフ描画Y座標
	 * @param width グラフ描画横幅
	 * @param height グラフ描画縦幅
	 * @return 結果
	 */
	public boolean draw(final Graphics g, final float x, final float y, final float width, final float height) {
		boolean result = false;
		if (null != plot) {
			if (null != backgroundColor) {
				g.setColor(backgroundColor);
				g.fillRect(x, y, width, height);
			}
			result = plot.draw(g, x, y, width, height);
		}
		return result;
	}
}
