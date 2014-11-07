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
package org.azkfw.chart.core.plot;

import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Rect;

/**
 * このインターフェースは、プロット機能を表現したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public interface Plot {

	/**
	 * グラフィクスに描画する。
	 * 
	 * @param g グラフィクス
	 * @param x 描画範囲（X座標）
	 * @param y 描画範囲（Y座標）
	 * @param width 描画範囲（横幅）
	 * @param height 描画範囲（縦幅）
	 * @return 描画結果
	 */
	public boolean draw(final Graphics g, final int x, final int y, final int width, final int height);

	/**
	 * グラフィクスに描画する。
	 * 
	 * @param g グラフィクス
	 * @param x 描画範囲（X座標）
	 * @param y 描画範囲（Y座標）
	 * @param width 描画範囲（横幅）
	 * @param height 描画範囲（縦幅）
	 * @return 描画結果
	 */
	public boolean draw(final Graphics g, final float x, final float y, final float width, final float height);

	/**
	 * グラフィクスに描画する。
	 * 
	 * @param g グラフィクス
	 * @param aRect 描画範囲
	 * @return 描画結果
	 */
	public boolean draw(final Graphics g, final Rect aRect);

}
