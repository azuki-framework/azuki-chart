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
package org.azkfw.chart.design.chart;

import java.awt.Color;

import org.azkfw.chart.design.color.ColorIndex;

/**
 * このクラスは、チャートスタイルを定義するための基底クラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/26
 * @author Kawakicchi
 */
public abstract class AbstractChartStyle implements ChartStyle {

	private Color backgroundColor;

	private boolean overflow;

	private ColorIndex colorIndex;

	/**
	 * コンストラクタ
	 */
	public AbstractChartStyle() {
		colorIndex = ColorIndex.ColorfulColor01;
		backgroundColor = Color.WHITE;
		overflow = false;
	}

	protected final Color getColorIndex(final int aIndex) {
		return colorIndex.get(aIndex);
	}

	@Override
	public void setBackgroundColor(final Color aColor) {
		backgroundColor = aColor;
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void setOverflow(final boolean aOverflow) {
		overflow = aOverflow;
	}

	@Override
	public boolean isOverflow() {
		return overflow;
	}

}
