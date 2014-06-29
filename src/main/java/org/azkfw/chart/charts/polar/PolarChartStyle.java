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
package org.azkfw.chart.charts.polar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.looks.legend.CustomLegendStyle;
import org.azkfw.chart.looks.title.CustomTitleStyle;
import org.azkfw.chart.style.AbstractSeriesChartStyle;

/**
 * このクラスは、極座標グラフのルックス情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/20
 * @author Kawakicchi
 */
public class PolarChartStyle extends AbstractSeriesChartStyle<PolarSeries, PolarSeriesPoint> {

	public PolarChartStyle() {
		setTitleDesign(new CustomTitleStyle());
		setLegendDesign(new CustomLegendStyle());
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
}
