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
package org.azkfw.chart.charts.polararea;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import org.azkfw.chart.looks.AbstractLooks;

/**
 * このクラスは、鶏頭図のルックス情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author Kawakicchi
 */
public class PolarAreaLooks extends AbstractLooks {

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

	/**
	 * 軸のフォントを取得する。
	 * 
	 * @return フォント
	 */
	public Font getAxisFont() {
		return new Font("Arial", Font.BOLD, 16);
	}

	/**
	 * 軸のフォントカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getAxisFontColor() {
		return Color.BLACK;
	}

	/**
	 * 軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getAxisLineColor() {
		return Color.BLACK;
	}

	/**
	 * 軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getAxisLineStroke() {
		return new BasicStroke(1.f);
	}

	/**
	 * 副軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getAssistAxisLineColor() {
		return Color.LIGHT_GRAY;
	}

	/**
	 * 副軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getAssistAxisLineStroke() {
		return new BasicStroke(1.f);
	}

	/**
	 * 円のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getAxisCircleColor() {
		return Color.LIGHT_GRAY;
	}

	/**
	 * 円のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getAxisCircleStroke() {
		float dash[] = { 4.0f, 4.0f };
		BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
		return dsahStroke;
	}

	/**
	 * データのストロークを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return ストローク
	 */
	public Stroke getSeriesStroke(final int aIndex, final PolarAreaSeries aSeries) {
		return new BasicStroke(2.f);
	}

	/**
	 * データのストロークカラーを取得する。
	 * 
	 * @param aIndex データインデックス
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesStrokeColor(final int aIndex, final PolarAreaSeries aSeries) {
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
	 * @param aSeries シリーズデータ
	 * @return カラー
	 */
	public Color getSeriesFillColor(final int aIndex, final PolarAreaSeries aSeries) {
		Color color = getSeriesStrokeColor(aIndex, aSeries);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
		return color;
	}
}
