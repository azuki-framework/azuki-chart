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
package org.azkfw.chart.looks.marker;

import java.awt.Graphics2D;

import org.azkfw.graphics.Size;

/**
 * このインターフェースは、グラフポイントマーカー機能を表現したインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public interface Marker {

	/**
	 * マーカーサイズを取得する。
	 * 
	 * @return サイズ
	 */
	public Size getSize();

	/**
	 * マーカーを描画する。
	 * 
	 * @param g Graphics
	 * @param aX 描画位置X座標
	 * @param aY 描画位置X座標
	 */
	public void draw(final Graphics2D g, final float aX, final float aY);
}
