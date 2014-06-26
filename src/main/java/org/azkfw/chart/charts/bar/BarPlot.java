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
package org.azkfw.chart.charts.bar;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.azkfw.chart.displayformat.DisplayFormat;
import org.azkfw.chart.plot.AbstractPlot;
import org.azkfw.graphics.Margin;
import org.azkfw.graphics.Point;
import org.azkfw.graphics.Size;

/**
 * このクラスは、棒グラフのプロットクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class BarPlot extends AbstractPlot {

	/** X軸情報 */
	private BarXAxis axisX;
	/** Y軸情報 */
	private BarYAxis axisY;

	/** データセット */
	private BarDataset dataset;

	/** Looks */
	private BarLooks looks;

	/** Margin */
	private Margin margin;

	/**
	 * コンストラクタ
	 */
	public BarPlot() {
		axisX = new BarXAxis();
		axisY = new BarYAxis();
		dataset = null;
		looks = new BarLooks();
		margin = null;
	}

	/**
	 * ルックス情報を設定する。
	 * 
	 * @param aLooks ルックス情報
	 */
	public void setLooks(final BarLooks aLooks) {
		looks = aLooks;
	}

	/**
	 * データセットを設定する。
	 * 
	 * @param aDataset データセット
	 */
	public void setDataset(final BarDataset aDataset) {
		dataset = aDataset;
	}

	/**
	 * X軸情報を設定する。
	 * 
	 * @return X軸情報
	 */
	public BarXAxis getXAxis() {
		return axisX;
	}

	/**
	 * Y軸情報を設定する。
	 * 
	 * @return Y軸情報
	 */
	public BarYAxis getYAxis() {
		return axisY;
	}

	/**
	 * マージンを設定する。
	 * 
	 * @param aMargin マージン
	 */
	public void setMargin(final Margin aMargin) {
		margin = aMargin;
	}

	@Override
	protected boolean doDraw(final Graphics2D g, final float aX, final float aY, final float aWidth, final float aHeight) {
		// データ最小値・最大値取得
		Double dataMinValue = null;
		Double dataMaxValue = null;
		if (null != dataset) {
			for (BarSeries series : dataset.getSeriesList()) {
				for (BarSeriesPoint point : series.getPoints()) {
					if (null == dataMinValue) {
						dataMinValue = point.getValue();
						dataMaxValue = point.getValue();
					} else {
						dataMinValue = (dataMinValue > point.getValue()) ? point.getValue() : dataMaxValue;
						dataMaxValue = (dataMaxValue < point.getValue()) ? point.getValue() : dataMaxValue;
					}
				}
			}
		}
		System.out.println(String.format("Data minimum value : %f", dataMinValue));
		System.out.println(String.format("Data maximum value : %f", dataMaxValue));

		// データポイント数取得
		int dataPointSize = 5;
		if (null != dataset) {
			if (0 < dataset.getSeriesList().size()) {
				dataPointSize = dataset.getSeriesList().get(0).getPoints().size();
			}
		}
		System.out.println(String.format("Data point size : %d", dataPointSize));

		// 最小値・最大値・スケール取得
		// XXX: range は0より大きい値を想定
		double minValue = axisY.getMinimumValue();
		double maxValue = axisY.getMaximumValue();
		double scale = axisY.getScale();
		if (axisY.isMinimumValueAutoFit()) {
			if (null != dataMinValue) {
				minValue = dataMinValue;
			}
		}
		if (axisY.isMaximumValueAutoFit()) {
			if (null != dataMaxValue) {
				maxValue = dataMaxValue;
			}
		}
		if (axisY.isScaleAutoFit()) {
			double diff = maxValue - minValue;
			int logMin = (int) (Math.log10(minValue));
			int logMax = (int) (Math.log10(maxValue));
			if (logMin == logMax) {
				if (Math.pow(10, logMax) < diff) {
					scale = Math.pow(10, logMax);
				} else {
					scale = Math.pow(10, logMax - 1);
				}
			} else {
				scale = Math.pow(10, logMax - 1);
			}
		}
		System.out.println(String.format("Y axis minimum value : %f", minValue));
		System.out.println(String.format("Y axis maximum value : %f", maxValue));
		System.out.println(String.format("Y axis scale value : %f", scale));

		Size szChart = null;
		Point ptChart = null;

		// マージン適用
		if (null != margin) {
			szChart = new Size(aWidth - (margin.getLeft() + margin.getRight()), aHeight - (margin.getTop() + margin.getBottom()));
			ptChart = new Point(aX + margin.getLeft(), aY + margin.getTop());
		} else {
			szChart = new Size(aWidth, aHeight);
			ptChart = new Point(aX, aY);
		}

		Margin margin = new Margin(0.f, 0.f, 0.f, 0.f);

		double difValue = maxValue - minValue;
		// スケール計算(プレ)
		double pixYPerValue = (szChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;
		{
			{
				float maxYLabelWidth = 0.0f;
				FontMetrics fm = g.getFontMetrics(looks.getYAxisFont());
				DisplayFormat df = axisY.getDisplayFormat();
				for (double value = minValue; value <= maxValue; value += scale) {
					String str = df.toString(value);
					int width = fm.stringWidth(str);
					if (maxYLabelWidth < width) {
						maxYLabelWidth = width;
					}
				}
				System.out.println(String.format("Max y axis label width : %f", maxYLabelWidth));
				margin.setLeft(maxYLabelWidth);
			}

			// スケール計算(プレ)
			pixYPerValue = (szChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;

			{
				float chartWidth = szChart.getWidth() - margin.getLeft();
				float dataWidth = chartWidth / (float) dataPointSize;
				FontMetrics fm = g.getFontMetrics(looks.getXAxisFont());
				DisplayFormat df = axisX.getDisplayFormat();
				float minLeft = 0.f;
				float maxRight = szChart.getWidth();
				for (int i = 0; i < dataPointSize; i++) {
					String str = df.toString(i);
					int width = fm.stringWidth(str);
					if (0 < width) {
						margin.setBottom(looks.getXAxisFont().getSize());

						float x = margin.getLeft() + (i * dataWidth) + (dataWidth / 2);
						float left = x - (width / 2);
						float right = x + (width / 2);
						if (minLeft > left) {
							minLeft = left;
						}
						if (maxRight < right) {
							maxRight = right;
						}
					}
				}
				if (0 > minLeft) {
					margin.setLeft(margin.getLeft() - minLeft);
				}
				if (0 < maxRight - szChart.getWidth()) {
					margin.setRight(maxRight - szChart.getWidth());
				}
			}

			// スケール計算(プレ)
			pixYPerValue = (szChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;

			{
				int fontHeight = looks.getYAxisFont().getSize();
				DisplayFormat df = axisY.getDisplayFormat();
				float minTop = 0.f;
				for (double value = minValue; value <= maxValue; value += scale) {
					float y = (float) (szChart.getHeight() - margin.getBottom() - pixYPerValue * (value - minValue));
					String str = df.toString(value);
					if (0 < str.length()) {
						y -= fontHeight / 2;
						if (minTop > y) {
							minTop = y;
						}
					}
				}
				if (0 > minTop) {
					margin.setTop(-1 * minTop);
				}
			}

			// スケール計算(Fix)
			pixYPerValue = (szChart.getHeight() - (margin.getTop() + margin.getBottom())) / difValue;

			System.out.println(String.format("Margin : Left:%f Right:%f Top:%f Bottom:%f", margin.getLeft(), margin.getRight(), margin.getTop(),
					margin.getBottom()));
		}

		float x = ptChart.getX() + margin.getLeft();
		float y = ptChart.getY() + szChart.getHeight() - margin.getBottom();
		float width = szChart.getWidth() - (margin.getLeft() + margin.getRight());
		float height = szChart.getHeight() - (margin.getTop() + margin.getBottom());

		// Draw Y axis
		g.setColor(looks.getYAxisLineColor());
		g.setStroke(looks.getYAxisLineStroke());
		g.drawLine((int) (x), (int) (y), (int) (x), (int) (y - height));
		{
			int fontSize = looks.getYAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(looks.getYAxisFont());
			DisplayFormat df = axisY.getDisplayFormat();
			g.setColor(looks.getYAxisFontColor());
			g.setFont(looks.getYAxisFont());
			for (double value = minValue; value <= maxValue; value += scale) {
				String str = df.toString(value);
				float strWidth = fm.stringWidth(str);
				int yLabel = (int) (y - ((value - minValue) * pixYPerValue) + (fontSize / 2));
				int xLabel = (int) (x - strWidth);
				g.drawString(str, xLabel, yLabel);
			}
		}

		// Draw X axis
		g.setColor(looks.getXAxisLineColor());
		g.setStroke(looks.getXAxisLineStroke());
		g.drawLine((int) (x), (int) (y), (int) (x + width), (int) (y));
		{
			float dataWidth = width / dataPointSize;
			int fontSize = looks.getXAxisFont().getSize();
			FontMetrics fm = g.getFontMetrics(looks.getXAxisFont());
			DisplayFormat df = axisX.getDisplayFormat();
			g.setColor(looks.getXAxisFontColor());
			g.setFont(looks.getXAxisFont());
			for (int i = 0; i < dataPointSize; i++) {
				String str = df.toString(i);
				float strWidth = fm.stringWidth(str);
				int yLabel = (int) (y + fontSize);
				int xLabel = (int) (x + (i * dataWidth) + (dataWidth / 2) - (strWidth / 2));
				g.drawString(str, xLabel, yLabel);
			}
		}

		return true;
	}

}
