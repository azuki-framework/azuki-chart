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
package org.azkfw.chart.charts.scatter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.azkfw.chart.looks.legend.CustomLegendStyle;
import org.azkfw.chart.looks.title.CustomTitleStyle;
import org.azkfw.chart.style.AbstractSeriesChartStyle;

/**
 * このクラスは、散布図のルックス情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/25
 * @author Kawakicchi
 */
public class ScatterChartStyle extends AbstractSeriesChartStyle<ScatterSeries, ScatterSeriesPoint> {

	public ScatterChartStyle() {
		setTitleDesign(new CustomTitleStyle());
		setLegendDesign(new CustomLegendStyle());
	}

	/**
	 * グラフ背景色を取得する。
	 * 
	 * @return カラー
	 */
	public Color getBackgroundColor() {
		return Color.WHITE;
	}

	/**
	 * X軸のフォントを取得する。
	 * 
	 * @return フォント
	 */
	public Font getXAxisFont() {
		return new Font("Arial", Font.BOLD, 16);
	}

	/**
	 * X軸のフォントカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getXAxisFontColor() {
		return Color.BLACK;
	}

	/**
	 * X軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getXAxisLineColor() {
		return Color.BLACK;
	}

	/**
	 * X軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getXAxisLineStroke() {
		return new BasicStroke(1.f);
	}

	/**
	 * X軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getXAxisScaleColor() {
		return Color.LIGHT_GRAY;
	}

	/**
	 * X軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getXAxisScaleStroke() {
		float dash[] = { 2.0f, 2.0f };
		BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
		return dsahStroke;
	}

	/**
	 * Y軸のフォントを取得する。
	 * 
	 * @return フォント
	 */
	public Font getYAxisFont() {
		return new Font("Arial", Font.BOLD, 16);
	}

	/**
	 * Y軸のフォントカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getYAxisFontColor() {
		return Color.BLACK;
	}

	/**
	 * Y軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getYAxisLineColor() {
		return Color.BLACK;
	}

	/**
	 * Y軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getYAxisLineStroke() {
		return new BasicStroke(1.f);
	}

	/**
	 * Y軸のカラーを取得する。
	 * 
	 * @return カラー
	 */
	public Color getYAxisScaleColor() {
		return Color.LIGHT_GRAY;
	}

	/**
	 * Y軸のストロークを取得する。
	 * 
	 * @return ストローク
	 */
	public Stroke getYAxisScaleStroke() {
		float dash[] = { 2.0f, 2.0f };
		BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
		return dsahStroke;
	}
}
