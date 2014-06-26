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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Size;

/**
 * このクラスは、鶏頭図グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarAreaPlot extends AbstractPlot {

	/** X軸 */
	private PolarAreaAxis axis;

	/** データセット */
	private PolarAreaDataset dataset;

	/** Looks */
	private PolarAreaLooks looks;

	/** Margin */
	private Margin margin;

	/**
	 * コンストラクタ
	 */
	public PolarAreaPlot() {
		axis = new PolarAreaAxis();
		dataset = null;
		looks = new PolarAreaLooks();
		margin = null;
	}

	public void setLooks(final PolarAreaLooks aLooks) {
		looks = aLooks;
	}

	public void setDataset(final PolarAreaDataset aDataset) {
		dataset = aDataset;
	}

	public PolarAreaAxis getAxis() {
		return axis;
	}

	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		Size szChart = null;
		Point ptChartMiddle = null;

		// マージン適用
		if (null != margin) {
			szChart = new Size(aWidth - (margin.getLeft() + margin.getRight()), aHeight - (margin.getTop() + margin.getBottom()));
			ptChartMiddle = new Point(aX + margin.getLeft() + (szChart.getWidth() / 2.f), aY + margin.getTop() + (szChart.getHeight() / 2.f));
		} else {
			szChart = new Size(aWidth, aHeight);
			ptChartMiddle = new Point(aX + (aWidth / 2.f), aY + (aHeight / 2.f));
		}

		// データ最小値・最大値取得
		Double dataMaxValue = null;
		Double dataMinValue = null;
		if (null != dataset) {
			for (PolarAreaSeries series : dataset.getSeriesList()) {
				for (PolarAreaSeriesPoint point : series.getPoints()) {
					if (null == dataMaxValue) {
						dataMaxValue = point.getRange();
						dataMinValue = point.getRange();
					} else {
						dataMaxValue = (dataMaxValue < point.getRange()) ? point.getRange() : dataMaxValue;
						dataMinValue = (dataMinValue > point.getRange()) ? point.getRange() : dataMinValue;
					}
				}
			}
		}
		System.out.println(String.format("Data minimum value : %f", dataMinValue));
		System.out.println(String.format("Data maximum value : %f", dataMaxValue));

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
			double diff = maxValue - minValue;
			int s = (int) (Math.log10(diff));
			scale = Math.pow(10, s);
		}
		System.out.println(String.format("Axis minimum value : %f", minValue));
		System.out.println(String.format("Axis maximum value : %f", maxValue));
		System.out.println(String.format("Axis scale value : %f", scale));

		// スケール計算
		double difValue = maxValue - minValue;
		double pixXPerValue = (szChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (szChart.getHeight() / 2.f) / difValue;

		// Draw assist axis
		if (axis.isAssistAxis()) {
			g.setColor(looks.getAssistAxisLineColor());
			g.setStroke(looks.getAssistAxisLineStroke());
			for (double angle = 0.0; angle < 360.f; angle += axis.getAssistAxisAngle()) {
				if (0.0 == angle || 90.0 == angle || 180.0 == angle || 270.0 == angle) {
					// TODO: 主軸がある時のみ描画しない処理を追加
					continue;
				}
				int x = (int) (ptChartMiddle.getX() + (pixXPerValue * maxValue * Math.cos(RADIANS(angle))));
				int y = (int) (ptChartMiddle.getY() - (pixYPerValue * maxValue * Math.sin(RADIANS(angle))));
				g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (x), (int) (y));
			}
		}

		// Draw axis
		g.setColor(looks.getAxisLineColor());
		g.setStroke(looks.getAxisLineStroke());
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() - (szChart.getWidth() / 2.f)),
				(int) (ptChartMiddle.getY()));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() + (szChart.getWidth() / 2.f)),
				(int) (ptChartMiddle.getY()));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX()),
				(int) (ptChartMiddle.getY() - (szChart.getHeight() / 2.f)));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX()),
				(int) (ptChartMiddle.getY() + (szChart.getHeight() / 2.f)));

		// Draw circle
		g.setColor(looks.getAxisCircleColor());
		g.setStroke(looks.getAxisCircleStroke());
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - minValue);
			g.drawArc((int) (ptChartMiddle.getX() - rangeX), (int) (ptChartMiddle.getY() - rangeY), (int) (rangeX * 2.f), (int) (rangeY * 2.f), 0,
					360);
		}

		// Draw axis scale
		g.setColor(looks.getAxisLineColor());
		g.setStroke(new BasicStroke(1.f));
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			// double rangeY = pixYPerValue * (value - minValue);

			g.drawLine((int) (ptChartMiddle.getX() + rangeX), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() + rangeX),
					(int) (ptChartMiddle.getY() + 8.f));
		}

		// Draw series
		List<PolarAreaSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			PolarAreaSeries series = seriesList.get(index);

			List<PolarAreaSeriesPoint> points = series.getPoints();

			int angle = 360 / points.size();

			for (int i = 0; i < points.size(); i++) {
				PolarAreaSeriesPoint point = points.get(i);

				double value = point.getRange();
				if (0 == value) {
					continue;
				}

				double sWidth = pixXPerValue * value;
				double sHeight = pixXPerValue * value;
				// TODO: 定数化
				if (sWidth > 100000) {
					sWidth = 100000;
				} else if (sWidth < -100000) {
					sWidth = -100000;
				}
				if (sHeight > 100000) {
					sHeight = 100000;
				} else if (sHeight < -100000) {
					sHeight = -100000;
				}

				Color fillColor = looks.getSeriesFillColor(index, series);
				if (null != fillColor) {
					g.setColor(fillColor);
					g.fillArc((int) (ptChartMiddle.getX() - sWidth) + 1, (int) (ptChartMiddle.getY() - sHeight) + 1, (int) (sWidth * 2.f),
							(int) (sHeight * 2), i * angle, angle);
				}

				Color strokeColor = looks.getSeriesStrokeColor(index, series);
				if (null != strokeColor) {
					g.setStroke(looks.getSeriesStroke(index, series));
					g.setColor(strokeColor);
					g.drawArc((int) (ptChartMiddle.getX() - sWidth) + 1, (int) (ptChartMiddle.getY() - sHeight) + 1, (int) (sWidth * 2.f),
							(int) (sHeight * 2), i * angle, angle);
				}

			}
		}

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		FontMetrics fm = g.getFontMetrics();
		g.setColor(looks.getAxisFontColor());
		g.setFont(looks.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			// double rangeY = pixYPerValue * (value - minValue);

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawString(str, (int) (ptChartMiddle.getX() + rangeX - (strWidth / 2)), (int) (ptChartMiddle.getY() + 8.f + fontSize));
		}

		// XXX: debug
		//g.setStroke(new BasicStroke(1.f));
		//g.setColor(Color.RED);
		//g.drawRect((int) aX, (int) aY, (int) (aWidth - 1), (int) (aHeight - 1));
		return true;
	}

	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
