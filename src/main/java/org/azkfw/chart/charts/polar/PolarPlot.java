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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Size;

/**
 * このクラスは、極座標グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarPlot extends AbstractPlot {

	/** X軸 */
	private PolarAxis axis;

	/** データセット */
	private PolarDataset dataset;

	/** Looks */
	private PolarLooks looks;

	/**
	 * コンストラクタ
	 */
	public PolarPlot() {
		axis = new PolarAxis();
		dataset = null;
		looks = new PolarLooks();
	}

	public void setLooks(final PolarLooks aLooks) {
		looks = aLooks;
	}

	public void setDataset(final PolarDataset aDataset) {
		dataset = aDataset;
	}

	public PolarAxis getAxis() {
		return axis;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		Size szChart = new Size(aWidth, aHeight);
		Point ptChartMiddle = new Point(aX + (aWidth / 2.f), aY + (aHeight / 2.f));

		double maxValue = axis.getMaximumValue();
		if (axis.isMaximumValueAutoFit() && null != dataset) {
			// TODO: 
		}
		double minValue = axis.getMinimumValue();
		if (axis.isMinimumValueAutoFit() && null != dataset) {
			// TODO: 
		}
		double scale = axis.getScale();
		// TODO: auto scale

		double difValue = maxValue - minValue;
		double pixXPerValue = (szChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (szChart.getWidth() / 2.f) / difValue;

		// Draw axis scale
		int fontSize = looks.getAxisFont().getSize();
		FontMetrics fm = g.getFontMetrics();

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
		fontSize = looks.getAxisFont().getSize();
		g.setColor(looks.getAxisFontColor());
		g.setFont(looks.getAxisFont());
		fm = g.getFontMetrics();
		g.setStroke(new BasicStroke(1.f));
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - minValue);
			g.drawLine((int) (ptChartMiddle.getX() + rangeX), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() + rangeX),
					(int) (ptChartMiddle.getY() + 8.f));

			String str = (null != axis.getDisplayFormat()) ? axis.getDisplayFormat().toString(value) : Double.toString(value);
			int strWidth = fm.stringWidth(str);
			g.drawString(str, (int) (ptChartMiddle.getX() + rangeX - (strWidth / 2)), (int) (ptChartMiddle.getY() + 8.f + fontSize));
		}

		// Draw series
		List<PolarSeries> seriesList = dataset.getSeriesList();
		for (int index = 0; index < seriesList.size(); index++) {
			PolarSeries series = seriesList.get(index);

			List<PolarSeriesPoint> points = series.getPoints();
			int[] pxs = new int[points.size() + 1];
			int[] pys = new int[points.size() + 1];
			for (int i = 0; i < points.size(); i++) {
				PolarSeriesPoint point = points.get(i);
				pxs[i] = (int) (ptChartMiddle.getX() + pixXPerValue * point.getRange() * Math.cos(RADIANS(point.getAngle())));
				pys[i] = (int) (ptChartMiddle.getY() - pixYPerValue * point.getRange() * Math.sin(RADIANS(point.getAngle())));
			}
			pxs[points.size()] = pxs[0];
			pys[points.size()] = pys[0];

			Color fillColor = looks.getSeriesFillColor(index);
			if (null != fillColor) {
				g.setColor(fillColor);
				g.fillPolygon(pxs, pys, points.size() + 1);
			}

			Color strokeColor = looks.getSeriesStrokeColor(index);
			if (null != strokeColor) {
				g.setStroke(looks.getSeriesStroke(index));
				g.setColor(strokeColor);
				g.drawPolygon(pxs, pys, points.size() + 1);
			}
		}

		// XXX: debug
		g.setStroke(new BasicStroke(1.f));
		g.setColor(Color.RED);
		//g.drawRect((int) aX, (int) aY, (int) (aWidth - 1), (int) (aHeight - 1));
		return true;
	}

	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
