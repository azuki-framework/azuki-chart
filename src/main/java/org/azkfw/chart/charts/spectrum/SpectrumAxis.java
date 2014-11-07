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
package org.azkfw.chart.charts.spectrum;

import org.azkfw.chart.core.axis.AbstractDisplayAxis;
import org.azkfw.chart.displayformat.StringDisplayFormat;

/**
 * このクラスは、スペクトログラムの軸情報を保持するクラスです。
 * 
 * @since 1.0.0
 * @version 1.0.0 2014/07/09
 * @author Kawakicchi
 */
public class SpectrumAxis extends AbstractDisplayAxis {

	/** 最小値 */
	private double minValue;
	/** 最小値自動設定 */
	private boolean minValueAutoFit;
	/** 最大値 */
	private double maxValue;
	/** 最大値自動設定 */
	private boolean maxValueAutoFit;

	/**
	 * コンストラクタ
	 */
	public SpectrumAxis() {
		setDisplayFormat(new StringDisplayFormat());

		minValue = 0.0;
		minValueAutoFit = true;
		maxValue = 1.0;
		maxValueAutoFit = true;
	}

	/**
	 * 最小値を設定する。
	 * 
	 * @param aValue 最小値
	 */
	public final void setMinimumValue(final double aValue) {
		minValue = aValue;
	}

	/**
	 * 最小値を取得する。
	 * 
	 * @return 最小値
	 */
	public final double getMinimumValue() {
		return minValue;
	}

	/**
	 * 最小値の自動設定を設定する。
	 * 
	 * @param aAutoFit 自動設定
	 */
	public final void setMinimumValueAutoFit(final boolean aAutoFit) {
		minValueAutoFit = aAutoFit;
	}

	/**
	 * 最小値が自動設定か判断する。
	 * 
	 * @return 判断結果
	 */
	public final boolean isMinimumValueAutoFit() {
		return minValueAutoFit;
	}

	/**
	 * 最大値を設定する。
	 * 
	 * @param aValue 最大値
	 */
	public final void setMaximumValue(final double aValue) {
		maxValue = aValue;
	}

	/**
	 * 最大値を取得する。
	 * 
	 * @return 最大値
	 */
	public final double getMaximumValue() {
		return maxValue;
	}

	/**
	 * 最大値の自動設定を設定する。
	 * 
	 * @param aAutoFit
	 */
	public final void setMaximumValueAutoFit(final boolean aAutoFit) {
		maxValueAutoFit = aAutoFit;
	}

	/**
	 * 最大値が自動設定が判断する。
	 * 
	 * @return 判断結果
	 */
	public final boolean isMaximumValueAutoFit() {
		return maxValueAutoFit;
	}
}
