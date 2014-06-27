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
package org.azkfw.chart.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * このクラスは、グラフのユーティリティクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public final class ColorPalette {

	public static ColorPalette COLORFUL01;
	public static ColorPalette COLORFUL02;
	public static ColorPalette COLORFUL03;
	public static ColorPalette COLORFUL04;
	public static ColorPalette COLORFUL05;
	
	public static ColorPalette MONOCHRO01;

	static {
		COLORFUL01 = new ColorPalette();
		COLORFUL01.add(new Color(60, 103, 154, 255));
		COLORFUL01.add(new Color(157, 61, 58, 255));
		COLORFUL01.add(new Color(125, 152, 68, 255));
		COLORFUL01.add(new Color(102, 78, 131, 255));
		COLORFUL01.add(new Color(56, 140, 162, 255));
		COLORFUL01.add(new Color(203, 120, 51, 255));
		COLORFUL01.add(new Color(74, 126, 187, 255));
		COLORFUL01.add(new Color(190, 75, 72, 255));
		COLORFUL01.add(new Color(152, 185, 84, 255));
		COLORFUL01.add(new Color(125, 96, 160, 255));
		COLORFUL01.add(new Color(70, 170, 197, 255));
		COLORFUL01.add(new Color(246, 146, 64, 255));

		COLORFUL02 = new ColorPalette();
		COLORFUL02.add(new Color(91, 155, 213, 255));
		COLORFUL02.add(new Color(237, 125, 49, 255));
		COLORFUL02.add(new Color(165, 165, 165, 255));
		COLORFUL02.add(new Color(255, 192, 0, 255));
		COLORFUL02.add(new Color(68, 114, 196, 255));
		COLORFUL02.add(new Color(112, 173, 71, 255));
		COLORFUL02.add(new Color(37, 94, 145, 255));
		COLORFUL02.add(new Color(158, 72, 14, 255));
		COLORFUL02.add(new Color(99, 99, 99, 255));
		COLORFUL02.add(new Color(153, 115, 0, 255));

		COLORFUL03 = new ColorPalette();
		COLORFUL03.add(new Color(91, 155, 213, 255));
		COLORFUL03.add(new Color(165, 165, 165, 255));
		COLORFUL03.add(new Color(68, 114, 196, 255));
		COLORFUL03.add(new Color(37, 94, 145, 255));
		COLORFUL03.add(new Color(99, 99, 99, 255));
		COLORFUL03.add(new Color(38, 68, 120, 255));
		COLORFUL03.add(new Color(124, 175, 221, 255));
		COLORFUL03.add(new Color(183, 183, 183, 255));
		COLORFUL03.add(new Color(105, 142, 208, 255));
		COLORFUL03.add(new Color(50, 125, 194, 255));

		COLORFUL04 = new ColorPalette();
		COLORFUL04.add(new Color(237, 125, 49, 255));
		COLORFUL04.add(new Color(255, 192, 0, 255));
		COLORFUL04.add(new Color(112, 173, 71, 255));
		COLORFUL04.add(new Color(158, 72, 14, 255));
		COLORFUL04.add(new Color(153, 115, 0, 255));
		COLORFUL04.add(new Color(67, 104, 43, 255));
		COLORFUL04.add(new Color(241, 151, 90, 255));
		COLORFUL04.add(new Color(255, 205, 51, 255));
		COLORFUL04.add(new Color(140, 193, 104, 255));
		COLORFUL04.add(new Color(210, 96, 18, 255));

		COLORFUL05 = new ColorPalette();
		COLORFUL05.add(new Color(112, 173, 71, 255));
		COLORFUL05.add(new Color(68, 114, 196, 255));
		COLORFUL05.add(new Color(255, 192, 0, 255));
		COLORFUL05.add(new Color(67, 104, 43, 255));
		COLORFUL05.add(new Color(38, 68, 120, 255));
		COLORFUL05.add(new Color(153, 115, 0, 255));
		COLORFUL05.add(new Color(140, 193, 104, 255));
		COLORFUL05.add(new Color(105, 142, 208, 255));
		COLORFUL05.add(new Color(255, 205, 51, 255));
		COLORFUL05.add(new Color(90, 138, 57, 255));
	}

	private List<Color> colors;

	public ColorPalette() {
		colors = new ArrayList<Color>();
	}

	public void add(final Color aColor) {
		colors.add(aColor);
	}

	public Color get(final int aIndex) {
		Color color = null;
		if (colors.size() > aIndex) {
			color = colors.get(aIndex);
		}
		return color;
	}
}
