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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Rect;
import org.azkfw.graphics.Size;

/**
 * このクラスは、極座標グラフのプロット情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarPlot extends AbstractPlot {

	private PolarAxis axis;

	private PolarDataset dataset;
	
	public PolarPlot() {
		axis = new PolarAxis();
		dataset = null;
	}

	public void setDataset(final PolarDataset aDataset) {
		dataset = aDataset;
	}
	
	private String toStringValue(final double aValue) {
		return String.format("%.2f", aValue);
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float x, final float y, final float width, final float height) {
		Rect rtChart = new Rect(x, y, width, height);
		Size szChart = new Size(width, height);
		Point ptChartMiddle = new Point(x + (width / 2.f), y + (height / 2.f));

		double maxValue = axis.getMaximumValue();
		if (axis.isMaximumValueAutoFit() && null != dataset) {

		}
		double minValue = axis.getMinimumValue();
		if (axis.isMinimumValueAutoFit() && null != dataset) {

		}
		double scale = axis.getScale();

		double difValue = maxValue - minValue;
		double pixXPerValue = (szChart.getWidth() / 2.f) / difValue;
		double pixYPerValue = (szChart.getWidth() / 2.f) / difValue;

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.f));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() - (szChart.getWidth() / 2.f)),
				(int) (ptChartMiddle.getY()));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX() + (szChart.getWidth() / 2.f)),
				(int) (ptChartMiddle.getY()));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX()),
				(int) (ptChartMiddle.getY() - (szChart.getHeight() / 2.f)));
		g.drawLine((int) (ptChartMiddle.getX()), (int) (ptChartMiddle.getY()), (int) (ptChartMiddle.getX()),
				(int) (ptChartMiddle.getY() + (szChart.getHeight() / 2.f)));

		float dash[] = { 4.0f, 4.0f };
		g.setColor(Color.GRAY);
		BasicStroke dsahStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.0f, dash, 0.0f);
		g.setStroke(dsahStroke);
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - minValue);
			g.drawArc((int) (ptChartMiddle.getX() - rangeX), (int) (ptChartMiddle.getY() - rangeY), (int) (rangeX * 2.f), (int) (rangeY * 2.f), 0,
					360);
		}
		
		int fontSize = 16;
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, fontSize));
		FontMetrics fm = g.getFontMetrics();
		g.setStroke(new BasicStroke(1.f));
		for (double value = minValue; value <= maxValue; value += scale) {
			double rangeX = pixXPerValue * (value - minValue);
			double rangeY = pixYPerValue * (value - minValue);
			g.drawLine((int)(ptChartMiddle.getX()+rangeX), (int)(ptChartMiddle.getY()), (int)(ptChartMiddle.getX()+rangeX), (int)(ptChartMiddle.getY()+8.f));
			
			String str = toStringValue(value);
			int strWidth = fm.stringWidth(str);
			g.drawString(str, (int)(ptChartMiddle.getX()+rangeX-(strWidth/2)), (int)(ptChartMiddle.getY()+8.f+fontSize));
		}
		
		for (PolarSeries series : dataset.getSeriesList()) {
			List<PolarSeriesPoint> points = series.getPoints();
			
			int[] pxs = new int[points.size()+1];
			int[] pys = new int[points.size()+1];
			for (int i = 0 ; i < points.size() ;i++) {
				PolarSeriesPoint point = points.get(i);				
				pxs[i] = (int)(ptChartMiddle.getX() + pixXPerValue * point.getRange() *  Math.cos( RADIANS(point.getAngle()) ));
				pys[i] = (int)(ptChartMiddle.getY() - pixYPerValue * point.getRange() *  Math.sin( RADIANS(point.getAngle()) ));
			}
			pxs[points.size()] = pxs[0];
			pys[points.size()] = pys[0];
			
			g.setColor(new Color(0x00, 0x99,0x44, 64));
			g.fillPolygon(pxs, pys, points.size()+1);
			
			g.setColor(new Color(0x00, 0x99,0x44,255));
			g.drawPolygon(pxs, pys, points.size()+1);
		}

		// XXX: debug
		g.setStroke(new BasicStroke(1.f));
		g.setColor(Color.RED);
		g.drawRect((int) x, (int) y, (int) (width - 1), (int) (height - 1));
		return true;
	}


	protected static double RADIANS(double aAngle) {
		return aAngle * Math.PI / 180.0;
	}
}
