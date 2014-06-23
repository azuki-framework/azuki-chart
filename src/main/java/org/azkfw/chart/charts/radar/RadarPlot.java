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
import java.awt.Graphics2D;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Size;

/**
 * このクラスは、レーダーチャートのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class RadarPlot extends AbstractPlot {

	/** X軸 */
	private RadarAxis axis;

	/** データセット */
	private RadarDataset dataset;

	/** Looks */
	private RadarLooks looks;

	/** Margin */
	private Margin margin;

	/**
	 * コンストラクタ
	 */
	public RadarPlot() {
		axis = new RadarAxis();
		dataset = null;
		looks = new RadarLooks();
		margin = null;
	}

	public void setLooks(final RadarLooks aLooks) {
		looks = aLooks;
	}

	public void setDataset(final RadarDataset aDataset) {
		dataset = aDataset;
	}

	public RadarAxis getAxis() {
		return axis;
	}

	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		Size szChart = null;
		Point ptChartMiddle = null;
		if (null != margin) {
			szChart = new Size(aWidth - (margin.getLeft() + margin.getRight()), aHeight - (margin.getTop() + margin.getBottom()));
			ptChartMiddle = new Point(aX + margin.getLeft() + (szChart.getWidth() / 2.f), aY + margin.getTop() + (szChart.getHeight() / 2.f));
		} else {
			szChart = new Size(aWidth, aHeight);
			ptChartMiddle = new Point(aX + (aWidth / 2.f), aY + (aHeight / 2.f));
		}

		int cntPoint = 5;
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
			if (0 < dataset.getSeriesList().size()) {
				cntPoint = dataset.getSeriesList().get(0).getPoints().size();
			}
		}

		// XXX: range は0より大きい値を想定
		double minValue = axis.getMinimumValue();
		double maxValue = axis.getMaximumValue();
		double scale = axis.getScale();
		if (null != dataset) {
			if (axis.isMinimumValueAutoFit()) {
				minValue = dataMinValue;
			}
			if (axis.isMaximumValueAutoFit()) {
				maxValue = dataMaxValue;
			}
			if (axis.isScaleAutoFit()) {
				double diff = maxValue - minValue;
				int s = (int) (Math.log10(diff));
				scale = Math.pow(10, s);
			}
		}

		double difValue = maxValue - minValue;
		double pixXPerValue = (szChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (szChart.getWidth() / 2.f) / difValue;

		// Draw axis
		g.setColor(looks.getAxisLineColor());
		g.setStroke(looks.getAxisLineStroke());
		for (int i = 0; i < cntPoint; i++) {
			double angle = (360.f / cntPoint) * i + 90;
			int x = (int) (ptChartMiddle.getX() + (pixXPerValue * maxValue * Math.cos(RADIANS(angle))));
			int y = (int) (ptChartMiddle.getY() - (pixYPerValue * maxValue * Math.sin(RADIANS(angle))));
			g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (x), (int) (y));
		}

		// Draw circle
		g.setColor(looks.getAxisCircleColor());
		g.setStroke(looks.getAxisCircleStroke());
		for (double value = minValue; value <= maxValue; value += scale) {
			int[] pxs = new int[cntPoint + 1];
			int[] pys = new int[cntPoint + 1];
			for (int i = 0; i < cntPoint; i++) {
				double angle = (360.f / cntPoint) * i + 90;
				int x = (int) (ptChartMiddle.getX() + (pixXPerValue * value * Math.cos(RADIANS(angle))));
				int y = (int) (ptChartMiddle.getY() - (pixYPerValue * value * Math.sin(RADIANS(angle))));
				pxs[i] = x;
				pys[i] = y;
			}
			pxs[cntPoint] = pxs[0];
			pys[cntPoint] = pys[0];
			g.drawPolyline(pxs, pys, cntPoint + 1);
		}

		// Draw series
		List<RadarSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			RadarSeries series = seriesList.get(index);

			List<RadarSeriesPoint> points = series.getPoints();

			int[] pxs = new int[cntPoint + 1];
			int[] pys = new int[cntPoint + 1];

			for (int i = 0; i < points.size(); i++) {
				RadarSeriesPoint point = points.get(i);

				double angle = -1 * (360.f / cntPoint) * i + 90;
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
			pxs[cntPoint] = pxs[0];
			pys[cntPoint] = pys[0];

			Color fillColor = looks.getSeriesFillColor(index, series);
			if (null != fillColor) {
				g.setColor(fillColor);
				g.fillPolygon(pxs, pys, cntPoint + 1);
			}
			Color strokeColor = looks.getSeriesStrokeColor(index, series);
			if (null != strokeColor) {
				g.setStroke(looks.getSeriesStroke(index, series));
				g.setColor(strokeColor);
				g.drawPolyline(pxs, pys, cntPoint + 1);
			}
		}

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		FontMetrics fm = g.getFontMetrics();
		g.setColor(looks.getAxisFontColor());
		g.setFont(looks.getAxisFont());
		g.setStroke(new BasicStroke(1.f));
		for (double value = minValue; value <= maxValue; value += scale) {
			// double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - minValue);

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawString(str, (int) (ptChartMiddle.getX() - 8.f - (strWidth)), (int) (ptChartMiddle.getY() - rangeY + (fontSize / 2)));
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
