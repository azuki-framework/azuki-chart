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
package org.azkfw.chart.design.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * このインターフェースは、カラーインデックスを定義するためのインターフェースです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/30
 * @author Kawakicchi
 */
public interface ColorIndex {

	public static ColorIndex ColorfulColor01 = new StaticColorIndex(
			new Color(60, 103, 154, 255),
			new Color(157, 61, 58, 255),
			new Color(125, 152,68, 255),
			new Color(102, 78, 131, 255),
			new Color(56, 140, 162, 255),
			new Color(203, 120, 51, 255),
			new Color(74, 126, 187, 255),
			new Color(190, 75, 72, 255), 
			new Color(152, 185, 84, 255), 
			new Color(125, 96, 160, 255), 
			new Color(70, 170, 197, 255), 
			new Color(246,146, 64, 255));
	
	public static ColorIndex ColorfulColor02 = new StaticColorIndex(
			new Color(91, 155, 213, 255),
			new Color(237, 125, 49, 255),
			new Color(165, 165, 165, 255),
			new Color(255, 192, 0, 255),
			new Color(68, 114, 196, 255),
			new Color(112, 173, 71, 255),
			new Color(37, 94, 145, 255),
			new Color(158, 72, 14, 255),
			new Color(99, 99, 99, 255),
			new Color(153, 115, 0, 255)
			);

	public static ColorIndex ColorfulColor03 = new StaticColorIndex(
			new Color(91, 155, 213, 255),
			new Color(165, 165, 165, 255),
			new Color(68, 114, 196, 255),
			new Color(37, 94, 145, 255),
			new Color(99, 99, 99, 255),
			new Color(38, 68, 120, 255),
			new Color(124, 175, 221, 255),
			new Color(183, 183, 183, 255),
			new Color(105, 142, 208, 255),
			new Color(50, 125, 194, 255)
			);
	
	public static ColorIndex ColorfulColor04 = new StaticColorIndex(
			new Color(237, 125, 49, 255),
			new Color(255, 192, 0, 255),
			new Color(112, 173, 71, 255),
			new Color(158, 72, 14, 255),
			new Color(153, 115, 0, 255),
			new Color(67, 104, 43, 255),
			new Color(241, 151, 90, 255),
			new Color(255, 205, 51, 255),
			new Color(140, 193, 104, 255),
			new Color(210, 96, 18, 255)
			);

	public static ColorIndex ColorfulColor05 = new StaticColorIndex(
			new Color(112, 173, 71, 255),
			new Color(68, 114, 196, 255),
			new Color(255, 192, 0, 255),
			new Color(67, 104, 43, 255),
			new Color(38, 68, 120, 255),
			new Color(153, 115, 0, 255),
			new Color(140, 193, 104, 255),
			new Color(105, 142, 208, 255),
			new Color(255, 205, 51, 255),
			new Color(90, 138, 57, 255)
			);
	
	/**
	 * インデックスのカラーを取得する。
	 * 
	 * @param aIndex インデックス
	 * @return カラー。インデックスが範囲外の場合、<code>null</code>を返す。
	 */
	public Color get(final int aIndex);

	/**
	 * インデックス数を取得する。
	 * 
	 * @return インデックス数
	 */
	public int size();

	
	public static class StaticColorIndex implements ColorIndex {
		
		private List<Color> colors;

		public StaticColorIndex(final Color... aColors) {
			colors = new ArrayList<Color>();
			for (Color color : aColors) {
				colors.add(color);
			}
		}

		public Color get(final int aIndex) {
			if (colors.size() > aIndex) {
				return colors.get(aIndex);
			} else {
				return null;
			}
		}

		public int size() {
			return colors.size();
		}
	}
}
