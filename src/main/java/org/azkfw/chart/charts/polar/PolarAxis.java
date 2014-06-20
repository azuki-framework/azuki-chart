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

import org.azkfw.chart.displayformat.DisplayFormat;

/**
 * このクラスは、極座標グラフの軸情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/06/19
 * @author Kawakicchi
 */
public class PolarAxis {

	/** 最小値 */
	private double minValue;
	/** 最小値自動設定 */
	private boolean minValueAutoFit;

	/** 最大値 */
	private double maxValue;
	/** 最大値自動設定 */
	private boolean maxValueAutoFit;

	/** 目盛 */
	private double scale;
	/** 補助目盛 */
	private double assistScale;

	private boolean assistAxis;
	private double assistAxisAngle;

	/** 表示形式 */
	private DisplayFormat displayFormat;

	/**
	 * コンストラクタ
	 */
	public PolarAxis() {
		minValue = 0.0;
		minValueAutoFit = true;

		maxValue = 1.0;
		maxValueAutoFit = true;

		scale = 0.2;
		assistScale = 0.1;

		assistAxis = true;
		assistAxisAngle = 30.f;

		displayFormat = null;
	}

	public void setMinimumValue(final double aValue) {
		minValue = aValue;
	}

	public double getMinimumValue() {
		return minValue;
	}

	public void setMinumumValueAutoFit(final boolean aAutoFit) {
		minValueAutoFit = aAutoFit;
	}

	public boolean isMinimumValueAutoFit() {
		return minValueAutoFit;
	}

	public void setMaximumValue(final double aValue) {
		maxValue = aValue;
	}

	public double getMaximumValue() {
		return maxValue;
	}

	public void setMaximumValueAutoFit(final boolean aAutoFit) {
		maxValueAutoFit = aAutoFit;
	}

	public boolean isMaximumValueAutoFit() {
		return maxValueAutoFit;
	}

	public void setScale(final double aScale) {
		scale = aScale;
	}

	public double getScale() {
		return scale;
	}

	public void setAssistScale(final double aScale) {
		assistScale = aScale;
	}

	public double getAssistScale() {
		return assistScale;
	}

	public void setAssistAxis(final boolean aAssist) {
		assistAxis = aAssist;
	}

	public boolean isAssistAxis() {
		return assistAxis;
	}

	public void setAssistAxisAngle(final double aAngle) {
		assistAxisAngle = aAngle;
	}

	public double getAssistAxisAngle() {
		return assistAxisAngle;
	}

	public void setDisplayFormat(final DisplayFormat aDisplayFormat) {
		displayFormat = aDisplayFormat;
	}

	public DisplayFormat getDisplayFormat() {
		return displayFormat;
	}
}
