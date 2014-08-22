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
 * このインターフェースは、タイトルデザインを表現するインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public interface TitleStyle {

	/**
	 * タイトルの表示位置を定義
	 * 
	 * @since 1.0.0
	 * @version 1.0.0 2014/06/26
	 * @author Kawakicchi
	 */
	public enum TitleDisplayPosition {
		/** グラフ上部 */
		Top(),
		/** グラフ下部 */
		Bottom(),
		/** グラフ左部 */
		Left(),
		/** グラフ右部 */
		Right();

		private TitleDisplayPosition() {
		}
	}

	/**
	 * 表示を行うか判定する。
	 * 
	 * @return 判定結果
	 */
	public boolean isDisplay();

	/**
	 * タイトル表示位置を取得する。
	 * 
	 * @return 表示位置
	 */
	public TitleDisplayPosition getPosition();

	/**
	 * マージンを取得する。
	 * 
	 * @return マージン
	 */
	public Margin getMargin();

	/**
	 * パディングを取得する。
	 * 
	 * @return パディング
	 */
	public Padding getPadding();

	/**
	 * フォントカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getFontColor();

	/**
	 * フォントを取得する。
	 * 
	 * @return フォント
	 */
	public Font getFont();

	/**
	 * フォント影を取得する。
	 * 
	 * @return 影
	 */
	public boolean isFontShadow();

	/**
	 * ストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getStroke();

	/**
	 * ストロークカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getStrokeColor();

	/**
	 * 背景色を取得する。
	 * 
	 * @return 背景色
	 */
	public Color getBackgroundColor();

}
