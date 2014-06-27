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
package org.azkfw.chart.charts.radar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Graphics;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、レーダーチャートのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarPlot extends AbstractPlot {

	/** 軸情報 */
	private RadarAxis axis;

	/** データセット */
	private RadarDataset dataset;

	/** Looks */
	private RadarLooks looks;

	/**
	 * コンストラクタ
	 */
	public RadarPlot() {
		axis = new RadarAxis();
		dataset = null;
		looks = new RadarLooks();
	}

	public void setLooks(final RadarLooks aLooks) {
		looks = aLooks;
	}

	/**
	 * データセットを設定する。
	 * 
	 * @param aDataset データセット
	 */
	public void setDataset(final RadarDataset aDataset) {
		dataset = aDataset;
	}

	/**
	 * 軸情報を取得する。
	 * 
	 * @return 軸情報
	 */
	public RadarAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDraw(final Graphics g, final Rect aRect) {
		Size szChart = null;
		Point ptChartMiddle = null;
		szChart = new Size(aRect.getWidth(), aRect.getHeight());
		ptChartMiddle = new Point(aRect.getX() + (aRect.getWidth() / 2.f), aRect.getY() + (aRect.getHeight() / 2.f));

		// スケール調整
		ScaleValue scaleValue = getScaleValue();

		// スケール計算
		double difValue = scaleValue.getDiff();
		double pixXPerValue = (szChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (szChart.getHeight() / 2.f) / difValue;

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}
		System.out.println(String.format("Data point size : %d", dataPointSize));

		// Draw axis
		g.setColor(looks.getAxisLineColor());
		g.setStroke(looks.getAxisLineStroke());
		for (int i = 0; i < dataPointSize; i++) {
			double angle = (360.f / dataPointSize) * i + 90;
			int x = (int) (ptChartMiddle.getX() + (pixXPerValue * scaleValue.getMax() * Math.cos(RADIANS(angle))));
			int y = (int) (ptChartMiddle.getY() - (pixYPerValue * scaleValue.getMax() * Math.sin(RADIANS(angle))));
			g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (x), (int) (y));
		}

		// Draw circle
		g.setColor(looks.getAxisCircleColor());
		g.setStroke(looks.getAxisCircleStroke());
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			float[] pxs = new float[dataPointSize + 1];
			float[] pys = new float[dataPointSize + 1];
			for (int i = 0; i < dataPointSize; i++) {
				double angle = (360.f / dataPointSize) * i + 90;
				float x = (float) (ptChartMiddle.getX() + (pixXPerValue * value * Math.cos(RADIANS(angle))));
				float y = (float) (ptChartMiddle.getY() - (pixYPerValue * value * Math.sin(RADIANS(angle))));
				pxs[i] = x;
				pys[i] = y;
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];
			g.drawPolyline(pxs, pys, dataPointSize + 1);
		}

		// Draw series
		List<RadarSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			RadarSeries series = seriesList.get(index);

			List<RadarSeriesPoint> points = series.getPoints();

			float[] pxs = new float[dataPointSize + 1];
			float[] pys = new float[dataPointSize + 1];

			for (int i = 0; i < points.size(); i++) {
				RadarSeriesPoint point = points.get(i);

				double angle = -1 * (360.f / dataPointSize) * i + 90;
				double value = point.getValue();

				int x = (int) (ptChartMiddle.getX() + (pixXPerValue * value * Math.cos(RADIANS(angle))));
				int y = (int) (ptChartMiddle.getY() - (pixYPerValue * value * Math.sin(RADIANS(angle))));
				// TODO: 定数化
				if (x > 100000) {
					x = 100000;
				} else if (x < -100000) {
					x = -100000;
				}
				if (y > 100000) {
					y = 100000;
				} else if (y < -100000) {
					y = -100000;
				}
				pxs[i] = x;
				pys[i] = y;
			}
			pxs[dataPointSize] = pxs[0];
			pys[dataPointSize] = pys[0];

			Color fillColor = looks.getSeriesFillColor(index, series);
			if (null != fillColor) {
				g.setColor(fillColor);
				g.fillPolygon(pxs, pys, dataPointSize + 1);
			}
			Color strokeColor = looks.getSeriesStrokeColor(index, series);
			if (null != strokeColor) {
				g.setStroke(looks.getSeriesStroke(index, series));
				g.setColor(strokeColor);
				g.drawPolyline(pxs, pys, dataPointSize + 1);
			}
		}

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		FontMetrics fm = g.getFontMetrics(looks.getAxisFont());
		g.setColor(looks.getAxisFontColor());
		g.setFont(looks.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = scaleValue.getMin(); value <= scaleValue.getMax(); value += scaleValue.getScale()) {
			// double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - scaleValue.getMin());

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawStringA(str, (int) (ptChartMiddle.getX() - 8.f - (strWidth)), (int) (ptChartMiddle.getY() - rangeY - (fontSize / 2)));
		}

		return true;
	}

	private ScaleValue getScaleValue() {
		// データ最小値・最大値取得
		Double dataMaxValue = null;
		Double dataMinValue = null;
		if (null != dataset) {
			for (RadarSeries series : dataset.getSeriesList()) {
				for (RadarSeriesPoint point : series.getPoints()) {
					if (null == dataMaxValue) {
						dataMaxValue = point.getValue();
						dataMinValue = point.getValue();
					} else {
						dataMaxValue = (dataMaxValue < point.getValue()) ? point.getValue() : dataMaxValue;
						dataMinValue = (dataMinValue > point.getValue()) ? point.getValue() : dataMinValue;
					}
				}
			}
		}
		debug(String.format("Data minimum value : %f", dataMinValue));
		debug(String.format("Data maximum value : %f", dataMaxValue));

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double minValue = axis.getMinimumValue();
		double maxValue = axis.getMaximumValue();
		double scale = axis.getScale();
		if (axis.isMinimumValueAutoFit()) {
			if (null != dataMinValue) {
				minValue = dataMinValue;
			}
		}
		if (axis.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axis.isScaleAutoFit()) {
			double dif = maxValue - minValue;
			int logDif = (int) (Math.log10(dif));
			double scaleDif = Math.pow(10, logDif);
			if (dif >= scaleDif * 5) {
				scale = scaleDif;
			} else if (dif >= scaleDif * 2) {
				scale = scaleDif / 2;
			} else {
				scale = scaleDif / 10;
			}
		}
		ScaleValue scaleValue = new ScaleValue(minValue, maxValue, scale);
		debug(String.format("Axis minimum value : %f", minValue));
		debug(String.format("Axis maximum value : %f", maxValue));
		debug(String.format("Axis scale value : %f", scale));

		return scaleValue;
	}

	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
