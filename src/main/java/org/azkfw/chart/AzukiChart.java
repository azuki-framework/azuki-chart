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
import java.awt.Rectangle;

import org.azkfw.chart.core.plot.ChartPlot;
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
	private ChartPlot plot;

	/**
	 * 背景色
	 */
	private Color backgroundColor;

	/**
	 * コンストラクタ
	 * 
	 * @param plot プロット情報
	 */
	public AzukiChart(final ChartPlot plot) {
		this.plot = plot;
	}

	/**
	 * 背景色を設定する。
	 * 
	 * @param color 背景色
	 */
	public void setBackgoundColor(final Color color) {
		backgroundColor = color;
	}

	/**
	 * プロット情報を取得する。
	 * 
	 * @return プロット情報
	 */
	public ChartPlot getPlot() {
		return plot;
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
	public boolean draw(final Graphics2D g, final int x, final int y, final int width, final int height) {
		return draw(new AzukiGraphics2D(g), new Rect(x, y, width, height));
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
		return draw(new AzukiGraphics2D(g), new Rect(x, y, width, height));
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics2D g, final Rectangle rect) {
		return draw(new AzukiGraphics2D(g), new Rect(rect));
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics2D g, final Rect rect) {
		return draw(new AzukiGraphics2D(g), rect);
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
	public boolean draw(final Graphics g, final int x, final int y, final int width, final int height) {
		return draw(g, new Rect(x, y, width, height));
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
		return draw(g, new Rect(x, y, width, height));
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics g, final Rectangle rect) {
		return draw(g, new Rect(rect));
	}

	/**
	 * グラフを描画する。
	 * 
	 * @param g Graphics
	 * @param rect Rect情報
	 * @return 結果
	 */
	public boolean draw(final Graphics g, final Rect rect) {
		boolean result = false;

		if (null != backgroundColor) {
			g.setColor(backgroundColor);
			g.fillRect(rect);
		}

		if (null != plot) {
			result = plot.draw(g, rect);
		}

		return result;
	}
}
