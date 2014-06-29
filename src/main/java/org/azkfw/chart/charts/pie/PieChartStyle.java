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
package org.azkfw.chart.charts.pie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.looks.legend.CustomLegendStyle;
import org.azkfw.chart.looks.title.CustomTitleStyle;
import org.azkfw.chart.style.AbstractChartStyle;

/**
 * @since 1.0.0
 * @version 1.0.0 2014/06/22
 * @author Kawakicchi
 */
public class PieChartStyle extends AbstractChartStyle {

	private static List<Color> COLORS = new ArrayList<Color>();
	static {
		COLORS.add(new Color(60, 103, 154, 255));
		COLORS.add(new Color(157, 61, 58, 255));
		COLORS.add(new Color(125, 152, 68, 255));
		COLORS.add(new Color(102, 78, 131, 255));
		COLORS.add(new Color(56, 140, 162, 255));
		COLORS.add(new Color(203, 120, 51, 255));

		COLORS.add(new Color(74, 126, 187, 255));
		COLORS.add(new Color(190, 75, 72, 255));
		COLORS.add(new Color(152, 185, 84, 255));
		COLORS.add(new Color(125, 96, 160, 255));
		COLORS.add(new Color(70, 170, 197, 255));
		COLORS.add(new Color(246, 146, 64, 255));
	}
	
	public PieChartStyle() {
		setTitleDesign(new CustomTitleStyle());
		setLegendDesign(new CustomLegendStyle());
	}

	/**
	 * データのストロークカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @return カラー
	 */
	public Color getDataStrokeColor(final int aIndex) {
		Color color = null;
		if (aIndex < COLORS.size()) {
			color = COLORS.get(aIndex);
		} else {
			color = new Color(0, 0, 0, 255);
		}
		return color;
	}

	/**
	 * データの塗りつぶしカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @return カラー
	 */
	public Color getDataFillColor(final int aIndex) {
		Color color = getDataStrokeColor(aIndex);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
		return color;
	}
}
